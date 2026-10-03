# ADB Modules App — Investigation Index

One row per phase of the `PLAN.md` program (Phases 0–26). Phase order and names
are verbatim from `PLAN.md` and must not be changed here.

**Standard:** `investigations/METHOD.md`
**Last updated:** 2026-10-02 (Phase 2)

Status vocabulary: `Not Started | In Progress | Research Complete |
Awaiting Verification | Audited | Complete | Blocked`

---

## Phase index

| Phase | Name | Status | Report | Started | Completed | Primary sources | Major unknowns | Major contradictions | MasterRef impact |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 0 | Investigation Infrastructure | Complete | [REPORT](phase-00/REPORT.md) | 2026-10-02 | 2026-10-02 | Local repo + project docs (`L1`, `L2`) | U-001, U-002, U-003, U-004, U-005, U-006 | C-002 open; C-001 and C-004 retracted | Infrastructure only; no MasterRef content change recommended |
| 1 | Rootless Store Deep Investigation | Complete | [REPORT](phase-01/REPORT.md) | 2026-10-02 | 2026-10-02 | Local repo source at `main` @ `4b35c36` (L1) + git history; no network sources needed | U-007, U-008, U-009, U-010, U-011 | C-005, C-006 open; C-007 refuted; C-008 corrected | 8 recommendations queued for Phase 25; **no MasterRef edit** |
| 2 | Shevery ADB Module Compatibility Investigation | Complete | [REPORT](phase-02/REPORT.md) | 2026-10-02 | 2026-10-02 | Shevery @ `bfc55ce9` + Nightzuku @ `60a8feb6` (L1, cloned); Shevery docs (L2, 4 points contradicted) | U-012…U-018; U-003/U-005/U-006 **resolved** | C-002 resolved; C-009, C-010, C-011, C-012, C-013 open; C-005 quantified | 8 recommendations queued for Phase 25; **no MasterRef edit**. Premise correction: origin is Nightzuku, not Porter |
| 3 | ADB Module Lifecycle | Not Started | — | — | — | — | — | — | — |
| 4 | Porter Investigation | Not Started | — | — | — | — | — | — | — |
| 5 | Shizuku Compatibility Investigation | Not Started | — | — | — | — | — | — | — |
| 6 | Execution Abstraction Investigation | Not Started | — | — | — | — | — | — | — |
| 7 | Security Investigation | Not Started | — | — | — | — | — | — | — |
| 8 | WebUI Investigation | Not Started | — | — | — | — | — | — | — |
| 9 | Background Services Investigation | Not Started | — | — | — | — | — | — | — |
| 10 | Android Compatibility Investigation | Not Started | — | — | — | — | — | — | — |
| 11 | Storage Investigation | Not Started | — | — | — | — | — | — | — |
| 12 | Catalog / Source Investigation | Not Started | — | — | — | — | — | — | — |
| 13 | Updates and Rollback | Not Started | — | — | — | — | — | — | — |
| 14 | Runtime Recovery Investigation | Not Started | — | — | — | — | — | — | — |
| 15 | Logging / Diagnostics | Not Started | — | — | — | — | — | — | — |
| 16 | UI / UX Investigation | Not Started | — | — | — | — | — | — | — |
| 17 | Testing Investigation | Not Started | — | — | — | — | — | — | — |
| 18 | Licensing / Provenance Investigation | Not Started | — | — | — | — | — | — | — |
| 19 | Performance Investigation | Not Started | — | — | — | — | — | — | — |
| 20 | Reliability Investigation | Not Started | — | — | — | — | — | — | — |
| 21 | Interoperability Investigation | Not Started | — | — | — | — | — | — | — |
| 22 | Architecture Stress Testing | Not Started | — | — | — | — | — | — | — |
| 23 | Future Backend Investigation | Not Started | — | — | — | — | — | — | — |
| 24 | Future Module Ecosystem Investigation | Not Started | — | — | — | — | — | — | — |
| 25 | MasterRef Expansion Audit | Not Started | — | — | — | — | — | — | Owns the MasterRef change list |
| 26 | MasterRef Incorporation | Not Started | — | — | — | — | — | — | The only phase that edits `MasterRef.md` |

---

## Phase dependency map

Recorded so that a later phase does not invent answers to unblock itself.

```
Phase 0  Infrastructure
   │
   ▼
Phase 1  Rootless Store ──┬──► Phase 6  Execution Abstraction ◄── Phase 4 Porter
                          │                     ▲
Phase 2  Shevery ─────────┼──► Phase 3 Lifecycle│
                          │                     ▲
                          └──► Phase 14 Runtime Recovery
                                     ▲
Phase 5  Shizuku ─────────┘
                                     │
Phase 7  Security ──┬──► Phase 8 WebUI
                    └──► Phase 16 UI/UX

Phases 9–13, 15, 17–21 consume findings from 1–8 and 14
Phase 22 stress-tests findings from 1–21
Phases 23–24 are future-facing and must not create current requirements
Phase 25 audits MasterRef against 0–24
Phase 26 incorporates verified findings into MasterRef
Architecture Confirmation / Revision follows Phase 26 and is NOT Phase 27
```

