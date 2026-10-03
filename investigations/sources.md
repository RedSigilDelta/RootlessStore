# ADB Modules App — Investigation Source Register

Central register of sources used by the investigation program.

**Established by:** Phase 0 — Investigation Infrastructure
**Standard:** `investigations/METHOD.md` §14, template
`investigations/templates/SOURCE-RECORD.md`
**Rule:** a source entry establishes what a *source* is. The claim it supports
belongs to the phase evidence ledger (`investigations/phase-NN/evidence.md`).

Entries are appended, never silently rewritten. When a source is found to be
wrong or superseded, add a dated note to its entry and register the affected
claim in `investigations/contradictions.md`.

---

## Naming hazards

Names used in this project's documents can collide with unrelated public
projects. Ambiguity is a fabrication risk, so hazards are recorded explicitly.

### H-001 — "Porter" is a heavily overloaded name

| Field | Value |
| --- | --- |
| Registered by | Phase 0 |
| Date | 2026-10-02 |

Searches for "Porter" in an Android/privilege context return at least two
unrelated projects:

1. **A Shizuku fork** (`eu.darken.porter`, developer handle `d4rken`), which
   matches this project's stated intent ("Porter is the primary intended
   privileged execution backend", `AGENTS.md` §11).
2. **An unrelated deployment platform** (`porter.run`) with its own SDKs, MCP
   server, and documentation, which dominates generic search results for
   "Porter SDK".

Recorded status: `LEAD` for both. Neither identity, artifact, nor version has
been established for this project. `PLAN.md` Phase 4 owns establishing the
exact Porter dependency and version.

**Consequence:** Phase 4 must state the artifact identity (repository, owner,
package name, release tag) before any Porter API claim. Until then, all Porter
API knowledge in this program is `UNKNOWN`.

### H-002 — "Shizuku" covers at least three distinct artifacts

| Field | Value |
| --- | --- |
| Registered by | Phase 0 |
| Date | 2026-10-02 |

"The Shizuku API", "the Shizuku server", and "the Shizuku app" are different
artifacts with independent versions and behaviours. Additionally, one search
result observed during Phase 0 describes a fork that maintains compatibility
with "Shizuku API clients" by emulating provider authorities and intent
filters — evidence that forks exist and that `dev.rikka.shizuku` API
compatibility may be provided by something other than upstream Shizuku.

Consequence: Shizuku findings must name client API version, server
implementation, and implementation version (`METHOD.md` §7.3).

---

## Local project sources

These are the primary implementation sources available without network access.
They are the foundation of Phase 1 and the baseline for later phases.

### S-001 — This repository (Rootless Store fork)

| Field | Value |
| --- | --- |
| Project | Rootless Store (fork used as the ADB Modules App research base) |
| Role in this project | Project foundation under investigation |
| Repository URL | `https://github.com/RedSigilDelta/RootlessStore.git` |
| Local path | `/mnt/sdcard/Git/RootlessStore` |
| Branch | `main` |
| Commit checked | `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba` |
| Commit date | 2026-10-02 |
| Application version | `versionName 2.3.1`, `versionCode 2` (`app/build.gradle.kts`) |
| License | AGPL-3.0 (`LICENSE`) |
| Reliability level | L1 — Primary Implementation Evidence |
| Date checked | 2026-10-02 |
| Currency | `CURRENT` for the recorded commit |
| Phases using this source | 0, 1, and all later phases |

#### S-001a — Historical revisions of S-001 examined in Phase 1

Phase 1 required git history to settle two questions that the current tree alone
cannot answer: whether the Room schema is upgrade-safe (P1-F05, P1-F06), and when
the current entity set entered the `@Database` declaration. These revisions were
read with `git show` and are recorded so any later phase can reproduce the
conclusion.

