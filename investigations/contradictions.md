# ADB Modules App — Investigation Contradictions Register

Central register of significant contradictions between sources.

**Standard:** `investigations/METHOD.md` §8, template
`investigations/templates/CONTRADICTION.md`
**Method authority:** `INVESTIGATION_METHOD.md` §13 (Conflicting Sources), §14
(Contradiction Record), §46 (Contradictions Register)
**Opened by:** Phase 0 — Investigation Infrastructure
**Last updated:** 2026-10-02 (Phase 4)

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

Phase 3 additions:

| ID | Title | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-014 | `ARCHITECTURE.md`/`MasterRef.md` imply tracked module service processes; the reference tracks none | Material | **Unresolved** | 6, 9 |
| C-015 | Documents require persisted process state to be distinguished from verified state; local code verifies nothing before killing | Material | **Unresolved** | 14, 7 |
| C-016 | The forks enforce the same lifecycle by non-equivalent means and diverge on update tiers | Material | **Unresolved** (extends C-010) | 13, 21 |
| C-017 | The reference's update path cannot fail safely, yet is presented as an ordinary operation | Minor | **Unresolved** | 13 |
| C-018 | Update is destructive in the reference and silently merging locally; neither is safe | Minor | **Unresolved** | 13 |
| C-019 | `ARCHITECTURE.md` §7/§50 classify the existing execution machinery as `KEEP`/`ADAPT` for Porter; Porter's bridge cannot run it | Material | **Unresolved** | 6 |
| C-020 | Porter's dependency set coexists with upstream `provider`, but `shizuku-compat` would crash the app — and `shizuku-bridge` is offered for exactly this app's shape | Minor | **Unresolved** | 6 |
| C-021 | Porter's docs describe `exec` as a plain command runner; it is implemented on a bound user service with per-connection caching | Minor | **Unresolved** | 6 |

Open contradictions after Phase 3: **12** (C-005, C-006, C-009, C-010, C-011,
C-012, C-013, C-014, C-015, C-016, C-017, C-018). C-002 resolved.
Blocking contradictions: **0** throughout the program.

C-016 is recorded as an **extension of C-010**, not a replacement. C-010 was about
*how* the same numeric limits are enforced during extraction; C-016 is about the
*lifecycle* the two forks implement around those limits — in particular that
update discovery has three tiers in Shevery and two in Nightzuku, and that
Nightzuku's model cannot even represent prop-driven update state (P3-A79,
P3-A80). Both entries stay open; neither subsumes the other.

C-014 and C-015 are contradictions between **this project's own documents** and
**the code**, not between external sources. They are recorded here because
`AGENTS.md` §17 requires the disagreement be preserved rather than quietly edited
away, and because Phase 3's remit included "verify existing claims rather than
assuming they are correct". Neither document was modified: `ARCHITECTURE.md` is
the current proposal and Phase 3 only records that its §33/§36/§38 are contradicted
by source, while `MasterRef.md` is protected until Phase 25/26.

Phase 4 additions:

| ID | Title | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-019 | `ARCHITECTURE.md` §7/§50 classify the existing execution machinery as `KEEP`/`ADAPT` for Porter; Porter's bridge cannot run it | Material | **Unresolved** | 6 |
| C-020 | Porter's dependency set coexists with upstream `provider`, but `shizuku-compat` would crash the app — and `shizuku-bridge` is offered for exactly this app's shape | Minor | **Unresolved** | 6 |
| C-021 | Porter's docs describe `exec` as a plain command runner; it is implemented on a bound user service with per-connection caching | Minor | **Unresolved** | 6 |

Open contradictions after Phase 4: **15** (C-005, C-006, C-009, C-010, C-011,
C-012, C-013, C-014, C-015, C-016, C-017, C-018, C-019, C-020, C-021).
C-002 resolved. Blocking contradictions: **0** throughout the program.

**C-019 is the most consequential contradiction the program has raised.** Phase 4
established from primary source that Porter's Shizuku bridge throws
`UnsupportedOperationException` for `bindUserService`, `peekUserService` and
`unbindUserService` (`porter-api` `docs/api-reference.md:164`, tag `0.9.0`), while
this project executes privileged work exclusively through `Shizuku.bindUserService`
at 14 call sites. `ARCHITECTURE.md` §50 lists "execution contexts" and "privileged
execution" under `ADAPT` — components whose concepts remain useful. For Porter that
is understated: the code cannot run at all, so the classification for that backend is
`REPLACE`.

