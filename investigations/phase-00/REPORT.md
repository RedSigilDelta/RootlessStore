# ADB Modules App — Phase 0 — Investigation Report

| Field | Value |
| --- | --- |
| Phase | **0 — Investigation Infrastructure** |
| Status | `Complete` |
| Started | 2026-10-02 |
| Completed | 2026-10-02 |
| Depends on | None |
| Feeds | Every phase (1–26) |
| Repository state | `main` @ `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba` |
| Evidence ledger | [`evidence.md`](evidence.md) |
| Baseline | [`baseline.md`](baseline.md) |
| Method | [`../METHOD.md`](../METHOD.md) |
| Leads to next phase | Yes — Phase 1 requires `investigations/METHOD.md` and this directory's formats |

---

## 1. Scope

### 1.1 Phase definition (verbatim from `PLAN.md:52-66`)

Phase 0 — Investigation Infrastructure:

1. Define the standard investigation-document format
2. Define source reliability levels
3. Define how conflicting sources are handled
4. Define how implementation evidence is distinguished from documentation
5. Define how conceptual architecture is distinguished from verified architecture
6. Define how outdated information is marked
7. Define how Android-version-specific findings are recorded
8. Define how Porter-version-specific findings are recorded
9. Define how Shizuku compatibility findings are recorded
10. Define investigation completion criteria
11. Define MasterRef incorporation criteria
12. Create investigation index
13. Create investigation status tracking

### 1.2 What Phase 0 is

Phase 0 establishes the *method and infrastructure* for the investigation
program. It produces formats, registers, vocabulary, and a status mechanism. It
does **not** produce findings about Rootless Store, Shevery, Porter, Shizuku, or
Android.

### 1.3 What Phase 0 is not

- Not an implementation phase. No production code was added or modified.
- Not a MasterRef phase. `MasterRef.md` was not touched (see §7.3).
- Not an architecture-revision phase. `ARCHITECTURE.md` was not modified. Where
  Phase 0 found conflict, the conflict was recorded, not resolved.
- Not a substitute for any later phase. Every external technology question is
  explicitly deferred to its owning phase.

### 1.4 Technologies in scope

Only the project's own repository and its documentation. External technologies
(Shevery, Porter, Shizuku server, Android platform) appear in Phase 0 **only** as
registered leads and unknowns.

### 1.5 Version context

| Item | Version / commit | Date checked |
| --- | --- | --- |
| This repository | `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba` on `main` | 2026-10-02 |
| App version | `2.3.1` (code 2) | 2026-10-02 |
| AGP / Kotlin | `9.2.1` / `2.4.0` | 2026-10-02 |
| Shizuku client API | `13.1.5` | 2026-10-02 |
| libsu | `core` `6.0.0` | 2026-10-02 |
| Porter | **Unknown** — see U-001 | 2026-10-02 |
| Shevery | **Unknown** — see U-003 | 2026-10-02 |
| Android | Not assessed — Phase 0 records only this project's SDK configuration | 2026-10-02 |

### 1.6 Limitations of this scope

1. Phase 0 has no external primary evidence. Every external technology is a
   `LEAD`.
2. `MasterRef.md` was **not** read line by line. Phase 0 therefore makes no claim
   that MasterRef is consistent with anything. The Phase 25 audit owns that
   comparison.
3. `ARCHITECTURE.md` was read selectively — only the sections cited in this
   report. No claim is made about unread sections.
4. No build, lint, or test was executed.
5. Two claims raised early in Phase 0 were found to be unsupported and were
   retracted during the self-audit (C-001, C-004). They are retained in the
   registers as an audit trail.

---

## 2. Questions to Answer

Phase 0's checklist is definitional, so the questions are: what must be defined,
who is the authority for each definition, and how will compliance be checked?

| # | Checklist item | Question | Answered? |
| --- | --- | --- | --- |
| Q1 | Investigation-document format | What structure must every phase report and every record type follow, and how does it map to `INVESTIGATION_METHOD.md` §35? | Yes — §5.1 |
| Q2 | Source reliability levels | What are the levels, what may each support, and what is recorded per source? | Yes — §5.2 |
| Q3 | Conflicting sources | What is the procedure, the record format, the severity model, and the closure rule? | Yes — §5.3 |
| Q4 | Implementation vs documentation | What labels distinguish verified implementation from documented, observed, inferred, and proposed behaviour? | Yes — §5.4 |
| Q5 | Conceptual vs verified architecture | What labels distinguish existing, documented, conceptual, proposed, and contradicted architecture? | Yes — §5.5 |
| Q6 | Outdated information | What markers record currency, and what is the procedure when a later phase invalidates an earlier statement? | Yes — §5.6 |
| Q7 | Android-version recording | How are Android 12–17 differences recorded without generalization? | Yes — §5.7 |
| Q8 | Porter-version recording | How are Porter findings version-pinned given the artifact is not yet identified? | Yes — §5.8 |
| Q9 | Shizuku compatibility recording | How are Shizuku client/server/fork distinctions and the `window.Shizuku` compatibility surface recorded? | Yes — §5.9 |
| Q10 | Completion criteria | What must be true before a phase may be marked `Audited` or `Complete`? | Yes — §5.10 |
| Q11 | MasterRef incorporation criteria | What makes a finding eligible for MasterRef, and what is prohibited during Phases 0–24? | Yes — §5.11 |
| Q12 | Investigation index | Where is the program index, and what does it track? | Yes — §5.12 |
| Q13 | Investigation status tracking | Where is status recorded, and what vocabulary and transition rules apply? | Yes — §5.13 |

---

## 3. Primary Sources

Level 1 — primary implementation evidence actually inspected.

| Source | Level | Version / commit | Location inspected | Used for |
| --- | --- | --- | --- | --- |
| This repository (build configuration) | L1 | `4b35c36b` | `settings.gradle.kts`, `app/build.gradle.kts`, `gradle/libs.versions.toml`, all `build.gradle.kts` | Verified baseline: modules, SDK levels, dependencies, absence of Porter (§5.14, `baseline.md`) |
| This repository (production source) | L1 | `4b35c36b` | `data/src/main/aidl/IShellService.aidl`, `IShellCallback.aidl`; `data/.../shizuku/**`; libsu-importing files | Verified baseline: AIDL surface, Shizuku file inventory, root-shell path |
| This repository (documentation in-repo) | L1 | `4b35c36b` | `LICENSE`, `README.md`, `docs/code-style.md`, `docs/storage-model.md` | License, fork relationship, status text, storage documentation (contradiction C-002) |
| Repository metadata | L1 | `4b35c36b` | `git log -1`, `git remote -v`, `git status --porcelain` | Commit, date, remote, working-tree state |
| Environment probe | L1 | n/a | `rg` availability check | Tooling note (`baseline.md` §8) |

No external (non-repository) primary source was inspected in Phase 0.

---

## 4. Secondary Sources

No Level 3, 4, 5, or 6 source is used as evidence anywhere in Phase 0.

