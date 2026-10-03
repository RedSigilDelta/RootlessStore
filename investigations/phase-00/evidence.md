# Phase 0 — Evidence Ledger

Phase: **0 — Investigation Infrastructure**
Maintained by: Phase 0 investigation session
Last updated: 2026-10-02
Repository state: `main` @ `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba`

The authority for each claim below is this ledger. The central
`investigations/sources.md` register is the authority for the *source*, not for
the claim.

Field definitions follow `investigations/METHOD.md` §12 and
`templates/EVIDENCE-LEDGER.md`.

---

## A. Claims supported by primary implementation evidence (L1)

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P0-A01 | The repository is a fork of `Resilien-Mobile/RootlessStore` | L1 | `4b35c36b` | `README.md:127`; `git remote -v` → origin `RedSigilDelta/RootlessStore` | Source Code / repository metadata | VERIFIED | CURRENT | High | Upstream URL also at `README.md:327` |
| P0-A02 | `applicationId` and namespace are `com.baidaidai.rootless_store` | L1 | `4b35c36b` | `app/build.gradle.kts:23`, `:38` | Source Code | VERIFIED | CURRENT | High | |
| P0-A03 | App version is `2.3.1` / code `2` | L1 | `4b35c36b` | `app/build.gradle.kts:41-42` | Source Code | VERIFIED | CURRENT | High | |
| P0-A04 | `minSdk 26`, `targetSdk 28`, `compileSdk release(37)` | L1 | `4b35c36b` | `app/build.gradle.kts:24-26`, `:39-40` | Source Code | VERIFIED | CURRENT | High | `ExpiredTargetSdkVersion` lint suppressed at `:20` |
| P0-A05 | Exactly eight Gradle modules are included | L1 | `4b35c36b` | `settings.gradle.kts:27-34` | Source Code | VERIFIED | CURRENT | High | `app`, `illusioncube`, `data`, `domain`, `application`, `ui`, `core`, `service` |
| P0-A06 | Shizuku client dependencies are `dev.rikka.shizuku:api` and `:provider` at `13.1.5` | L1 | `4b35c36b` | `gradle/libs.versions.toml:4`, `:80`, `:121` | Source Code | VERIFIED | CURRENT | High | Both use the same `api` version ref |
| P0-A07 | `com.github.topjohnwu.libsu:core` is declared at `6.0.0`, with no `service` or `nio` variant | L1 | `4b35c36b` | `gradle/libs.versions.toml:6`, `:83` | Source Code | VERIFIED | CURRENT | High | |
| P0-A08 | libsu's `Shell` API is imported in five production files | L1 | `4b35c36b` | `application/.../ObserveExecutionContextUseCase.kt:9`; `data/.../PluginExecutionGatewayImpl.kt:10`; `data/.../KernelVersionDataSource.kt:3`; `data/.../SeLinuxStatusDataSource.kt:5`; `data/.../StoreStatusGatewayImpl.kt:28` | Source Code | VERIFIED | CURRENT | High | Establishes a root-shell path exists; says nothing about whether root is present at runtime |
| P0-A09 | A shell-service AIDL interface exists with `exec`, `command`, `kill`, `installShellPlugin`, `uninstallShellPlugin`, `exportShellPlugin` | L1 | `4b35c36b` | `data/src/main/aidl/IShellService.aidl` | Source Code | VERIFIED | CURRENT | High | Interface shape only; behaviour is Phase 1 |
| P0-A10 | **No Porter dependency or reference exists anywhere in Kotlin, Gradle, or TOML sources** | L1 | `4b35c36b` | `gradle/libs.versions.toml` (full read) + all `build.gradle.kts` + repository-wide search for `porter` across `.kt`/`.kts`/`.toml` | Source Code | VERIFIED | CURRENT | High | Verified negative. Basis for U-001 |
| P0-A11 | The fork's license is GNU AGPL-3.0 | L1 | `4b35c36b` | `LICENSE:1-2` | Source Code | VERIFIED | CURRENT | High | Upstream license remains `Unknown` (U-002) |
| P0-A12 | AGP `9.2.1`, Kotlin `2.4.0`, coroutines `1.11.0`, serialization-json `1.11.0` | L1 | `4b35c36b` | `gradle/libs.versions.toml:3`, `:11`, `:39`, `:45` | Source Code | VERIFIED | CURRENT | High | |
| P0-A13 | Shizuku-related production files exist in `application`, `data`, and `ui` (14 files, inventoried) | L1 | `4b35c36b` | See `phase-00/baseline.md` §6 | Source Code | VERIFIED | CURRENT | High | Inventory only; no behaviour claim |
| P0-A14 | `ARCHITECTURE.md` contains 71 numbered sections; `INVESTIGATION_METHOD.md` 56; `INVESTIGATION.md` 44 | L1 | `4b35c36b` | Section-heading enumeration of each document | Source Code | VERIFIED | CURRENT | High | Needed to cite sections unambiguously |
| P0-A15 | `INVESTIGATION.md` states at `:23` that it does not define the authoritative investigation phase order | L1 | `4b35c36b` | `INVESTIGATION.md:23` | Source Code | VERIFIED | CURRENT | High | Evidence that `INVESTIGATION.md` cannot be a phase-count contradiction source |
| P0-A16 | `docs/storage-model.md` routes Magisk-compatibility plugin work to external storage under `.../files/Magisk`, marked non-persistent | L1 | `4b35c36b` | `docs/storage-model.md:36-60` | Source Code (documentation in-repo) | VERIFIED | CURRENT | High | Verified as *written*; not verified against implementing code (U-006) |
| P0-A17 | `rg` is not installed in this environment | L1 | n/a | `/usr/bin/bash: line 1: rg: command not found` | Experiment (environment probe) | VERIFIED | CURRENT | High | Tooling note for later phases |

