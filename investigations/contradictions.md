# ADB Modules App — Investigation Contradictions Register

Central register of significant contradictions between sources.

**Standard:** `investigations/METHOD.md` §8, template
`investigations/templates/CONTRADICTION.md`
**Method authority:** `INVESTIGATION_METHOD.md` §13 (Conflicting Sources), §14
(Contradiction Record), §46 (Contradictions Register)
**Opened by:** Phase 0 — Investigation Infrastructure
**Last updated:** 2026-10-02 (Phase 2)

Rules: a contradiction is never closed by deleting the losing claim, by
preferring the newer-looking source, or by stopping discussion of it. Entries are
appended, never overwritten. `PLAN.md` requires contradictions to be *recorded*
rather than silently resolved.

Severity:

- **Blocking** — prevents an architectural decision from being made safely.
- **Material** — affects a conclusion, compatibility claim, or design decision;
  a safe interim interpretation exists.
- **Minor** — affects documentation accuracy or wording.

Retracted entries are retained on purpose. A retraction is part of the audit
trail: it records that Phase 0 raised a claim, could not substantiate it, and
withdrew it rather than deleting it (`AGENTS.md` §17).

---

## Summary

| ID | Title | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-001 | Alleged 26 vs 27 phase-count conflict | Minor | **Retracted** — claim not substantiated | — |
| C-002 | ADB Module storage: internal app-managed vs external Magisk-compat paths | Material | **Resolved** (Phase 2) | — (resolved; storage contract → 11) |
| C-003 | README states Phase 0 is not complete; Phase 0 is complete | Minor | Resolved — README corrected in Phase 0 | — |
| C-004 | Alleged `module.prop` format conflation in `ARCHITECTURE.md` | Minor | **Retracted** — misattributed section, scope is correct | — |

Open contradictions **as of Phase 0**: **1** (C-002). Blocking contradictions: **0**.
Superseded by the Phase 1 tally below.

Phase 1 additions:

| ID | Title | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-005 | `ARCHITECTURE.md` assumes manifest/archive validation stages the implementation lacks | Material | **Unresolved** (quantified in Phase 2) | 2, 7 |
| C-006 | `PluginManifest.kt` documents field requirements that no code enforces | Minor | **Unresolved** | 3, 7 |
| C-007 | Alleged missing `CodeBrickEntity` migration causing a crash | Material | **Refuted** — post-reset baseline already contained the entity | — |
| C-008 | Subagents disagreed on extraction Zip-Slip coverage (4/5 vs 2/10) | Minor | **Corrected** — 1 of 9 protected; 1 reachable, 4 dead | — |

Open contradictions after Phase 1: **2** (C-002, C-005) plus one minor
documentation contradiction (C-006). Blocking contradictions: **0**. C-007
refuted; C-001 and C-004 retracted.

Phase 2 additions:

| ID | Title | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-009 | The two official docs disagree on whether `full` mode permits web network | Material | **Unresolved** | 7 |
| C-010 | Nightzuku and Shevery enforce identical limits by different, non-equivalent means | Material | **Unresolved** | 21 |
| C-011 | The official API doc contradicts its own source on four points | Material | **Unresolved** | — (informational) |
| C-012 | Derived-from-Nightzuku provenance is unacknowledged on-platform | Minor | **Unresolved** | 18 |
| C-013 | Package identity and release-asset naming changed across versions | Minor | **Unresolved** | 13 |

Open contradictions after Phase 2: **7** (C-005, C-006, C-009, C-010, C-011,
C-012, C-013). C-002 resolved. Blocking contradictions: **0** throughout.

C-011 is listed as unresolved on purpose. Its four divergences are *settled* in
favour of source — source was read directly and the doc was read at the same
commit — but this project does not edit a third party's documentation, so the
conflict itself persists for every future reader of that doc. Recording it keeps
the correction visible instead of silently preferring one source
(`INVESTIGATION_METHOD.md` §13).

---

## Contradiction C-001 — Alleged 26 vs 27 phase-count conflict (RETRACTED)

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Retracted** |
| Last updated | 2026-10-02 |
| Owning phase | — |

### Claim A (as originally raised)

"`INVESTIGATION.md` states that the investigation program consists of 26 phases."

### Verification result

**Not substantiated.** `INVESTIGATION.md` contains no phase-count statement.

| Check | Result |
| --- | --- |
| `grep -n "phases" INVESTIGATION.md` | No matches |
| `grep -niE "phase" INVESTIGATION.md` | Only 2 matches, both about *document role*, not phase counts (`INVESTIGATION.md:23`, `INVESTIGATION.md:30`) |
| Numbered sections in `INVESTIGATION.md` | 44 content sections (`1.`–`44.`) — these are document sections, not investigation phases |
| `INVESTIGATION.md:23` | States the document "does not define the authoritative investigation phase order" |

### Resolution