The contradiction is left **open**, not resolved, because reclassifying a component
is an architecture decision (`INVESTIGATION_METHOD.md` §40, §53) and Phase 4's remit
was to establish the evidence and record the conflict. Phase 6 owns it. Both documents
were left unedited: `ARCHITECTURE.md` is the current proposal, and `MasterRef.md` is
protected until Phase 25/26.

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

## Contradiction C-014 — The project's documents imply tracked module service processes; the reference tracks none

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | 6 — Execution Abstraction; 9 — Background Services |
| Evidence | `phase-03/evidence.md` P3-A39, P3-A40, P3-A51, P3-A62, P3-A105 |

### Claim A — the project's documents

`ARCHITECTURE.md` §33 describes a "Service Runtime" with "Runtime Tracking" and
"Recovery / Persistence" in its data flow, and §36 describes "Process Handle /
Identity" and "Runtime State". `MasterRef.md` §31 says the reference behaviour
"includes automatically running enabled services once per relevant Shizuku binder
session", and §32 tells the reader to "Track runtime" and "Persist state".

Both read as though a module service becomes a tracked, persisted process.

### Claim B — the reference implementation

Nothing in the module subsystem retains a process handle, a PID, or any execution
record:

- `setEnabled` writes or deletes a marker file and does nothing else; it kills
  nothing (P3-A39).
- A repository-wide search for `destroy()` / `kill(` under the module subsystem
  returns exactly two hits: the 120-second script timeout, and the WebUI bridge's
  own timeout. Neither is a service-lifecycle mechanism (P3-D05).
- `service.sh` is bounded by the **same** 120-second `MAX_SCRIPT_SECONDS` as
  `action.sh`, and a timeout yields synthetic exit code 124 (P3-A51).
- There is no persisted execution record, PID or journal of any kind (P3-A105).

So a module service is a bounded, fire-and-forget invocation that the host waits
for and then forgets. It is closer to a cron entry than to a daemon, and closer to
a Magisk `service.sh` **launcher** than to a supervising host.

### Consequence

There is **no `Running → Stopped` transition** for a module service in the
reference contract. "Running" is not a state the reference can observe, and
"Stopped" is not a transition it can perform. Consequently:

- Disabling a module does not stop it (P3-A39).
- Uninstalling a module does not stop it (P3-A95).
- Nothing in the UI can honestly display "this service is running".

This affects `MasterRef.md` §31/§32 and `ARCHITECTURE.md` §33/§36, which currently
cannot both be right and the code.

### Investigation performed

`AdbModuleManager`, `AdbModule`, `ModuleSettings`, `ModulesScreen`,
`ModuleWebViewActivity` read in full at `bfc55ce9`; the same subsystem read at
`60a8feb6` and compared; the kill/destroy and execution-record searches run
repository-wide and recorded as negative results P3-D03 and P3-D05.

### Resolution

**Unresolved.** The factual side is settled: the reference tracks nothing. What is
*not* settled — and cannot be settled by Phase 3 — is what this project should do:

- Matching the reference exactly means **no** process tracking for ADB Module
  services, which is a weaker and less safe product than `ARCHITECTURE.md` §36
  promises.
- Diverging means `service.sh` gains supervision it was never designed for, which
  risks breaking modules that assume the script is simply run once.

`AGENTS.md` §24 forbids adopting one implementation's semantics as the
architecture, and `INVESTIGATION_METHOD.md` §40 requires an explicit decision
rather than a silent edit. Phase 3 records the conflict and makes no choice.
Recorded as `P3-AR03` and `CONTRADICTED` in `phase-03/REPORT.md` §8.

Interpretation adopted meanwhile: no artifact may state that the reference
provides a tracked or supervising module service runtime.

### Impact

Phase 6 (execution handle and execution state model), Phase 9 (background
services), Phase 14 (runtime recovery), Phase 22 (stress-testing multiple
simultaneous executions), Phase 25 (`MasterRef.md` §31/§32 audit).

---