| Commit | Subject | Why examined | What it established |
| --- | --- | --- | --- |
| `efab664` | `chore(database): Remove legacy room migrations` | Suspected schema/identity conflict | `version = 5` → `version = 1`; `addMigrations(4)` → `addMigrations()`; DB file name `RootlessStoreDataBase` unchanged. Basis of U-007 and P1-F05. |
| `0c011ef` | `refactor(data)!: Move data providers into the data module` | Entity-list history | At `version = 3` the entity list already contained `CodeBrickEntity`, `PluginExecutionEntity`, `NotificationPreferenceEntity`. Decisive for the C-007 refutation. |
| `f10cd29` | `refactor(plugin)!: Separate plugin status persistence` | Entity-list history | `version = 4`; adds `PluginStatusEntity`. |
| `67bab13` | `refactor(environment)!: Separate environment status persistence` | Entity-list history | `version = 5`; adds `EnvironmentStatusEntity`. Current HEAD state. |
| `217ec09` | `feat(codebrick): Add Code Brick storage and execution` | When CodeBricks entered the project | CodeBrick support postdates the original database, so its table required a migration path. |
| `e3eef67` | Merge of `feature/add_code-bricks_system-tile-binding` | CodeBrick tile origin | Establishes that Quick Settings tile binding arrived via PR #56, relevant to checklist item 3.3/3.6. |

Reliability level: **L1 — Primary Implementation Evidence (historical)**.
These revisions are labelled `Historical` per `AGENTS.md` §33. Behaviour observed
at them must not be stated as current behaviour.

### S-001b — Subagent research reports used as leads only

Four read-only research reports were generated during Phase 1 and retained outside
the repository:

| Report | Subject |
| --- | --- |
| `/tmp/opencode/phase1-market.md` | Market, sources, paging, trust |
| `/tmp/opencode/phase1-codebrick.md` | CodeBrick architecture and promotion |
| `/tmp/opencode/phase1-persist-notif-webui.md` | Persistence, notifications, WebUI |
| `/tmp/opencode/phase1-lifecycle.md` | Plugin lifecycle, execution, recovery |

These are **not sources**. They are unverified working notes whose value was to
locate candidate files. Every claim in them was re-derived from source before use;
three were corrected or refuted on the record (C-007, C-008, and the
notification/page-size items listed in `phase-01/evidence.md` Part 6). They are
listed here only so a later phase does not mistake a scratch file for evidence.

### S-002 — Upstream Rootless Store

| Field | Value |
| --- | --- |
| Project | Rootless Store (upstream) |
| Role in this project | Upstream origin of the fork; provenance and drift reference |
| Repository URL | `https://github.com/Resilien-Mobile/RootlessStore` |
| Provenance evidence | `README.md` lines 127, 327, 329 (fork relationship stated by the project) |
| Wiki URL | `https://resilien-mobile.github.io/RootlessStore_WiKi/` (`README.md` line 6) |
| Version checked | `Unknown` — not inspected during Phase 0 |
| License | `Unknown` — the fork carries AGPL-3.0 (`LICENSE`); upstream license requires independent confirmation in Phase 18 |
| Reliability level | L1 once inspected; currently L2 for the fork relationship claim only |
| Date checked | 2026-10-02 (fork relationship only) |
| Currency | `UNKNOWN-CURRENCY` |
| Phases using this source | 18 (licensing/provenance) |

### S-003 — Project documentation set

| Field | Value |
| --- | --- |
| Project | This project's authoritative documents |
| Files | `AGENTS.md`, `PLAN.md`, `INVESTIGATION_METHOD.md`, `ARCHITECTURE.md`, `INVESTIGATION.md`, `MasterRef.md`, `README.md`, `docs/code-style.md`, `docs/storage-model.md` |
| Role | Rules, scope, method, proposed architecture, historical research, consolidated reference, overview, repository conventions |
| Reliability level | L2 for statements of project intent; **not** evidence of external behaviour |
| Date checked | 2026-10-02 |
| Currency | `CURRENT` |
| Phases using this source | all |

Note: `ARCHITECTURE.md` is self-described as "a hypothesis subject to the
investigation program" and `MasterRef.md` is protected during Phases 0–24.
Neither may be cited as evidence of existing behaviour.

---

## External technology sources

Entries below are seeds. Each must be filled in with a verified artifact,
version, and commit by the phase that owns it.

### S-004 — Porter (identity not yet established)

| Field | Value |
| --- | --- |
| Project | Porter — a maintained Shizuku fork, package `eu.darken.porter` |
| Role in this project | Intended primary privileged execution backend |
| Repository URL | `https://github.com/d4rken-org/porter` — examined by Phase 2 at `2d88f34bc348552b7fb22eb73ccaba5b9922cce5`. **Whether this is "the Porter" this project means remains a Phase 4 question (U-001)** |
| Documentation URL | `Unknown` |
| Version checked | **None.** A commit was read for a negative search only; no version/release was established |
| License | `Unknown` |
| Reliability level | **L1** for the Phase 2 negative result; still `LEAD` for capabilities |
| Date checked | 2026-10-02 |
| Currency | `UNKNOWN-CURRENCY` for capabilities |
| Phases using this source | 4 (owner), 5, 6, 10, 22, 23 |