`Retracted` on 2026-10-02 during the Phase 0 self-audit.

The alleged conflict does not exist. `INVESTIGATION.md` is internally consistent
with `PLAN.md` (27 phases) precisely because it disclaims authority over phase
order. The original Phase 0 note mistook `INVESTIGATION.md`'s 44 numbered
*document sections* for a phase count.

### Why this is retained

The retraction is itself a verified result: it demonstrates that an
unsubstantiated contradiction was caught and withdrawn rather than carried
forward as fact. `PLAN.md` and `INVESTIGATION_METHOD.md` §13 require sources to
be identified before a conflict is recorded; this entry records that requirement
being enforced.

### Residual action for Phase 25

None arising from this entry. If Phase 25 finds a real phase-count discrepancy
elsewhere (for example inside `MasterRef.md`), it opens a new contradiction with
its own evidence.

---

## Contradiction C-002 — ADB Module storage: internal app-managed vs external Magisk-compat paths

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Resolved** — 2026-10-02, Phase 2 |
| Last updated | 2026-10-02 |
| Owning phase | — (resolved by Phase 2); storage contract for ADB Modules → 11 |
| Resolved by | `phase-02/evidence.md` P2-A17, P2-A52, P2-A77, P2-B13 |

### Claim A

ADB Modules use isolated, application-managed **internal** storage, and the
architecture explicitly excludes Magisk/KSU module storage semantics.

| Field | Value |
| --- | --- |
| Source | `ARCHITECTURE.md` §29 ("Module Storage"), §56 ("No Magisk/KSU Architecture") |
| Reliability level | L2 (project document; `ARCHITECTURE.md` is self-described as a proposal) |
| Version | Not versioned |
| Date | 2026-10-02 |
| Evidence location | `ARCHITECTURE.md:707-717` (§29: `/data/user/0/<package>/files/adb_modules/<module-id>`; "must not use Magisk/KSU module storage semantics"); `ARCHITECTURE.md:1330-1341` (§56: no `/data/adb/modules`, no mount overlays, no Magisk/KernelSU hooks) |
| Currency | `CURRENT` |

Reinforced by `INVESTIGATION.md` §44 conclusion 4: "ADB Modules are not
Magisk/KSU systemless modules" (`INVESTIGATION.md:889`), and by `AGENTS.md` §24,
which forbids using `/data/adb/modules` as the ADB Module storage model.

### Claim B

The repository's own storage documentation routes "Magisk compatible plugins"
to **external** storage under the app's external-files directory, and describes
them as non-persistent.

| Field | Value |
| --- | --- |
| Source | `docs/storage-model.md` §"外部存储 (Sdcard侧)" |
| Reliability level | L2 (project documentation) |
| Version | Not versioned |
| Date | 2026-10-02 |
| Evidence location | `docs/storage-model.md:36-60` — three documented paths under `/storage/emulated/0/Android/data/com.baidaidai.rootless_store/files/Magisk`, `.../Magisk/template`, `.../Magisk/_template_.zip`; described as `非持久化` (non-persistent) |
| Currency | `CURRENT` (present in the repository at the inspected commit) |

### Version difference

Not a version difference. Both statements are current in the same repository.
`docs/storage-model.md` describes **existing Rootless Store behaviour**;
`ARCHITECTURE.md` describes the **proposed ADB Module contract**. They are
therefore not necessarily inconsistent — they may be describing two different
things.

### The actual problem

The ambiguity is one of **concept identity**, and it is exactly the distinction
`AGENTS.md` §13–§14 requires to be kept:

- If the existing `Magisk` external-storage workflow in `docs/storage-model.md`
  *is* the thing that will be called an "ADB Module", then `ARCHITECTURE.md` §29
  contradicts both that documentation and §56.
- If the existing `Magisk` workflow is a **Rootless Plugin compatibility feature**
  that remains distinct from ADB Modules, then both documents are correct and the
  only defect is naming ambiguity.

`AGENTS.md` §13 states that ADB Module ≠ Rootless Plugin and that Shevery
semantics must not be silently transformed into Rootless Store plugin semantics.
The repository's existing "Magisk 兼容插件" (Magisk-compatibility plugin) workflow
is a plausible instance of that exact conflation risk, but Phase 0 has not
established which reading is correct.

### Investigation performed

- [x] `ARCHITECTURE.md` §25–§31 read in full (ADB Module subsystem, manifest,
      archive, storage, installation, environment)
- [x] `ARCHITECTURE.md` §56 read
- [x] `docs/storage-model.md` read in full
- [x] `AGENTS.md` §13, §14, §24 re-checked
- [x] Phase 1: `InstallMagiskPluginUseCase` → `unzipFromFileToDirectory` traced
      — the only Zip-Slip-protected extraction path (P1-F14)
- [x] Phase 2: Shevery's real ADB Module storage verified from source at
      `bfc55ce9` — **app-private `filesDir/adb_modules`**, with `/data/adb/modules`
      explicitly disclaimed (P2-A52, P2-B13)

