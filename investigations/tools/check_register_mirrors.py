#!/usr/bin/env python3
"""Check that every designated register mirror matches its authoritative register.

investigation tooling only. not production code.

The register is authoritative. The mirrors are copies. A mirror that drops,
reorders, adds, or rewords a register row is a defect, because a reader of the
mirror forms a wrong picture of the program's open-question surface.

This script verifies, for each register/mirror pair:

  1. row set equality  - every register ID appears in the mirror, and the
     mirror contains no ID the register does not define.
  2. field equality    - the shared columns hold identical cell text.

Mirrors may legitimately drop a column (STATUS.md and INDEX.md omit the
register's "Blocks Phase 1?") or add one. Only shared columns are compared.

Usage
-----
    python3 investigations/tools/check_register_mirrors.py
    python3 investigations/tools/check_register_mirrors.py --root .

Exit codes
----------
    0  every mirror matches its register
    1  drift found; each mismatch is printed with file, ID, field,
       expected value, and actual value
    2  a register or mirror could not be located or parsed
"""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path

ID_RE = re.compile(r"^(?:U|C)-\d+$")


# A table is identified by the columns it must contain. These signatures are
# unique within the register/mirror set: "Priority" appears only in the unknown
# tables, "Severity" only in the contradiction tables. STATUS.md section 7 is a
# separate scoping table for U-001 and is deliberately excluded by construction.
Table = dict[str, dict[str, str]]


class ParseError(Exception):
    pass


def split_row(line: str) -> list[str]:
    return [c.strip() for c in line.strip().strip("|").split("|")]


def is_separator(cells: list[str]) -> bool:
    return bool(cells) and all(c and set(c) <= set("-: ") for c in cells)


def collect(path: Path, spec: dict) -> Table:
    """Collect every row whose first cell is an ID, from tables that contain
    all of `required` in their header.

    Header handling, two rules:

    1. The last recognised header is remembered across prose and blank lines, so
       a continuation table that omits its header row is still attributed to the
       right schema. This matters in practice - the "Phase 1 additions" block in
       unknowns.md carries no repeated header, so a parser that only read
       labelled tables would silently see 6 register rows instead of 11 and
       report every Phase 1 unknown as "not in the register".
    2. A header row that does *not* satisfy `required` clears the remembered
       header, so a different table elsewhere in the file cannot have its rows
       misattributed to this schema. This is what keeps STATUS.md section 7 (the
       U-001 scoping table) out of the contradictions pass even though it
       happens to contain a "Status" column.
    """
    required = spec["required"]
    omittable = spec.get("omittable", ())
    rows: Table = {}
    header: list[str] | None = None
    for lineno, line in enumerate(path.read_text(encoding="utf-8").splitlines(), 1):
        if not line.lstrip().startswith("|"):
            # prose or blank line: keep the remembered header (rule 1)
            continue
        cells = split_row(line)
        if is_separator(cells):
            continue
        if "ID" in cells:
            header = cells if required <= set(cells) else None
            continue
        if not cells or not ID_RE.match(cells[0]):
            continue
        if header is None:
            continue
        # A continuation row may omit trailing columns, but only columns the
        # register declared omittable. unknowns.md's "Phase 1 additions" block
        # legitimately drops "Blocks Phase 1?"; any other narrowing means a row
        # was truncated or malformed and must not be silently mis-mapped.
        omitted = header[len(cells):]
        allowed = omittable
        if omitted and tuple(omitted) != tuple(allowed):
            raise ParseError(
                f"{path}:{lineno}: row {cells[0]!r} is missing required "
                f"column(s) {list(omitted)}; only {list(allowed) or 'no column'} "
                f"may be omitted from a continuation row"
            )
        if len(cells) > len(header):
            raise ParseError(
                f"{path}:{lineno}: row {cells[0]!r} has {len(cells)} cells but "
                f"the last recognised header has only {len(header)}"
            )
        record = dict(zip(header[: len(cells)], cells))
        previous = rows.get(cells[0])
        if previous is not None and previous != record:
            raise ParseError(
                f"{path}:{lineno}: {cells[0]} defined twice with conflicting values"
            )
        rows[cells[0]] = record
    return rows


# --------------------------------------------------------------------------
# Designation table. Adding a mirror means adding one line here.
# --------------------------------------------------------------------------