## Contradiction C-015 — Documents require verified process state; the local code verifies nothing before killing

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | 14 — Runtime Recovery Investigation; 7 — Security |
| Evidence | `phase-03/evidence.md` P3-A118, P3-A120, P3-A121, P3-A122, P3-A123 |

### Claim A — the project's documents

`ARCHITECTURE.md` §36 states: "The application must distinguish persisted process
information from verified active process state." §38 says persistence "must not
blindly claim that a process remains active". `MasterRef.md` §59 says a persisted
PID "may become stale, reused, unavailable, or invalid" and therefore "runtime
recovery must verify actual state."

Both documents state the requirement as a rule of the system.

### Claim B — the local implementation

`RecoverPluginRuntimeStateUseCase` iterates every persisted execution row and, for
each one, immediately issues a kill. There is no liveness probe, no `kill -0`, no
`/proc/<pid>/cmdline` match against the plugin's entry point, and no start-time
comparison to detect PID reuse (P3-A121). Recovery therefore **terminates rather
than reconciles** (P3-A120).

The two branches also have opposite failure semantics:

- Non-ADB context: abort, disable, delete the row — unconditionally (P3-A119).
- ADB context: abort, then disable and delete **only if the kill reported
  success**. `abortPluginProcessByShizuku` returns `processAbortResult != null`,
  which is `true` whenever the call was non-null even if the kill failed — and
  `false` when Shizuku is absent (P3-A118). So with Shizuku unavailable the row
  and the enabled flag both survive, and there is no retry (P3-A123).

The kill itself is `kill -9 $pluginProcessPid` built by string interpolation into a
shell command, with the PID typed `Int` but neither range-checked nor quoted
(P3-A122).

### Consequence

A legitimately running plugin is killed on every app restart, and a stale row whose
PID has been recycled kills an unrelated process. The documents describe the
opposite: verification before action.

Severity note: the **absence** of a check is `VERIFIED` from source. Whether a
recycled PID is actually killed is a runtime fact and is **not** established — see
U-020, which Phase 14 owns.

### Investigation performed

`RecoverPluginRuntimeStateUseCase`, `PluginExecutionGatewayImpl`,
`PluginExecutionRepositoryImpl`, `PluginExecutionDao`, `PluginExecutionEntity`,
`MainActivity` read at `6df93ae`; recovery-callers and PID-check searches run
repository-wide and recorded as P3-D11 and P3-D12.

### Resolution

**Unresolved.** The static fact is settled and Phase 3 makes no claim beyond it.
What Phase 14 must decide is the reconciliation contract: what "verified active"
means operationally, which check is cheap enough to run at every app start, and
what happens when the verification itself is inconclusive.

Interpretation adopted meanwhile: `MasterRef.md` §59 and `ARCHITECTURE.md` §36 are
recorded as **requirements**, not as descriptions of current behaviour. Recorded
as `P3-AR08` and `CONTRADICTED` in `phase-03/REPORT.md` §8, and as security finding
`P3-S11`.

### Impact

Phase 14 (runtime recovery — owns it), Phase 7 (privilege-boundary enforcement),
Phase 15 (diagnostics — an unexplained kill is currently untraceable), Phase 25
(`MasterRef.md` §59/§62 audit).

---

## Contradiction C-016 — The forks implement the same lifecycle by non-equivalent means (extends C-010)

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | 13 — Updates and Rollback; 21 — Interoperability |
| Evidence | `phase-03/evidence.md` P3-A79, P3-A80, P3-A126, P3-A127; `phase-02/evidence.md` P2-A42, P2-A43 |

### Claim A — Shevery @ `bfc55ce9`

Update discovery has three tiers, in order: (1) `updateJson` from `module.prop`,
(2) a catalog match on `moduleId` (case-insensitive), (3) `url`/`repo` prop parsed
for a GitHub repository, then the Releases API. A tier only short-circuits if it
*finds* an update, so an `updateJson` reporting "no update" falls through to the
next tier. `AdbModule` carries `url`, `updateJson` and `updateInfo` fields
(P3-A79, P3-A80 — the Nightzuku side).

