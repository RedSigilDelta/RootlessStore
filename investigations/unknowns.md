# ADB Modules App — Investigation Unknowns Register

Central register of unresolved questions.

**Standard:** `investigations/METHOD.md` §13, template
`investigations/templates/UNKNOWN.md`
**Method authority:** `INVESTIGATION_METHOD.md` §15 (Unknowns), §45 (Unknowns
Register)
**Opened by:** Phase 0 — Investigation Infrastructure
**Last updated:** 2026-10-02 (Phase 2)

An unknown is a legitimate investigation result. No entry is closed by
substituting an assumption. Unknowns survive phase completion:
`INVESTIGATION_METHOD.md` §45 — "Do not allow unresolved questions to disappear
merely because a phase is marked complete."

Priority: `Blocking`, `High`, `Medium`, `Low`.

---

## Summary

| ID | Short description | Priority | Status | Owning phase | Blocks Phase 1? |
| --- | --- | --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Blocking (for backend design) | Open | 4 | No |
| U-002 | Upstream Rootless Store repository state and license | Medium | Open | 18 | No |
| U-003 | Reference Shevery commit for the ADB Module compatibility contract | High | **Resolved** (Phase 2) | — | No |
| U-004 | Shizuku server implementation/version available to users | High | Open | 5 | No |
| U-005 | Whether `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract | High | **Resolved** (Phase 2) | — | No |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is or is not the ADB Module feature | High | **Resolved** (Phase 2); residual design question → 11 | 11 | No |

Phase 1 additions:

| U-007 | Real-world impact of the database version reset 5 → 1 in commit `efab664` | High | Open | 14 |
| U-008 | Source authentication, integrity, and update-discovery controls beyond the unauthenticated GET | High | Open | 12 |
| U-009 | Whether the UI prevents executing a plugin whose `isEnabled` is false | Medium | Open | 3 |
| U-010 | What `illusioncube` is and what its presence in `data`/`application` implies | Medium | Open | 6 |
| U-011 | Whether any non-default `PluginState` value is ever produced by any writer | High | Open | 3 |

Phase 2 additions:

| U-012 | Whether Nightzuku and Shevery will converge or diverge further | Medium | Open | 21 |
| U-013 | Whether any format-stability or deprecation policy exists or will be published | Medium | Open | 21 |
| U-014 | Whether third-party module authors depend on the Shevery-only fallbacks (`run.sh`, `main.sh`, `exec.sh`, `late_start.sh`) | Medium | Open | 12 |
| U-015 | Android-version-specific extraction and `setExecutable` behaviour | Medium | Open | 10 |
| U-016 | `IShizukuService.newProcess` env-merge semantics — does the child inherit the server's environment? | Medium | Open | 5 |
| U-017 | Whether any real module depends on `AXERON`/`AXERONVER` or the `su` shim behaviour | Low | Open | 12 |
| U-018 | Intended script invocation form and working directory for this project | High | Open | 6 |

Open unknowns: **15** (was 11; Phase 2 resolved 3 — U-003, U-005, U-006 — and
added 7). One of them — **U-001** — carries `Blocking` **priority**.
Per `METHOD.md` §13, `Blocking` is a priority value, not a program gate: U-001 is
`Open` and blocks only Porter-dependent architecture decisions (Phases 6, 10, 22,
23), which Phase 4 resolves. It does not block Phase 1, Phase 2, or Phase 3.

**U-003 / U-005 resolved in Phase 2.** The authoritative reference is pinned:
`HmnDev-Tech/shevery` @ `bfc55ce9c8898043f1a5c4896be276d154d08243`, 2026-10-02
(origin `kerneldroid/Nightzuku` @ `60a8feb6`). Comparing `ARCHITECTURE.md` §26
against that source found §26's "required" set overstated — only `id` is enforced —
and its optional set incomplete — `updateJson`, `url`, `github`, `shellBridge`,
`repo` and the `run.sh`/`main.sh`/`exec.sh`/`late_start.sh` fallbacks all exist.
The proposed contract is therefore **wrong in both directions**; §26 must not be
marked `VERIFIED` as written. Full field-level comparison is in
`phase-02/REPORT.md` §8.2–§8.3.

**U-006 resolved in Phase 2; C-002 likewise.** Phase 1 established that
`InstallMagiskPluginUseCase` → `unzipFromFileToDirectory` is the only
Zip-Slip-protected extraction path in the repository (P1-F14). Phase 2 verified
that the reference implementation stores modules in app-private
`filesDir/adb_modules` and explicitly disclaims `/data/adb/modules`
(P2-A52, P2-B13), that its `id` regex differs from Magisk's (P2-A17), and that the
local `PluginManifest` is a Kotlin `@Serializable` JSON contract with no
`module.prop` support at all (P2-A77). The Magisk-compat workflow is a **separate
Rootless Plugin feature**, not the ADB Module feature.

The residual question — whether the internal-storage *mechanism* should be shared
between the two package types — is a design decision for Phase 11 and is **not**
tracked as a separate unknown, because it is not an unknown: Phase 2 answered the
factual question and left the design question to its owner.

---

## Unknown U-001 — Porter artifact, version, and API surface

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Priority | Blocking (for any Porter-dependent architecture decision) |
| Status | Open |
| Owning phase | 4 — Porter Investigation |
| Blocks | Phase 6 (Execution Abstraction), Phase 10 (Porter Android compatibility), Phase 22/23 (backend stress/future work). Does not block Phase 1. |

### Unknown

Which exact artifact does "Porter" refer to for this project, at which version,
and what API surface does it actually expose?

### Why unknown

The name is overloaded and the project declares no dependency on it. Nothing in
the repository pins a Porter artifact, and generic search results for "Porter"
are dominated by an unrelated deployment platform (see `sources.md` hazard
H-001).

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `gradle/libs.versions.toml` | L1 | current commit | Any Porter coordinate | Not found |
| All `build.gradle.kts` files | L1 | current commit | Any Porter dependency | Not found |
| Repository-wide search for `porter` in `.kt` / `.kts` / `.toml` | L1 | current commit | Existing Porter integration or references | No hits |
| `AGENTS.md` §11 | L2 | n/a | Porter identity constraints | Requires the five-category capability classification; forbids invented APIs |
| `INVESTIGATION.md` §29–§30 | L2 | n/a | Porter identity or API | Explicitly states the API surface was **not** established |
| `ARCHITECTURE.md` §16 | L2 | n/a | Porter contract | Describes it as proposed |
| Web search (Phase 0 reconnaissance) | L6 | n/a | Official Porter repository | Only two candidates found: a candidate Shizuku fork (`eu.darken.porter`) and an unrelated `porter.run` deployment platform. Neither confirmed as this project's intended artifact. |

### What would resolve it

1. Identify the official Porter project: repository owner, package name, and
   release channel.
2. Pin an exact release tag or commit and record it in `sources.md`.
3. Inspect its API surface: initialization, permission model, execution model,
   process model, output model, termination, lifecycle, failure states, recovery
   (`INVESTIGATION_METHOD.md` §19).
4. For every capability, record API, input, output, handle, error behaviour,
   lifecycle, failure behaviour, and recovery behaviour.

### Current best hypothesis

**None recorded.** No hypothesis is entered. Candidate A (a maintained Shizuku
fork) is plausible from the candidate package name alone, but a package name seen
only in an unverified search result is not evidence, and `AGENTS.md` §11 forbids
treating Porter as Shizuku-shaped. Entering a hypothesis here would risk exactly
the inference the methodology prohibits.

### Cross-phase dependencies

- Phase 5 must not assume Porter and Shizuku are interchangeable.
- Phase 6 must treat the Porter branch of the execution abstraction as
  `UNKNOWN` until this resolves.
- Phase 10 must mark Porter Android-version rows `Unknown`.
- Phase 23 (future backends) depends entirely on this.
- **No phase may state a Porter API as existing.** `AGENTS.md` §11.

---

## Unknown U-002 — Upstream Rootless Store repository state and license

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 18 — Licensing / Provenance Investigation |
| Blocks | Nothing. |

### Unknown

What is the current state of the upstream `Resilien-Mobile/RootlessStore`
repository, and what license governs it — independent of this fork's AGPL-3.0?

### Why unknown

Phase 0 established the fork relationship from this repository's own README only.
Upstream was not fetched, so neither its license, its current version, nor its
divergence from this fork is known.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `README.md:127` | L2 | n/a | Fork relationship | Confirmed: "This repository is a fork of 'Resilien-Mobile/RootlessStore'" |
| `README.md:327-329` | L2 | n/a | Upstream and fork URLs | Both stated |
| `README.md:6` | L2 | n/a | Official wiki URL | `https://resilien-mobile.github.io/RootlessStore_WiKi/` |
| `LICENSE` | L1 | current commit | This fork's license | AGPL-3.0 |
| Upstream repository | — | — | License, current version, divergence | **Not checked** (no network fetch performed in Phase 0) |