REGISTERS = {
    "U": {
        "path": "unknowns.md",
        "required": {"Priority", "Short description"},
        # register field -> the columns a mirror is expected to copy
        "fields": ("Short description", "Priority", "Owning phase"),
        # the "Phase 1 additions" block omits this trailing column
        "omittable": ("Blocks Phase 1?",),
        "mirrors": [
            ("STATUS.md", {"Priority", "Short description"}),
            ("INDEX.md", {"Priority", "Short description"}),
        ],
    },
    "C": {
        "path": "contradictions.md",
        "required": {"Severity", "Status"},
        # a mirror may name the register's "Title" column "Short description"
        "aliases": {"Title": "Short description"},
        "fields": ("Title", "Severity", "Status", "Owning phase"),
        "mirrors": [
            ("STATUS.md", {"Severity"}),
            ("INDEX.md", {"Severity"}),
        ],
    },
}


def normalize(cell: str) -> str:
    """Strip presentational markdown emphasis.

    Bolding is presentation, not content: the register writes `Unresolved` while
    a mirror may write `**Unresolved**` or `***Unresolved***` to draw the eye.
    Requiring identical emphasis markers would force mirrors to drop their
    highlighting and would flag cosmetic differences as semantic drift, so all
    emphasis characters are removed before comparison. Note the register is
    itself inconsistent about emphasis - C-002 is plain where C-005 is bold -
    which is precisely why this is normalised rather than mandated.
    """
    return cell.replace("*", "").strip()


def resolve(record: dict[str, str], column: str, aliases: dict[str, str]) -> str | None:
    """Fetch `column` from `record`, trying the mirror's alias first."""
    for name in (aliases.get(column, column), column):
        if name in record:
            return record[name]
    return None


def check_pair(root: Path, spec: dict, mirror_name: str, mirror_required: set[str],
               prefix: str, errors: list[str], missing: list[str]) -> tuple[int, int]:
    reg = collect(root / spec["path"], spec)
    mir = collect(root / mirror_name, {**spec, "required": mirror_required})
    aliases = spec.get("aliases", {})
    fields = spec["fields"]

    for id_ in sorted(set(reg) - set(mir)):
        errors.append(f"{mirror_name}: missing row {id_} (present in {spec['path']})")
    for id_ in sorted(set(mir) - set(reg)):
        errors.append(f"{mirror_name}: unknown row {id_} (not in {spec['path']})")

    compared = 0
    for id_ in sorted(set(reg) & set(mir)):
        for field in fields:
            expected = reg[id_].get(field)
            actual = resolve(mir[id_], field, aliases)
            if expected is None:
                continue
            if actual is None:
                errors.append(
                    f"{mirror_name}: {id_} has no '{field}' column "
                    f"(register value {expected!r})"
                )
                continue
            compared += 1
            if normalize(expected) != normalize(actual):
                column = aliases.get(field, field)
                errors.append(
                    f"{mirror_name}: {id_} column '{column}' drifted\n"
                    f"    register {spec['path']}: {expected!r}\n"
                    f"    mirror  {mirror_name}: {actual!r}"
                )
    return len(reg), compared


def main(argv: list[str] | None = None) -> int:
    ap = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    ap.add_argument("--root", default=str(Path(__file__).resolve().parent.parent),
                    help="the investigations/ directory (default: alongside this script)")
    args = ap.parse_args(argv)

    root = Path(args.root).resolve()
    errors: list[str] = []
    missing: list[str] = []

    print(f"register mirror check: {root}")
    print("mirrors must match their authoritative register exactly on shared columns.\n")

    for prefix, spec in REGISTERS.items():
        reg_path = root / spec["path"]
        if not reg_path.is_file():
            missing.append(f"register not found: {reg_path}")
            continue
        for mirror_name, mirror_required in spec["mirrors"]:
            mirror_path = root / mirror_name
            if not mirror_path.is_file():
                missing.append(f"mirror not found: {mirror_path}")
                continue
            try:
                n_rows, n_cells = check_pair(
                    root, spec, mirror_name, mirror_required, prefix, errors, missing
                )
            except ParseError as exc:
                missing.append(str(exc))
                continue
            label = "unknowns" if prefix == "U" else "contradictions"
            print(f"  PASS  {mirror_name:<18} {label:<15} "
                  f"{n_rows} rows, {n_cells} cells compared")
        print()

    if missing:
        print("PARSE FAILURES", file=sys.stderr)
        for m in missing:
            print(f"  ERROR {m}", file=sys.stderr)
    if errors:
        print(f"DRIFT: {len(errors)} problem(s)", file=sys.stderr)
        for e in errors:
            print(f"  ERROR {e}", file=sys.stderr)
    if missing or errors:
        print("\nFAIL: mirrors have drifted from their registers.", file=sys.stderr)
        return 1
    print("PASS: all mirrors match their registers.")
    return 0


if __name__ == "__main__":
    sys.exit(main())