### Resolution

**Resolved** on 2026-10-02 by Phase 2. The two documents were describing
different things, which is the second reading in "The actual problem" above.

Evidence:

1. **The reference implementation uses app-private storage.** Shevery resolves
   modules under `context.filesDir/adb_modules/<module-id>` (P2-A52) and its own
   README states `/data/adb/modules` is **not** used and the feature is not
   Magisk/KernelSU (P2-B13). `ARCHITECTURE.md` §29 is therefore *correct* as a
   compatibility claim — it is now backed by a citation rather than by assertion.
2. **`docs/storage-model.md`'s external paths describe a different feature.** They
   belong to the Magisk-compat plugin workflow, whose `id` constraint differs from
   the module format's (P2-A17), and which this repository implements over
   Kotlin-serialised JSON manifests with no `module.prop` support at all
   (P2-A77).
3. **`AGENTS.md` §13's distinction holds.** ADB Module ≠ Rootless Plugin. The
   Phase 0 suspicion that the two were being conflated is confirmed — then
   disconfirmed as a storage conflict.

`ARCHITECTURE.md` §29 was **not** edited: its path string is confirmed, and
Phase 11 owns the storage contract (persistence, tamper model, migration).

### Residual uncertainty

Whether this project should *reuse* the internal-storage mechanism for both
package types is a design question for Phase 11, not a contradiction.

### Interpretation adopted

Until resolved, all storage statements in this program carry an explicit scope
label:

- "ADB Module storage **contract**" = `ARCHITECTURE.md` §29, status `PROPOSED`,
  unverified against any reference implementation.
- "Rootless Store **current** plugin storage" = `docs/storage-model.md`,
  status `DOCUMENTED`, to be re-verified against code in Phase 1.

No artifact in `investigations/` describes the ADB Module storage path as
existing behaviour. The path string in `ARCHITECTURE.md` §29 is reproduced only
as a *proposal*, never as a verified layout.

### Residual uncertainty

1. Whether the existing Magisk-compat storage workflow will be reused, wrapped, or
   replaced for ADB Modules.
2. Whether internal app-managed storage (`/data/user/0/...`) is actually
   reachable and adequate for the required runtime behaviour (background services
   reading their own files), or whether the compatibility contract requires
   external paths. `ARCHITECTURE.md` §29 says the path is "the investigated
   compatibility model", which asserts an investigation that Phase 2 must verify.
3. The non-persistent/external storage in `docs/storage-model.md` conflicts with
   the internal persistent path in `ARCHITECTURE.md` §29 for any package that must
   survive cache eviction and reboot.

### Impact

- Architecture: `ARCHITECTURE.md` §29, §31 (module environment),
  §50 (Rootless integration boundary), §67 invariants relating to storage.
- Compatibility: determines whether ADB Modules can be dropped in at the
  Shevery-documented location.
- Security: external-storage placement on shared/user-visible storage changes the
  trust and tamper model relative to internal app-private storage; this is a
  Phase 7 input.
- Testing: Phase 17 storage tests cannot be specified until the path contract is
  settled.

### Next action

| Action | Owning phase | Target |
| --- | --- | --- |
| Verify current Rootless Store plugin storage in code and compare with `docs/storage-model.md` | 1 | Phase 1 report, storage + execution sections |
| Verify the real Shevery ADB Module storage location from source; confirm or refute `ARCHITECTURE.md` §29 | 2 | Phase 2 report |
| Establish the ADB Module storage contract, including persistence and tamper model | 11 | Phase 11 report |
| Fold the storage trust boundary into the security analysis | 7 | Phase 7 report |

---

## Contradiction C-003 — README phase status vs verified Phase 0 completion

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | Resolved — README corrected in Phase 0 |
| Last updated | 2026-10-02 |
| Owning phase | — |

### Claim A

"Phase 0 — Investigation Infrastructure: Pending / Restarting … The project is
restarting at Phase 0; it is not yet complete."

| Field | Value |
| --- | --- |
| Source | `README.md` §"Current Status" |
| Reliability level | L2 |
| Version | Not versioned |
| Date | 2026-10-02 |
| Evidence location | `README.md:311-319` (status line `README.md:313`, explanation `README.md:315`, next-phase pointer `README.md:319`) |
| Currency | Was `CURRENT` before Phase 0 completed; stale afterwards |

### Claim B

Phase 0 completed on 2026-10-02 with all 13 `PLAN.md` Phase 0 checklist items
addressed and self-audited.

| Field | Value |
| --- | --- |
| Source | This phase's `investigations/phase-00/REPORT.md` Appendix A and Appendix D |
| Reliability level | L1 (artifacts produced in this phase, auditable) |
| Version | Commit under investigation |
| Date | 2026-10-02 |
| Evidence location | `investigations/phase-00/REPORT.md` |
| Currency | `CURRENT` |