---

## B. Claims supported by official documentation (L2)

No external official documentation (L2) was inspected in Phase 0. Phase 0 is
methodological: it defines formats rather than answering external technical
questions. External L2 evidence begins in Phase 1 (Rootless Store) and Phase 2
(Shevery).

Project documents (`AGENTS.md`, `PLAN.md`, `INVESTIGATION_METHOD.md`,
`ARCHITECTURE.md`, `INVESTIGATION.md`, `MasterRef.md`, `README.md`) are recorded
as `S-003` in `investigations/sources.md`. They are L2 for statements of project
*intent* and are **not** evidence of external or runtime behaviour.

| ID | Claim | Level | Version | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P0-B01 | The program consists of 27 phases, 0–26 | L2 | n/a | `PLAN.md:15`, `:19`, `:32`, `:46`; `AGENTS.md` §3–§5 | Official Documentation (project) | VERIFIED | CURRENT | High | Canonical structure; `INVESTIGATION.md` is consistent (P0-A15) |
| P0-B02 | Phase 0 has 13 checklist items | L2 | n/a | `PLAN.md:54-66` | Official Documentation (project) | VERIFIED | CURRENT | High | Coverage table is `REPORT.md` Appendix A |
| P0-B03 | Phase completion requires 14 criteria | L2 | n/a | `PLAN.md:798-818`; `INVESTIGATION_METHOD.md:1188-1204` (§42) | Official Documentation (project) | VERIFIED | CURRENT | High | Operationalised in `METHOD.md` §16 |
| P0-B04 | Source hierarchy is 6 levels, L1–L6 | L2 | n/a | `INVESTIGATION_METHOD.md:185-248` (§6) | Official Documentation (project) | VERIFIED | CURRENT | High | Reproduced in `METHOD.md` §2 and `templates/SOURCE-RECORD.md` |
| P0-B05 | Evidence classification is 6 values | L2 | n/a | `INVESTIGATION_METHOD.md:356-389` (§11) | Official Documentation (project) | VERIFIED | CURRENT | High | `LEAD` recorded in `METHOD.md` §3 as an explicit local extension |
| P0-B06 | Confidence levels are High / Medium / Low | L2 | n/a | `INVESTIGATION_METHOD.md:393-424` (§12) | Official Documentation (project) | VERIFIED | CURRENT | High | `None` recorded in `METHOD.md` §4 as an explicit local extension restricted to `UNKNOWN`/`LEAD` |
| P0-B07 | Currency markers are Current / Historical / Version-specific / Unknown | L2 | n/a | `INVESTIGATION_METHOD.md:305-324` (§9) | Official Documentation (project) | VERIFIED | CURRENT | High | Implemented as `CURRENT` / `HISTORICAL` / `VERSION-SPECIFIC` / `UNKNOWN-CURRENCY` |
| P0-B08 | The required phase report has 14 numbered sections | L2 | n/a | `INVESTIGATION_METHOD.md:1034-1060` (§35) | Official Documentation (project) | VERIFIED | CURRENT | High | Section order preserved in `templates/PHASE-REPORT.md` |
| P0-B09 | Android compatibility range is Android 12–17 | L2 | n/a | `INVESTIGATION_METHOD.md:541-561` (§18) | Official Documentation (project) | VERIFIED | CURRENT | High | Support markers `Supported` / `Partially supported` / `Unsupported` / `Unknown` |
| P0-B10 | Porter requires the 15-item establishment set, with per-capability API/input/output/handle/error/lifecycle/failure/recovery detail | L2 | n/a | `INVESTIGATION_METHOD.md:565-602` (§19) | Official Documentation (project) | VERIFIED | CURRENT | High | Encoded in `templates/BACKEND-COMPATIBILITY-MATRIX.md` |
| P0-B11 | Shizuku must be investigated as a compatibility backend, distinguishing Shizuku / Shevery / ADB Modules App behaviour | L2 | n/a | `INVESTIGATION_METHOD.md:606-637` (§20); `AGENTS.md` §12 | Official Documentation (project) | VERIFIED | CURRENT | High | |
| P0-B12 | The source register must track Rootless Store, Shevery, Porter, Shizuku, and Android platform behaviour | L2 | n/a | `INVESTIGATION_METHOD.md:1304-1326` (§47) | Official Documentation (project) | VERIFIED | CURRENT | High | All five have entries (`S-001`, `S-006`, `S-004`, `S-005`, `S-008`) |
| P0-B13 | MasterRef is protected during Phases 0–24 and edited only via Phase 25/26 | L2 | n/a | `AGENTS.md` §5, §25, §31; `PLAN.md:823-843`; `INVESTIGATION_METHOD.md:1086-1154` (§37–§39) | Official Documentation (project) | VERIFIED | CURRENT | High | |
| P0-B14 | `ARCHITECTURE.md` places `module.prop` in the **ADB Module** manifest model, correctly scoped | L2 | n/a | `ARCHITECTURE.md:637-657` (§26); `:151-165` (§5); `:633` (§25); `:188` (§6) | Official Documentation (project) | VERIFIED | CURRENT | High | Evidence used to **retract** C-004 |
| P0-B15 | `ARCHITECTURE.md` §29 specifies internal app-managed ADB Module storage and §56 excludes Magisk/KSU semantics | L2 | n/a | `ARCHITECTURE.md:707-717`, `:1330-1341` | Official Documentation (project) | DOCUMENTED | CURRENT | Medium | Proposal, not verified against any reference implementation (U-005) |
| P0-B16 | `INVESTIGATION.md` records that Shevery's README *states* code files are Apache-2.0 | L2 | n/a | `INVESTIGATION.md:874` | Secondary/historical | DOCUMENTED | HISTORICAL | Low | Unverified secondary claim; Phase 18 |
| P0-B17 | `INVESTIGATION.md` concludes ADB Modules are not Magisk/KSU systemless modules | L2 | n/a | `INVESTIGATION.md:889` | Official Documentation (project) | DOCUMENTED | HISTORICAL | Medium | Historical claim; no commit-pinned source |