Constraints on this entry: `AGENTS.md` §11 forbids inventing Porter APIs and
requires every Porter capability to be classified. Until Phase 4 establishes
the artifact, no Porter capability may be recorded as `VERIFIED` or
`DOCUMENTED`.

**Phase 2 negative result (P2-C05).** At `2d88f34b`, a repository-wide
case-insensitive search for `module.prop`, `AdbModule`, and `adb_module` returns
**0 matches**, and no `*module*` directory exists (including in its `porter-api`
submodule). Porter has **no** ADB Module subsystem.

This resolves a Phase 0 hypothesis in the negative direction and corrects a
premise: Porter is **not** the origin of the Shevery ADB Module ecosystem. The
two are unrelated projects by different authors. See S-201/S-202 — the origin is
Nightzuku.

It does **not** resolve U-001. Establishing Porter's identity, version, and API
surface remains Phase 4's job, and no Porter capability is recorded here.

Additional observation recorded during Phase 0 as a `LEAD`: this project
currently declares **no Porter dependency**. Verified by inspecting
`gradle/libs.versions.toml`, all `build.gradle.kts` files, and a repository-wide
search for the string `porter` (no hits in Kotlin, Gradle, or TOML sources).

### S-005 — Shizuku

| Field | Value |
| --- | --- |
| Project | Shizuku |
| Role in this project | Intended compatibility backend; also the mechanism this project already uses today |
| Upstream repository URL | `Unknown` — not verified during Phase 0 |
| Documentation URL | `Unknown` |
| Version used by this project | Client API `dev.rikka.shizuku:api` / `:provider` **13.1.5** (`gradle/libs.versions.toml`) |
| Server implementation used at runtime | `Unknown` — depends on the Shizuku app the user runs; may be a fork |
| License | `Unknown` |
| Reliability level | L1 for this project's *use* of the API; `Unknown` for upstream server behaviour |
| Date checked | 2026-10-02 |
| Currency | `CURRENT` for the declared dependency; server side `UNKNOWN-CURRENCY` |
| Phases using this source | 1, 5 (owner), 8, 9, 10, 22 |

### S-006 — Shevery

| Field | Value |
| --- | --- |
| Project | Shevery |
| Role in this project | Primary reference implementation for the ADB Module compatibility contract |
| Candidate repository | `https://github.com/HmnDev-Tech/shevery` (also observed: `zax4r0/shevery`) |
| Documentation observed | `docs/adb-modules-guide.md`, `docs/adb-modules-api.md` within the repository |
| Version / commit checked | `None` — not inspected during Phase 0 |
| License | `Unknown` — `INVESTIGATION.md` §43 records a README statement that code files are Apache-2.0; **not verified** |
| Reliability level | `LEAD` |
| Date checked | 2026-10-02 |
| Currency | `UNKNOWN-CURRENCY` |
| Phases using this source | 2 (owner), 12, 18, 21 |

Phase 2 owns pinning a reference commit.

### S-007 — libsu

| Field | Value |
| --- | --- |
| Project | libsu (`com.github.topjohnwu.libsu`) |
| Role in this project | Root shell acquisition used by the current execution path |
| Repository URL | `https://github.com/topjohnwu/libsu` |
| Version used by this project | **6.0.0** (`gradle/libs.versions.toml`) |
| Module used | `core` only (no `service`, no `nio`) |
| License | `Unknown` — not verified during Phase 0 |
| Reliability level | L2 for dependency identity; source not yet inspected |
| Date checked | 2026-10-02 |
| Currency | `CURRENT` for the declared version |
| Phases using this source | 1, 18 |

### S-008 — Android platform

| Field | Value |
| --- | --- |
| Project | Android platform / Android Open Source Project |
| Role in this project | Execution environment, permission and background-execution model, WebView behaviour |
| Source | AOSP platform sources; per-API-level behaviour |
| Version checked | `None` — Phase 0 only recorded the project's own SDK configuration |
| Reliability level | L1 when a specific AOSP file/API level is cited |
| Date checked | 2026-10-02 |
| Currency | n/a |
| Phases using this source | 9, 10 (owner), 11, 16 |