### What would resolve it

Fetch the upstream repository, record its license file, its default branch
commit, its latest release/tag, and compute divergence from this fork.

### Current best hypothesis

**None recorded.**

### Cross-phase dependencies

Phase 18 owns this. Until resolved, `sources.md` S-002 keeps upstream license and
version as `Unknown`, and no artifact may state an upstream license as fact.

---

## Unknown U-003 — Reference Shevery commit for the ADB Module compatibility contract

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | **Resolved** — 2026-10-02, Phase 2 |
| Owning phase | — (closed by Phase 2) |
| Resolved by | `phase-02/evidence.md` P2-A01–P2-A19; `phase-02/REPORT.md` §3, §6.1 |

### Resolution (Phase 2, 2026-10-02)

Resolved. The authoritative reference is `HmnDev-Tech/shevery` @
`bfc55ce9c8898043f1a5c4896be276d154d08243` (2026-10-02), package
`com.hamondev.shevery`, Apache-2.0. The module subsystem lives in
`manager/src/main/java/moe/shizuku/manager/module/` (6 files, ~2 300 LOC) and was
read in full. The `zax4r0/shevery` candidate from Phase 0 was not pursued
further. Origin is `kerneldroid/Nightzuku` @ `60a8feb65d1a9c95692624222ef26afb3063b9d3`
(P2-C04). Recorded as S-201/S-202 in `sources.md`.

This does **not** mean the format is stable — see U-012 and U-013.

### Unknown (as originally raised)

Which exact Shevery commit defines the ADB Module format, metadata, script
lifecycle, environment, WebUI surface, and storage layout that this project must
be compatible with?

### Why unknown

