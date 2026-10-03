# ADB Modules App — Investigation Method (Operational Standard)

**Status: Active project standard**

**Phase that established this document:** Phase 0 — Investigation Infrastructure

**Authority:** This document operationalizes `INVESTIGATION_METHOD.md` for the
`investigations/` directory. It does not replace `INVESTIGATION_METHOD.md`,
`PLAN.md`, or `AGENTS.md`. Where this document and `INVESTIGATION_METHOD.md`
conflict, `INVESTIGATION_METHOD.md` governs and the conflict is recorded in
`investigations/contradictions.md`.

**Canonical phase structure:** Phases 0–26 as defined by `PLAN.md`. This
document must never introduce, remove, renumber, or reorder a phase.

---

## 1. Purpose

Every phase report in this program must be:

- evidence-driven,
- traceable to a recorded source,
- version-aware,
- explicit about uncertainty,
- explicit about contradictions,
- explicit about the difference between what exists, what is documented,
  what was observed, what is inferred, and what is merely proposed.

This document defines the vocabulary and formats that make those properties
checkable by an auditor who did not perform the investigation.

---

## 2. Source Reliability Levels

Sources are recorded with a reliability level. A source's level constrains how
strongly it may be used, but does not by itself determine whether a claim is
true. A Level 6 source may still be correct; it simply cannot be the sole
basis for a `VERIFIED` claim.

| Level | Name | Description | Typical use |
| --- | --- | --- | --- |
| L1 | Primary Implementation Evidence | Source code, exact tagged release, exact commit, actual platform implementation, controlled device experiment | Default basis for behavioural claims |
| L2 | Official Documentation | Official API/developer/project documentation, compatibility documentation, release notes | Contract and intended behaviour |
| L3 | Official Project Discussion | Official issue trackers, official discussions, maintainer statements, migration notes | Context; must be read with version and intent |
| L4 | High-Quality Secondary Source | Reputable technical documentation, detailed engineering articles, established references | Corroboration, orientation |
| L5 | Community Source | Forums, Reddit, community threads, personal blogs, user reports | Leads and edge-case discovery only |
| L6 | Search Snippet | Search-result summaries | Discovery of leads only; never evidence |

### 2.1 Rules

1. A claim classified `VERIFIED` must rest on at least one L1 source, or on
   two independent L2 sources that agree, or on an L1 source plus an L2 source.
2. A Level 5 or Level 6 source may never be the sole support for a claim
   classified `VERIFIED`.
3. When only L2 is available, the claim is `DOCUMENTED`, never `VERIFIED`.
4. When only L5/L6 is available, the claim is `LEAD` (see §5) and is recorded
   as an unknown until a higher-level source is obtained.
5. Search engines are used to discover sources. The source itself is the
   evidence.

---

## 3. Evidence Classification

Each substantive claim in a phase report carries exactly one of these
classifications. Classifications are never merged.

| Label | Meaning |
| --- | --- |
| `VERIFIED` | Confirmed directly through strong evidence (normally L1) |
| `DOCUMENTED` | Explicitly stated by an authoritative source (normally L2) but not independently verified |
| `OBSERVED` | Confirmed through a controlled test or experiment |
| `INFERRED` | Reasonably derived from evidence but not directly established |
| `PROPOSED` | A potential architecture or implementation choice that is not a statement of fact |
| `UNKNOWN` | Insufficient evidence to establish an answer |
| `LEAD` † | A discovery lead only; not yet supported by adequate evidence |

† `LEAD` is a **local extension** of this directory. `INVESTIGATION_METHOD.md`
§11 defines six classifications; `LEAD` is added because §6 treats search
snippets and community sources as leads that are usable but not evidentiary.
A `LEAD` is always recorded in the evidence ledger's lead sections (§E of
`templates/EVIDENCE-LEDGER.md`) and is never counted as a finding.

### 3.1 The documentation / implementation distinction

`DOCUMENTED` and `VERIFIED` answer different questions and must never be
collapsed:

- `DOCUMENTED` — *"the official documentation states X."*
- `VERIFIED` — *"the implementation at commit C does X."*

When both are recorded, they are recorded as two separate statements, and any
divergence becomes a contradiction (§8) rather than a silent preference for
one of them.

### 3.2 The architecture distinction

`ARCHITECTURE.md` is a hypothesis. Reports must classify architecture
statements as one of:

| Label | Meaning |
| --- | --- |
| `EXISTING` | What the source code currently implements, proven from L1 evidence |
| `DOCUMENTED-ARCH` | Architecture described by an authoritative external document |
| `CONCEPTUAL` | A model used to reason about the system; not implemented anywhere |
| `PROPOSED` | The current proposal in `ARCHITECTURE.md` |
| `CONTRADICTED` | An `EXISTING` finding that the proposal does not account for |

A `PROPOSED` statement must never be written in a way that could be read as
`EXISTING`. When a report says "the architecture does X", it must say
`PROPOSED (ARCHITECTURE.md §N)` and, where relevant, `EXISTING (path:line)`.

---

## 4. Confidence

Confidence reflects evidence quality, not conviction.

| Confidence | Meaning |
| --- | --- |
| High | Directly evidenced; no material unresolved conflict |
| Medium | Well supported but resting on partial, indirect, or version-scoped evidence |
| Low | Plausible; meaningful gaps remain |
| None † | Effectively unknown |

† `None` is a **local extension**. `INVESTIGATION_METHOD.md` §12 defines
`High`, `Medium`, and `Low` only. `None` is permitted solely for `UNKNOWN` and
retired `LEAD` entries, where `INVESTIGATION_METHOD.md` §12's three levels would
otherwise misrepresent an entry with no supporting evidence. A `VERIFIED` or
`DOCUMENTED` claim may never carry `None`.

Every `INFERRED` claim is capped at `Medium`. Every `LEAD` is capped at `Low`.
Every `UNKNOWN` is `None`.

---

## 5. Leads

A `LEAD` is a specific, actionable pointer to something worth investigating
(for example, "Shevery stores modules under `files/adb_modules/<id>`").

Leads exist so that discovery is not lost, and they must be promoted by a
later phase or explicitly retired. Leads are never reported as findings.

---

## 6. Version Context and Currency

### 6.1 Mandatory version fields

Any version-sensitive finding must state the context it applies to. At minimum:

- Android version / API level
- Porter version
- Shizuku version
- Shevery version or commit
- Rootless Store version or commit
- Backend version where the finding is backend-specific
- Date the evidence was collected

When a version cannot be established, record `Unknown`. Never invent a version
and never infer a version from a neighbouring value.

### 6.2 Currency classification

Every version-sensitive finding is marked:

| Marker | Meaning |
| --- | --- |
| `CURRENT` | Verified against the current release/HEAD at the date recorded |
| `HISTORICAL` | Accurate for an identified earlier version; must not be restated as current |
| `VERSION-SPECIFIC` | Only true for the stated version(s) |
| `UNKNOWN-CURRENCY` | Currency cannot be established |

### 6.3 Marking outdated information

Information becomes outdated rather than simply wrong. When a later phase
finds that a previously recorded statement no longer holds:

1. Do not delete the earlier statement.
2. Mark it `HISTORICAL` or `SUPERSEDED`, with the superseding evidence.
3. Add a dated note to the affected phase report.
4. Record the change in `investigations/STATUS.md` under the phase it affects.
5. Register it in `investigations/contradictions.md` if the two statements were
   previously treated as current and mutually exclusive.

The `SUPERSEDED` marker is used only when the earlier statement was *wrong*.
`HISTORICAL` is used when the earlier statement was *correct for its time*.

---

## 7. Version-Specific Recording Rules

These three rules implement the Phase 0 requirement that Android, Porter and
Shizuku findings be recorded consistently.

### 7.1 Android-version-specific findings

The project compatibility range is Android 12, 13, 14, 15, 16, 17.

1. Every finding that depends on platform behaviour is recorded per Android
   version using the support markers in
   `templates/ANDROID-COMPATIBILITY-MATRIX.md`.
2. Findings are **not** generalized across versions without evidence. A
   version gap is recorded as `Unknown`, not assumed identical.
3. A behaviour observed on one device/OS build is recorded as
   `OBSERVED (device: <model>, Android <v>)` and is not promoted to universal
   Android behaviour.
4. `targetSdk`/`minSdk`/`compileSdk` of this project must be stated whenever an
   Android behaviour claim could depend on them.
5. Platform-restriction claims (background execution, foreground services,
   notification permission, storage, package visibility, battery) must name the
   API level that introduced the restriction.

### 7.2 Porter-version-specific findings

1. Every Porter claim must name the exact Porter version, artifact, and
   commit, or be recorded as `Unknown`.