---

## Artifact index

| Path | Contents |
| --- | --- |
| `README.md` | Directory purpose, layout, conventions |
| `METHOD.md` | Operational method and standards |
| `INDEX.md` | This file |
| `STATUS.md` | Current program position and transitions |
| `sources.md` | Source register + naming hazards |
| `contradictions.md` | Contradiction register |
| `unknowns.md` | Unknowns register |
| `templates/PHASE-REPORT.md` | Phase report structure |
| `templates/EVIDENCE-LEDGER.md` | Phase evidence ledger structure |
| `templates/CONTRADICTION.md` | Contradiction entry format |
| `templates/UNKNOWN.md` | Unknown entry format |
| `templates/SOURCE-RECORD.md` | Source entry format |
| `templates/ANDROID-COMPATIBILITY-MATRIX.md` | Android 12–17 matrix format |
| `templates/BACKEND-COMPATIBILITY-MATRIX.md` | Porter + Shizuku matrix format |
| `phase-00/REPORT.md` | Phase 0 report |
| `phase-00/baseline.md` | Verified project baseline (SDK, modules, dependencies) |
| `phase-00/evidence.md` | Phase 0 evidence ledger |
| `phase-01/REPORT.md` | Phase 1 report (Rootless Store deep investigation) |
| `phase-01/evidence.md` | Phase 1 evidence ledger (42 verified findings) |
| `phase-02/REPORT.md` | Phase 2 report (Shevery ADB Module compatibility) |
| `phase-02/evidence.md` | Phase 2 evidence ledger (111 rows) |
| `tools/check_register_mirrors.py` | Register mirror check — see §"Register mirror check" |

---

## Register summary

### Open unknowns

