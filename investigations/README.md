# ADB Modules App — Investigation Directory

Investigation artifacts for the ADB Modules App research program.

**Status: Active.** Established by Phase 0 — Investigation Infrastructure.

---

## 1. What this directory is

This directory holds the working artifacts of the investigation program
defined by `PLAN.md` (Phases 0–26).

It is **not** an implementation directory. No production source code belongs
here, and no file here may modify application behaviour.

---

## 2. Authority

| Document | Authority |
| --- | --- |
| `AGENTS.md` | Agent behavior and project rules |
| `PLAN.md` | Authoritative investigation scope, checklist, and phase order |
| `INVESTIGATION_METHOD.md` | Investigation methodology and evidence standards |
| `ARCHITECTURE.md` | Current **proposed** architecture (changeable) |
| `MasterRef.md` | Consolidated project reference — **protected during Phases 0–24** |
| `INVESTIGATION.md` | Original pre-MasterRef investigation; historical reference |
| `investigations/METHOD.md` | Operational standard implementing `INVESTIGATION_METHOD.md` for this directory |

`PLAN.md` and `INVESTIGATION_METHOD.md` govern. This directory never overrides
them. Where this directory and those documents conflict, the conflict is
recorded in `contradictions.md` rather than resolved silently.

---

## 3. Layout

```
investigations/
├── README.md                    This file — structure and conventions
├── METHOD.md                    Operational method: evidence, confidence,
│                                versions, contradictions, completion criteria
├── INDEX.md                     One row per phase — status, report, sources
├── STATUS.md                    Current program position and transitions
├── sources.md                   Central source register + naming hazards
├── contradictions.md            Central contradiction register
├── unknowns.md                  Central unknowns register
├── templates/
│   ├── PHASE-REPORT.md          Required phase report structure
│   ├── EVIDENCE-LEDGER.md       Per-phase claim → source ledger
│   ├── CONTRADICTION.md         Central contradiction entry format
│   ├── UNKNOWN.md               Central unknown entry format
│   ├── SOURCE-RECORD.md         Central source entry format
│   ├── ANDROID-COMPATIBILITY-MATRIX.md
│   └── BACKEND-COMPATIBILITY-MATRIX.md   (Porter + Shizuku)
└── phase-NN/
    ├── REPORT.md                The phase investigation report
    └── evidence.md              The phase evidence ledger
```

`phase-NN/` directories are created when that phase begins work. Empty stub
directories are not pre-created. The numbering follows `PLAN.md` exactly
(Phase 0 … Phase 26).

---

## 4. Conventions

1. **Phase numbering** matches `PLAN.md` verbatim. No renumbering, no phases
   outside 0–26.
2. **Reports are immutable in direction, not in content.** A later phase may
   append dated notes to an earlier phase report; it must not rewrite the
   earlier phase's conclusions. Corrections are recorded as `HISTORICAL`,
   `SUPERSEDED`, or `CONTRADICTED` with the superseding evidence.
3. **Central registers are updated with the phase report**, in the same
   commit. `INDEX.md` and `STATUS.md` reflect committed state, not intent.
4. **Every substantive claim carries a classification** from `METHOD.md` §3:
   `VERIFIED`, `DOCUMENTED`, `OBSERVED`, `INFERRED`, `PROPOSED`, `LEAD`,
   `UNKNOWN`.
5. **Every version-sensitive claim carries version context** and a currency
   marker (`CURRENT`, `HISTORICAL`, `VERSION-SPECIFIC`, `UNKNOWN-CURRENCY`).
6. **Contradictions and unknowns are outcomes, not failures.** They are
   recorded in the central registers and referenced from the phase report.
7. **MasterRef is read-only** until Phase 25 audits it and Phase 26
   incorporates verified findings.
8. **No implementation.** `investigations/` never contains production code.

---

## 5. Working a phase

1. Read `PLAN.md` for the phase checklist. The checklist is authoritative.
2. Define scope and translate each checklist item into a concrete, answerable
   question (`METHOD.md` §5 of `INVESTIGATION_METHOD.md`).
3. Record sources in `sources.md` as they are used, with reliability level.
4. Inspect implementation evidence (L1) before relying on documentation.
5. Collect evidence into `phase-NN/evidence.md`.
6. Write `phase-NN/REPORT.md` using `templates/PHASE-REPORT.md`, including the
   checklist coverage table.
7. Append contradictions and unknowns to the central registers.
8. Update `INDEX.md` and `STATUS.md`.
9. Self-audit the phase against `PLAN.md` and `INVESTIGATION_METHOD.md`.
10. Only then set the status. If criteria are unmet, use `Research Complete`,
    `Awaiting Verification`, or `Blocked` — not `Complete`.

---

## 6. Status values

Permitted values only:

```
Not Started | In Progress | Research Complete | Awaiting Verification |
Audited | Complete | Blocked
```

Definitions and the criteria for each are in `METHOD.md` §15.

---

## 7. Quick reference — evidence labels

| Label | One-line meaning |
| --- | --- |
| `VERIFIED` | Confirmed from primary implementation evidence (L1) |
| `DOCUMENTED` | Stated by an authoritative source (L2), not independently confirmed |
| `OBSERVED` | Confirmed by a controlled test |
| `INFERRED` | Derived from evidence, not directly established |
| `PROPOSED` | A design choice, not a statement of fact |
| `LEAD` | A discovery pointer only † |
| `UNKNOWN` | Insufficient evidence |

† `LEAD` and the `None` confidence level are local extensions of this
directory, defined and justified in `METHOD.md` §3 and §4. They add no
permissiveness: a `LEAD` is never evidence and `None` is never permitted on a
`VERIFIED` or `DOCUMENTED` claim.

---

## 8. Quick reference — reliability levels

| Level | Name |
| --- | --- |
| L1 | Primary Implementation Evidence |
| L2 | Official Documentation |
| L3 | Official Project Discussion |
| L4 | High-Quality Secondary Source |
| L5 | Community Source |
| L6 | Search Snippet (lead only, never evidence) |

---

## 9. Rules that are easy to break

- Do not skip a `PLAN.md` checklist item. Record it as unresolved instead.
- Do not invent a source, a version, an API, or a finding.
- Do not present a proposal as existing behaviour.
- Do not merge Package / Backend / Privilege / Policy / Trust / Runtime State.
- Do not resolve a contradiction by deleting evidence.
- Do not close an unknown with an assumption.
- Do not edit `MasterRef.md`.
- Do not begin implementation.
- Do not start another phase from inside a phase.