---

## C. Claims from official project discussion (L3)

None. No issue tracker, discussion forum, or maintainer statement was consulted in
Phase 0.

---

## D. Secondary sources (L4)

None. No secondary technical source was relied upon in Phase 0.

---

## E. Community sources and search results (L5/L6) — leads only

These are **leads**. None supports any claim above. They are recorded so a later
phase does not repeat the search.

| ID | Lead | Level | Source | Date seen | Promoted to? | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| P0-E01 | A Shizuku fork exists under package name `eu.darken.porter`, maintained by developer handle `d4rken`, described as allowing ADB Modules to run with Shizuku-like privilege without root | L6 | Search results only | 2026-10-02 | No | Candidate identity for Porter. Not confirmed. Registered as hazard H-001 / unknown U-001 |
| P0-E02 | An unrelated project `porter.run` offers Porter SDKs, an MCP server, and deployment documentation, and dominates generic "Porter" search results | L6 | Search results only | 2026-10-02 | No | Name-collision hazard only (H-001). Not this project's backend |
| P0-E03 | A Shevery repository exists at `github.com/HmnDev-Tech/shevery`, containing `docs/adb-modules-guide.md` and `docs/adb-modules-api.md` | L6 | Search results only | 2026-10-02 | No | Candidate reference repository. Authority and commit unconfirmed (U-003) |
| P0-E04 | A second Shevery repository exists at `github.com/zax4r0/shevery` | L6 | Search results only | 2026-10-02 | No | Candidate alternative; ownership relationship unknown (U-003) |
| P0-E05 | A Shizuku-compatible fork may emulate `dev.rikka.shizuku` provider authorities and intent filters while supporting Shizuku API clients | L6 | Search results only | 2026-10-02 | No | Recorded as hazard H-002. Implies `dev.rikka.shizuku` API compatibility may come from a non-upstream implementation (U-004) |
| P0-E06 | A well-known third-party Android deployment/automation framework lists `Shizuku` and a `Porter`-named Shizuku fork among its supported privilege-escalation methods | L6 | Search results only | 2026-10-02 | No | Third-party corroboration of a Porter-adjacent fork existing. Not authoritative (U-001) |