Its `BootCompleteReceiver` gates ADB boot-start on `getStartOnBootAdb()` plus
several device conditions, requires `AdbArm` (which accepts either
`WRITE_SECURE_SETTINGS` or Device Owner), requires `NEARBY_WIFI_DEVICES` on API 33+,
requires `ACCESS_LOCAL_NETWORK` on API 37 (Android 17), and defers on a pre-S
keyguard with a 120-second timeout (P3-A126).

### Claim B — Nightzuku @ `60a8feb6`

Update discovery has two tiers: `updateJson`, then a fallback that **assumes the
repository name equals `module.id`** (`repoName = module.id`, P3-A79). Its
`AdbModule` has no `url`, `updateJson` or `updateInfo` field at all, so
prop-driven update state is not representable in its model (P3-A80).

Its `BootCompleteReceiver` gates ADB boot-start on `WRITE_SECURE_SETTINGS` **and**
`lastLaunchMode == ADB`, with no `AdbArm` Device-Owner fallback, no pre-S keyguard
deferral and no Android-17 local-network gate (P3-A127).

### Consequence

Two modules with identical `module.prop` content can behave differently: one
auto-updates in Shevery and not in Nightzuku, because the catalog tier and the
`url`/`repo` tier exist only in Shevery. Two devices with the same ROM can fail to
auto-start the server on boot for reasons specific to their fork.

This is the lifecycle-level counterpart of C-010, which covers extraction limits.
Both remain open; C-016 does not subsume C-010.

### Investigation performed

Both `module/update/` trees read at their pins and the three-tier / two-tier
precedence compared; both `AdbModule` data classes compared field by field; both
`BootCompleteReceiver` files diffed in full.

### Resolution

**Unresolved.** Both forks are internally consistent. The portable intersection is
the defensible target — the same framing Phase 2 reached for the package format
(§8.5 of the Phase 2 report) — but adopting it is a decision, not an observation.

Version caveat recorded rather than hidden: the Nightzuku pin (`60a8feb6`,
2026-07-20) is roughly 2.5 months older than the Shevery pin (`bfc55ce9`,
2026-10-02). The divergence is therefore established *at these two pins*; this
entry does not establish which fork moved, or whether Nightzuku has since
converged. U-012 already tracks the direction-of-travel question.

Interpretation adopted meanwhile: no artifact may state "Shevery and Nightzuku
implement the same lifecycle", nor "Nightzuku supports `updateJson` modules from
`url`/`repo`".

### Impact

Phase 13 (update discovery and keying), Phase 21 (cross-fork interoperability and
divergence monitoring), Phase 10 (Android 17 boot behaviour differs by fork).

---

## Contradiction C-017 — The reference's update path cannot fail safely, yet is presented as an ordinary operation

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 13 — Updates and Rollback |
| Evidence | `phase-03/evidence.md` P3-A23, P3-A24, P3-A83, P3-A87, P3-A90 |

### Claim A — the documentation's framing

Update is presented as a routine maintenance operation: a frequency setting
(`manual`/`daily`/`weekly`), a three-tier discovery chain, and a `ModuleActionResult`
carrying `version`, `versionCode`, `zipUrl` and `changelog`. The UI treats a
positive `UpdateResult` as an install offer.

### Claim B — the implementation

Every one of those affordances sits on top of an operation that cannot fail safely:

- Install deletes the target directory **before** attempting the rename, and
  verifies readability only **after** the rename (P3-A23). An update that fails at
  either point leaves the module **uninstalled** (P3-A24).
- There is no rollback artefact of any kind: no previous version retained, no
  backup, no history (P3-A90).
- Any exception during update discovery is caught and returned as
  `hasUpdate = false` (P3-A83), so a broken endpoint, a rate-limited API and a
  genuinely current module are indistinguishable to the user.
- Because the `disable` marker lives inside the replaced directory, updating a
  disabled module silently re-enables it (P3-A87).

### Consequence

An update is the highest-risk operation in the reference subsystem, and it is the
one with the least user-visible failure signalling. A user who clicks "update" on a
broken release can end up with no module and no error, and their remedy is to find
and reinstall the previous ZIP themselves.

### Investigation performed

`ModuleInstaller.installModule` traced into `AdbModuleManager.install`;
`UpdateChecker.checkUpdate` read in full including its catch block; `ModulesScreen`
update affordance traced to the `UpdateResult` consumer; `setEnabled` and
`readModule` cross-read for the marker-file consequence.