### Version difference

None. This is a status-tracking drift, not a behavioural conflict.

### Investigation performed

- [x] `README.md` §"Current Status" read
- [x] `PLAN.md` Phase 0 checklist enumerated (13 items, `PLAN.md:54-66`)
- [x] Phase 0 completion criteria checked against `PLAN.md:798-818` and
      `INVESTIGATION_METHOD.md` §42
- [x] README updated to reflect the verified state

### Resolution

`Resolved` on 2026-10-02. `README.md:311-319` was updated from
"Pending / Restarting" to a completed status pointing at
`investigations/INDEX.md` and `investigations/STATUS.md`. The change is recorded
here rather than applied silently.

### Interpretation adopted

Project-facing status documentation is kept synchronized with committed
investigation state. Status text is not a substitute for `STATUS.md`; the
register remains authoritative.

### Residual uncertainty

None. If Phase 1 later changes status, the same rule applies.

### Impact

- Documentation only.
- No architecture, compatibility, or security impact.

### Next action

| Action | Owning phase | Target |
| --- | --- | --- |
| Update the README status block when the active phase changes | 1 (next transition) | Each transition |

---

## Contradiction C-004 — Alleged `module.prop` format conflation in `ARCHITECTURE.md` (RETRACTED)

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Retracted** |
| Last updated | 2026-10-02 |
| Owning phase | — |

### Claim A (as originally raised)

`ARCHITECTURE.md` §14 lists `module.prop` under "Required information" for an ADB
Module, which conflicts with `AGENTS.md` §13/§24's prohibition on letting ADB
Module semantics leak into Rootless Plugins.

### Verification result

**Not substantiated — two separate errors in the original reading.**

| Check | Result |
| --- | --- |
| Cited section | Misattributed. `module.prop` appears at `ARCHITECTURE.md:157`, inside **§5 "Package Architecture"**, not §14. The "Required information" wording is at **§26 "ADB Module Manifest"** (`ARCHITECTURE.md:637-657`), correctly scoped to the ADB Module subsystem. |
| Claimed conflict | Absent. `ARCHITECTURE.md` §5 scopes `module.prop` under the `ADB Module` heading with "It retains its own:" — i.e. explicitly preserving *distinct* per-format semantics. §25 (`ARCHITECTURE.md:633`) states the ADB Module subsystem "integrates with common runtime infrastructure **without becoming a Rootless Plugin**". §6 (`ARCHITECTURE.md:188`) states the common abstraction "must not erase package-specific semantics". |

### Resolution

`Retracted` on 2026-10-02 during the Phase 0 self-audit. `ARCHITECTURE.md` §26
places `module.prop` in the ADB Module manifest model, which is consistent with
`AGENTS.md` §13.

### Why this is retained

It records that Phase 0 initially asserted a conflict from a partial read of
`ARCHITECTURE.md`, then verified the full §5/§25/§26 context and withdrew the
claim. This is the intended behaviour of `INVESTIGATION_METHOD.md` §13
("Identify each source") and §36 ("internally consistent").

### Residual action for Phase 2

One genuine question survives and is **not** a contradiction: whether
`ARCHITECTURE.md` §26's required manifest fields (`id`, `name`, `version`,
`versionCode`, `author`, `description`) and optional fields (`banner`, `webui`,
`usesShellBridge`, `action`) match the real Shevery `module.prop` contract.
That is an accuracy question owned by Phase 2, and it is registered as **U-005**.

---

## Contradiction C-005 — Architecture assumes validation stages the implementation lacks

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** (format half now quantified) |
| Owning phase | 7 (security); 11 (storage remediation) |
| Evidence | `phase-01/evidence.md` P1-F10, P1-F14, P1-F15; `phase-02/evidence.md` P2-A78, P2-A80, P2-B01 |

### Claim A — architecture position

`ARCHITECTURE.md` §26 specifies a plugin manifest field set and §28 specifies
archive handling and validation behaviour. The document's structure treats
manifest validation and archive validation as stages between acquiring a package
and installing it.

### Claim B — implementation

There is **no manifest validation stage and no archive validation stage** on the
primary install paths.

- `parsePluginManifest` (`AndroidFileSystemCapabilityGatewayImpl.kt:397-403`,
  `AndroidFileSystemReadOperatorGatewayImpl.kt:96-102`) is a bare
  `kotlinx.serialization` decode with `ignoreUnknownKeys` and `isLenient`. No field
  is checked.
- Of nine ZIP extraction loops, exactly **one** validates containment, and it
  serves only the Magisk importer (P1-F14). All four reachable plugin and
  environment install paths write `File(target, entry.name)` directly.
- Manifest strings then reach a `sh -c` command line unquoted (P1-F15).

### Investigation performed

1. Enumerated every `ZipInputStream` loop in the repository and classified each by
   presence of a canonical-path containment check.