Two candidate repositories were observed during Phase 0 reconnaissance
(`HmnDev-Tech/shevery`, `zax4r0/shevery`) and two candidate documents
(`docs/adb-modules-guide.md`, `docs/adb-modules-api.md`). No commit was pinned and
no file was inspected. Phase 0 explicitly does not treat search results as
evidence.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `AGENTS.md` §13 | L2 | n/a | What Shevery compatibility requires | Requires evidence-backed compatibility claims across package format, metadata, files, installation, runtime, WebUI, action, service, trust, execution |
| `INVESTIGATION.md` §14–§27, §40–§41 | L2 | n/a | Shevery findings | Historical claims exist; none source-pinned to a commit |
| `INVESTIGATION.md` §43 (line 874) | L2 | n/a | Shevery license | Records that Shevery's README *states* code files are Apache-2.0 — **not verified** |
| Candidate repositories | L6 | none | Authoritative repo/commit | Not confirmed; not pinned |

### What would resolve it

1. Confirm which repository is the authoritative Shevery project.
2. Pin a commit (and release tag if one exists).
3. Verify the documented format against that commit's source.
4. Record the commit in `sources.md` and cite it in every Phase 2 finding.

### Current best hypothesis

**None recorded.** `INVESTIGATION.md`'s findings are a lead about what to check,
not evidence about what is true.

### Cross-phase dependencies

- Phase 2's compatibility matrix is meaningless without a pinned commit.
- `ARCHITECTURE.md` §26 (manifest fields), §27–§28 (archive/validation), §29
  (storage), §31 (environment variables), §32–§33 (action/service runtime),
  §41 (WebUI security), and §59 (compatibility boundary) all assert a
  *Shevery-compatible* contract that Phase 2 must confirm or mark `CONTRADICTED`.
- Until resolved, no artifact may claim Shevery compatibility as verified.

---

## Unknown U-004 — Shizuku server implementation and version available to users

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 5 — Shizuku Compatibility Investigation |
| Blocks | Phase 6 (backend capability parity), Phase 9 (background services), Phase 10 (Android version interaction). Does not block Phase 1. |

### Unknown

Which Shizuku server implementation and version will be present at runtime, and
does this project's declared client API version (`dev.rikka.shizuku:api` /
`:provider` **13.1.5**) constrain or validate it?

### Why unknown

The client API version is declared in this project and is verified. The *server*
side is whatever Shizuku app the user has installed, which may be upstream
Shizuku or a fork. No server version is pinned, and forks that emulate
`dev.rikka.shizuku` provider authorities were observed as a possibility
(`sources.md` hazard H-002) without confirmation.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `gradle/libs.versions.toml` | L1 | current commit | Shizuku client API version | `dev.rikka.shizuku:api` / `:provider` **13.1.5** |
| `data/src/main/aidl/` | L1 | current commit | AIDL user-service interface | Present (implementation detail recorded in `phase-00/baseline.md`) |
| `ShizukuEndpointTemplate.kt` (path recorded in baseline) | L1 | current commit | Client API usage patterns | Present |
| `AGENTS.md` §12 | L2 | n/a | Shizuku rules | Requires service lifecycle, Binder, UserService, permissions, process execution, termination, reconnection, WebUI, API-level differences; requires preserving `window.Shizuku` |
| Upstream Shizuku repository | — | — | Server version/implementation | **Not checked** |

### What would resolve it

1. Establish the upstream Shizuku project and its current server version.
2. Enumerate the forks that this app must interoperate with, if any.
3. Verify the `dev.rikka.shizuku` API surface against each candidate server.
4. Record per-implementation capability and version differences.

### Current best hypothesis

**None recorded.**

### Cross-phase dependencies

- Phase 5 owns this.
- Phase 8 (WebUI) depends on the `window.Shizuku` compatibility surface, which is
  owed to module authors and is independent of the server implementation.
- Phase 22 must not assume Porter and Shizuku have identical capabilities
  (`INVESTIGATION.md` §30).

---

## Unknown U-005 — Do `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract?

| Field | Value |
| --- | --- |
| Phase raised | 0 (during retraction of C-004) |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | **Resolved** — 2026-10-02, Phase 2 |
| Owning phase | — (closed by Phase 2) |
| Resolved by | `phase-02/evidence.md` P2-A03–P2-A22, P2-B01, P2-B12; `phase-02/REPORT.md` §6.1–§6.2, §8.2–§8.3 |

### Resolution (Phase 2, 2026-10-02)

**Resolved: the fields do not match as written.** `ARCHITECTURE.md` §26 is wrong
in both directions.

| Direction | Finding | Evidence |
| --- | --- | --- |
| Overstated | §26 lists `id name version versionCode author description` as required. **Only `id` is enforced.** `name` falls back to `id`; the rest default to `null`. | P2-A05–P2-A07 |
| Understated | §26 lists 4 optional keys. **Twelve** optional keys exist, including `updateJson`, `url`, `github`, `repo`, `shellBridge`, plus the undocumented `run.sh`/`main.sh`/`exec.sh` action fallbacks and `service.sh`/`late_start.sh` service fallbacks. | P2-A12–P2-A14, P2-B12 |
| Unstated | `id` is constrained to 2–64 chars, first char a letter — this is the *only* fatal validation in the whole format. | P2-A17 |

Consequence recorded as C-011 and as `ARCHITECTURE.md` corrections §8.2–§8.3.
The official Shevery doc's own "required fields" list is also wrong (P2-B01),
which is why this is recorded as a contradiction rather than a resolved point.

`ARCHITECTURE.md` §26 is **not** marked `VERIFIED` and was not edited
(`INVESTIGATION_METHOD.md` §40 — an explicit architecture decision, not a silent
edit).