---

## Phase 2 sources

These are the concrete sources Phase 2 read. They narrow S-004/S-006 above to
specific commits and file paths.

### S-201 — Shevery source, pinned commit

| Field | Value |
| --- | --- |
| Repository | `https://github.com/HmnDev-Tech/shevery` |
| Commit | `bfc55ce9c8898043f1a5c4896be276d154d08243` |
| Date of commit | 2026-10-02 |
| Branch | `main` (no `master` branch exists; GitHub web search cites `/master/` paths, a search-index artefact) |
| Files read in full | `manager/src/main/java/moe/shizuku/manager/module/` — 6 files, ~2 300 LOC: `AdbModuleManager.kt`, `ModuleSettings.kt`, `ModuleScriptRunner.kt`, `AdbModuleWebViewHost.kt`, `ModuleBridgeInstaller.kt`, `AdbModule.kt` |
| Docs read in full | `docs/adb-modules-api.md` (348 lines), `README.md` |
| Reliability | **L1** — source read directly |
| Evidence | P2-A01–P2-A76 |

### S-202 — Nightzuku source, pinned commit

| Field | Value |
| --- | --- |
| Repository | `https://github.com/kerneldroid/Nightzuku` |
| Commit | `60a8feb65d1a9c95692624222ef26afb3063b9d3` |
| Date of commit | 2026-07-20 |
| Application ID | `kerneldroid.nightzuku` |
| License | Apache-2.0 (LICENSE file at the pinned commit) |
| Role | **Origin** of the ADB Module subsystem (module commit `93fe85e7`, 2026-05-06) |
| Reliability | **L1** |
| Evidence | P2-A16, P2-A43, P2-A63, P2-C04 |

### S-203 — Shevery wiki

| Field | Value |
| --- | --- |
| Repository | `https://github.com/HmnDev-Tech/shevery.wiki.git` |
| Commit | `8408921772d0a2d80a2c0b2f63004d8a12632540` |
| Date checked | 2026-09-30 |
| Reliability | **L2** — official, but a separate repository, and it documents a `window.Shizuku.runShell` method that **does not exist** in source (P2-B03) |
| Caveat | Read second-hand by a subagent from a clone of the wiki repository. Citations are to line numbers in that clone, not to rendered URLs. Re-verify before Phase 25 relies on them |
| Evidence | P2-B03, P2-B14, P2-B15 |

### S-204 — Porter, pinned commit (negative result)

| Field | Value |
| --- | --- |
| Repository | `https://github.com/d4rken-org/porter` |
| Commit | `2d88f34bc348552b7fb22eb73ccaba5b9922cce5` |
| Application ID | `eu.darken.porter` |
| Self-description | "A minimal, maintained Shizuku fork" |
| Reliability | **L1** for the negative result; does **not** resolve U-001 |
| Purpose | Establish that Porter and Shevery are unrelated projects and that Porter has no module subsystem (P2-C05) |

### S-205 — GitHub repository metadata (platform evidence)

| Field | Value |
| --- | --- |
| Source | GitHub REST API: `/repos/{owner}/{repo}` and `/search/repositories` |
| Queries | Shevery and Nightzuku metadata; `topic:shevery-modules` (13 repositories, 2026-10-02 snapshot) |
| Reliability | **L3** — platform index, not authoritative for format semantics |
| Purpose | Establish `fork: false` / `parent: null` for Shevery, and the absence of any specification repository (P2-C01, P2-C03) |
| Caveat | A negative search result scopes what is publicly indexed, not what exists (P2-C01) |

### S-206 — Nightzuku module-subsystem commit

| Field | Value |
| --- | --- |
| Commit | `93fe85e7983c377b74254e988a34c8caf9b34ed3` |
| Date | 2026-05-06 |
| Repository | `kerneldroid/Nightzuku` |
| Role | First appearance of the module subsystem — establishes the format's origin date |
| Evidence | P2-C04 |

### S-207 — Shevery first module commit

| Field | Value |
| --- | --- |
| Commit | `4c58598b6151c87f409d43aba7be58b073c7777d` |
| Date | 2026-05-15 |
| Repository | `HmnDev-Tech/shevery` |
| Relationship | A **3-hunk delta** from Nightzuku `d961535` |
| Caveat | Compared at commit level, not a full diff audit. Phase 18 should redo this properly |
| Evidence | P2-C04 |