2. A claim about Porter must state **which artifact** it describes (the
   Android app, a client library, a service, or a documented protocol).
   "Porter" is not a single artifact; see naming hazards **H-001** in
   `investigations/sources.md`.
3. Porter capabilities must be classified as `VERIFIED`, `DOCUMENTED`,
   `INFERRED`, `CONCEPTUAL`, or `UNKNOWN` — the five categories required by
   `AGENTS.md` §11. No Porter API may be described as existing without an L1 or
   L2 source for that exact version.
4. Shizuku-shaped assumptions must not be transferred to Porter. If a Porter
   claim is derived from Shizuku behaviour it is `INFERRED` and must be
   labelled as such.
5. Backend identity must never be recorded as implying a privilege level.
   Privilege is recorded separately (§9).

### 7.3 Shizuku compatibility findings

1. Every Shizuku claim must name the Shizuku client API version, the server
   implementation in use (upstream Shizuku, a fork, or another implementation),
   and that implementation's version.
2. `window.Shizuku` and other compatibility surfaces are recorded as
   *compatibility obligations*, never as proof of a backend's internal
   architecture.
3. Where the compatibility surface and the underlying implementation diverge,
   both are recorded and the divergence becomes a contradiction.
4. Shizuku API-level differences (permission request, `bindUserService`,
   `UserServiceArgs`, content provider authority, intent filters) are recorded
   per Shizuku version and per Android version where they interact.
5. Shizuku privilege is recorded as depending on how the Shizuku server was
   started; that relationship is evidence, not an assumption.

---

## 8. Conflicting Sources

### 8.1 Procedure

1. Record both claims, each with its source, level, version, and date.
2. Compare versions and dates. Determine whether the conflict is historical.
3. Inspect source code where available.
4. Perform a controlled experiment where appropriate and record the device
   matrix (§10).
5. Record a resolution status: `Resolved`, `Partially resolved`, or
   `Unresolved`.
6. Preserve residual disagreement. A conflict is never closed by deleting the
   losing claim.
7. Selecting the newest-looking source without investigation is prohibited.

### 8.2 Contradiction record format

Every significant contradiction is appended to
`investigations/contradictions.md` and cross-referenced from the phase report,
using `templates/CONTRADICTION.md`:

```
## Contradiction <ID> — <short title>
Phase:      <phase>
Severity:   Blocking | Material | Minor
Claim A:    <statement>
  Source / level / version / date / evidence location
Claim B:    <statement>
  Source / level / version / date / evidence location
Version difference:
Possible explanation:
Resolution:  Resolved | Partially resolved | Unresolved
Interpretation adopted (and why):
Residual uncertainty:
Impact:
Action owner / next phase:
```

A contradiction is **Blocking** when it prevents an architectural decision
from being made safely.

---

## 9. Concept Separation Rule

Findings about the following concepts must never be merged, and every relevant
report section must keep them separate:

```
Package  ≠  Backend  ≠  Privilege  ≠  Policy  ≠  Trust  ≠  Runtime State
```

- **Package** — what is executed.
- **Backend** — how execution is provided.
- **Privilege** — the authority the resulting process actually has.
- **Policy** — what the package is permitted to do.
- **Trust** — the user's explicit trust decision.
- **Runtime State** — what is happening now.

Reporting a backend's availability as evidence of privilege, or trust as
evidence of policy permission, is a reporting defect.

Additional non-equivalences that must stay explicit:
`Installed ≠ Enabled ≠ Running`, `Backend failure ≠ Module failure`,
`Service availability ≠ Service process health`,
`PID ≠ durable execution identity`, `Source trust ≠ Execution trust`.

---

## 10. Experiment and Device Matrix Standard

Any experiment records:

```
Device / manufacturer / model
Android version and API level
App version / commit
Backend + backend version
Dependency versions
Configuration / permissions / trust state
Network state / battery state / background state
Procedure
Expected result
Observed result
Logs or artifacts
Limitations
```

A single experiment does not establish universal behaviour. Experiment
results are `OBSERVED`, not `VERIFIED`, unless the experiment is accompanied by
a platform-level or source-level explanation.

---

## 11. Phase Report Format

Every phase produces, at minimum, `investigations/phase-NN/REPORT.md` following
`templates/PHASE-REPORT.md`, plus an `evidence.md` ledger. Additions are
allowed where the phase requires them; removals of required sections are not.

Required section order (§35 of `INVESTIGATION_METHOD.md`):