### Unknown (as originally raised)

Do the required fields `id`, `name`, `version`, `versionCode`, `author`,
`description` and the optional fields `banner`, `webui`, `usesShellBridge`,
`action` in `ARCHITECTURE.md` §26 correspond to fields that actually exist in the
Shevery `module.prop` contract, with the same names, types, and semantics?

### Why unknown

`ARCHITECTURE.md` §26 is a proposal. It has never been compared against the
Shevery source, and no Shevery commit is pinned (U-003). Naming resemblance to
Magisk-style `module.prop` keys is not evidence.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `ARCHITECTURE.md:637-657` (§26) | L2 | n/a | Proposed manifest fields | Recorded; status `PROPOSED` |
| `ARCHITECTURE.md:151-165` (§5) | L2 | n/a | `module.prop` placement | Correctly scoped to ADB Module |
| `INVESTIGATION.md` §16 (`"module.prop" Findings`) | L2 | n/a | Field-level detail | Historical claims only; not commit-pinned |
| Shevery source | — | — | Actual `module.prop` schema | **Not checked** |

### What would resolve it

Resolve U-003 first, then compare `ARCHITECTURE.md` §26 field-by-field against
the pinned Shevery implementation and its documentation. Any divergence is
recorded as a contradiction and any required change to `ARCHITECTURE.md` goes
through an explicit architecture decision, not a silent edit
(`INVESTIGATION_METHOD.md` §40).

### Current best hypothesis

**None recorded.**

### Cross-phase dependencies

Phase 2 must not mark `ARCHITECTURE.md` §26 as `VERIFIED` without this. Phase 7
needs it to reason about metadata trust and provenance. Phase 12 needs it for
catalog field mapping.

---

## Unknown U-006 — Is the existing "Magisk compatible plugin" workflow the ADB Module feature?

| Field | Value |
| --- | --- |
| Phase raised | 0 (raised alongside contradiction C-002) |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | **Resolved** — 2026-10-02, Phase 2 |
| Owning phase | — (closed by Phase 2); residual design question → 11 |
| Resolved by | `phase-02/evidence.md` P2-A17, P2-A52, P2-A77, P2-B13; `phase-02/REPORT.md` §9 |

### Resolution (Phase 2, 2026-10-02)

**Resolved: they are two different features.** Three independent lines of
evidence, none of which requires Phase 11:

1. The reference ADB Module implementation stores modules in app-private
   `filesDir/adb_modules` and **explicitly disclaims** `/data/adb/modules`
   (P2-A52, P2-B13). The external-storage reading in `docs/storage-model.md`
   describes a Rootless Plugin feature, not the module feature.
2. The `id` regex `[A-Za-z][A-Za-z0-9._-]{1,63}` **differs from Magisk's**
   (P2-A17). A Magisk module and an ADB Module are not interchangeable by id.
3. The local `PluginManifest` is a Kotlin `@Serializable` JSON contract with
   `ignoreUnknownKeys` and `isLenient` — there is **no `module.prop` support at
   all** in this repository (P2-A77).

C-002 is likewise resolved: the reference implementation does not promise
external or `/data/adb/modules` storage, so the two documents were describing
different things all along. The naming ambiguity that made it look like a conflict
remains a documentation defect, which is the substance of C-002's resolved state.

The design question that is **not** an unknown and is explicitly **not** answered
here: whether this project should reuse the internal-storage mechanism for both
package types. That belongs to Phase 11.

### Unknown (as originally raised)

Does this repository's existing "Magisk 兼容插件" (Magisk-compatibility plugin)
workflow — which `docs/storage-model.md` routes to external storage under
`/storage/emulated/0/Android/data/com.baidaidai.rootless_store/files/Magisk` —
correspond to the ADB Module feature, or is it a separate Rootless Plugin
compatibility feature?

### Why unknown

`docs/storage-model.md` is documentation, not code. Phase 0 did not inspect the
`data`/`service` modules, so the actual behaviour is unverified. The two readings
lead to different architectures, and `AGENTS.md` §13 forbids assuming they are
the same thing.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `docs/storage-model.md:36-60` | L2 | n/a | Magisk-compat storage paths and persistence semantics | Three external paths documented, marked non-persistent |
| `docs/storage-model.md:4-17` | L2 | n/a | Internal plugin storage | `~/files/Plugin`, persistent |
| `ARCHITECTURE.md:707-717` (§29) | L2 | n/a | ADB Module storage | Internal `/data/user/0/<package>/files/adb_modules/<module-id>` |
| `ARCHITECTURE.md:1330-1341` (§56) | L2 | n/a | Magisk/KSU exclusion | No `/data/adb/modules`, no hooks |
| `INVESTIGATION.md:889` | L2 | n/a | Relationship | "ADB Modules are not Magisk/KSU systemless modules" |
| Implementation source | — | — | Actual storage behaviour | **Not checked — Phase 1** |

### What would resolve it

Phase 1 inspects the storage implementation (`data` and `service` modules) and
establishes what the Magisk-compat workflow actually does today, then compares it
against `docs/storage-model.md`. Phase 2 establishes whether Shevery's ADB Module
format corresponds to it. The answer determines whether contradiction C-002 is a
documentation defect or a genuine architectural conflict.

### Current best hypothesis

**None recorded.** Recording a hypothesis here would itself be the conflation
`AGENTS.md` §13 warns against.

### Cross-phase dependencies