No lead was promoted. Promoting any of these would require an L1 or L2 source
naming the artifact and version (`investigations/METHOD.md` §2.1 rules 3–4).

---

## F. Inferences

| ID | Inference | Derived from | Classification | Confidence | What would confirm or refute it |
| --- | --- | --- | --- | --- | --- |
| P0-F01 | The fork's privilege model today rests on Shizuku user services plus a libsu root shell, with no abstraction layer between them | P0-A06, P0-A08, P0-A09, P0-A13, P0-A10 | INFERRED | Medium | Phase 1 source inspection of the execution path in `data/.../execution/gateway/` and `data/.../shizuku/` |
| P0-F02 | `compileSdk release(37)` denotes a preview or future SDK identifier rather than a released stable platform, given AGP `9.2.1` | P0-A04, P0-A12 | INFERRED | Medium | Phase 10 must establish which Android release SDK 37 corresponds to before any API-level claim |
| P0-F03 | The existing "Magisk compatible plugin" workflow is more likely a Rootless Plugin compatibility feature than an implementation of the proposed ADB Module feature, given `ARCHITECTURE.md` §56 and `INVESTIGATION.md:889` both exclude Magisk semantics from ADB Modules | P0-A16, P0-B15, P0-B17 | INFERRED | Medium | Phase 1 code inspection of the Magisk-compat path (U-006, contradiction C-002) |
| P0-F04 | Phase 0's reconnaissance of external technologies is insufficient to make any external technology claim, and all external findings must begin at Phase 1 | P0-E01 … P0-E06 | INFERRED | High | Not applicable — this is a statement about evidence sufficiency |

---

## G. Proposals (not facts)

| ID | Proposal | Rationale | Classification | Decision owner |
| --- | --- | --- | --- | --- |
| P0-G01 | Add `LEAD` as an explicit seventh evidence label for this directory | `INVESTIGATION_METHOD.md` §6 treats search snippets/community sources as usable leads; without a label they risk being written as findings | PROPOSED (local extension, documented in `METHOD.md` §3) | Phase 0, self-audited |
| P0-G02 | Add `None` as a fourth confidence level for `UNKNOWN`/`LEAD` entries only | Avoids misrepresenting no-evidence entries as "Low" | PROPOSED (local extension, documented in `METHOD.md` §4) | Phase 0, self-audited |
| P0-G03 | Require a "naming hazards" section in the source register | "Porter" and "Shizuku" both collide with unrelated public projects; ambiguous names are a fabrication source | PROPOSED | Phase 0, self-audited |
| P0-G04 | Require a device/experiment matrix template separate from the evidence ledger | `INVESTIGATION_METHOD.md` §16–§17 demand many fields; a dedicated template prevents omission | PROPOSED | Phase 0, self-audited |
| P0-G05 | Require an explicit checklist-coverage appendix in every phase report | `PLAN.md` §798-818 and `INVESTIGATION_METHOD.md` §42 both require per-item accounting; an appendix makes it auditable | PROPOSED | Phase 0, self-audited |
| P0-G06 | Keep `README.md`'s status block synchronized with committed investigation state, recording the change in the contradiction register | Project-facing status drift is a documentation defect | PROPOSED | Phase 0, self-audited (executed once, see C-003) |