### Resolution

**Unresolved.** Phase 3 can establish that the behaviour is defective; it cannot
decide the replacement, because that is an architecture and product decision
(`INVESTIGATION_METHOD.md` §40, §53 — a research finding is not an automatic
implementation decision).

Interpretation adopted meanwhile: source governs. The reference's update behaviour
is a **defect**, not a compatibility requirement, and this project must not copy it
merely because it is the reference. Recorded as `P3-AR12` and `P3-S02` in
`phase-03/REPORT.md`.

### Impact

Phase 13 (owns the fix), Phase 7 (update as a supply-chain attack surface),
Phase 15 (the failure is currently invisible — error classification), Phase 25
(`MasterRef.md` §74 audit).

---

## Contradiction C-018 — Update is destructive in the reference and silently merging locally; neither is safe

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 13 — Updates and Rollback |
| Evidence | `phase-03/evidence.md` P3-A86, P3-A87, P3-A93, P3-A94 |

### Claim A — the reference

Update replaces the module directory wholesale: extract to staging, delete the
target, rename. Files absent from the new archive are destroyed, including `logs/`,
the `disable` marker, and any module-written state (P3-A86, P3-A87).

Failure mode: **the module is gone.**

### Claim B — this repository

Re-installing over an existing `pluginPackageName` `mkdirs()` the target and
overwrites files in place. There is no staging, no delete and no rollback
(P3-A93). A partially-applied install leaves a **mixture** of old and new files in
one directory with no way to tell them apart, and the database is updated anyway
because `InstallPluginUseCase` still runs `addPlugin` / `registerPluginStatus` after
extraction "succeeds" (P3-A94).

Failure mode: **the module is silently corrupt.**

### Consequence

The two failure modes are opposite and both are unacceptable, so the project cannot
resolve this by picking one implementation. It needs a third behaviour — staged,
validated, atomic replacement with a retained previous version — which is a Phase 13
design task.

The local path is the more dangerous of the two in practice: a corrupt directory
looks installed and behaves unpredictably, whereas a missing directory is at least
visibly missing.

### Investigation performed

Local `unzipFromFile` / `unzipFromUri` / `InstallPluginUseCase` /
`InstallPluginFromMarketUseCase` read at `6df93ae`; reference `install` read at
`bfc55ce9`; the two failure modes contrasted.

### Resolution

**Unresolved.** Recorded so Phase 13 starts from a stated problem rather than from
the assumption that one fork's behaviour is the contract.

Interpretation adopted meanwhile: neither implementation's update semantics may be
cited as the compatibility target. This is an explicit instance of Phase 2's
§8.5 "portable intersection" framing applied to lifecycle rather than format.

### Impact

Phase 13 (owns the fix), Phase 11 (storage layout must accommodate staging and a
retained previous version), Phase 20 (reliability: partial install), Phase 25
(`MasterRef.md` §73/§74 audit).


---

## Contradiction C-019 — The architecture classifies the execution machinery as `ADAPT`, but Porter's bridge cannot run it at all

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Severity | Material |
| Current status | **Unresolved** |
| Owning phase | 6 — Execution Abstraction Investigation |
| Evidence | `phase-04/evidence.md` P4-A126, P4-A127, P4-A128, P4-A129, P4-A130, P4-A132 |

### Claim A — `ARCHITECTURE.md`

§50 classifies Rootless components into `KEEP` / `ADAPT` / `EXTEND` / `REFACTOR` /
`REPLACE`. §7 says the architecture "should reuse those systems when their semantics
are compatible", and §50 lists under `ADAPT` — "components whose concepts remain
useful but need backend/package changes":

- execution contexts,
- WebUI,
- **privileged execution**,
- plugin installation.

### Claim B — the Porter SDK's own contract

`porter-api` at tag `0.9.0`, `docs/api-reference.md:164`:

> User services (`bindUserService`, `peekUserService`, `unbindUserService`) and the
> manager-only calls throw `UnsupportedOperationException`.

`docs/developers.md:358` restates it: "Upstream's user services do not work through the
bridge; use Porter's own."

### Claim C — what this project actually does