- Phase 1 must treat this as an open question, not assume equivalence.
- Phase 11 must not write a storage contract until this is settled.
- Phase 7's storage trust analysis depends on internal vs external placement.
- Phase 2's compatibility boundary depends on whether two distinct features are
  being conflated.

---

## Unknown U-007 — Real-world impact of the database version reset in `efab664`

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 14 — Runtime Recovery Investigation |
| Blocks | Nothing. Phase 1 completed without resolving it. |
| Evidence | `phase-01/evidence.md` P1-F05 |

### Unknown

Commit `efab664` ("chore(database): Remove legacy room migrations") changed the
Room database from `version = 5` to `version = 1`, emptied `addMigrations()`, and
left the database file name `"RootlessStoreDataBase"` unchanged. How many real
devices crossed that boundary, and what exactly does a user experience when the
app opens a version-5 database against a `version = 1` declaration with no
migration path and no `fallbackToDestructiveMigration()`?

### Why unknown

The code change is verified from git history. The user impact is not, because
Phase 1 performed no device testing (`INVESTIGATION_METHOD.md` §21 — experimental
findings require a device, Android version, app version, and procedure). The
release history of the affected window is also unexamined.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `git show efab664` — `RootlessStoreDatabase.kt` | L1 | commit | Version change | `version = 5` → `version = 1` (Verified) |
| `git show efab664` — `DataBaseHiltModule.kt` | L1 | commit | Migration registration and DB name | `addMigrations(4 migrations)` → `addMigrations()`; name unchanged (Verified) |
| Current tree `DatabaseHiltModule.kt:26-32` | L1 | current commit | Present configuration | 4 migrations registered, no destructive fallback (Verified) |
| Room runtime behaviour on downgrade | — | — | Exact exception and user-visible result | **Not checked** |

### What would resolve it

1. Establish the release tags or versions spanning `efab664` and record them in
   `sources.md`.
2. Confirm on a device, or from Room's documented `onDowngrade`/`onUpgrade`
   contract, what happens when the on-disk version exceeds the declared version
   with no migration path.
3. Record the result as a device-test finding with device, Android version, app
   version, and procedure (`INVESTIGATION_METHOD.md` §21).

### Current best hypothesis

**None recorded.** The code evidence establishes the mechanism; it does not
establish how often it fired or whether Room threw, downgraded, or reset. A
hypothesis here would be an assumption dressed as a finding.

### Cross-phase dependencies

- Phase 10 and Phase 14 must not assume a stable on-disk schema across app
  versions.
- Phase 17 needs the released version list before it can write meaningful
  `MigrationTestHelper` coverage.
- Phase 13 (updates/rollback) is directly relevant: this is an upgrade-path
  defect, if it is a defect.

---

## Unknown U-008 — Source authentication, integrity, and update discovery

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 12 — Catalog / Source Investigation |
| Blocks | Any trust conclusion about package acquisition. Does not block Phase 1. |
| Evidence | `phase-01/evidence.md` P1-F50, P1-F51, P1-F11 |

### Unknown

Beyond the verified unauthenticated `GET {endpoint}/plugin/getAllPlugins?page={n}`
(P1-F50) and application-wide cleartext permission (P1-F51), what authentication,
integrity verification, and update-discovery controls exist for a plugin source?

Specific open questions:

1. Is any credential or token stored, and if so how is it transmitted?
2. Is any signature, hash, digest, or certificate pin verified anywhere in the
   acquisition or install path?
3. How is an update discovered, given that `pluginUrl` is not persisted after
   install (P1-F11)?

### Why unknown

Phase 1 read the market API transport and the manifest deserialiser. It did not
trace the source repository, credential storage, or any update-detection path.
A Phase 1 subagent reported a token scheme and page-size behaviour; the ledger
records those as **Not established** and no finding relies on them.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `data/.../market/remote/api/MarketApi.kt:18-30` | L1 | current commit | Request shape, headers, auth | GET + path segments + `page` param; **no auth header, no signature, no pinning** (Verified) |
| `app/src/main/AndroidManifest.xml` | L1 | current commit | Transport policy | `usesCleartextTraffic="true"` (Verified) |
| `data/.../plugin/mapper/PluginMapper.kt:39` | L1 | current commit | Post-install source linkage | `pluginUrl` **not persisted** (Verified) |
| `PluginSourceEntity`, source repository/API | L1 | current commit | Credential storage and handling | Mapped structurally; behaviour **not established** |

### What would resolve it

1. Read `PluginSourceEntity`, the source DAO/repository, and the source remote
   API end to end.
2. Establish whether any integrity field exists in the catalog payload and
   whether any code verifies it.
3. Establish the update-discovery mechanism independently of the market list.
4. Record per-source trust requirements.

### Current best hypothesis

**None recorded.** The verified absence of an auth header at the API layer is not
the same as "the system has no authentication", and the phase must not infer one
from the other.

### Cross-phase dependencies

- Phase 7 (security) needs U-008 before it can conclude anything about package
  authenticity or supply chain.
- Phase 12 owns this outright.
- Phase 13 (updates/rollback) is blocked on the update-discovery half.

---

## Unknown U-009 — Is `isEnabled` enforced anywhere on the execution path?

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 3 — ADB Module Lifecycle |
| Blocks | Nothing. |
| Evidence | `phase-01/evidence.md` P1-F28 |

### Unknown

`PluginStatus.isEnabled` is persisted and rendered, and no execution use case or
gateway reads it (P1-F28). Does the UI prevent invoking a plugin whose
`isEnabled` is `false`, or is the flag purely decorative at runtime?