2. Traced each extractor to its callers, separating reachable paths from dead code.
3. Read both `parsePluginManifest` implementations in full.
4. Read `PluginManifest.kt` KDoc and compared each stated requirement against code
   that enforces it.

### Resolution

**Not resolved.** This is a gap between a proposal and the implementation, not a
conflict between two sources about the same fact. `ARCHITECTURE.md` is
authoritative as design intent; the source is authoritative as current behaviour;
both are correct about themselves. Phase 1 does not edit `ARCHITECTURE.md`
(`INVESTIGATION_METHOD.md` §40 requires an explicit architecture decision, not a
silent edit).

### Phase 2 contribution (2026-10-02)

Phase 2 quantified the gap and found the reference implementation is **stronger**
than the proposal on containment and **weaker** on atomicity.

| Aspect | `ARCHITECTURE.md` §27–§28 | Local implementation | Reference (Shevery) |
| --- | --- | --- | --- |
| Entry-count limit | Required | **Absent** (P2-A78) | 2048 (P2-A42) |
| Extracted-byte cap | Required | **Absent** (P2-A78) | 200 MiB, enforced during copy (P2-A42) |
| Path-traversal defence | Required | 1 of 9 loops protected, Magisk path only (P1-F14) | Two layers, both applied (P2-A44–P2-A47) |
| Behaviour on unsafe entry | Not specified | **Fails open** — skips and continues (P2-A80) | Fails closed — aborts the install |
| Manifest field validation | 6 fields "required" (U-005) | None (P1-F10) | 1 field, `id` (P2-A05) |
| Failure atomicity | Not specified | — | Deletes target before rename, **no rollback** (P2-A50) |

Two consequences for Phase 7:

- **Fails-open is the more serious defect.** The protected path silently skips a
  malicious entry and reports success (P2-A80); the reference aborts. Phase 7
  should treat "reject the archive" as the required semantic, not "skip the bad
  entry".
- **`ARCHITECTURE.md` §26 is wrong in both directions** (U-005 resolution), so
  §28's validation stages are specified against a contract that does not exist.
  Correcting §26 is a prerequisite for specifying §28 correctly.

The gap remains open; remediation scope belongs to Phase 7 (security) and
Phase 11 (storage).

### Interpretation adopted

MasterRef and every Phase 1+ artifact must state that validation **does not
exist** today. No document may describe manifest or archive validation as current
behaviour of this application.

### Residual uncertainty

Whether §28 describes intended-but-unimplemented work or behaviour assumed to
already exist upstream. Phase 2 resolves the format half.

### Impact

Direct input to Phase 7 (security) and Phase 2 (compatibility boundary). Any
Shevery-compatible contract in §26 must specify validation that Phase 1 has
established is currently absent.

### Next action

Phase 2 compares §26 field-by-field against the pinned Shevery contract and records
the validation each field requires. Phase 7 owns the security design.

---

## Contradiction C-006 — `PluginManifest` documents requirements that nothing enforces

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 3, 7 |
| Evidence | `phase-01/evidence.md` P1-F10, P1-F11, P1-F42 |

### Claim A — documentation

`PluginManifest.kt:32-42` states that `pluginPackageName` "Must be a valid
Android-style package name" and should avoid "spaces and special characters like
`(`, `)`, `!`, `?`, `/`, `.`, etc." `:44-56` recommends a SHA-256 or UUID primary
key. `:139-142` states "the host should chmod these paths before running the
plugin." `:94-96` states the host "should validate this before installation or
before enabling."

### Claim B — implementation

None of it is enforced. No format validation exists for any field (P1-F10).
`executableFiles` is neither persisted nor applied (P1-F11). `pluginUrl` is not
persisted. No validation runs before install or before enabling (P1-F28). The
CodeBrick promotion path uses a timestamp as the plugin primary key, directly
contradicting the documented recommendation (P1-F42).

### Investigation performed

Read `PluginManifest.kt` in full; read both deserialisers; searched for every
consumer of `executableFiles`, `pluginUrl`, and `requiredEnvironment`.

### Resolution

**Not resolved.** KDoc describing intent is not a specification of current
behaviour, and it is not false as written — it says what a conforming plugin
*should* provide. The contradiction is that the project simultaneously relies on
those guarantees downstream. `ShizukuEndpointTemplate.kt:242-256` implements
`isSafeFileName`/`isSafeRelativePath` for exactly these properties, but only on
the ADB install path.

### Interpretation adopted

KDoc is documentation, not enforcement. Downstream code must be audited for
whether it *assumes* the documented guarantees — and several places do (P1-F15).

### Residual uncertainty

Whether the KDoc predates or postdates the unhardened paths, i.e. regression vs
never-implemented intent. Git blame was not run.

### Impact

Minor alone, but it explains why P1-F15 and P1-F18 are reachable: the documented
contract creates a false sense of validation.

### Next action