Search reconnaissance was performed to identify external technologies well enough
to register them without fabricating details. All results are `LEAD`-class and
recorded in `evidence.md` §E and `sources.md` §Naming hazards:

| Lead | Level | Promoted to evidence? | Recorded as |
| --- | --- | --- | --- |
| Shizuku fork under package `eu.darken.porter` | L6 | No | P0-E01, hazard H-001, unknown U-001 |
| Unrelated `porter.run` deployment platform | L6 | No | P0-E02, hazard H-001 |
| `HmnDev-Tech/shevery` with `docs/adb-modules-guide.md`, `docs/adb-modules-api.md` | L6 | No | P0-E03, unknown U-003 |
| `zax4r0/shevery` | L6 | No | P0-E04, unknown U-003 |
| A Shizuku-compatible fork emulating `dev.rikka.shizuku` authorities | L6 | No | P0-E05, hazard H-002, unknown U-004 |
| Third-party framework listing a Porter-named Shizuku fork | L6 | No | P0-E06, unknown U-001 |

**Project documents are treated separately.** `AGENTS.md`, `PLAN.md`,
`INVESTIGATION_METHOD.md`, `ARCHITECTURE.md`, `INVESTIGATION.md`,
`MasterRef.md`, and `README.md` are registered as `S-003` and are Level 2 for
statements of project *intent*. They are not evidence of external or runtime
behaviour (`evidence.md` §B note).

---

## 5. Implementation Evidence — the Phase 0 definitions

This section is the substance of Phase 0: it states each definition, its
authority, and where it is implemented on disk. Full text is in
[`../METHOD.md`](../METHOD.md); the mapping is here.

### 5.1 Standard investigation-document format — checklist item 1

**Authority:** `INVESTIGATION_METHOD.md` §35 (14 numbered sections), §34 (evidence
ledger), §45/§46/§47 (registers), §48 (efficiency), `AGENTS.md` §18, §19.

**Rule:** every phase produces `investigations/phase-NN/REPORT.md` in the section
order of `INVESTIGATION_METHOD.md` §35 plus an `evidence.md` ledger. Sections may
be extended; they may not be removed.

**Implemented in:**

| Artifact | Purpose |
| --- | --- |
| [`../METHOD.md`](../METHOD.md) §11 | Report format, section order, file conventions |
| [`../templates/PHASE-REPORT.md`](../templates/PHASE-REPORT.md) | Report skeleton with all 14 sections, checklist coverage appendix, self-audit |
| [`../templates/EVIDENCE-LEDGER.md`](../templates/EVIDENCE-LEDGER.md) | Claim → source ledger, with sections for verified / documented / discussion / secondary / leads / inferences / proposals / unknowns / contradictions / experiments / retirements |
| [`evidence.md`](evidence.md) | Phase 0 ledger, populated as the worked example |

### 5.2 Source reliability levels — checklist item 2

**Authority:** `INVESTIGATION_METHOD.md` §6 (L1–L6), §7 (per-source fields),
§47 (register contents), `AGENTS.md` §20.

**Rule:** L1 primary implementation → L2 official documentation → L3 official
project discussion → L4 high-quality secondary → L5 community → L6 search
snippet. A level constrains how strongly a source may be used. L5/L6 can never
be the sole support for a `VERIFIED` claim; L2-only evidence yields `DOCUMENTED`,
never `VERIFIED`.

**Implemented in:** `../METHOD.md` §2 and §2.1; [`../sources.md`](../sources.md)
(entries `S-001`–`S-008` plus naming hazards `H-001`/`H-002`);
[`../templates/SOURCE-RECORD.md`](../templates/SOURCE-RECORD.md).

**Two additions, both documented as local extensions and both recorded as
proposals** (`evidence.md` P0-G01, P0-G03):

1. A mandatory **naming hazards** section, because "Porter" and "Shizuku" each
   collide with unrelated public projects and ambiguous names are a fabrication
   source.
2. `LEAD` as an explicit label so search reconnaissance cannot be mistaken for a
   finding.

### 5.3 Conflicting sources — checklist item 3

**Authority:** `INVESTIGATION_METHOD.md` §13 (nine-step procedure), §14
(contradiction record fields), §46 (register), `AGENTS.md` §17, `PLAN.md:28`.

**Rule:** record both claims with source, level, version, and date; compare
versions and dates; inspect source where available; test where appropriate;
record a resolution status (`Resolved` / `Partially resolved` / `Unresolved`);
preserve residual disagreement. A contradiction is never closed by deleting the
losing claim or by preferring the newer-looking source.

**Implemented in:** `../METHOD.md` §8; [`../contradictions.md`](../contradictions.md);
[`../templates/CONTRADICTION.md`](../templates/CONTRADICTION.md).

**Severity model added** so `STATUS.md` can escalate correctly: `Blocking`
(prevents a safe architectural decision) > `Material` (affects a conclusion, with
a safe interim reading) > `Minor` (documentation accuracy).

**Retraction is a permitted, recorded outcome.** A claim that cannot be
substantiated is marked `Retracted` with the check that disproved it, and the
entry is retained. Phase 0 exercised this twice (§9).

### 5.4 Implementation evidence vs documentation — checklist item 4

**Authority:** `INVESTIGATION_METHOD.md` §10 (five behaviour categories), §11
(six evidence classifications), §12 (confidence), `AGENTS.md` §16, §22, §33.

**Rule:** `DOCUMENTED` means *"the official documentation states X."* `VERIFIED`
means *"the implementation at commit C does X."* They are recorded as two
separate statements and any divergence becomes a contradiction rather than a
silent preference for one. Categories are never merged
(`INVESTIGATION_METHOD.md:352`).

**Implemented in:** `../METHOD.md` §3, §3.1, §3.2, §4, §10;
[`../templates/EVIDENCE-LEDGER.md`](../templates/EVIDENCE-LEDGER.md)
(classification, currency, and confidence are mandatory columns).

**Confidence discipline:** `INFERRED` is capped at `Medium`; `LEAD` at `Low`;
`UNKNOWN` at `None`. `None` is a documented local extension
(`../METHOD.md` §4), permitted only for `UNKNOWN` and retired `LEAD` entries and
never on a `VERIFIED` or `DOCUMENTED` claim.

### 5.5 Conceptual architecture vs verified architecture — checklist item 5