1. Scope
2. Questions to Answer
3. Primary Sources
4. Secondary Sources
5. Existing Implementation Evidence
6. Compatibility Findings
7. Security Findings
8. Architecture Implications
9. Unknowns
10. Contradictions
11. Verified Conclusions
12. Recommendations for MasterRef Expansion
13. Sources / References
14. Items Requiring Future Investigation

Each report additionally carries:

- A **checklist coverage table** mapping every `PLAN.md` checklist item for the
  phase to the section that addresses it and the resulting classification.
- A **self-audit** section, present before the phase is marked `Audited` or
  `Complete`.

### 11.1 File conventions

- Phase directories are created when the phase begins work; empty stub
  directories are not created in advance.
- `REPORT.md` — the phase report.
- `evidence.md` — the phase evidence ledger.
- Additional phase files (matrices, extracts) are allowed and should be
  referenced from `REPORT.md`.
- Central registers live at the `investigations/` root and are updated in the
  same commit as the phase report.

---

## 12. Evidence Ledger Format

Significant claims are recorded in `investigations/phase-NN/evidence.md` using
`templates/EVIDENCE-LEDGER.md`:

```
ID | Claim | Source (+level) | Version / Commit | Evidence location |
Evidence type | Confidence | Status | Notes
```

A claim that reaches the central `investigations/sources.md` register must also
appear in the phase ledger. The phase ledger is the authority for the claim;
the source register is the authority for the source.

---

## 13. Unknowns Register Format

`investigations/unknowns.md` is the central register. Each entry:

```
ID | Unknown | Phase raised | Why unknown | Evidence already checked |
What would resolve it | Priority | Status | Cross-phase dependencies
```

Rules:

- An unknown is never closed by substituting an assumption.
- Unknowns raised in a phase survive that phase's completion.
- Closing an unknown records who resolved it, with which evidence, and on what
  date.
- Cross-phase dependencies are stated explicitly so later phases do not
  invent answers to unblock progress.

Priority values: `Blocking`, `High`, `Medium`, `Low`.

`Blocking` is a **priority value, not a program gate**. It means the unknown
prevents a specific architectural decision from being made safely — the same
sense as `Blocking` severity for contradictions (§8). It does **not** mean the
investigation program, the current phase, or the next phase is halted. Each
`Blocking` unknown must therefore state its scope in the entry's `Blocks` field,
and any summary that counts `Blocking` unknowns must say so in those terms
rather than reporting a bare count.

---

## 14. Source Register Format

`investigations/sources.md` is the central register of external technologies.
Each entry records the fields required by §7 of `INVESTIGATION_METHOD.md`:

```
Project | Role in this project | Source URL | Version | Branch/tag/commit |
Documentation | Relevant files | License | Date checked | Reliability |
Phases using this source | Notes
```

The register must additionally record **naming hazards** — cases where a
project name used in this repository collides with an unrelated public
project — because ambiguous names cause fabricated sources.

---

## 15. Status Vocabulary

Only these phase statuses are permitted (`INVESTIGATION_METHOD.md` §43):

```
Not Started | In Progress | Research Complete | Awaiting Verification |
Audited | Complete | Blocked
```

Rules:

- `Research Complete` requires the report to exist in full.
- `Audited` requires the self-audit to be complete and every `PLAN.md`
  checklist item for the phase to be mapped.
- `Complete` requires every §16 criterion to be satisfied.
- `Blocked` requires an explicit statement of what is missing and why.
- A phase is never advanced to the next phase while its own criteria are unmet
  unless the next phase does not depend on it, and that dependency is recorded.

---

## 16. Phase Completion Criteria

A phase may be marked `Complete` only when all of the following hold
(`PLAN.md` "Investigation Completion Rule" and `INVESTIGATION_METHOD.md` §42):

1. Every applicable `PLAN.md` checklist item for the phase is addressed, and
   each is mapped in the report's checklist coverage table.
2. Each checklist item resolves to at least one legitimate outcome: verified
   answer, documented answer, verified limitation, verified incompatibility,
   verified historical behaviour, or an explicit unresolved question.
3. Primary sources have been reviewed and recorded.
4. Relevant implementation evidence has been inspected (L1) where it exists.
5. Important claims carry version context.
6. Android-, Porter- and Shizuku-version-specific findings follow §7.
7. Contradictions are documented in the central register.
8. Unknowns are documented in the central register.
9. Compatibility findings are documented as
   *"compatible with X under conditions A, B, C; behaviour D unverified"*
   rather than as bare "supports X".