Phase 3 records which declared fields are operational. Phase 25 should list every
declared-but-not-enforced field in MasterRef.

---

## Contradiction C-007 — Alleged missing `CodeBrickEntity` migration (REFUTED)

| Field | Value |
| --- | --- |
| Phase raised | 1 (by a Phase 1 subagent) |
| Raised on | 2026-10-02 |
| Severity | Material (as claimed) |
| Current status | **Refuted** |
| Owning phase | — |
| Evidence | `phase-01/evidence.md` P1-F06 |

### Claim A — as originally raised

A Phase 1 subagent reported that no Room migration creates the `CodeBrickEntity`
table, and that this would crash `CodeBrickDao` queries after upgrade.

### Verification result

**The table-creation absence is real; the crash conclusion is wrong.**

Verified: no migration SQL in the current tree creates `CodeBrickEntity`,
`PluginExecuteStatusEntry`, or the notification-preference table. The migrations
only rebuild `pluginInfo`, `pluginSource`, `pluginStatus`, `environmentInfo`, and
`environmentStatus`.

Refuting the conclusion required the entity-list history. Reading
`RootlessStoreDatabase.kt` at each version bump shows that at the post-reset
baseline (`0c011ef`, version 3) the entity list already contained
`CodeBrickEntity`, `PluginExecutionEntity`, and `NotificationPreferenceEntity`. The
version-1 baseline is created by Room's `onCreate` from that list, so a version-1
database has all eight tables. Migrations 1→5 leave the three non-rebuilt tables
intact. Both a fresh install and a full 1→5 upgrade therefore end with a complete
schema.

### Resolution

**Refuted.** Withdrawn as a live defect. Retained because it is the reason P1-F04
(schemas not exported) matters: without committed schema history this question
cannot be answered from the repository alone and required git archaeology across
version bumps.

### Why this is retained

`AGENTS.md` §17 and `INVESTIGATION_METHOD.md` §14 require refutations on the
record. A later phase must not rediscover the absence, assume a crash, and report
it as new.

### Residual action for Phase 17

Export Room schemas and write `MigrationTestHelper` coverage for the 1→5 chain.
That would have answered the question directly.

---

## Contradiction C-008 — Disagreement on extraction Zip-Slip coverage

| Field | Value |
| --- | --- |
| Phase raised | 1 (by Phase 1 subagents) |
| Raised on | 2026-10-02 |
| Severity | Minor (process) |
| Current status | **Corrected** |
| Owning phase | — |
| Evidence | `phase-01/evidence.md` P1-F14 |

### Claim A — subagent report 1

"4 of 5 extraction functions lack a Zip-Slip check."

### Claim B — subagent report 2

"2 of 10 validated."

### Verification result

Direct enumeration gives a different answer from both:

- **Nine** `ZipInputStream` extraction loops exist across
  `AndroidFileSystemCapabilityGatewayImpl` and
  `AndroidFileSystemUnzipOperatorGatewayImpl`.
- **One** validates containment:
  `AndroidFileSystemUnzipOperatorGatewayImpl.unzipFromFileToDirectory`
  (canonical-path check at `:52-57`). Its single caller is
  `InstallMagiskPluginUseCase.kt:125`.
- **Four** loops are reachable and unprotected: the `Capability` impl's
  `unzipFromFile` (`:184`), `unzipEnvironmentFromFile` (`:233`), `unzipFromUri`
  (`:266`), `unzipEnvironmentFromUri` (`:298`), reached from `PluginGatewayImpl`
  and `EnvironmentGatewayImpl`.
- **Four** loops are unprotected with no callers anywhere: the `UnzipOperator`
  impl's `unzipFromFile` (`:104`), `unzipEnvironmentFromFile` (`:151`),
  `unzipFromUri` (`:184`), `unzipEnvironmentFromUri` (`:221`).

The accurate statement: **1 of 9 protected, covering the Magisk import only; all 4
reachable plugin/environment install paths are unprotected.**

### Resolution

**Corrected.** Both subagent counts were inaccurate. The discrepancy arose because
neither enumerated callers, so neither could separate reachable paths from dead
code, and both counted differently across two classes that share method names.

### Why this is retained

This is a process finding, not a code finding. It is the concrete reason
`INVESTIGATION_METHOD.md`'s rule that subagent conclusions are not automatically
verified was applied in Phase 1. It also establishes that **reachability analysis
is required** for any security finding of this shape.

### Residual action for Phase 7

Any Phase 7 statement about extraction safety must cite the reachable set, not a
raw function count.

---

## Contradiction C-009 — The two official docs disagree on whether `full` mode permits web network

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | 7 — Security Investigation |
| Evidence | `phase-02/evidence.md` P2-A74, P2-A75, P2-B10, P2-B15 |

### Claim A — `docs/adb-modules-api.md`

`SHIZUKU_MODULE_MODE=full` means "Full Trust — no restrictions. Can use Shizuku
service, execute commands, and WebView with internet."