| ID | Short description | Priority | Status | Owning phase |
| --- | --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Blocking (for backend design) | Open | 4 |
| U-002 | Upstream Rootless Store repository state and license | Medium | Open | 18 |
| U-003 | Reference Shevery commit for the ADB Module compatibility contract | High | **Resolved** (Phase 2) | — |
| U-004 | Shizuku server implementation/version available to users | High | Open | 5 |
| U-005 | Whether `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract | High | **Resolved** (Phase 2) | — |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is or is not the ADB Module feature | High | **Resolved** (Phase 2); residual design question → 11 | 11 |
| U-007 | Real-world impact of the database version reset 5 → 1 in commit `efab664` | High | Open | 14 |
| U-008 | Source authentication, integrity, and update-discovery controls beyond the unauthenticated GET | High | Open | 12 |
| U-009 | Whether the UI prevents executing a plugin whose `isEnabled` is false | Medium | Open | 3 |
| U-010 | What `illusioncube` is and what its presence in `data`/`application` implies | Medium | Open | 6 |
| U-011 | Whether any non-default `PluginState` value is ever produced by any writer | High | Open | 3 |
| U-012 | Whether Nightzuku and Shevery will converge or diverge further | Medium | Open | 21 |
| U-013 | Whether any format-stability or deprecation policy exists or will be published | Medium | Open | 21 |
| U-014 | Whether third-party module authors depend on the Shevery-only fallbacks (`run.sh`, `main.sh`, `exec.sh`, `late_start.sh`) | Medium | Open | 12 |
| U-015 | Android-version-specific extraction and `setExecutable` behaviour | Medium | Open | 10 |
| U-016 | `IShizukuService.newProcess` env-merge semantics — does the child inherit the server's environment? | Medium | Open | 5 |
| U-017 | Whether any real module depends on `AXERON`/`AXERONVER` or the `su` shim behaviour | Low | Open | 12 |
| U-018 | Intended script invocation form and working directory for this project | High | Open | 6 |

Open unknowns: **15** (was 11). 15 are `Open`; 3 are `**Resolved** (Phase 2)`
and are listed only to keep this mirror row-identical to the register. None is
`Accepted-as-unknown`.

**Resolved in Phase 2: U-003, U-005, U-006.** They are removed from this mirror
and the §5 mirror in `STATUS.md` because a resolved entry is not an open unknown;
their resolution blocks remain in `unknowns.md` per its maintenance rule 1
(`INVESTIGATION_METHOD.md` §45).

`Blocking` is a priority value, not a program gate (`METHOD.md` §13). U-001 is
`Open` and blocks only Phases 6, 10, 22 and 23. It does not block Phase 3.
Full entries: [`unknowns.md`](unknowns.md).

### Contradictions

| ID | Short description | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-001 | Alleged 26 vs 27 phase-count conflict | Minor | **Retracted** — claim not substantiated | — |
| C-002 | ADB Module storage: internal app-managed vs external Magisk-compat paths | Material | **Resolved** (Phase 2) | — (resolved; storage contract → 11) |
| C-003 | README states Phase 0 is not complete; Phase 0 is complete | Minor | Resolved — README corrected in Phase 0 | — |
| C-004 | Alleged `module.prop` format conflation in `ARCHITECTURE.md` | Minor | **Retracted** — misattributed section, scope is correct | — |
| C-005 | `ARCHITECTURE.md` assumes manifest/archive validation stages the implementation lacks | Material | **Unresolved** (quantified in Phase 2) | 2, 7 |
| C-006 | `PluginManifest.kt` documents field requirements that no code enforces | Minor | **Unresolved** | 3, 7 |
| C-007 | Alleged missing `CodeBrickEntity` migration causing a crash | Material | **Refuted** — post-reset baseline already contained the entity | — |
| C-008 | Subagents disagreed on extraction Zip-Slip coverage (4/5 vs 2/10) | Minor | **Corrected** — 1 of 9 protected; 1 reachable, 4 dead | — |
| C-009 | The two official docs disagree on whether `full` mode permits web network | Material | **Unresolved** | 7 |
| C-010 | Nightzuku and Shevery enforce identical limits by different, non-equivalent means | Material | **Unresolved** | 21 |
| C-011 | The official API doc contradicts its own source on four points | Material | **Unresolved** | — (informational) |
| C-012 | Derived-from-Nightzuku provenance is unacknowledged on-platform | Minor | **Unresolved** | 18 |
| C-013 | Package identity and release-asset naming changed across versions | Minor | **Unresolved** | 13 |

Open contradictions: **7** (C-005, C-006, C-009, C-010, C-011, C-012, C-013).
Blocking contradictions: **0** throughout. Retained for audit: 2 retracted
(C-001, C-004), 1 refuted (C-007), 1 corrected (C-008), **1 resolved** (C-002).
Full entries: [`contradictions.md`](contradictions.md).

C-011 is intentionally left open: its four divergences are settled in favour of
source, but this project does not edit a third party's documentation, so the
conflict persists for every future reader of that doc.

---

## Mirror-table maintenance rule

`INDEX.md` §"Register summary" and `STATUS.md` §4–§5 are **mirrors** of
`unknowns.md` and `contradictions.md`. The register is authoritative.

Rules:

1. Rows must be copied, not paraphrased. ID, short description, priority or
   severity, status, and owning phase must match the register exactly.
2. Mirrors may add columns or notes; they may not add, drop, reword, or reorder
   rows relative to the register.
3. Any phase that appends to a register **must** update every mirror in the same
   change. A mirror missing a row is a defect, not a formatting preference.
4. `Blocking` is a priority/severity value, not a program gate (`METHOD.md` §13,
   `contradictions.md` §8). Any mirror or summary that counts `Blocking` entries
   must state the scope in those terms.

These rules are enforced, not merely stated. See §"Register mirror check".

---

## Register mirror check

`tools/check_register_mirrors.py` is investigation tooling, not production code.
It verifies that each designated mirror matches its authoritative register on
row set and on every shared column, and exits non-zero with a specific message
when they drift.

Run it from the repository root:

```sh
python3 investigations/tools/check_register_mirrors.py
```

Add `--root <dir>` to point it at a copy of `investigations/` instead.

Exit codes:

| Code | Meaning |
| --- | --- |
| 0 | every mirror matches its register |
| 1 | drift found: missing row, extra row, or a changed cell — each printed with file, ID, column, and both values |
| 1 | a register or mirror file is missing, or a table row is malformed — reported under `PARSE FAILURES` |

What it compares:

| Register | Authoritative | Mirrors | Columns compared |
| --- | --- | --- | --- |
| Unknowns | `unknowns.md` | `STATUS.md` §4, `INDEX.md` | Short description, Priority, Owning phase |
| Contradictions | `contradictions.md` | `STATUS.md` §5, `INDEX.md` | Title, Severity, Status, Owning phase |

Intended behaviour, so the check is not misread:

- **Emphasis is ignored.** The register writes `Unresolved`; a mirror may write
  `**Unresolved**`. Bold is presentation, not content, so `*` is stripped before
  comparison.
- **A mirror may drop or add columns.** The unknown mirrors omit the register's
  `Blocks Phase 1?`. Only shared columns are compared. The one exception is a
  row narrowed by more than that single declared-omittable column, which is
  treated as malformed rather than silently mis-mapped.
- **A headerless continuation table is read.** The "Phase 1 additions" blocks
  are real register rows; a checker that only read labelled tables would see 6
  unknowns instead of 11 and wrongly report U-007…U-011 as unregistered.
- **`STATUS.md` §7 is out of scope.** The U-001 scoping table is a different
  schema and is excluded by construction.

To designate a further mirror, add one line to `REGISTERS` in the script. There
is no test suite; the check is self-testing by construction and was validated
against injected drift (dropped row, reworded cell, invented row, missing
register, truncated row, emphasis-only difference, legitimate omission).

---

## MasterRef status

`MasterRef.md` is protected during Phases 0–24.

| Item | State |
| --- | --- |
| Last modification to `MasterRef.md` by this program | None |
| MasterRef audit | Deferred to Phase 25 |
| MasterRef incorporation | Deferred to Phase 26 |
| Recommended changes recorded so far | `phase-00/REPORT.md` §12 (infrastructure only); `phase-01/REPORT.md` §12 (8 recommendations — validation absence, the one hardened path, `ExecutionContext` overloading, backend divergence table, persistence history, declared-but-not-operational fields, WebUI bridge facts, absent `window.Shizuku`) |