---

## H. Unknowns raised

Cross-reference `investigations/unknowns.md`.

| Unknown ID | Short description | Priority | Owning phase |
| --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Blocking | 4 |
| U-002 | Upstream Rootless Store repository state and license | Medium | 18 |
| U-003 | Reference Shevery commit for the ADB Module compatibility contract | High | 2 |
| U-004 | Shizuku server implementation/version available to users | High | 5 |
| U-005 | Whether `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract | High | 2 |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is or is not the ADB Module feature | High | 1, 2 |

---

## I. Contradictions raised

Cross-reference `investigations/contradictions.md`.

| Contradiction ID | Short description | Severity | Status |
| --- | --- | --- | --- |
| C-001 | Alleged 26 vs 27 phase-count conflict | Minor | **Retracted** — no such statement exists in `INVESTIGATION.md` |
| C-002 | ADB Module storage: internal app-managed vs external Magisk-compat paths | Material | Unresolved |
| C-003 | README phase status vs verified Phase 0 completion | Minor | Resolved — README corrected |
| C-004 | Alleged `module.prop` format conflation in `ARCHITECTURE.md` | Minor | **Retracted** — section misattributed; scope is correct |

---

## J. Experiments performed

None. Phase 0 performed no device tests, no builds, and no network fetches.

Specifically not done, and therefore not claimed:

| Not performed | Consequence | Owning phase |
| --- | --- | --- |
| `./gradlew` build, lint, or test execution | No evidence about build/test health exists | 17 |
| Any device or emulator test | No `OBSERVED` claim is possible in Phase 0 | 9, 10, 16, 17 |
| Any network fetch of an external repository | All external technology identification is `LEAD`-level | 1, 2, 4, 5 |
| Full line-by-line read of `MasterRef.md` | Phase 0 makes no claim that MasterRef is consistent with anything; the Phase 25 audit owns that comparison | 25 |
| Full read of every `ARCHITECTURE.md` section | Phase 0 cites only sections it read (§5, §6, §14/§25/§26/§29/§56 as verified individually) | 22 and per-topic phases |

---

## K. Retired or superseded entries

| Original ID | Original claim | Superseded by | New classification | Date | Reason |
| --- | --- | --- | --- | --- | --- |
| C-001 / Claim A | "`INVESTIGATION.md` states the program consists of 26 phases" | P0-A15; `grep` results recorded in C-001 | **Retracted** | 2026-10-02 | Source inspection found no phase-count statement; the original note confused 44 document sections with phases |
| C-004 / Claim A | "`ARCHITECTURE.md` §14 lists `module.prop` under 'Required information', conflicting with `AGENTS.md` §13" | P0-B14 (`ARCHITECTURE.md:637-657`, `:151-165`, `:633`, `:188`) | **Retracted** | 2026-10-02 | Wrong section number and correct scoping; §26 is the ADB Module Manifest and §5 places `module.prop` under the ADB Module heading with "It retains its own" |
| STATUS.md §5 (draft) | C-002 described as a `module.prop`/§14 conflict | C-004 | Retracted | 2026-10-02 | `INDEX.md` and `STATUS.md` now reference C-002 as the storage conflict and list C-001/C-004 as retracted |
| `sources.md` S-004 (draft) | Porter artifact recorded as `eu.darken.porter` fork | P0-E01 kept as `LEAD`; `sources.md` S-004 keeps repository URL `Unknown` | LEAD | 2026-10-02 | An unverified search result must not appear as a repository URL |