`ShizukuUserServiceGatewayImpl.startShizukuUserService()` calls
`Shizuku.bindUserService(args, connection)` with `.tag("shell_service").version(6)
.daemon(true)`, and **14 call sites** obtain that service through
`findShizukuUserService()`. Its privileged logic is its own AIDL
(`IShellService.aidl`, `IShellCallback.aidl`) hosted by `ShizukuEndpointTemplate`
**inside that user-service process**.

### Consequence

None of that code can execute on Porter. Not "needs changes" — it throws before it
runs. Concretely, on the Porter backend these become unavailable until rewritten
against `connection.userService(UserServiceArgs(...))` and
`connection.exec`/`startProcess`:

- plugin execution via `executePluginWithoutEnvironmentByShizuku`
- shell-plugin install, uninstall and export
- the CPU and network status data sources
- daemon-style plugin persistence keyed on a bound user service

`ADAPT` — "concepts remain useful but need backend/package changes" — understates
this. For the Porter backend the honest classification is `REPLACE`.

Note what is **not** in conflict: the dependency set. An app keeping upstream's
`dev.rikka.shizuku:api` and `:provider` "can use this SDK for Porter alone"
(P4-A132), and both sides speak Shizuku client API 13. The build stays valid; the
**calls** must change. That asymmetry is why the contradiction is about
architecture rather than packaging.

### Investigation performed

`porter-api` cloned and `docs/api-reference.md` read in full (179 lines);
`docs/developers.md` read in full (392 lines); `ShizukuUserServiceGatewayImpl.kt`,
both local AIDL files and `ShizukuEndpointTemplate.kt` read; all
`findShizukuUserService()` call sites enumerated across `application/` and `data/`.

### Resolution

**Unresolved.** The evidence is settled; the decision is not. Reclassifying a
component in the architecture is an explicit architecture decision
(`INVESTIGATION_METHOD.md` §40 — no silent corrections; §53 — a finding is not an
automatic implementation decision). Phase 4 established the conflict. Phase 6 owns
the reclassification, because Phase 6 owns the abstraction the reimplementation is an
instance of.

Interpretation adopted meanwhile: no artifact may state that the project's existing
execution code is `ADAPT`-compatible with Porter, or that Porter is a drop-in
replacement for the Shizuku backend. Recorded as `CONTRADICTED` (P4-AR02) in
`phase-04/REPORT.md` §8.

### Impact

Phase 6 (owns the fix), Phase 17 (test matrix), Phase 22 (stress-testing the
replacement), Phase 25 (`ARCHITECTURE.md` §7/§50 and `MasterRef.md` §7 audit).

---

## Contradiction C-020 — `shizuku-bridge` is offered for this app's shape, and `shizuku-compat` would crash it

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 6 — Execution Abstraction Investigation (with Phase 17) |
| Evidence | `phase-04/evidence.md` P4-A131, P4-A132, P4-A135, P4-A04 |

### Claim A — Porter's documentation

`docs/developers.md:337-343` offers `shizuku-bridge` for exactly this situation:

> Code written against upstream's `dev.rikka.shizuku:api`, including libraries built on
> it, can run on Porter unchanged through `shizuku-bridge`. It brings `sdk`,
> `sdk-extras` and `dev.rikka.shizuku:api`.

and `docs/developers.md:330-333` offers the native alternative:

> An app that keeps upstream's `dev.rikka.shizuku:api` and `:provider`, for example
> for a library built on them, can use this SDK for Porter alone. Leave out
> `shizuku-compat` and keep upstream's `ShizukuProvider`.

### Claim B — the same documentation's hard constraint

`docs/developers.md:316-320`:

> Declaring that provider without `moe.shizuku.api.BinderContainer` on the
> classpath, which `shizuku-compat` ships, crashes your app on launch, whether or not
> Porter or Shizuku is installed. `dev.rikka.shizuku:provider` ships the same class,
> so **the two cannot both be in one app**, including through another library.

### Claim C — this project

It depends on `dev.rikka.shizuku:api` and `:provider` **13.1.5**, and is written
against upstream's `Shizuku` API — the exact shape `shizuku-bridge` targets.

### Consequence

There is a build-configuration trap with no safe default:

- Adding `shizuku-compat` alongside the existing `provider` **crashes the app on
  launch**, unconditionally.