### Why unknown

Phase 1 read the whole `application/.../execute/` package, both execute use cases,
`ExecutePluginUseCase`, and `PluginExecutionGatewayImpl`. It did not trace every
UI entry point that can trigger execution — the plugin action panel, the Quick
Settings tile path, notification actions, or CodeBrick-driven invocation.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `ExecutePluginUseCase.kt`, `ExecutePluginByAppShellUseCase.kt`, `ExecutePluginByShizukuUseCase.kt` | L1 | current commit | `isEnabled` check | None found (Verified absent) |
| `PluginExecutionGatewayImpl.kt` | L1 | current commit | `isEnabled` check | None found (Verified absent) |
| UI invocation entry points | — | — | Whether the UI gates execution | **Not checked** |

### What would resolve it

Enumerate every caller of `ExecutePluginUseCase` and of the CodeBrick execution
path, and record whether each gates on `isEnabled`. If none does, the flag is
decorative and should be described as such.

### Current best hypothesis

**None recorded.** "The flag is decorative" is a conclusion Phase 3 must reach
from the full caller set, not an inference from the execution package alone.

### Cross-phase dependencies

Phase 3 owns plugin lifecycle. Phase 16 (UI/UX) needs the answer to describe what
the enabled toggle actually does.

---

## Unknown U-010 — What is `illusioncube`?

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 6 — Execution Abstraction Investigation |
| Blocks | Nothing. |
| Evidence | `phase-01/evidence.md` P1-F01 |

### Unknown

`illusioncube` is a 7-file, 185-line module declared in `settings.gradle.kts`,
with no internal project dependencies, and is a dependency of both `data` and
`application`. What is it, and what does its presence at the bottom of the
dependency stack imply for the execution architecture?

### Why unknown

Phase 1 established the module's existence, size, and position in the graph as
part of the architecture inventory. It did not read the module's contents,
because the Phase 1 checklist items are organised around the plugin, CodeBrick,
and market subsystems rather than around a module inventory of third-party or
internal utility code.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `settings.gradle.kts` | L1 | current commit | Module declaration | Present (Verified) |
| `illusioncube/build.gradle.kts` | L1 | current commit | Internal dependencies | None (Verified) |
| File count | L1 | current commit | Size | 7 Kotlin files, 185 lines (Verified) |
| Module contents | — | — | Purpose, public API, provenance | **Not read** |

### What would resolve it

Read all 7 files and the module's `build.gradle.kts`, record its package name,
purpose, and any external dependency, and add it to `sources.md` with a licence
determination if it is third-party code. Phase 18 (licensing/provenance) also
depends on this answer.

### Current best hypothesis

**None recorded.** The module name alone is not evidence of purpose.

### Cross-phase dependencies

- Phase 6 needs to know whether it is part of the execution path or an unrelated
  utility.
- Phase 18 needs its provenance and licence before any MasterRef expansion.

---

## Unknown U-011 — Does any writer ever produce a non-default `PluginState`?

| Field | Value |
| --- | --- |
| Phase raised | 1 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 3 — ADB Module Lifecycle |
| Blocks | Any lifecycle or recovery design that relies on plugin state. Does not block Phase 1. |
| Evidence | `phase-01/evidence.md` P1-F28 context; §8 of the Phase 1 report |

### Unknown

`PluginState` declares five values: `Great`, `PermissionProblems`,
`PluginRuntimeProblems`, `RootlessStoreRuntimeProblems`, `Stop`. Phase 1 found
**no writer** that sets any value other than a default: state is persisted and
rendered but never produced by execution, install, or recovery logic. Are there
writers Phase 1 missed, or is `PluginState` currently inert?

### Why unknown

Phase 1 read the execution use cases, the execution gateway, the uninstall use
case, and the plugin repository/status mapping. It did not perform an exhaustive
search of every writer of `PluginStatusEntity`, nor did it inspect every UI or
recovery path that might report state.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `domain/.../plugin/model/PluginState.kt` | L1 | current commit | Declared values | 5 values (Verified) |
| `ExecutePluginUseCase.kt`, `ExecutePluginByAppShellUseCase.kt`, `ExecutePluginByShizukuUseCase.kt` | L1 | current commit | State write on execution | None found |
| `PluginExecutionGatewayImpl.kt` | L1 | current commit | State write on error/crash | None; `PluginProcessMonitor` emits to an in-memory flow, not to persistence (Verified) |
| `PluginStatusEntity` writers, all sites | — | — | Exhaustive writer set | **Not checked** |

### What would resolve it

Search every writer of `PluginStatusEntity` and every `PluginState` construction
site across all modules. If the only writer is the registration path with a
default value, record `PluginState` as declared-but-not-operational alongside
`PluginRunModel`, `executableFiles`, and `pluginUrl`.

### Current best hypothesis

**None recorded.** "Inert" is a strong claim that requires the exhaustive writer
set.

### Cross-phase dependencies

- Phase 3 owns plugin lifecycle and state.
- Phase 14 (recovery) cannot design recovery around a state field that is never
  written.
- Phase 25 should treat `PluginState` as a documentation-vs-implementation item.

---

## Unknown U-012 — Will Nightzuku and Shevery converge, or diverge further?

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 21 — Interoperability Investigation |
| Blocks | Nothing. |
| Evidence | `phase-02/evidence.md` P2-C04, P2-A16, P2-A63, P2-A63 |