10. Security implications are documented, including abuse cases.
11. Architecture implications are documented using §3.2 labels, and every
    conflict with the current proposal is recorded as `CONTRADICTED`.
12. Recommendations for MasterRef expansion are recorded **as
    recommendations only**. `MasterRef.md` is not modified during Phases 0–24.
13. The report has been self-audited against `PLAN.md` and
    `INVESTIGATION_METHOD.md`.
14. Remaining limitations are explicitly recorded.

---

## 17. MasterRef Incorporation Criteria

`MasterRef.md` is protected during Phases 0–24. Nothing in this program may
edit it except through the controlled process below.

### 17.1 During Phases 0–24

- MasterRef is read-only reference material.
- Findings go to the phase report and the central registers.
- Section 12 of each report records *recommended* MasterRef changes.

### 17.2 Phase 25 — Audit

Phase 25 compares completed findings against MasterRef and produces a
concrete change list: missing, outdated, incorrect, unsupported, duplicated,
contradictory, overly conceptual, insufficiently sourced. Audit identifies
changes; it does not apply them.

### 17.3 Phase 26 — Incorporation

A finding is eligible for incorporation only when:

1. It appears in a phase report.
2. Its evidence classification is `VERIFIED`, `DOCUMENTED`, `OBSERVED`, or
   `INFERRED` **with its label preserved** in the incorporated text.
3. Its version context is stated or its absence is explicitly recorded.
4. It is not contradicted by an unresolved contradiction.
5. It is not an unlabelled `PROPOSED` statement presented as fact.
6. It retains its uncertainty. Uncertainty is preserved, not smoothed away.

Each incorporation records: investigation, finding, evidence, current MasterRef
section, required change, reason, and resulting classification.

`PROPOSED`, `UNKNOWN`, `HISTORICAL`, and future-facing material may be
incorporated **only** when explicitly labelled as such.

### 17.4 Prohibited

- Incorporating an assumption as a fact.
- Removing material merely because new information exists.
- Resolving a contradiction by deletion.
- Converting a conceptual section into a claim of implementation.
- Incorporating a proposal as an existing capability.

---

## 18. Index and Status Tracking

- `investigations/INDEX.md` — one row per phase: number, name, status, report
  path, primary sources, major unknowns, major contradictions, MasterRef
  impact.
- `investigations/STATUS.md` — current program position, the active phase, the
  most recent status transitions, and blocking items.
- Both are updated in the same commit as the phase report they describe.

### 18.1 Register mirrors

`unknowns.md` and `contradictions.md` are authoritative. `STATUS.md` §4–§5 and
the `INDEX.md` register summary are **mirrors** of them, and `INDEX.md`'s own
"Register summary" rules state this.

A mirror is a copy, not a restatement. Rows must be copied rather than
paraphrased; a mirror may drop or add a column but may not add, drop, reword, or
reorder a row relative to its register. Any phase that appends to a register
must update every mirror in the same change. A mirror missing a row is a
defect, not a formatting preference.

These rules are enforced by `investigations/tools/check_register_mirrors.py`,
run from the repository root:

```sh
python3 investigations/tools/check_register_mirrors.py
```

It exits 0 when every mirror matches, 1 on drift, and prints the file, ID,
column, register value, and mirror value for each mismatch. Usage, exit codes,
and what the comparison deliberately ignores are documented in `INDEX.md` §
"Register mirror check".

---

## 19. MasterRef Protection

During Phases 0–24 the following are prohibited:

- Editing `MasterRef.md`.
- Silently correcting `ARCHITECTURE.md` to match a finding.
- Deleting a contradicting statement without recording it.
- Marking a phase complete to unblock the next phase.

When a finding conflicts with existing documentation, the conflict is recorded
(`INVESTIGATION_METHOD.md` §40). The document is corrected only through its
proper phase: findings through Phase 26 for MasterRef, and an explicit
architecture decision for `ARCHITECTURE.md`.

---

## 20. Change Discipline for This Document

This document may be amended when:

- `PLAN.md` or `INVESTIGATION_METHOD.md` changes in a way that alters the
  method;
- a real investigation demonstrates that a format here is insufficient;
- a later phase needs a record type that does not yet exist.

Any amendment must state which phase required it and why, and must not relax an
existing standard silently. Amendments that relax an evidence or completion
standard require an explicit, recorded decision.