### S-208 — Shevery `AXERON` environment commit

| Field | Value |
| --- | --- |
| Commit | `f223250ead28320b6cc1c2b8df478105c117847e` |
| Date | 2026-05-24 |
| Purpose | Introduced `AXERON=true` and `AXERONVER=1.0.0` into the module environment, plus the `su` shim |
| Significance | Evidence that Shevery was chasing compatibility with a **third** ecosystem. Both variables are absent from Nightzuku |
| Evidence | P2-A56, P2-A61, P2-A62 |

### S-209 — Shevery release tags (identity and asset naming)

| Field | Value |
| --- | --- |
| Tags examined | `v13.8.0-r26` (commit `0eceefce`) and later, through `14.1.0` |
| Window | 2026-07-03 onward |
| Findings | Application ID changed `moe.shizuku.*` → `com.hamondev.shevery` with a revert in between; release-asset prefixes changed `shizuku-*` → `shevery-*` → `manager-*` |
| Reliability | **L3** — release metadata |
| Evidence | P2-C06, contradiction C-013 |

### S-210 — Local `PluginManifest` contract

| Field | Value |
| --- | --- |
| Path | `PluginManifest.kt` |
| Nature | Kotlin `@Serializable` JSON data class, deserialised with `ignoreUnknownKeys` and `isLenient` |
| Relevance | Establishes that this repository has **no** `module.prop` support and no ADB Module subsystem (P2-A77), and documents `webUiEntryPoint` / `executableFiles` in KernelSU `webroot` terms (P2-A81, P2-A82) |
| Evidence | P2-A77, P2-A81, P2-A82 |

### S-211 — Local ZIP extraction paths

| Field | Value |
| --- | --- |
| Paths | `AndroidFileSystemCapabilityGatewayImpl.kt:183,232,265,297` (unprotected); `AndroidFileSystemUnzipOperatorGatewayImpl.kt:52-57` (the only protected loop) |
| Relevance | Establishes the local validation gap quantified in C-005 |
| Evidence | P2-A78, P2-A79, P2-A80 |

### S-212 — `ARCHITECTURE.md` (proposal, read-only)

| Field | Value |
| --- | --- |
| Sections compared | §25–§34, §26 (manifest), §27–§28 (archive/validation), §29 (storage), §31 (environment), §32–§33 (runtime), §56 |
| Outcome | §26 **wrong in both directions** (U-005 resolved as a mismatch); §29 **confirmed correct** by source (C-002 resolved); §31 six-variable set **correct** as a requirement set; invocation form and cwd **unspecified** → U-018 |
| Edit status | **Not modified.** Corrections recorded in `phase-02/REPORT.md` §8 for an explicit architecture decision (`INVESTIGATION_METHOD.md` §40) |

### S-213 — Third-party module catalog (snapshot, not evidence)

| Field | Value |
| --- | --- |
| Source | GitHub topic `shevery-modules` |
| Snapshot | 2026-10-02, 13 repositories |
| Reliability | **Lead only.** No package contents were downloaded or inspected |
| Purpose | Establish that third-party modules exist and that their READMEs claim compatibility with both Shevery and Nightzuku — a *claim*, not a format definition |
| Not established | Whether any real module depends on the Shevery-only fallbacks — U-014 |

### S-214 — Sources deliberately rejected

| Field | Value |
| --- | --- |
| Rejected | `uptodown.com`, `apkmirror.com`, and similar store mirrors |
| Reason | Repackaged binaries of unverified provenance; inspecting a repackaged artefact is weaker evidence than the public repository at a pinned commit |
| Also not used | Search-result snippets as evidence for any claim (`INVESTIGATION_METHOD.md` §7 — leads only) |


---

## Register maintenance rules

1. Every phase that consults a new source adds an entry here and links the
   evidence IDs it supports.
2. Reliability level is recorded per entry; an entry's level may be raised
   (e.g. L4 → L1) only when the underlying source is actually inspected.
3. A source that cannot be identified is recorded with `Unknown` URLs and
   remains a `LEAD`. It must never be cited as evidence.
4. Naming hazards are permanent entries; they are removed only when the
   ambiguity is structurally resolved.
5. Licensing fields stay `Unknown` until Phase 18 verifies them. Phase 0 makes
   no licensing determination.