### Unknown

The two forks have already diverged silently (see U-013). Do they track each
other, or does the Shevery-only feature set (`run.sh`, `late_start.sh`, `github`,
`MODPATH`, `ARCH`, `AXERON`, `su` shim) keep widening?

### Why unknown

Only one point in each fork's history was observed (2026-07-20 Nightzuku,
2026-10-02 Shevery). A single snapshot pair cannot distinguish a converging
fork from a diverging one, and no upstream statement about coordination exists
(P2-A18).

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Nightzuku vs Shevery module subsystem | L1 | `60a8feb6` / `bfc55ce9` | Behavioural deltas | 8 concrete deltas found (P2-A16, P2-A42, P2-A63) |
| Both READMEs | L2 | same | Any coordination/compat statement | **None found** (P2-C03) |
| Release cadence | L3 | 2026-05 → 2026-10 | Whether one follows the other | Not established |

### What would resolve it

Re-diff the two module subsystems at two later pins. One interval cannot answer
this; repeated measurement is required.

### Current best hypothesis

**None recorded.** Convergence and divergence are equally consistent with a
single snapshot pair.

### Cross-phase dependencies

Phase 21 owns cross-fork interoperability. Phase 25 should not describe the
Shevery-only set as stable.

---

## Unknown U-013 — Does any format-stability or deprecation policy exist?

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 21 |
| Blocks | Nothing. |
| Evidence | `phase-02/evidence.md` P2-A18, P2-C01 |

### Unknown

Is there any published stability guarantee, versioning scheme, or deprecation
policy for `module.prop`? Nothing equivalent to a version key was found in
either fork.

### Why unknown

No specification repository, RFC, or schema registry exists (P2-C01), and
neither fork ships a format-version key (P2-D01). Absence of a search result is
evidence of absence only within what a platform index covers.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `module/` subsystem, both forks | L1 | both pins | `formatVersion` / `schemaVersion` / `specVersion` keys | **None** (P2-D01) |
| `docs/`, `README.md`, wiki | L2 | both pins | Stability or deprecation policy | **None** (P2-A18) |
| `gh search repos` | L3 | 2026-10-02 | A spec repo | 0 results (P2-C01) |
| `module.prop` parser | L1 | Shevery | Any version negotiation | Untyped map; last-wins (P2-A03) |

### What would resolve it

A published statement from either project, or evidence that versioned props have
begun circulating in the wild. Neither exists today.

### Current best hypothesis

**None recorded.**

### Cross-phase dependencies

Phase 21. Phase 25 must not present the format as stable or versioned.

---

## Unknown U-014 — Do real modules depend on the Shevery-only fallbacks?

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 12 — Catalog / Sources Investigation |
| Blocks | Nothing. |
| Evidence | `phase-02/evidence.md` P2-B12, P2-A16 |

### Unknown

Do any distributed ADB Modules actually ship `run.sh`, `main.sh`, `exec.sh` or
`late_start.sh`, or depend on `github`/`shellBridge`/`repo`? Phase 2 established
the fallbacks exist but did not establish whether anything uses them.

### Why unknown

Catalog contents were deliberately not analysed in Phase 2. Catalog trust is
Phase 12's subject, and third-party module READMEs are author claims rather than
package contents (Phase 2 report §4).

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery source | L1 | `bfc55ce9` | Fallback existence | Present (P2-A12, P2-A13) |
| `docs/adb-modules-api.md` | L2 | `bfc55ce9` | Documented names | Only `action.sh` documented (P2-B12) |
| `topic:shevery-modules` | L3 | 2026-10-02 | Package listings | 13 repos identified; **contents not inspected** (P2-C02) |

### What would resolve it

Download sample module packages and inspect their filenames and props. Recording
real usage frequencies is what would turn "exists" into "matters".

### Current best hypothesis

**None recorded.** Low usage and high usage are both consistent with the current
evidence.

### Cross-phase dependencies

Phase 12 owns catalog trust and content. Phase 21 needs the answer to decide
whether Shevery-only fallbacks are worth implementing.

---

## Unknown U-015 — Android-version-specific extraction and `setExecutable` behaviour

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 10 — Android Compatibility Investigation |
| Blocks | Nothing. |
| Evidence | `phase-02/evidence.md` P2-A29, P2-A30, P2-A42, P2-A45 |

### Unknown

Do `File.setExecutable`, ZIP-entry handling, or canonical-path behaviour differ
across Android 12–17 in ways that affect the two-layer path defence or the
`+x` marking? No device was available in Phase 2 (report Appendix C).

### Why unknown

`INVESTIGATION_METHOD.md` §21 requires device, Android version, app version, and
procedure for an experimental finding. None of those were available.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery source | L1 | `bfc55ce9` | Platform API usage | `File.setExecutable(true,false)`, `java.nio.Path.startsWith` |
| Android platform docs | — | — | Version-specific behaviour | **Not checked** |

### What would resolve it

Phase 10's per-version matrix, plus device testing on Android 12–17.

### Current best hypothesis

**None recorded.** `java.io.File.setExecutable` has been stable API, but
"stable API" is not "verified behaviour", and extraction edge cases are exactly
where SELinux policy differences surface.

### Cross-phase dependencies

Phase 10 owns the version matrix. Phase 7 needs it for the extraction threat
model.

---

## Unknown U-016 — `IShizukuService.newProcess` environment-merge semantics

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 5 — Shizuku Compatibility Investigation |
| Blocks | Nothing. |
| Evidence | `phase-02/evidence.md` P2-A67 |