- Adding `shizuku-bridge` pulls in a Porter connection path that runs the app's
  upstream-API code against Porter — but that path cannot carry this project's
  user services (C-019), so it would only partially work.
- Adding the plain SDK (`sdk` / `sdk-extras`) alongside the existing `provider` is
  documented as safe.

The three options are not equivalent, and the documentation offers all three without
ranking them for an app that keeps `provider`. "Using this SDK for Porter alone"
requires reading three separate paragraphs to establish.

### Investigation performed

`docs/developers.md` read in full; `docs/api-reference.md` bridge section read in
full; the project's `gradle/libs.versions.toml` Shizuku coordinates confirmed at
13.1.5; the collision claim cross-checked against the manifest snippet Porter's own
docs prescribe.

### Resolution

**Unresolved.** Which artifact set this project should ship is an architecture and
build decision for Phase 6. Phase 4 records the hazard so a later phase does not
discover it as a launch crash.

Interpretation adopted meanwhile: no artifact may add `shizuku-compat` to this
project's dependencies. Any Porter integration must either use `sdk`/`sdk-extras`
with the existing `provider`, or use `shizuku-bridge` and accept C-019's limits.

### Impact

Phase 6 (owns the choice), Phase 17 (a build-matrix test should assert the app
launches with each dependency set), Phase 25 (`ARCHITECTURE.md` §59 audit).

---

## Contradiction C-021 — `exec` is documented as a command runner but implemented as a user service

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Severity | Minor |
| Current status | **Unresolved** |
| Owning phase | 6 — Execution Abstraction Investigation |
| Evidence | `phase-04/evidence.md` P4-A45, P4-A54, P4-A55, P4-A56, P4-A65, P4-A133 |

### Claim A — the documentation

`docs/developers.md:142-149` presents `exec` as a direct primitive:

> `sdk-extras` runs a command at Porter's identity and returns its exit code and
> output:
> ```kotlin
> val result = connection.exec("sh", "-c", "pm list packages -3")
> ```

There is nothing in that section about a service, a binding, or startup cost. The
implication is that `exec` is a round trip to the server.

### Claim B — the implementation

`PorterShell.kt` implements `exec` on top of an SDK-managed user service:

- `start()` calls `ShellCalls.binding(this).service(this)` before every command
  (P4-A54);
- the binding is `userService(UserServiceArgs(componentName = PorterShellService,
  processNameSuffix = "porter_shell", tag = "eu.darken.porter.sdk.extras.shell",
  version = 2))` (P4-A54);
- the binding is cached per `PorterConnection` in a `WeakHashMap`, so it is created on
  first use and reused after (P4-A55);
- "One binding for every call, because a Shizuku server older than 13.4 keeps each
  binding it was given until the app's process dies" (P4-A56).

The SDK's own API reference is more candid than the developer guide: "The first
`newProcess`, and the first after the shell service died, also waits for that
service's process to start."

### Consequence

`exec` is not free and not synchronous-with-the-server. On a cold connection, the
first command pays for starting a separate process at the server's identity, and the
cost recurs whenever that service dies or the connection is replaced. A design that
treats `exec` as a cheap per-call round trip will be surprised by latency spikes at
the moments that matter most — first action after connection, first action after a
Porter restart.

The secondary point matters for the backend abstraction: because `exec` is a user
service, the "user service" capability cannot be treated as orthogonal to the "shell
execution" capability. Porter couples them.

### Investigation performed

`PorterShell.kt` read in full (196 lines) including `ShellCalls`, `ShellBinding` and
`start()`; `PorterShellProcess.kt` read in full; both documentation sections read.

### Resolution

**Unresolved.** Source governs — `exec` is a user-service-backed operation, and the
documentation's framing understates the mechanism. Whether this project's backend
contract must model that coupling is a Phase 6 decision.

Interpretation adopted meanwhile: no artifact may describe Porter `exec` as a direct
server round trip, and the Porter adapter must warm the shell service rather than
assume first-call latency.

### Impact

Phase 6 (capability modelling, `P4-AR05`/`P4-AR06`), Phase 19 (performance — first-call
latency is a measurable cost), Phase 25 (`MasterRef.md` §42 audit).

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