| Field | Value |
| --- | --- |
| Source | `docs/adb-modules-api.md` |
| Reliability level | L2 (official documentation) |
| Version | Shevery `bfc55ce9` |
| Date | 2026-10-02 |
| Evidence location | `docs/adb-modules-api.md:78-84`, cited as P2-B10 |
| Currency | `CURRENT` |

### Claim B — Shevery wiki

"The FULL mode does not grant WebView network access. Even Full Trust modules
cannot make network requests from WebView."

| Field | Value |
| --- | --- |
| Source | `HmnDev-Tech/shevery.wiki` |
| Reliability level | L2 (official documentation, separate repo) |
| Version | `8408921772d0a2d80a2c0b2f63004d8a12632540` |
| Date | 2026-09-30 |
| Evidence location | wiki `ADB-Modules` page, cited as P2-B15 |
| Currency | `CURRENT` |

### Claim C — source

Neither statement describes what the code does. `ModuleSettings.kt:120-174`
implements **every** per-module gate as
`isModuleTrusted(module.id)` OR-ed with the global mode gate (P2-A75), and the
web-network gate is specifically listed among the six gates that trust overrides
(P2-A74, P2-A75).

### Version difference

None. Both docs and the source were read in the same week; the wiki pin is three
days older than the source pin. This is **not** a historical drift.

### The actual problem

**Both claims are true, because "Full" is used to mean two different things, and
neither document says so.**

| Reading | Meaning | Web network? |
| --- | --- | --- |
| `SHIZUKU_MODULE_MODE=full` | the global *mode* | **No** — needs `custom` + `webNetwork` permission |
| module is *trusted* | the per-module *override* | **Yes** — trust bypasses every gate |

A reader who conflates the mode with the override reaches the opposite conclusion
depending on which doc they read.

### Investigation performed

- [x] `ModuleSettings.kt:120-174` read; all six gates enumerated
- [x] `docs/adb-modules-api.md` read for the mode description
- [x] Wiki read for the same question
- [x] `custom` mode's permission set enumerated, confirming `webNetwork` exists

### Resolution

**Unresolved.** Phase 2 can establish the mechanism; it cannot change either
document, and Phase 2 does not own the trust model.

Interpretation adopted: until Phase 7 rules, all statements about web network in a
Shevery module context must name **which** of mode or trust is being discussed.
Phases 8 and 12 inherit this obligation.

### Residual uncertainty

Whether per-module trust overriding the web-network gate is intentional or a
convenient boolean-algebra artefact was not determinable from the source, which
carries no comment on it.

### Impact

Directly affects Phase 8 (WebUI network policy) and Phase 7 (trust model). A
module author reading either doc could reasonably expect or rule out network
access, with opposite outcomes.

### Next action

Phase 7 decides whether this project replicates the override at all.

---

## Contradiction C-010 — Both forks enforce identical limits by different, non-equivalent means

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | 21 — Interoperability Investigation |
| Evidence | `phase-02/evidence.md` P2-A42, P2-A43, P2-A63 |

### Claim A — Shevery

Limits are enforced **during** extraction: the copy loop checks cumulative
extracted bytes against 200 MiB and aborts (P2-A42).

### Claim B — Nightzuku

The same numeric limits are stated, but byte enforcement is a **post-hoc check
after** the whole archive is written (P2-A43).

### Consequence

A zip bomb is fully materialised in Nightzuku and rejected afterwards; in Shevery
it is interrupted mid-extraction. The *stated* limits are identical; the
*guarantees* are not. In the local repository, byte limits are absent altogether
(P2-A78), so the reference is stronger here too.

### Investigation performed

Both extraction paths read at their respective pins and compared; the ordering of
the check relative to the copy loop was traced in each.

### Resolution

**Unresolved.** Both forks are internally consistent; they simply are not
interchangeable at the limit. Resolving this requires a decision about which
semantics this project adopts — a Phase 21 interoperability matter with a Phase 7
security input.

Interpretation adopted: no artifact may state "the limits are the same in both
forks" without this qualification.

### Impact

Phase 7 (resource-exhaustion threat model), Phase 11 (extraction implementation),
Phase 21 (interoperability).

---

## Contradiction C-011 — The official API doc contradicts its own source on four points

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | — (informational; no project action) |
| Evidence | `phase-02/evidence.md` P2-A05, P2-A16, P2-A64, P2-A26, P2-A27 |

### Claim A — `docs/adb-modules-api.md`, read at `bfc55ce9`

The doc and the source shipped in the same repository at the same commit.