### Unknown

Phase 2 inferred (P2-A67, classified INFERRED, not VERIFIED) that the module
`extraEnv` map is merged into the child process environment by
`IShizukuService.newProcess`. `IShizukuService` was **not read**. Specifically
unknown: does the child inherit the server's own environment, and does that
inheritance add variables to the documented eleven?

### Why unknown

This is a Shizuku-API-semantics question, and Phase 2's scope is the module
package format. Phase 5 owns the service contract.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery call site | L1 | `bfc55ce9` | `newProcess(remote, env, dir)` shape | Verified call shape (P2-A58, P2-A67) |
| `IShizukuService` AIDL/implementation | — | — | Merge semantics | **Not read** |
| Nightzuku equivalent | L1 | `60a8feb6` | Same question | Same gap |

### What would resolve it

Read the Shizuku server/client AIDL definition and its `newProcess`
implementation. Phase 5's server-implementation work (U-004) overlaps here.

### Current best hypothesis

**None recorded.** The inference in P2-A67 is explicitly marked INFERRED and no
conclusion in the Phase 2 report rests on it.

### Cross-phase dependencies

Phase 5 owns this outright. Phase 6 needs the env-merge rule before it can define
the execution environment contract.

---

## Unknown U-017 — Does anything depend on `AXERON` or the `su` shim?

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | Low |
| Status | Open |
| Owning phase | 12 |
| Blocks | Nothing. |
| Evidence | `phase-02/evidence.md` P2-A61, P2-A62, P2-A56 |

### Unknown

Are `AXERON=true`, `AXERONVER=1.0.0`, and the host `su` shim (added 2026-05-24 in
commit `f223250e`) load-bearing for any distributed module? Phase 2 established
they exist and that `AXERON` traces to a third ecosystem, but not their use.

### Why unknown

Same reason as U-014: package contents were not inspected.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery source | L1 | `bfc55ce9` | Presence and origin | Present; commit `f223250e` (P2-A61, P2-A56) |
| Nightzuku source | L1 | `60a8feb6` | Presence | **Absent** (P2-A63) |
| Module packages | — | — | Dependence | **Not checked** |

### What would resolve it

Same module-content sampling as U-014. Can be closed together with it.

### Current best hypothesis

**None recorded.** The commit message style suggests interoperability work with a
third ecosystem; that is a lead, not evidence of dependence.

### Cross-phase dependencies

Phase 12. Phase 7 needs the `su` shim question answered for the privilege-escalation
threat model regardless of module dependence.

---

## Unknown U-018 — Intended script invocation form and working directory

| Field | Value |
| --- | --- |
| Phase raised | 2 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 6 — Execution Abstraction Investigation |
| Blocks | Phase 17 (execution tests cannot assert an invocation contract). |
| Evidence | `phase-02/evidence.md` P2-A26, P2-A27, P2-B05, P2-B06 |

### Unknown

The reference implementation runs `sh -c <entire file contents>` with cwd
`/data/local/tmp` (P2-A26, P2-A27), which contradicts the official doc
(P2-B05, P2-B06). `ARCHITECTURE.md` §32–§33 does not specify invocation form or
working directory. Should this project replicate `sh -c` + `/data/local/tmp`,
or use the more conventional `sh $MODDIR/action.sh` + cwd `$MODDIR`?

### Why unknown

This is an architecture decision, not a fact about the reference. Phase 2 can
establish what Shevery does; it cannot decide what this project should do
(`INVESTIGATION_METHOD.md` §40 — explicit architecture decision, not a silent
edit).

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery `ModuleScriptRunner` | L1 | `bfc55ce9` | Invocation form | `sh -c <contents>`, cwd `/data/local/tmp` (P2-A26, P2-A27) |
| `docs/adb-modules-api.md` | L2 | `bfc55ce9` | Documented invocation | `sh $MODDIR/action.sh` (P2-B05, P2-B06) — **contradicted** |
| Nightzuku runner | L1 | `60a8feb6` | Same | Same form (P2-A16) |
| `ARCHITECTURE.md` §32–§33 | L2 | n/a | Project's own intent | **Silent** — unspecified |

### What would resolve it

An explicit Phase 6 architecture decision, recorded with its rationale. Phase 6
owns the execution environment contract, which is where this belongs.

### Current best hypothesis

**None recorded.** Both options are defensible; `sh -c <contents>` buys fidelity,
`sh <path>` + `$MODDIR` cwd buys conventionality. The deciding factor is whether
real modules depend on the odd behaviour — which is U-014.

### Cross-phase dependencies

- Phase 6 owns this outright.
- Phase 17 needs it before execution tests can be written.
- U-014 is the input that would settle it empirically.


---

## Register maintenance rules

1. Entries are appended, never silently removed. A closed entry keeps its
   resolution block.
2. Closing an entry records who resolved it, with which evidence, and on what
   date.
3. Unknowns raised in a phase survive that phase's completion
   (`INVESTIGATION_METHOD.md` §45).
4. Cross-phase dependencies are stated explicitly so a later phase does not
   invent an answer to unblock itself.
5. A `Blocking` unknown appears in `STATUS.md` §4 and §7. `Accepted-as-unknown`
   entries must be labelled as unknown wherever referenced.
6. Phase 25 must treat every open unknown as a MasterRef audit item; Phase 26 may
   incorporate unknown material **only** when explicitly labelled as unknown.