**Authority:** `AGENTS.md` §1 ("`ARCHITECTURE.md` is the current proposed
architecture"), §11, §23, §35; `INVESTIGATION_METHOD.md` §24, §40; `PLAN.md:27`.

**Rule:** architecture statements carry one of five labels — `EXISTING` (proven
from L1 source), `DOCUMENTED-ARCH`, `CONCEPTUAL`, `PROPOSED` (the current
`ARCHITECTURE.md` position), `CONTRADICTED` (an `EXISTING` finding the proposal
does not account for). A `PROPOSED` statement must never be readable as
`EXISTING`. `ARCHITECTURE.md` is never silently corrected; conflicts are recorded
(`INVESTIGATION_METHOD.md` §40).

**Concept separation** is enforced as a reporting rule in `../METHOD.md` §9:
`Package ≠ Backend ≠ Privilege ≠ Policy ≠ Trust ≠ Runtime State`, plus
`Installed ≠ Enabled ≠ Running`, `Backend failure ≠ Module failure`,
`PID ≠ durable execution identity`, and `Source trust ≠ Execution trust`.
Reporting a backend's availability as evidence of privilege is defined as a
reporting defect.

### 5.6 Outdated information — checklist item 6

**Authority:** `INVESTIGATION_METHOD.md` §8 (version pinning, "do not invent a
version"), §9 (current vs historical), §48 (efficiency), `AGENTS.md` §9, §16.

**Rule:** every version-sensitive claim carries a currency marker —
`CURRENT`, `HISTORICAL`, `VERSION-SPECIFIC`, `UNKNOWN-CURRENCY`. When exact
version information cannot be established, record `Unknown`; never invent or
infer a version.

**Non-deletion procedure** (`../METHOD.md` §6.3) when a later phase invalidates
an earlier statement:

1. Do not delete the earlier statement.
2. Mark it `HISTORICAL` (it was right for its time) or `SUPERSEDED` (it was
   wrong).
3. Add a dated note to the affected phase report.
4. Record the change in `../STATUS.md`.
5. Register a contradiction if both statements had been treated as current and
   mutually exclusive.

**Implemented in:** `../METHOD.md` §6.1–§6.3;
[`../templates/EVIDENCE-LEDGER.md`](../templates/EVIDENCE-LEDGER.md) section K
("Retired or superseded entries"); `evidence.md` §K, which is populated with the
four retractions made during Phase 0.

### 5.7 Android-version-specific findings — checklist item 7

**Authority:** `INVESTIGATION_METHOD.md` §17 (device matrix), §18 (Android 12–17
range and support markers), `AGENTS.md` §10, `PLAN.md` (Phase 10).

**Rule:** the compatibility range is Android 12, 13, 14, 15, 16, 17. Findings are
recorded per version using `Supported` / `Partially supported` / `Unsupported` /
`Unknown`. No generalisation across versions without evidence; a version gap is
`Unknown`, not "probably the same". One device result is not universal Android
behaviour.

**`targetSdk` condition:** because this app targets API 28
(`app/build.gradle.kts:40`) while compiling against SDK 37, any Android behaviour
claim that could depend on target-SDK-gated restrictions must state the
`targetSdk` condition explicitly. Recorded as `baseline.md` §2.1 and required by
`../templates/ANDROID-COMPATIBILITY-MATRIX.md` §1 and §3.

**Implemented in:** `../METHOD.md` §7.1;
[`../templates/ANDROID-COMPATIBILITY-MATRIX.md`](../templates/ANDROID-COMPATIBILITY-MATRIX.md)
(version grid, row detail with introduced-API-level field, device/experiment
matrix, unverified-rows section).

### 5.8 Porter-version-specific findings — checklist item 8

**Authority:** `AGENTS.md` §11, §27; `INVESTIGATION_METHOD.md` §19, §50; `PLAN.md`
(Phase 4).

**Rule:** every Porter claim must name the exact version, artifact, and commit, or
be recorded as `Unknown`. A Porter claim must state **which artifact** it
describes — app, client library, service, or documented protocol. Porter
capabilities are classified as `VERIFIED`, `DOCUMENTED`, `INFERRED`, `CONCEPTUAL`,
or `UNKNOWN` (the five categories named by `AGENTS.md` §11). Shizuku-shaped
assumptions may not be transferred to Porter; such a claim is `INFERRED` and
labelled. Backend identity never implies privilege.

**Per-capability detail required** (`INVESTIGATION_METHOD.md` §19): API, input,
output, handle, error behaviour, lifecycle, failure behaviour, recovery
behaviour.

**Implemented in:** `../METHOD.md` §7.2;
[`../templates/BACKEND-COMPATIBILITY-MATRIX.md`](../templates/BACKEND-COMPATIBILITY-MATRIX.md)
§0 rules, §1 version context, §2 capability matrix, §4 privilege.

**Current state:** Phase 0 established that **no Porter dependency or reference
exists in this repository** (`evidence.md` P0-A10, a verified negative) and that
the artifact identity is unresolved (`evidence.md` P0-E01/P0-E02, hazard H-001).
No Porter capability is recorded as `VERIFIED` or `DOCUMENTED` anywhere. This is
U-001, owned by Phase 4.

### 5.9 Shizuku compatibility findings — checklist item 9

**Authority:** `AGENTS.md` §12, §25, §27; `INVESTIGATION_METHOD.md` §20; `PLAN.md`
(Phase 5).

**Rule:** every Shizuku claim must name the client API version, the server
implementation in use (upstream, a fork, or another implementation), and that
implementation's version. The `window.Shizuku` namespace and related WebUI bridges
are **compatibility obligations** owed to module authors, never evidence of a
backend's internal architecture. Where the compatibility surface and the
underlying implementation diverge, both are recorded and the divergence becomes a
contradiction. Shizuku privilege is recorded as depending on how the server was
started — and that relationship is evidence, not an assumption.

**Implemented in:** `../METHOD.md` §7.3;
[`../templates/BACKEND-COMPATIBILITY-MATRIX.md`](../templates/BACKEND-COMPATIBILITY-MATRIX.md)
§1 (artifact-level version rows), §5 (compatibility surface table), §6 (failure,
lifecycle, recovery), §7 (security boundaries).

**Current state:** the client API is pinned at `13.1.5` (`evidence.md` P0-A06).
The server side is user-supplied and unpinned (U-004), and the possibility that a
fork provides `dev.rikka.shizuku` compatibility is a recorded `LEAD`
(`evidence.md` P0-E05, hazard H-002).

### 5.10 Investigation completion criteria — checklist item 10

**Authority:** `PLAN.md:798-818` ("Investigation Completion Rule"), `PLAN.md:15`
and the per-phase checklists, `INVESTIGATION_METHOD.md` §41 (research
completeness), §42 (13-item completion criteria), §43 (status vocabulary),
`AGENTS.md` §31.

**Rule — research completeness** (§41): a checklist item is complete only when the
investigation established one of: verified answer, documented answer, verified
limitation, verified incompatibility, verified historical behaviour, or an
explicit unresolved question. A checkbox is never complete merely because
someone searched.

**Rule — completion** (`../METHOD.md` §16) operationalises both `PLAN.md:798-818`
and `INVESTIGATION_METHOD.md` §42 into 14 criteria, including the per-item
checklist coverage table, the classification of architecture conflicts, and the
prohibition on MasterRef edits during Phases 0–24.

**Rule — status** (`../METHOD.md` §15): only the seven statuses of
`INVESTIGATION_METHOD.md` §43 may be used. `Research Complete` requires a full
report. `Audited` requires a completed self-audit with every checklist item
mapped. `Complete` requires every criterion in §16. `Blocked` requires an
explicit statement of what is missing. A phase is not advanced while its own
criteria are unmet unless the next phase does not depend on it and that
dependency is recorded.

**Implemented in:** `../METHOD.md` §15, §16;
[`../templates/PHASE-REPORT.md`](../templates/PHASE-REPORT.md) Appendix A
(checklist coverage) and Appendix D (self-audit checklist).

### 5.11 MasterRef incorporation criteria — checklist item 11

**Authority:** `AGENTS.md` §5, §25, §26, §31; `PLAN.md:823-843` (MasterRef
Protection Rule), `INVESTIGATION_METHOD.md` §37, §38, §39, §40.

**Rule — during Phases 0–24:** `MasterRef.md` is read-only. Findings go to the
phase report and registers. Section 12 of each report records *recommended*
changes only.

**Rule — Phase 25 (audit):** compare completed findings against MasterRef and
produce a concrete change list (missing, outdated, incorrect, unsupported,
duplicated, contradictory, overly conceptual, insufficiently sourced). Audit
identifies changes; it does not apply them.

**Rule — Phase 26 (incorporation):** a finding is eligible only if it appears in a
phase report, carries an eligible classification with its label preserved in the
incorporated text, states its version context or explicitly records its absence,
is not contradicted by an unresolved contradiction, is not an unlabelled
proposal, and retains its uncertainty. Each incorporation records
investigation, finding, evidence, current MasterRef section, required change,
reason, and resulting classification. `PROPOSED`, `UNKNOWN`, `HISTORICAL`, and
future-facing material may be incorporated **only when explicitly labelled as
such**.

**Prohibited:** incorporating an assumption as fact; removing material merely
because new information exists; resolving a contradiction by deletion; converting
a conceptual section into a claim of implementation; incorporating a proposal as
an existing capability.

**Implemented in:** `../METHOD.md` §17 (17.1–17.4) and §19;
[`../templates/PHASE-REPORT.md`](../templates/PHASE-REPORT.md) §12 (recommendations
only, with a required classification column); `../INDEX.md` §"MasterRef status"
(explicit protection block).

### 5.12 Investigation index — checklist item 12

**Authority:** `INVESTIGATION_METHOD.md` §44 (recommended fields), `PLAN.md:15`,
`AGENTS.md` §19, §31.

**Rule:** one row per phase (0–26), tracking phase, name, status, report path,
dates, primary sources, major unknowns, major contradictions, and MasterRef
impact.

**Implemented in:** [`../INDEX.md`](../INDEX.md) — phase index with all 27 rows,
a phase-dependency map so a later phase cannot invent answers to unblock itself,
an artifact index, a register summary, and an explicit MasterRef-protection
block.

### 5.13 Investigation status tracking — checklist item 13

**Authority:** `INVESTIGATION_METHOD.md` §43 (status vocabulary),
`PLAN.md:15`, `AGENTS.md` §31, §32.

**Rule:** status is tracked explicitly using only the seven permitted values, and
status reflects committed state rather than intent.

**Implemented in:** [`../STATUS.md`](../STATUS.md) — current position, per-phase
status table, a dated status-transition log, open unknowns, open contradictions,
verified baseline facts, blocking items, and the program rules currently in
force.

### 5.14 Verified project baseline (supporting infrastructure work)

Establishing the baseline was necessary to make the version and currency rules
operational: a method that demands version context needs a known starting point.
Recorded in [`baseline.md`](baseline.md) and `evidence.md` §A.

Key verified facts: fork of `Resilien-Mobile/RootlessStore`; AGPL-3.0;
`com.baidaidai.rootless_store`; version `2.3.1`; `minSdk 26` / `targetSdk 28` /
`compileSdk release(37)`; eight Gradle modules; Shizuku `api`/`provider`
`13.1.5`; libsu `core` `6.0.0` imported in five production files; AIDL shell
service interface present; **no Porter dependency or reference anywhere**.

---

## 6. Compatibility Findings

Phase 0 establishes no compatibility claim. Compatibility is asserted only by the
phases that own it. What Phase 0 does establish is the **format** in which such
claims must be made:

1. A compatibility statement must read *"compatible with X under conditions
   A, B, C; behaviour D unverified"* — never a bare "supports X"
   (`../templates/PHASE-REPORT.md` §6).
2. Every compatibility statement carries the artifact **and version** it applies
   to. With Porter and Shevery identities unresolved (U-001, U-003), no
   compatibility statement about either is admissible at this time.
3. `Supported` may be claimed only from L1 source or an `OBSERVED` device
   experiment, never from documentation alone
   (`../METHOD.md` §7.1 rule 1).
4. A compatibility obligation owed to third-party module authors (notably the
   `window.Shizuku` surface) is recorded separately from a backend's internal
   capability, in a dedicated table
   (`../templates/BACKEND-COMPATIBILITY-MATRIX.md` §5).

| Compatibility area | Current status | Owning phase |
| --- | --- | --- |
| Rootless Store internal behaviour | `Unknown` — baseline only | 1 |
| Shevery ADB Module compatibility | `Unknown` — no reference commit (U-003) | 2 |
| Porter capability parity | `Unknown` — artifact unidentified (U-001) | 4 |
| Shizuku client API | `DOCUMENTED` dependency at `13.1.5`; server side `Unknown` (U-004) | 5 |
| Android 12–17 | `Unknown` — no platform assessment performed | 10 |

---

## 7. Security Findings

Phase 0 produces no security findings about the application, because it inspects
no application logic. It does produce **security-relevant properties of the
investigation process**, which are findings about how future security conclusions
may be trusted.

### 7.1 Process security properties established

| ID | Property | Rationale | Implemented in |
| --- | --- | --- | --- |
| S-01 | An external technology with an unidentified artifact cannot produce a `VERIFIED` or `DOCUMENTED` capability claim | Prevents an invented API from becoming an architectural dependency | `../METHOD.md` §2.1 rules 3–4, §7.2 rules 1–3 |
| S-02 | Ambiguous project names are registered as hazards, not resolved by assumption | "Porter" already has two competing candidates; guessing wrong would produce a fabricated trust model for a privileged backend | `../sources.md` H-001, H-002 |
| S-03 | Privilege is recorded separately from backend identity | A backend's availability must never be reported as its privilege level; fallback that changes privilege is a security event, not an implementation detail | `../METHOD.md` §9, §7.2 rule 5; `../templates/BACKEND-COMPATIBILITY-MATRIX.md` §4 |
| S-04 | Every report section must contain abuse cases, not only intended behaviour | Each security boundary must answer who controls the input, what privilege results, and whether the input can cross a boundary | `../templates/PHASE-REPORT.md` §7 |
| S-05 | Archive/manifest/storage trust questions are assigned to owning phases rather than answered by assumption | `module.prop` parsing, ZIP extraction, and module storage are the primary attack surfaces of the proposed ADB Module subsystem | U-005, U-006, C-002; `../templates/BACKEND-COMPATIBILITY-MATRIX.md` §7 |
| S-06 | Licences are recorded as `Unknown` until Phase 18 verifies them | Prevents an unverified licence statement from entering architecture or attribution reasoning | `../sources.md` register-maintenance rule 5; `baseline.md` §9 |

### 7.2 Security-relevant open items carried forward

| Item | Why it matters for security | Owning phase |
| --- | --- | --- |
| U-001 — Porter artifact unknown | The primary intended privileged backend has no verified identity, therefore no verified authentication, permission, or privilege model. Designing around an assumed Porter would build privilege assumptions on nothing. | 4 |
| U-003 — Shevery commit unknown | The compatibility contract that will define manifest parsing, script execution, and WebUI bridging is unpinned, so its attack surface cannot be enumerated. | 2 |
| U-004 — Shizuku server unknown | Privilege depends on how the server was started; an unpinned server means the privilege level of a running module is undetermined. | 5 |
| C-002 — storage conflict | Internal app-private storage and external user-visible storage have different tamper models. Settling ADB Module storage is a security decision, not only a layout decision. | 1, 2, 7, 11 |
| `targetSdk 28` | A low target SDK changes which platform security restrictions apply. Relevant to notification permission, foreground-service types, and storage access. Recorded as configuration fact only. | 7, 9, 10, 11, 16 |

### 7.3 MasterRef protection — verified

`git status --porcelain` after Phase 0 shows a modified `README.md` (the status
block, recorded as contradiction C-003) and an untracked `investigations/`
directory. **`MasterRef.md` is unmodified.** `ARCHITECTURE.md` is unmodified.
No file under `app/`, `application/`, `core/`, `data/`, `domain/`, `illusioncube/`,
`service/`, or `ui/` was created or modified. No build artifact was produced.

---

## 8. Architecture Implications

Labels per `../METHOD.md` §3.2.

| ID | Implication | Label | Evidence | Affected `ARCHITECTURE.md` section | Recommendation |
| --- | --- | --- | --- | --- | --- |
| AI-01 | The proposed ADB Module subsystem (§25–§34) has no corresponding implementation in this repository | `CONTRADICTED` (by absence) | P0-A10, baseline §5.4, P0-A13 | §25–§34, §71 | Phase 1 must state explicitly which parts of §25–§34 have no code counterpart, so no reader infers existing capability |
| AI-02 | `ARCHITECTURE.md` §29 ADB Module storage and `docs/storage-model.md` external Magisk-compat storage describe different locations; whether they describe different features or conflicting ones is unresolved | `PROPOSED` + open contradiction | C-002, P0-A16, P0-B15 | §29, §31, §50, §67 | Resolve via Phase 1 (code) then Phase 2 (format). Do not adopt either path as the contract until then |
| AI-03 | `ARCHITECTURE.md` §26 manifest fields are unverified against any reference implementation | `PROPOSED` | P0-B14, U-005 | §26 | Phase 2 must verify field-by-field; divergences recorded as contradictions, not silent edits |
| AI-04 | The proposal's primary backend (Porter, §16) is entirely absent from the codebase | `CONTRADICTED` (by absence) | P0-A10, U-001 | §16, §20, §53 | Phase 4 establishes the artifact before §16 is treated as anything but a goal |
| AI-05 | `ARCHITECTURE.md` §5/§25/§26 correctly scope `module.prop` to the ADB Module subsystem, preserving format separation required by `AGENTS.md` §13 | `DOCUMENTED-ARCH` (consistent) | P0-B14 | §5, §6, §25, §26 | No change needed. Recorded so the Phase 0 self-audit's retraction is traceable |
| AI-06 | `ARCHITECTURE.md` §27/§28 propose archive validation including path traversal, absolute paths, entry count, and extraction size | `PROPOSED` | P0-B15 (read of §27–§28), no code counterpart | §27, §28 | Phase 7 verifies these against the Phase 1 extraction implementation; a proposal with no implementation is not a control |
| AI-07 | §2 "Documentation Source of Truth" designates MasterRef as the reference; Phase 0 confirms MasterRef is unmodified and unaudited | `DOCUMENTED-ARCH` | §7.3 above, `PLAN.md:823-843` | §2 | Phase 25 owns the audit; no Phase 0 change |

No `ARCHITECTURE.md` text was modified. Each conflict above is recorded, not
resolved (`INVESTIGATION_METHOD.md` §40).

---

## 9. Unknowns

Full register: [`../unknowns.md`](../unknowns.md). Template:
[`../templates/UNKNOWN.md`](../templates/UNKNOWN.md).

| ID | Unknown | Why unresolved | What would resolve it | Priority | Phase |
| --- | --- | --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Name is overloaded; no dependency declared; no confirmed official repository | Confirm artifact identity; pin tag/commit; inspect the §19 capability set | Blocking (for backend design) | 4 |
| U-002 | Upstream Rootless Store state and license | Upstream not fetched | Fetch upstream; record license, HEAD, latest tag, divergence | Medium | 18 |
| U-003 | Reference Shevery commit for the ADB Module contract | Two candidate repositories, neither confirmed or pinned | Confirm authoritative repo; pin commit; verify format against source | High | 2 |
| U-004 | Shizuku server implementation/version at runtime | Client API pinned; server is user-supplied; forks may provide compatibility | Establish upstream server version; enumerate forks; verify API against each | High | 5 |
| U-005 | Whether `ARCHITECTURE.md` §26 fields match the real `module.prop` contract | Proposal never compared against Shevery source | Resolve U-003; compare field-by-field | High | 2 |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is the ADB Module feature | Only documentation read; implementing code not inspected | Phase 1 code inspection; Phase 2 format comparison | High | 1, 2 |

**None blocks Phase 1.** U-001 is blocking only for Porter-dependent design
(Phases 6, 10, 22, 23).

---

## 10. Contradictions

Full register: [`../contradictions.md`](../contradictions.md). Template:
[`../templates/CONTRADICTION.md`](../templates/CONTRADICTION.md).

| ID | Contradiction | Severity | Status | Interpretation adopted |
| --- | --- | --- | --- | --- |
| C-001 | Alleged 26 vs 27 phase-count conflict | Minor | **Retracted** | None needed. `PLAN.md`'s 27 phases govern; `INVESTIGATION.md:23` explicitly disclaims phase-order authority |
| C-002 | ADB Module storage: internal app-managed vs external Magisk-compat paths | Material | **Unresolved** | All storage statements carry a scope label. The ADB Module path is `PROPOSED`; the current plugin path is `DOCUMENTED` pending Phase 1 code verification |
| C-003 | README phase status vs verified Phase 0 completion | Minor | Resolved | README corrected and the change recorded here rather than applied silently |
| C-004 | Alleged `module.prop` format conflation | Minor | **Retracted** | `ARCHITECTURE.md` §26 scopes `module.prop` to the ADB Module manifest; no conflation exists |

### 10.1 Why the retractions are reported

Two contradictions raised during Phase 0 were withdrawn because source
inspection disproved them:

- **C-001** alleged that `INVESTIGATION.md` states a 26-phase program. Searching
  `INVESTIGATION.md` found no phase-count statement at all; the original note had
  mistaken the document's 44 numbered *sections* for phases. `INVESTIGATION.md:23`
  in fact states the document does not define the authoritative phase order.
- **C-004** alleged that `ARCHITECTURE.md` conflates `module.prop` with Rootless
  Plugin semantics, citing "§14". `module.prop` appears at `ARCHITECTURE.md:157`
  (§5 Package Architecture), correctly scoped under the ADB Module heading with
  "It retains its own:"; the "Required information" wording is at
  `ARCHITECTURE.md:637-657` (§26 ADB Module Manifest), also correctly scoped; and
  §25 states the subsystem integrates "without becoming a Rootless Plugin".

Both entries are retained in the register with the disconfirming check. This is
the behaviour `INVESTIGATION_METHOD.md` §13 and §36 require, and it is reported
here rather than quietly removed.

---

## 11. Verified Conclusions

Only statements whose classification supports the wording.

| ID | Conclusion | Classification | Confidence | Version context |
| --- | --- | --- | --- | --- |
| VC-01 | The investigation program has 27 phases (0–26), per `PLAN.md:15` | VERIFIED | High | n/a (project structure) |
| VC-02 | Phase 0's checklist has 13 items, all addressed | VERIFIED | High | `PLAN.md:54-66` |
| VC-03 | The source hierarchy is six levels L1–L6, as reproduced in `METHOD.md` §2 | VERIFIED | High | `INVESTIGATION_METHOD.md` §6 |
| VC-04 | The required report format is 14 numbered sections plus an evidence ledger | VERIFIED | High | `INVESTIGATION_METHOD.md` §34–§35 |
| VC-05 | Phase completion requires 14 criteria and a self-audit | VERIFIED | High | `PLAN.md:798-818`, `INVESTIGATION_METHOD.md` §42 |
| VC-06 | MasterRef is protected during Phases 0–24 and modified only in Phase 26 | VERIFIED | High | `AGENTS.md` §5, `PLAN.md:823-843` |
| VC-07 | `MasterRef.md` and `ARCHITECTURE.md` were not modified by Phase 0 | VERIFIED | High | `git status --porcelain` |
| VC-08 | This repository declares **no Porter dependency and contains no Porter reference** | VERIFIED | High | `4b35c36b` |
| VC-09 | This repository declares `dev.rikka.shizuku:api`/`:provider` `13.1.5` and `libsu:core` `6.0.0` | VERIFIED | High | `4b35c36b` |
| VC-10 | The app is `minSdk 26` / `targetSdk 28` / `compileSdk release(37)`, version `2.3.1` | VERIFIED | High | `4b35c36b` |
| VC-11 | `ARCHITECTURE.md` scopes `module.prop` to the ADB Module manifest and does not conflate it with Rootless Plugins | VERIFIED | High | `ARCHITECTURE.md` §5, §25, §26 |
| VC-12 | The project has an unresolved documentation conflict over ADB Module storage location | VERIFIED (that a conflict exists) | High | `4b35c36b` |
| VC-13 | The Porter artifact, Shevery reference commit, and Shizuku server implementation are all unidentified | UNKNOWN | None | as of 2026-10-02 |
| VC-14 | Phase 0 performed no build, no device test, and no network fetch | VERIFIED | High | `4b35c36b` |

---

## 12. Recommendations for MasterRef Expansion

**Recommendations only. `MasterRef.md` was not modified and must not be during
Phases 0–24** (`AGENTS.md` §5, `PLAN.md:823-843`).

| ID | Current MasterRef section | Recommended change | Reason | Intended classification in MasterRef |
| --- | --- | --- | --- | --- |
| MR-01 | Whole document | No content change from Phase 0 | Phase 0 is methodological; it produced no external or behavioural finding | n/a |
| MR-02 | Phase 25 audit scope | Ensure the audit checks whether MasterRef states a **phase count**, and whether that count matches 27 | Phase 0 verified the canonical count is 27 and verified `INVESTIGATION.md` is consistent; MasterRef was not read | Verified fact (post-audit) |
| MR-03 | Storage / ADB Module sections | Ensure the audit checks whether MasterRef states an ADB Module storage path as fact or as proposal | C-002 shows a live conflict between `ARCHITECTURE.md` §29 and `docs/storage-model.md`; MasterRef may inherit one side silently | Unknown / Proposed (post-audit) |
| MR-04 | Backend sections | Ensure the audit checks whether MasterRef describes Porter behaviour or API | U-001: no verified Porter artifact exists; any such text must be labelled unknown or historical | Unknown (post-audit) |
| MR-05 | Shevery compatibility sections | Ensure the audit checks whether MasterRef states Shevery-specific behaviour without a pinned source | U-003; `INVESTIGATION.md` claims exist but are not commit-pinned | Documented / needs source attribution (post-audit) |
| MR-06 | Provenance / licensing | Ensure the audit checks whether MasterRef states the upstream license or dependency licenses | U-002 and `sources.md` keep these `Unknown` until Phase 18 | Unknown (post-audit) |
| MR-07 | Investigation-program sections | If MasterRef describes the investigation program, add a pointer to `investigations/INDEX.md` and `investigations/STATUS.md` | Status is now tracked in committed artifacts rather than only in prose | Implementation evidence (post-audit) |

---

## 13. Sources / References

### 13.1 Local project sources

| ID | Source | Level | Version / commit | Location | Used for |
| --- | --- | --- | --- | --- | --- |
| S-001 | This repository | L1 | `4b35c36b` | whole tree | Baseline, absence of Porter, storage documentation |
| S-002 | Upstream `Resilien-Mobile/RootlessStore` | L2 (relationship only) | Unknown | `README.md:127`, `:327` | Fork relationship; U-002 |
| S-003 | Project documentation set | L2 (intent) | Current | `AGENTS.md`, `PLAN.md`, `INVESTIGATION_METHOD.md`, `ARCHITECTURE.md`, `INVESTIGATION.md`, `MasterRef.md`, `README.md`, `docs/*` | All definitions and authority statements |

### 13.2 External technology sources

Registered in [`../sources.md`](../sources.md). **All are `LEAD`-level; none was
inspected in Phase 0.**

| ID | Source | Level | Version | Note |
| --- | --- | --- | --- | --- |
| S-004 | Porter | LEAD | Unknown | Artifact unidentified; hazard H-001; U-001 |
| S-005 | Shizuku | L1 (this project's use) / Unknown (server) | client `13.1.5` | Server side unpinned; hazard H-002; U-004 |
| S-006 | Shevery | LEAD | Unknown | No commit pinned; U-003 |
| S-007 | libsu `com.github.topjohnwu.libsu` | L1 (dependency identity) | `6.0.0` | License unverified |
| S-008 | Android platform / AOSP | Not yet consulted | n/a | Phase 0 recorded only this project's SDK configuration |

### 13.3 Complete source records

[`../sources.md`](../sources.md), in the format of
[`../templates/SOURCE-RECORD.md`](../templates/SOURCE-RECORD.md), including the
naming-hazard entries.

---

## 14. Items Requiring Future Investigation

| ID | Item | Why deferred | Owning phase |
| --- | --- | --- | --- |
| FI-01 | Full architectural map of Rootless Store: packages, layers, dependencies, persistence, execution, plugin lifecycle, sources, notifications, recovery, WebUI | Phase 0 does not investigate the subject application | 1 |
| FI-02 | Whether the existing Magisk-compatibility workflow is a Rootless Plugin feature or the ADB Module feature | Requires code inspection first | 1, 2 |
| FI-03 | Verified Shevery `module.prop` schema, archive format, script lifecycle, environment variables, WebUI surface, storage layout | No reference commit pinned | 2 |
| FI-04 | Verified Porter artifact identity, version, and API surface | No confirmed official source | 4 |
| FI-05 | Shizuku server implementation, fork compatibility, privilege dependence | Server is user-supplied | 5 |
| FI-06 | Capability parity between backends | Depends on FI-04 and FI-05 | 6 |
| FI-07 | Application-level security analysis of archive extraction, script execution, WebView bridges, trust model | Phase 0 inspects no application logic | 7 |
| FI-08 | Android 12–17 behaviour matrix, including `targetSdk 28` effects | No platform assessment performed | 9, 10 |
| FI-09 | ADB Module storage contract and its tamper model | C-002 unresolved | 11 |
| FI-10 | Build, lint, and test executability; current test coverage | No build executed | 17 |
| FI-11 | Licences: upstream Rootless Store, libsu, Shevery, Shizuku, Porter | Unverified | 18 |
| FI-12 | Whether SDK identifier 37 corresponds to a released or preview platform | Not established; do not assume | 10 |
| FI-13 | Full `MasterRef.md` audit | Phase 0 deliberately did not audit MasterRef | 25 |

---

## Appendix A — Checklist Coverage

All 13 `PLAN.md` Phase 0 items, mapped to the section that defines them and the
artifact that implements the definition. Outcomes follow
`INVESTIGATION_METHOD.md` §41 (verified answer / documented answer / verified
limitation / verified incompatibility / verified historical behaviour / explicit
unresolved question).

| # | `PLAN.md` checklist item (verbatim) | Addressed in | Implemented by | Outcome | Classification |
| --- | --- | --- | --- | --- | --- |
| 1 | Define the standard investigation-document format | §5.1 | `METHOD.md` §11, §12; `templates/PHASE-REPORT.md`, `templates/EVIDENCE-LEDGER.md` | Verified definition, mapped to `INVESTIGATION_METHOD.md` §34–§35 and `AGENTS.md` §18 | VERIFIED |
| 2 | Define source reliability levels | §5.2 | `METHOD.md` §2; `sources.md`; `templates/SOURCE-RECORD.md` | Verified definition (L1–L6), with documented naming-hazard requirement | VERIFIED |
| 3 | Define how conflicting sources are handled | §5.3, §10 | `METHOD.md` §8; `contradictions.md`; `templates/CONTRADICTION.md` | Verified definition; exercised twice (2 retractions, 1 unresolved) | VERIFIED |
| 4 | Define how implementation evidence is distinguished from documentation | §5.4 | `METHOD.md` §3, §3.1, §4, §10; `templates/EVIDENCE-LEDGER.md` | Verified definition (6 classifications, mandatory classification/currency/confidence columns) | VERIFIED |
| 5 | Define how conceptual architecture is distinguished from verified architecture | §5.5 | `METHOD.md` §3.2, §9, §19; `templates/PHASE-REPORT.md` §8 | Verified definition (5 architecture labels + concept separation rule) | VERIFIED |
| 6 | Define how outdated information is marked | §5.6 | `METHOD.md` §6.1–§6.3; `templates/EVIDENCE-LEDGER.md` §K; `evidence.md` §K | Verified definition (4 currency markers + 5-step non-deletion procedure) | VERIFIED |
| 7 | Define how Android-version-specific findings are recorded | §5.7 | `METHOD.md` §7.1; `templates/ANDROID-COMPATIBILITY-MATRIX.md` | Verified definition (Android 12–17, 4 support markers, `targetSdk` condition, device matrix) | VERIFIED |
| 8 | Define how Porter-version-specific findings are recorded | §5.8 | `METHOD.md` §7.2; `templates/BACKEND-COMPATIBILITY-MATRIX.md`; `sources.md` H-001 | Verified definition. Artifact identity remains an explicit unknown (U-001) | VERIFIED (definition) / UNKNOWN (Porter facts) |
| 9 | Define how Shizuku compatibility findings are recorded | §5.9 | `METHOD.md` §7.3; `templates/BACKEND-COMPATIBILITY-MATRIX.md` §5–§7 | Verified definition. Server identity remains an explicit unknown (U-004) | VERIFIED (definition) / UNKNOWN (server facts) |
| 10 | Define investigation completion criteria | §5.10 | `METHOD.md` §15, §16; `templates/PHASE-REPORT.md` App. A, App. D | Verified definition (14 criteria + 7 statuses + §41 outcome taxonomy) | VERIFIED |
| 11 | Define MasterRef incorporation criteria | §5.11 | `METHOD.md` §17; `templates/PHASE-REPORT.md` §12 | Verified definition (Phases 25/26 gates + 5 prohibitions) | VERIFIED |
| 12 | Create investigation index | §5.12 | `INDEX.md` | Artifact created: 27 phase rows, dependency map, artifact index, MasterRef status | VERIFIED |
| 13 | Create investigation status tracking | §5.13 | `STATUS.md` | Artifact created: position, per-phase table, transition log, registers, blocking items | VERIFIED |

**Coverage: 13 / 13. No item skipped. No item deferred.**

---

## Appendix B — Version Context

| Item | Version / commit | Date checked | Currency |
| --- | --- | --- | --- |
| This project (`Rootless Store` fork) | `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba` | 2026-10-02 | CURRENT |
| App version | `2.3.1` (code 2) | 2026-10-02 | CURRENT |
| Upstream Rootless Store | Unknown | — | UNKNOWN-CURRENCY (U-002) |
| Android (SDK identifier) | `release(37)`; platform release mapping **not established** | 2026-10-02 | UNKNOWN-CURRENCY (FI-12) |
| Android (min/target) | `minSdk 26`, `targetSdk 28` | 2026-10-02 | CURRENT |
| Porter | Unknown | 2026-10-02 | UNKNOWN-CURRENCY (U-001) |
| Shizuku client API | `dev.rikka.shizuku:api` / `:provider` `13.1.5` | 2026-10-02 | CURRENT |
| Shizuku server | Unknown | — | UNKNOWN-CURRENCY (U-004) |
| Shevery | Unknown | — | UNKNOWN-CURRENCY (U-003) |
| libsu | `core` `6.0.0` | 2026-10-02 | CURRENT |
| AGP / Kotlin | `9.2.1` / `2.4.0` | 2026-10-02 | CURRENT |
| kotlinx-coroutines / serialization-json | `1.11.0` / `1.11.0` | 2026-10-02 | CURRENT |

---

## Appendix C — Experiment Records

**No experiments performed in this phase.**

Phase 0 is methodological. It required no device, no build, and no external
system. The only environment probe was checking whether `ripgrep` is installed
(`evidence.md` P0-A17), which is not an experiment about system behaviour.

Consequently, Phase 0 contains **no `OBSERVED` classification**. This is stated
explicitly so no reader mistakes Phase 0's completeness for behavioural evidence
about the application or any backend.

---

## Appendix D — Self-Audit

Performed before the phase was marked `Audited`, then `Complete`.

- [x] Every `PLAN.md` checklist item for this phase addressed — 13/13 (Appendix A)
- [x] Appendix A maps every checklist item to a section, an artifact, an outcome, and a classification
- [x] Primary sources reviewed and recorded — 5 L1 source groups (§3), none external
- [x] Implementation evidence inspected where it exists — repository source read for every L1 claim; `rg` was unavailable, so `grep`/`find`/`git grep` were used
- [x] Important claims carry version context — Appendix B; every ledger entry has a `Version / Commit` and `Currency` column
- [x] Android-version-specific rules defined — §5.7, `templates/ANDROID-COMPATIBILITY-MATRIX.md`
- [x] Porter-version-specific rules defined — §5.8, including the no-invented-API rule
- [x] Shizuku-version-specific rules defined — §5.9, including client/server/fork separation
- [x] Documentation vs implementation distinguished — §5.4; §3 and §4 explicitly state no external L1/L2 evidence was used
- [x] Conceptual vs verified architecture distinguished — §5.5; 7 architecture implications labelled
- [x] Verified behaviour separated from assumptions — every ledger row carries a classification and confidence; inferences isolated in §F and capped at Medium
- [x] Contradictions documented in the central register — 4 entries (2 retracted, 1 resolved, 1 unresolved)
- [x] Unknowns documented in the central register — 6 entries
- [x] Compatibility findings documented with conditions — §6 states that Phase 0 makes none, and states the required format
- [x] Security implications documented, including abuse cases — §7 (6 process properties, 5 carried-forward items)
- [x] Architecture implications documented and conflicts marked `CONTRADICTED` — §8 (AI-01, AI-04)
- [x] `MasterRef.md` not modified — verified by `git status --porcelain`
- [x] No implementation work performed — only `investigations/**` created; `README.md` status text updated and recorded as C-003
- [x] Phase 0–26 structure unchanged — `INDEX.md` uses exactly the 27 phases of `PLAN.md`
- [x] Remaining limitations explicitly recorded — §1.6, §6, Appendix C, and the unknowns in §9

### D.1 Self-audit findings and corrections

The self-audit found and corrected four defects. They are recorded rather than
quietly fixed:

| Defect | Correction |
| --- | --- |
| C-001 alleged a 26-phase statement in `INVESTIGATION.md` that does not exist | Retracted with the disproving check. Root cause: 44 document sections misread as phases |
| C-004 attributed a `module.prop` conflation to `ARCHITECTURE.md` §14; the text is at §5 and §26, and it is correctly scoped | Retracted with the correcting citations. Root cause: partial read of `ARCHITECTURE.md` |
| `INDEX.md` and `STATUS.md` referenced the pre-correction contradiction set | Updated to the corrected C-001…C-004 set, with C-002 now the storage conflict |
| Two local extensions (`LEAD`, `None`) were unlabelled as extensions | Marked explicitly in `METHOD.md` §3, §4, and `README.md` §7 as local extensions with their justification and limits |

### D.2 Method-level observations

Two weaknesses in the authoritative method were found and are recorded rather
than silently patched:

1. `INVESTIGATION_METHOD.md` §11 defines six evidence classifications, but §6
   treats search snippets and community sources as usable *leads*. Without a
   lead label, reconnaissance risks being written as findings. Addressed by an
   explicitly documented local extension.
2. `INVESTIGATION_METHOD.md` §12 defines three confidence levels. An entry with
   no supporting evidence cannot honestly be described as "Low". Addressed by an
   explicitly documented local extension restricted to `UNKNOWN` and retired
   `LEAD` entries.

Neither extension relaxes a standard. Both are proposals recorded in
`evidence.md` §G and may be proposed for incorporation into the method itself
after Phase 26.

### D.3 Justification for status `Complete`

Every criterion in `investigations/METHOD.md` §16 (14 criteria) and every item in
`INVESTIGATION_METHOD.md` §42 is satisfied. The six open unknowns and one open
contradiction are legitimate investigation outcomes, not gaps in Phase 0: Phase 0
is a definitional phase whose subject matter is the method itself, and every
remaining question is assigned to its owning phase with an explicit resolution
path. No unknown or contradiction blocks Phase 1.

### D.4 Items still unresolved at Phase 0 completion

U-001, U-002, U-003, U-004, U-005, U-006; C-002. All are recorded in the central
registers and appear in `STATUS.md` §4 and §5.

---

## Phase 0 Artifacts

| Path | Contents |
| --- | --- |
| `investigations/README.md` | Directory purpose, layout, conventions |
| `investigations/METHOD.md` | Operational method (20 sections) |
| `investigations/INDEX.md` | 27-phase index, dependency map, artifact index, register summary |
| `investigations/STATUS.md` | Program position, phase status, transitions, open registers, baseline facts, blocking items |
| `investigations/sources.md` | Source register S-001…S-008 + naming hazards H-001/H-002 |
| `investigations/contradictions.md` | Contradiction register C-001…C-004 |
| `investigations/unknowns.md` | Unknowns register U-001…U-006 |
| `investigations/templates/PHASE-REPORT.md` | Phase report template (14 sections + 4 appendices) |
| `investigations/templates/EVIDENCE-LEDGER.md` | Evidence ledger template (11 sections) |
| `investigations/templates/CONTRADICTION.md` | Contradiction record template |
| `investigations/templates/UNKNOWN.md` | Unknown record template |
| `investigations/templates/SOURCE-RECORD.md` | Source record template |
| `investigations/templates/ANDROID-COMPATIBILITY-MATRIX.md` | Android 12–17 matrix template |
| `investigations/templates/BACKEND-COMPATIBILITY-MATRIX.md` | Porter + Shizuku matrix template |
| `investigations/phase-00/REPORT.md` | This report |
| `investigations/phase-00/baseline.md` | Verified project baseline |
| `investigations/phase-00/evidence.md` | Phase 0 evidence ledger |

---

*Phase 0 ends here. Per `PLAN.md`, the next phase is Phase 1 — Rootless Store Deep
Investigation. No part of Phase 1 was started by this phase.*