| # | Doc says | Source does | Evidence |
| --- | --- | --- | --- |
| 1 | Six "required fields": `id`, `name`, `version`, `versionCode`, `author`, `description` | **Only `id` is enforced.** `name` falls back to `id`; others default to `null`. | P2-A05–P2-A07 vs P2-B01 |
| 2 | `SHIZUKU_MODULE_MODE` is `safe` or `full` | Three values: `safe`, `custom`, `full` | P2-A64 vs P2-B02 |
| 3 | Scripts run as `sh $MODDIR/action.sh` | Runs as `sh -c <entire file contents>` | P2-A26 vs P2-B05 |
| 4 | Working directory is `$MODDIR` | Working directory is the literal `/data/local/tmp` | P2-A27 vs P2-B06 |

### Investigation performed

The doc was read in full (348 lines) and each behavioural statement checked
against the implementation at the same commit.

### Resolution

**All four are settled in favour of source**, because source was read directly at
the same commit and the doc was read at the same commit.

The contradiction is nonetheless recorded as **unresolved**, deliberately. This
project does not edit a third party's documentation, so any future reader who
trusts that doc will be misled, and this register is the only place that warning
exists. Closing it by writing "resolved" would remove the only trace.

This is the practical meaning of `INVESTIGATION_METHOD.md` §13 — a conflict is not
closed by preferring the source you happened to read.

### Interpretation adopted

Every one of the doc's four claims is treated as `CONTRADICTED BY SOURCE` in all
project artifacts. Point 4 in particular is not cosmetic: a module assuming
`pwd == $MODDIR` breaks under the real behaviour, which is why U-018 exists.

### Impact

Module authors porting from the documentation will produce broken modules. Phase
12's catalog trust model should treat "claims compatibility with Shevery" as
insufficient evidence of format correctness.

---

## Contradiction C-012 — Derived-from-Nightzuku provenance is unacknowledged on-platform

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 18 — Licensing / Provenance Investigation |
| Evidence | `phase-02/evidence.md` P2-C03, P2-C04 |

### Claim A — git history

Nightzuku added the module subsystem on 2026-05-06 (`93fe85e7`); Shevery was
created 2026-05-15 and its first module commit `4c58598b` is a **3-hunk delta**
from Nightzuku `d961535` (P2-C04).

### Claim B — platform metadata and documentation

Shevery's GitHub metadata reports `fork: false` and `parent: null`, and Shevery's
README and `docs/` contain **zero** occurrences of "Nightzuku" (P2-C03).

### The problem

The provenance relationship is evident in history and absent from every
on-platform surface. A user or module author has no documented way to learn that
two named projects share a format, or that it originated in the second one.

Both projects are Apache-2.0, so no licence obligation is in question — that is a
Phase 18 judgement, not a Phase 2 finding, and Phase 2 makes no licence claim.

### Investigation performed

Git history compared across both clones; GitHub repository metadata queried;
README and `docs/` searched for attribution.

### Resolution

**Unresolved.** Attribution is the upstream projects' business, not this
project's. Recorded so that Phase 18 examines the real provenance chain rather
than assuming a fork relationship from platform metadata.

### Impact

Phase 18 (licensing/provenance), Phase 21 (who owns the format going forward).

---

## Contradiction C-013 — Package identity and release-asset naming changed across versions

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 13 — Updates / Rollback Investigation |
| Evidence | `phase-02/evidence.md` P2-C06, P2-A14 |

### Claim A — identity

Shevery has been published under more than one application id over time:
`moe.shizuku.*` → `com.hamondev.shevery`, with a revert in between (P2-C06).

### Claim B — release assets

Release-asset filename prefixes changed across versions: `shizuku-*` →
`shevery-*` → `manager-*`. The current latest release is `14.1.0`, asset
`manager-release.apk` (report Appendix B).

### Relevance

`module.prop` supports `updateJson`, `url`, `github`, and `repo` (P2-A14). Any
update mechanism this project builds must tolerate an upstream that has renamed
both its package identity and its asset naming. A keyer that matched on either
would have broken at some point in this history.

### Investigation performed

Release tags and their assets enumerated; package ids compared across the history
window 2026-07-03 onward.

### Resolution

**Unresolved.** Phase 13 owns update/rollback design and this is a direct input.
Phase 2 records the identity churn as fact and makes no prediction about it
continuing.

### Impact

Phase 13 (update/rollback, updater keying), Phase 12 (catalog trust — a renamed
package identity is a supply-chain signal worth checking, not proof of anything).


---

## Register maintenance rules

1. New contradictions are appended here and cross-referenced from the raising
   phase's `REPORT.md` §10 and `evidence.md` section I.
2. `STATUS.md` §5 lists every open contradiction.
3. A contradiction may not be marked `Resolved` without recorded evidence of what
   settled it; the `Resolution` block must name that evidence.
4. `Blocking` contradictions must appear in `STATUS.md` §7 and must not be worked
   around silently in later phases.
5. A retracted entry is never deleted. Retraction is recorded with the check that
   disproved the claim.
6. Phase 25 must audit `MasterRef.md` against every open contradiction; Phase 26
   must not incorporate either side of an unresolved contradiction as settled
   fact.