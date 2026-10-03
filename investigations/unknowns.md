# ADB Modules App — Investigation Unknowns Register

Central register of unresolved questions.

**Standard:** `investigations/METHOD.md` §13, template
`investigations/templates/UNKNOWN.md`
**Method authority:** `INVESTIGATION_METHOD.md` §15 (Unknowns), §45 (Unknowns
Register)
**Opened by:** Phase 0 — Investigation Infrastructure
**Last updated:** 2026-10-02 (Phase 4)

An unknown is a legitimate investigation result. No entry is closed by
substituting an assumption. Unknowns survive phase completion:
`INVESTIGATION_METHOD.md` §45 — "Do not allow unresolved questions to disappear
merely because a phase is marked complete."

Priority: `Blocking`, `High`, `Medium`, `Low`.

---

## Summary

| ID | Short description | Priority | Status | Owning phase | Blocks Phase 1? |
| --- | --- | --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Blocking (for backend design) | **Resolved** (Phase 4) | — | No |
| U-002 | Upstream Rootless Store repository state and license | Medium | Open | 18 | No |
| U-003 | Reference Shevery commit for the ADB Module compatibility contract | High | **Resolved** (Phase 2) | — | No |
| U-004 | Shizuku server implementation/version available to users | High | Open | 5 | No |
| U-005 | Whether `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract | High | **Resolved** (Phase 2) | — | No |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is or is not the ADB Module feature | High | **Resolved** (Phase 2); residual design question → 11 | 11 | No |

Phase 1 additions:

| U-007 | Real-world impact of the database version reset 5 → 1 in commit `efab664` | High | Open | 14 |
| U-008 | Source authentication, integrity, and update-discovery controls beyond the unauthenticated GET | High | Open | 12 |
| U-009 | Whether the UI prevents executing a plugin whose `isEnabled` is false | Medium | **Resolved** (Phase 3) | — |
| U-010 | What `illusioncube` is and what its presence in `data`/`application` implies | Medium | Open | 6 |
| U-011 | Whether any non-default `PluginState` value is ever produced by any writer | High | **Resolved** (Phase 3) | — |

Phase 2 additions:

| U-012 | Whether Nightzuku and Shevery will converge or diverge further | Medium | Open | 21 |
| U-013 | Whether any format-stability or deprecation policy exists or will be published | Medium | Open | 21 |
| U-014 | Whether third-party module authors depend on the Shevery-only fallbacks (`run.sh`, `main.sh`, `exec.sh`, `late_start.sh`) | Medium | Open | 12 |
| U-015 | Android-version-specific extraction and `setExecutable` behaviour | Medium | Open | 10 |
| U-016 | `IShizukuService.newProcess` env-merge semantics — does the child inherit the server's environment? | Medium | Open | 5 |
| U-017 | Whether any real module depends on `AXERON`/`AXERONVER` or the `su` shim behaviour | Low | Open | 12 |
| U-018 | Intended script invocation form and working directory for this project | High | Open | 6 |

Phase 3 additions:

| U-019 | Backend-neutral equivalent of the reference's "binder session" service-start trigger | High | **Resolved** (factual half, Phase 4); decision → 6, 9 | 6, 9 |
| U-020 | Runtime behaviour of local recovery under PID reuse and Shizuku unavailability | High | Open | 14 |
| U-021 | Whether real modules depend on the 120 s service bound or on directory replacement by update | Medium | Open | 12 |
| U-022 | Whether GitHub release assets in practice carry a consumable digest | Medium | Open | 12 |
| U-023 | Whether an interrupted `disable`-marker write can read as "enabled" | Low | Open | 11 |
| U-024 | Whether the declared-but-uninvoked lifecycle APIs were a regressed feature or scaffolding | Low | Open | 6 |

Phase 4 additions:

| U-025 | Runtime confirmation that `bindUserService` through Porter's bridge throws `UnsupportedOperationException` with no fallback | High | Open | 5 |
| U-026 | Android 12–17 behaviour of Porter itself | Medium | Open | 10 |
| U-027 | Whether `startProcess`/`exec` behave identically on the `PorterBackend.SHIZUKU` wire | Medium | Open | 5 |
| U-028 | Whether the Porter Compatibility companion (`moe.shizuku.privileged.api`) interacts with this project's `dev.rikka.shizuku:provider` differently than documented | Medium | Open | 5 |
| U-029 | Whether Porter SDK `0.x` API instability will affect this project, and at what migration cost | Medium | Open | 21 |
| U-030 | Whether the SDK's internal shell-service `tag`/`version` strings are stable or implementation detail | Low | Open | 6 |

Open unknowns: **24** (was 19; Phase 4 resolved 1 — U-001 — and added 6).
Phase 4 also resolved the **factual half** of U-019, whose decision half now belongs
to Phases 6 and 9.

**U-001 resolved in Phase 4.** Porter is `d4rken-org/porter` (`eu.darken.porter`,
`0.8.0-rc0`/`800000`, Apache-2.0, commit `2d88f34b`); its SDK is a **separate**
repository `d4rken-org/porter-api` at tag `0.9.0` (`2f280522`), published from
JitPack as `com.github.d4rken-org.porter-api`. The API surface was established from
source across 30 files. The `Blocking` priority it carried is discharged, so no
unknown now carries it.

Three consequences Phase 0 did not anticipate:

1. **One SDK serves two wires** (`PorterBackend.{PORTER, SHIZUKU}`), so Phase 5
   faces the same SDK rather than an unrelated one.
2. **Porter is not an ADB Module source** — re-verified at the same commit Phase 2
   used, with per-token counts (P4-D01, P4-D02).
3. **This project's user-service-based execution cannot reach Porter through the
   Shizuku bridge**, which throws `UnsupportedOperationException`. The migration is
   therefore a reimplementation, not an adaptation (P4-AR02, C-019).

Residual risks are registered separately rather than folded into the resolution, per
maintenance rule 1: U-025 (device confirmation), U-026 (Android 12–17), U-027
(Shizuku-wire `exec`), U-028 (companion/provider), U-029 (SDK `0.x` instability),
U-030 (internal service identifiers).

Prior counts for context:

- Phase 2: 15 open (resolved U-003, U-005, U-006; added U-012…U-018).
- Phase 3: 19 open (resolved U-009, U-011; added U-019…U-024).
- Phase 4: 24 open (resolved U-001; added U-025…U-030).

**Previously:**
One of them — **U-001** — carries `Blocking` **priority**.
Per `METHOD.md` §13, `Blocking` is a priority value, not a program gate: U-001 is
`Open` and blocks only Porter-dependent architecture decisions (Phases 6, 10, 22,
23), which Phase 4 resolves. It did not block Phases 1, 2 or 3.

**U-009 / U-011 resolved in Phase 3.** Both were assigned to Phase 3 by Phase 1 and
are now answered from source with an exhaustive search each:

- **U-009** — the enabled flag is **not** enforced at execution.
  `ExecutePluginUseCase` has exactly one caller and never reads
  `PluginStatus.isEnabled`; the UI execute button has no check; only the adjacent
  *navigation* handler tests it. The switch is a UI affordance, not a lifecycle
  control (P3-A43, P3-D08). The reference *does* gate execution on enabled state
  (`check(module.enabled)`), so the compatibility contract needs an enforcement
  point this repository lacks — recorded as `P3-AR07`, `P3-C03`, `P3-S10`.
- **U-011** — `PluginState` is **inert**. Exactly one of its five values (`Great`)
  is ever written to the database, at three insert sites. `PermissionProblems`
  appears only as a hardcoded UI display value; `Stop`, `PluginRuntimeProblems` and
  `RootlessStoreRuntimeProblems` have zero construction sites project-wide. The DAO
  methods that would mutate execution state are declared with **no callers**
  (P3-A113, P3-A114, P3-A115, P3-A137, P3-D13).

Five new unknowns were opened because Phase 3 could establish static behaviour but
not runtime behaviour (U-019, U-020), not ecosystem behaviour (U-021, U-022), or
implementation intent (U-024). None of them contradicts a verified conclusion; each
records what would resolve it and which phase owns it.

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
| Status | **Resolved** (Phase 3, 2026-10-02) |
| Owning phase | 3 — ADB Module Lifecycle |
| Blocks | Nothing. |
| Evidence | `phase-01/evidence.md` P1-F28; `phase-03/evidence.md` P3-A43, P3-D08 |

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

### Resolution (Phase 3, 2026-10-02)

**RESOLVED — the UI does not prevent it. The flag is decorative at execution time.**

Phase 3 enumerated the full caller set, which is what Phase 1 explicitly left
unchecked:

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `ExecutePluginUseCase` callers | L1 | `6df93ae` | Every execution entry point | Exactly one: `RootlessStoreExecuteScreenViewModel.kt:26`. It passes only `pluginId`; the use case reads the manifest and store status and **never reads `PluginStatus.isEnabled`** |
| `PluginScreen.kt:274` | L1 | `6df93ae` | Enabled check on the execute button | **None** — `onExecuteClick = { onExecuteOneTimePlugin(pluginManifest.pluginId) }` is unconditional |
| `PluginScreen.kt:276` | L1 | `6df93ae` | Enabled check on navigation | **Yes** — `if (pluginStatus?.isEnabled == true)` guards only the *navigate* handler |
| `PluginScreen.kt:266-270` | L1 | `6df93ae` | Effect of switching off | Calls `onAbortPluginProcess(pluginId)` — a UI side effect, not an execution gate |
| `InstalledManifestCard.kt:118` | L1 | `6df93ae` | Where the flag is read | Toggle rendering only |
| CodeBrick path | L1 | `6df93ae` | Whether CodeBricks are gated | Not gated by plugin enablement; CodeBricks execute their own content |
| `PluginRunModel` consumers | L1 | `6df93ae` | Any run-model branching | None (P3-A58) |

Full reasoning and the count of `isEnabled` references: `phase-03/REPORT.md` §5.7 and
`phase-03/evidence.md` P3-A43, P3-D08.

**Interpretation adopted:** the flag is *persisted and rendered but not enforced*.
Enabling is therefore not a precondition for execution, and the switch is a UI
affordance rather than a lifecycle control.

**Why this matters beyond the local layer:** the reference implementation *does*
gate execution on enabled state (`check(module.enabled)` in
`AdbModuleManager.runModuleScriptStreaming`, P3-A38). Matching the ADB Module
compatibility contract therefore requires an enforcement point that this
repository does not currently have. Recorded as `P3-AR07` and `P3-C03` in the
Phase 3 report, and carried to Phase 7 as security finding `P3-S10`.

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
| Status | **Resolved** (Phase 3, 2026-10-02) |
| Owning phase | 3 — ADB Module Lifecycle |
| Blocks | Any lifecycle or recovery design that relies on plugin state. Does not block Phase 1. |
| Evidence | `phase-01/evidence.md` P1-F28; `phase-03/evidence.md` P3-A114, P3-A115, P3-A113 |

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

### Resolution (Phase 3, 2026-10-02)

**RESOLVED — `PluginState` is inert. Exactly one of its five values is ever written.**

Phase 3 ran the exhaustive writer search Phase 1 left undone — a repository-wide
search for every `PluginState.` construction site across all eight Gradle modules:

| Site | Level | Version | Value written | Persisted? |
| --- | --- | --- | --- | --- |
| `PluginStatusRepositoryImpl.kt:26` | L1 | `6df93ae` | `PluginState.Great` | Yes — `pluginStatus` insert |
| `PluginExecutionEntity.kt:27` | L1 | `6df93ae` | `PluginState.Great` | Yes — `PluginExecuteStatusEntry` insert |
| `EnvironmentStatusRepositoryImpl.kt:24` | L1 | `6df93ae` | `PluginState.Great` | Yes — `environmentStatus` insert |
| `InstalledManifestCard.kt:330` | L1 | `6df93ae` | `PluginState.PermissionProblems` | **No** — a hardcoded local value passed to a UI composable for display |
| 6 × test files | L1 | `6df93ae` | `PluginState.Great` | Test fixtures only |

`Stop`, `PluginRuntimeProblems` and `RootlessStoreRuntimeProblems` have **zero**
construction sites anywhere in the project, including tests.

**Corroborating finding.** The DAO that *would* mutate execution state declares
`updatePluginExecutionStateByPluginId` and `observePluginExecutionStateByPluginId`
(`PluginExecutionDao.kt:21-24`), but **neither has a single caller** across `app/`,
`application/`, `data/` or `ui/`. So the write path exists as a declaration and is
never invoked. `disableAllPlugins()` and `deleteAllPluginExecutions()` are likewise
declared and never invoked (P3-A137, P3-D13).

**Interpretation adopted:** `PluginState` is
**declared-but-not-operational**. Any UI switching on it can only ever observe
`Great`. This is the same defect class as C-006 (a documented manifest field that no
code enforces), now observed on the lifecycle side.

**Consequences carried forward:**

- Phase 14 cannot design recovery around `PluginState`, because the field does not
  change during recovery or crash. `RecoverPluginRuntimeStateUseCase` mutates only
  `isEnabled` and deletes the row (P3-A118, P3-A120).
- The intended third lifecycle dimension in `ARCHITECTURE.md` §48 is **not
  functioning** in the local implementation — recorded as `P3-AR06`, labelled
  `CONTRADICTED`.
- Phase 25 should treat `PluginState` as a documentation-vs-implementation item
  alongside `PluginRunModel` (P3-A58), `executableFiles` (P2-A82), `MagiskProp`
  (P3-A34) and the three dead DAO methods.

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

## Unknown U-019 — What is the backend-neutral equivalent of the reference's "binder session" trigger for unattended service start?

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 4 — Porter Investigation (primary); 6 and 9 own the resulting decision |
| Blocks | Any claim that ADB Module background execution is backend-neutral. Does not block Phase 3, which is complete without it. |
| Evidence | `phase-03/evidence.md` P3-A62, P3-A63, P3-A64, P3-A65, P3-A124 |

### Unknown

The reference starts eligible `service.sh` scripts at most once per **Shizuku
binder session**, and the only signal that arms this is
`Shizuku.OnBinderDeadListener` resetting a process-scoped `@Volatile` flag
(P3-A62, P3-A63). The trigger itself is a Compose `LaunchedEffect` inside
`HomeActivity` observing a Shizuku service resource (P3-A64), and it never fires
at boot (P3-A65). What is the equivalent event on a backend that is not Shizuku?

### Why unknown

Two independent blockers:

1. Porter's lifecycle events are unknown — U-001 is still open and Phase 4 has not
   run. There is no verified Porter equivalent of "binder died" to map onto.
2. Even with Porter known, choosing a trigger is an architecture decision, not a
   fact (`INVESTIGATION_METHOD.md` §40).

`AGENTS.md` §24 forbids defining the architecture in terms of one backend's
semantics, and §26 forbids silently changing execution semantics. Copying "once
per binder session" into a Porter runtime would do exactly what §24 prohibits.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery `AdbModuleManager` | L1 | `bfc55ce9` | Session definition | `@Volatile servicesStartedForBinder`, short-circuit on `!Shizuku.pingBinder()` (P3-A62) |
| Shevery `HomeActivity` | L1 | `bfc55ce9` | Trigger site | `LaunchedEffect` on `serviceResource?.status`/`uid`, gated `SUCCESS && isRunning` (P3-A64); wrapped in `catch (_: Throwable) {}` (P3-A66) |
| Shevery `BootCompleteReceiver` | L1 | `bfc55ce9` | Boot-time service start | **No module calls in the file** (P3-A124) |
| Nightzuku `HomeActivity` | L1 | `60a8feb6` | Same trigger | Present, same shape (P3-A63) |
| Porter | — | — | Lifecycle events | **Not investigated** — Phase 4 |
| This project | L1 | `6df93ae` | Any background execution path | None exists; `PluginRunModel.Daemon` is never branched on (P3-A58, P3-A70) |

### What would resolve it

1. Phase 4 establishes Porter's real lifecycle events (process death, service
   restart, binder/session equivalent).
2. An explicit Phase 6 or Phase 9 architecture decision records the neutral trigger,
   or records that unattended service start is Shizuku-only in the target
   architecture.

### Current best hypothesis

**None recorded.** Three shapes are defensible — backend-lifetime, backend-death,
or explicit user opt-in per session — and the choice changes observable module
behaviour. Recording a preference now would be `INVENTIGATION_METHOD.md` §16's
prohibited conversion of "I think" into "the system does".

### Cross-phase dependencies

- Phase 4 resolves the factual half (U-001).
- Phases 6 and 9 own the decision.
- Phase 22 stress-tests it across multiple backends.
- Phase 25 must not record a backend-neutral claim until 4/6/9 have run.

---

## Unknown U-020 — Runtime behaviour of the local recovery path under PID reuse and Shizuku unavailability

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 14 — Runtime Recovery Investigation |
| Blocks | Nothing. Recorded so the static finding is not mistaken for a tested one. |
| Evidence | `phase-03/evidence.md` P3-A118, P3-A120, P3-A121, P3-A123, P3-A124 |

### Unknown

Phase 3 established by source reading that the local recovery path issues
`kill -9 <pid>` on a persisted PID with **no liveness check, no cmdline match and
no start-time comparison** (P3-A121), and that the ADB-context branch retains its
row and enabled flag whenever the kill does not report success (P3-A118). Two
runtime consequences are not established:

1. On a real device, does a recycled PID actually cause an unrelated process to be
   killed?
2. Is the accumulating-row state user-visible, and does it degrade over repeated
   launches with Shizuku unavailable?

### Why unknown

`INVESTIGATION_METHOD.md` §10 requires device, manufacturer/model, Android
version, app version, dependency versions, backend, configuration, permissions,
trust state, network state, battery state, procedure, expected result, observed
result, logs and limitations for an experimental finding. No device or emulator is
available in this environment, so none of these can be honestly supplied.

Phase 3 recorded the **static** guarantee — the absence of a check — which is
`VERIFIED`. What is unverified is the runtime consequence, which is the part that
matters for severity.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `RecoverPluginRuntimeStateUseCase.kt` | L1 | `6df93ae` | Liveness check before kill | **None** — unconditional abort (P3-A120) |
| `PluginExecutionGatewayImpl.kt:127-133` | L1 | `6df93ae` | PID validation | `kill -9 $pluginProcessPid` via string interpolation; typed `Int`, not range-checked, not quoted (P3-A122) |
| `PluginExecutionGatewayImpl.kt:135-149` | L1 | `6df93ae` | Kill result semantics | `processAbortResult != null` — true even when the underlying kill failed (P3-A118) |
| Device / emulator | — | — | Runtime observation | **Not available** |

### What would resolve it

A controlled device test on Android 12–17: force PID reuse (or simulate it by
writing a foreign PID into `PluginExecuteStatusEntry`), launch the app, and observe
whether an unrelated process dies. Separately, run `MainActivity` repeatedly with
Shizuku stopped and observe row accumulation and UI effect.

### Current best hypothesis

**None recorded.** "It will kill an unrelated process" is a severity claim that
requires the experiment. What *is* verified is that nothing prevents it.

### Cross-phase dependencies

Phase 14 owns it. Phase 7 uses the PID-kill-without-validation row (`P3-S11`) as a
security finding regardless of the runtime outcome.

---

## Unknown U-021 — Do real modules depend on the 120 s service bound, or on wholesale directory replacement by an update?

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 12 (corpus access); 21 owns interoperability |
| Blocks | Nothing. |
| Evidence | `phase-03/evidence.md` P3-A51, P3-A86, P3-A87, P3-A91 |

### Unknown

`service.sh` is bounded by the same 120-second timeout as `action.sh`
(P3-A51), and an update replaces the module directory wholesale (P3-A86), which
discards module-written state and the `disable` marker (P3-A87). If real modules
rely on either behaviour, the project cannot safely diverge from it. Do any?

### Why unknown

No module corpus is available offline in this environment. Phase 2 recorded the
same limitation for the Shevery-only fallbacks (U-014), and the GitHub topic search
there was used only to establish that third-party modules exist — author README
claims are not behavioural evidence (`INVESTIGATION_METHOD.md` §6).

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery `AdbModuleManager` | L1 | `bfc55ce9` | Timeout applied to services | Yes — same `MAX_SCRIPT_SECONDS`, exit 124 (P3-A51) |
| Shevery `AdbModuleManager` | L1 | `bfc55ce9` | Update side effects | Whole-directory replace (P3-A86, P3-A87) |
| `module/discovery/` | L1 | `bfc55ce9` | Module corpus | Names and metadata only; **no module was downloaded or executed** |
| Catalog corpus | — | — | Real module behaviour | **Not analysed** |

### What would resolve it

Phase 12 acquires and analyses a real module corpus; Phase 21 then runs
interoperability tests covering long-running services, module-written state, and
disabled-then-updated modules.

### Current best hypothesis

**None recorded.** Both behaviours are plausible to rely on and both are defects, so
the empirical answer changes the Phase 13 design rather than merely confirming it.

### Cross-phase dependencies

Phases 12 and 21 own it. Phase 13 needs it before choosing between "preserve
directory state" and "document that updates reset it".

---

## Unknown U-022 — Do GitHub release assets in practice carry a digest this project could consume?

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 12 — Catalog / Source Investigation; 7 owns the integrity scheme |
| Blocks | Nothing. |
| Evidence | `phase-03/evidence.md` P3-A09, P3-A15, P3-A16 |

### Unknown

The reference selects a release asset purely by filename and reads nothing else
(P3-A09), and its `updateJson` response model has **no digest field at all**
(P3-A16). So there is currently nowhere to obtain a hash even if the ecosystem
publishes one. Do real module releases carry a checksum, signature, or attestation
that an integrity check could bind to?

### Why unknown

Requires enumerating release assets across the ecosystem's repositories, which is
catalog work (Phase 12) and requires network access to GitHub's release APIs at
scale. Phase 3 deliberately did not use search snippets as evidence for this.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery `ModuleInstaller.downloadRelease` | L1 | `bfc55ce9` | Digest handling | **None** (P3-A09, P3-A15) |
| Shevery `UpdateJsonResponse` | L1 | `bfc55ce9` | Digest field | **Absent** from the model (P3-A16) |
| GitHub release assets | — | — | Published digests | **Not enumerated** |

### What would resolve it

A Phase 12 survey of release assets for the repositories in the
`shevery-modules` topic, recording which (if any) publish `*.sha256`,
`*.sig`, or attestation files.

### Current best hypothesis

**None recorded.**

### Cross-phase dependencies

Phases 12 and 7 own it. Phase 13's update design must not assume a digest exists.

---

## Unknown U-023 — Can an interrupted `disable`-marker write leave a state that reads as "enabled"?

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Priority | Low |
| Status | Open |
| Owning phase | 11 — Storage Investigation |
| Blocks | Nothing. |
| Evidence | `phase-03/evidence.md` P3-A36, P3-A37 |

### Unknown

`setEnabled(false)` calls `marker.writeText("disabled\n")`, a non-atomic write,
and the marker's *content* is never read — only its existence (P3-A36). If the
write is interrupted the file may exist but be empty, or not exist at all. Does the
interruption produce a state that reads as enabled?

### Why unknown

Not observable statically. `File.writeText` opens with truncate-then-write; whether a
partial file exists at the moment of a process or power failure depends on the
filesystem and on when the failure lands. Only a device test can answer it.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| Shevery `AdbModuleManager.setEnabled` | L1 | `bfc55ce9` | Atomic write or fsync | `marker.writeText("disabled\n")` — no atomicity, no fsync (P3-A36, P3-A37) |
| Shevery `readModule` | L1 | `bfc55ce9` | Content read | `!directory.resolve(DISABLE_FILE).exists()` — existence only (P3-A344 → `AdbModuleManager.kt:344`) |
| Device | — | — | Interrupted-write behaviour | **Not available** |

### What would resolve it

A device test that interrupts a disable write at a controlled point and inspects the
resulting directory.

### Current best hypothesis

**None recorded.** Priority is `Low` because the exposure is narrow: it requires a
crash in a microsecond window, and the failure mode is "module appears enabled" —
which is the pre-existing state rather than an escalation.

### Cross-phase dependencies

Phase 11 owns it, as part of the storage-consistency rules.

---

## Unknown U-024 — Were the declared-but-uninvoked lifecycle APIs a regressed feature or scaffolding?

| Field | Value |
| --- | --- |
| Phase raised | 3 |
| Raised on | 2026-10-02 |
| Priority | Low |
| Status | Open |
| Owning phase | 6 (Execution Abstraction); 22 stress-tests the consequence |
| Blocks | Nothing. |
| Evidence | `phase-03/evidence.md` P3-A113, P3-A137, P3-D13 |

### Unknown

Four lifecycle APIs are declared and never invoked
(`PluginExecutionDao.updatePluginExecutionStateByPluginId`,
`PluginExecutionDao.observePluginExecutionStateByPluginId`,
`PluginStatusRepository.disableAllPlugins()`,
`PluginExecutionRepositoryImpl.deleteAllPluginExecutions()` — P3-A137). Was a real
feature removed or never wired up? The intent matters for whether an implementer
should complete them or delete them.

### Why unknown

Phase 3 established current behaviour from source, which is sufficient to report
"declared-but-not-operational" but not to establish *intent*. Establishing intent
requires `git log -S` on each symbol, which this phase did not run.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `PluginExecutionDao.kt:21-24` | L1 | `6df93ae` | Callers | **None** across `app/`, `application/`, `data/`, `ui/` (P3-A113) |
| `PluginStatusRepositoryImpl.kt:60-62` | L1 | `6df93ae` | Callers | **None** (P3-A137) |
| `PluginExecutionRepositoryImpl.kt:59-62` | L1 | `6df93ae` | Callers | Repository self-call only; no external caller (P3-A137) |
| Git history | — | — | Introduction commit for each symbol | **Not examined** |

### What would resolve it

`git log -S "<symbol>" --oneline` for each of the four symbols, and inspection of
the introducing commit's diff.

### Current best hypothesis

**None recorded.** Completing vs deleting is an implementation decision outside
Phase 3's remit (`AGENTS.md` §23 forbids implementing during Phases 0–24).

### Cross-phase dependencies

Phases 6 and 22. Phase 25 should record them as documentation-vs-implementation
items alongside the `PluginState`, `PluginRunModel` and `MagiskProp` findings.

---

## Unknown U-001 — Porter artifact, version, and API surface — RESOLVED (Phase 4)

| Field | Value |
| --- | --- |
| Phase raised | 0 |
| Raised on | 2026-10-02 |
| Priority | Blocking (for any Porter-dependent architecture decision) |
| Status | **Resolved** (Phase 4, 2026-10-02) |
| Owning phase | — (resolved) |
| Evidence | `phase-04/evidence.md` P4-A01–P4-A07, P4-A26, P4-A45–P4-A110, P4-D01–P4-D08 |

### Resolution (Phase 4, 2026-10-02)

**RESOLVED.** The identity and version questions are answered from primary source.

| Question | Answer | Evidence |
| --- | --- | --- |
| Which artifact? | **Two**: an application and a separate SDK repository | P4-A01, P4-A04 |
| Application | `d4rken-org/porter`, application id `eu.darken.porter` | P4-A01 |
| Application version | `0.8.0-rc0`, versionCode `800000`, at commit `2d88f34bc348552b7fb22eb73ccaba5b9922cce5` (2026-10-02) | P4-A02 |
| Release channel | GitHub Releases; **all four tags are pre-release** (`v0.1.1-beta0/1`, `v0.7.0-rc0`, `v0.8.0-rc0`) — no stable tag exists | P4-A03, P4-D07 |
| SDK | `d4rken-org/porter-api`, commit `2f2805226a33d6742c646a5efe022aa33d4c63ae` = tag `0.9.0` (2026-09-30) | P4-A04, P4-A06 |
| SDK coordinates | `com.github.d4rken-org.porter-api:{sdk, sdk-extras, shizuku-compat, shizuku-bridge}` via JitPack | P4-A04, P4-A05 |
| Wire protocol | Porter `VERSION 4` / `MIN_VERSION 4`; Shizuku `CLIENT_API_VERSION 13` / `MINIMUM 13` | P4-A102 |
| Android | `minSdk 24`, `targetSdk 37`, `compileSdk 37` | P4-A14 |
| Licence | Apache-2.0 application; MIT bundled Shizuku API | P4-A09 |
| API surface | Established from source across 30 Kotlin files: `Porter`, `PorterConnection`, `PorterBackend`, `PorterConnectionState`, `PorterAvailability`, `PorterIncompatibility`, `PermissionState`, `UserServiceArgs`, `PorterShell`, `PorterShellProcess`, `PorterShellResult`, `PorterSystemServices`, plus server-side `PorterCore` | P4-A27–P4-A110 |

### What the resolution changed

Three things Phase 0 and Phase 2 got wrong or did not know:

1. **The Phase 0 hazard H-001 is retired as a *naming* risk but not as a
   *stability* risk.** Porter is identified and is not `porter.run`. However, the
   SDK is explicitly `0.x` and unstable — "A minor release can add API and change
   behaviour this guide documents" (P4-A123) — and the application has no stable
   release tag at all. Recorded as U-029.
2. **Porter is not an ADB Module source.** Re-verified at the *same commit* Phase 2
   used, with per-token counts (P4-D01, P4-D02). `AGENTS.md` §24's prohibition on
   treating Porter as an ADB Module authority stands, now with source evidence.
3. **One SDK serves two wires.** `PorterBackend` is `{PORTER, SHIZUKU}` (P4-A26),
   so the same integration reaches an original Shizuku server. This materially
   helps Phase 5 and Phase 6, which Phase 0 assumed would face two unrelated SDKs.

### Residual risks — deliberately NOT folded into this entry

`unknowns.md` maintenance rule 1 keeps a closed entry closed. The residual risks are
registered separately so they do not disguise the resolution:

- **U-025** — device confirmation that `bindUserService` really throws
  `UnsupportedOperationException` through the bridge.
- **U-026** — Android 12–17 behaviour. Phase 10 owns it; Phase 4 deliberately makes
  **no** per-version claim.
- **U-027** — `exec`/`startProcess` on the `SHIZUKU` wire.
- **U-029** — SDK `0.x` migration cost.
- **C-019** — the architecture reclassification this resolution forces.

### Cross-phase dependencies after resolution

- Phase 5 may now assume a Porter SDK exists and can speak Shizuku's wire; it must
  still establish Shizuku's own semantics independently.
- Phase 6 now has a verified Porter contract and no longer needs `UNKNOWN` for the
  Porter branch. It inherits C-019 and U-030.
- Phase 10 can populate Porter rows instead of recording `Unknown`.
- Phase 23 (future backends) now has a concrete baseline to differ from.

---

## Unknown U-025 — Does the bridge really refuse user services at runtime?

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Priority | High |
| Status | Open |
| Owning phase | 5 (Shizuku compatibility); 6 consumes the answer |
| Blocks | Nothing. Recorded so the phase's most consequential claim is not mistaken for an observed one. |
| Evidence | `phase-04/evidence.md` P4-A126, P4-A127, P4-A128, P4-A130 |

### Unknown

`porter-api`'s `docs/api-reference.md:164` states that `bindUserService`,
`peekUserService` and `unbindUserService` throw `UnsupportedOperationException`
through the Shizuku bridge, and `docs/developers.md:358` repeats it in prose. This
project calls `Shizuku.bindUserService(...)` at 14 sites (P4-A128). Does that
actually throw on a device, and is there any fallback path Phase 4 could not see?

### Why unknown

The claim is quoted from two documents that ship inside the SDK repository, and both
were read at the pinned commit. But it was not **exercised**: no device or emulator
is available, and `INVESTIGATION_METHOD.md` §10 requires device, Android version,
app version, dependency versions, backend, configuration, procedure and observed
result for an experimental finding.

`INVESTIGATION_METHOD.md` §16 forbids converting a strong inference into "the system
does". The inference here is strong (two independent documents in one repository, both
agreeing, plus a developer-guide restatement) but it is not observation, so it is
recorded as `Inferred` in P4-A130 rather than `VERIFIED`.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `SDK docs/api-reference.md:164` | L1 (doc in SDK repo) | `0.9.0` | Bridge user-service support | "throw `UnsupportedOperationException`" (P4-A126) |
| `SDK docs/developers.md:358` | L2 | `0.9.0` | Same, in prose | "Upstream's user services do not work through the bridge; use Porter's own." (P4-A127) |
| `LOCAL ShizukuUserServiceGatewayImpl.kt:30-41` | L1 | `6df93ae` | The project's call | `Shizuku.bindUserService(args, connection)`, `.daemon(true)` (P4-A128) |
| `SDK` bridge implementation source | L1 | `0.9.0` | A code path that implements user services | **Not found** — consistent with the documented refusal, but absence in one searched tree is weaker than a positive reading |
| Device | — | — | Runtime behaviour | **Not available** |

### What would resolve it

A device test: install Porter and the SDK with `shizuku-bridge`, start
`PorterShizukuBridge.start(...)`, then call the project's existing
`bindUserService` path and observe the exception. A second run against an original
Shizuku server confirms the control case still works.

### Current best hypothesis

**None recorded.** "It throws `UnsupportedOperationException`" is already the
adopted working interpretation and is recorded as such in `phase-04/REPORT.md` §6
(P4-C02) and §8 (P4-AR02). Promoting it to observed fact requires the test.

### Cross-phase dependencies

Phase 5 owns the test; Phase 6 consumes the answer when designing the Porter
adapter. Phase 25 must present P4-A130 as an inference, not a verified failure.

---

## Unknown U-026 — Android 12–17 behaviour of Porter

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 10 — Android Compatibility Investigation |
| Blocks | Nothing. `PLAN.md` assigns the per-version matrix to Phase 10. |
| Evidence | `phase-04/evidence.md` P4-A14, P4-A18, P4-A19, P4-A20 |

### Unknown

What is Porter's actual behaviour on Android 12, 13, 14, 15, 16 and 17? Phase 4
established `minSdk 24`, `targetSdk 37`, `compileSdk 37`, an `Android17Compat`
shim covering six hidden-API breakages, and API-level gates on
`NEARBY_WIFI_DEVICES` and `ACCESS_LOCAL_NETWORK` (P4-A14, P4-A18, P4-A19). No
source states a **maximum** supported version, and none was found (P4-A20).

### Why unknown

`INVESTIGATION_METHOD.md` §18 requires `Supported / Partially supported /
Unsupported / Unknown` **per version**, and only where sufficient evidence exists.
`targetSdk 37` is evidence of *intent* to run on Android 17, not evidence of
behaviour on any specific device. `PLAN.md` Phase 10 exists precisely for this
matrix.

Phase 4 therefore records `Unknown` rather than guessing. This is deliberate:
`INVESTIGATION_METHOD.md` §16 forbids converting "probably fine" into a support
claim, and Phase 4 has no device.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `PORTER build.gradle.kts:19,23-24` | L1 | `2d88f34b` | SDK levels | `minSdk 24`, `targetSdk 37`, `compileSdk 37` (P4-A14) |
| `PORTER server/.../util/Android17Compat.kt` | L1 | `2d88f34b` | Android 17 handling | Six fallbacks present (P4-A18) — **source-verified, runtime-unverified** |
| `PORTER manager/src/main/AndroidManifest.xml:12-20` | L1 | `2d88f34b` | Version-gated permissions | `ACCESS_LOCAL_NETWORK`, `NEARBY_WIFI_DEVICES` (P4-A19) |
| Porter documentation | L2 | `2d88f34b` | A maximum supported version | **None found** (P4-A20) |
| `docs/troubleshooting.md:45` | L2 | `2d88f34b` | Stability caveats | "manufacturer modifications to Android can affect debugging access" |
| Device matrix | — | — | Behaviour on 12–17 | **Not available** |

### What would resolve it

Phase 10's device matrix: start Porter on each Android version, approve a client, run
one `exec` and one `startProcess`, record the outcome. Android 17 is the highest-value
row because `Android17Compat` shows known breakage there.

### Current best hypothesis

**None recorded.** See "Why unknown".

### Cross-phase dependencies

Phase 10 owns it outright. Phase 22 stress-tests it. Phase 25 must not record a
Porter Android-support claim before Phase 10 runs.

---

## Unknown U-027 — Does `exec`/`startProcess` behave the same on the Shizuku wire?

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 5 — Shizuku Compatibility Investigation |
| Blocks | Nothing. |
| Evidence | `phase-04/evidence.md` P4-A26, P4-A56, P4-A88, P4-A54 |

### Unknown

The Porter SDK exposes the same `exec` and `startProcess` on both
`PorterBackend.PORTER` and `PorterBackend.SHIZUKU` (P4-A26). Are their observable
semantics identical? Two source rows already suggest not:

- `ShellBinding` takes "one binding for every call, because a Shizuku server older
  than 13.4 keeps each binding it was given until the app's process dies" (P4-A56).
- `UserServiceArgs.daemon` notes "A Shizuku server below 13.4 keeps a cancelled
  binding, so there a non-daemon service still stops with that process" (P4-A88).

### Why unknown

Those rows establish **binding-lifecycle** differences. Whether `exec`'s output,
exit-code, stdin or kill semantics also differ is not established, and answering it
properly needs Shizuku's own server implementation plus a real Shizuku server —
which is Phase 5's subject, not Phase 4's.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `SDK sdk-extras/.../PorterShell.kt:164-166` | L1 | `0.9.0` | Wire-specific binding behaviour | Shizuku<13.4 keeps every binding (P4-A56) |
| `SDK sdk/.../UserServiceArgs.kt:28` | L1 | `0.9.0` | Wire-specific daemon behaviour | Same caveat (P4-A88) |
| `SDK sdk-extras/.../PorterShell.kt` | L1 | `0.9.0` | Wire branching in `exec`/`startProcess` | **None found** — the shell API does not branch on backend |
| Shizuku server implementation | — | — | `newProcess` semantics | **Not read** — Phase 5 |

### What would resolve it

Phase 5, by reading the Shizuku server side of `newProcess` against Porter's
`IPorterShellService`, and then a device test against an original Shizuku server.

### Current best hypothesis

**None recorded.** The absence of backend branching in `PorterShell.kt` is weakly
suggestive that `exec` semantics are uniform, but "weakly suggestive" is not a
hypothesis this register records.

### Cross-phase dependencies

Phase 5 owns it. Phase 6 needs the answer before treating one `ExecutionBackend`
contract as covering both wires.

---

## Unknown U-028 — Companion / provider coexistence

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 5 (Shizuku compatibility); 21 (interoperability) |
| Blocks | Nothing. |
| Evidence | `phase-04/evidence.md` P4-A10, P4-A11, P4-A132, P4-A135 |

### Unknown

The Porter Compatibility companion installs as `moe.shizuku.privileged.api` — Shizuku's
own application id — and "cannot be installed alongside Shizuku" (P4-A10, P4-A11).
This project depends on `dev.rikka.shizuku:provider` 13.1.5. Separately, P4-A135
records that `PorterShizukuApiProvider` and `dev.rikka.shizuku:provider` ship the same
`moe.shizuku.api.BinderContainer` class and cannot coexist **in one app**.

Does the *installed companion* interact with this app's in-process provider
differently from what the documentation describes for a generic Shizuku app?

### Why unknown

P4-A135 covers the in-app classpath collision, which is a build-time fact and is
settled. It does not cover the installed-companion case, which is a runtime/Android
package-identity interaction. No device is available, and Phase 4 did not read the
`compat/` module's internals.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `PORTER compat/build.gradle.kts:16` | L1 | `2d88f34b` | Companion identity | `moe.shizuku.privileged.api` (P4-A10) |
| `PORTER docs/compatibility.md:43` | L2 | `2d88f34b` | Coexistence | Cannot coexist with Shizuku (P4-A11) |
| `SDK docs/developers.md:330-333` | L2 | `0.9.0` | Keeping upstream provider | Allowed; omit `shizuku-compat` (P4-A132) |
| `SDK docs/developers.md:316-320` | L2 | `0.9.0` | Provider classpath collision | `BinderContainer` clash (P4-A135) |
| `compat/` internals | — | — | Provider/service interaction | **Not read** — Phase 5/21 |
| Device | — | — | Installed-companion behaviour | **Not available** |

### What would resolve it

A device test with the companion installed and this app's build installed, checking
whether the app binds and whether `window.Shizuku`-style paths behave.

### Current best hypothesis

**None recorded.**

### Cross-phase dependencies

Phases 5 and 21. Recorded as P4-F06 in the Phase 4 report.

---

## Unknown U-029 — Porter SDK `0.x` instability

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Priority | Medium |
| Status | Open |
| Owning phase | 21 — Interoperability Investigation |
| Blocks | Nothing now. It is a maintenance risk, not a correctness gate. |
| Evidence | `phase-04/evidence.md` P4-A06, P4-A103, P4-A121, P4-A122, P4-A123, P4-A124, P4-A144 |

### Unknown

The SDK is `0.x` and explicitly unstable: "A minor release can add API and change
behaviour this guide documents" (P4-A123), and "Compatibility is at source level
only: a library compiled against an earlier `0.x` has to be recompiled against the
new SDK" (P4-A122). The application itself has **no stable release tag** — all four
are pre-release (P4-A144).

How much churn will this project inherit, and what is the migration cost when a
release changes documented behaviour?

### Why unknown

This is a forward-looking maintenance question. Phase 4 pinned two commits and cannot
observe future releases. Answering it needs a release-history diff, which is ongoing
work rather than a point finding.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `SDK docs/developers.md:364-374` | L2 | `0.9.0` | Stability policy | `0.x` unstable; source-level only (P4-A122, P4-A123) |
| `SDK docs/developers.md:36-38` | L2 | `0.9.0` | Version pinning advice | "Pin an exact version … rather than `+`" (P4-A124) |
| `SDK protocol/.../PorterProtocol.kt:20-26` | L1 | `0.9.0` | Wire stability | Cumulative versions with a floor; capability bits never reused (P4-A103) |
| `git tag` (both repos) | L1 | — | Release maturity | SDK `0.1.0`→`0.9.0` in 23 days; app all pre-release (P4-A144) |
| Release-history diff | — | — | Behavioural change rate | **Not performed** |

### What would resolve it

A diff of `docs/api-reference.md` and the SDK public surface across SDK tags
`0.7.0 → 0.8.0 → 0.9.0`, establishing what a minor release actually changes. That
gives a rate rather than a guess.

### Current best hypothesis

**None recorded.** Phase 4 observed four SDK tags spanning 2026-09-07 to 2026-09-30,
but release *count* is not change *rate*, and the register does not record counts as
hypotheses.

### Cross-phase dependencies

Phase 21 owns ongoing monitoring. Phase 25 should record the instability as a
maintenance risk attached to any Porter recommendation.

---

## Unknown U-030 — Are the SDK's internal shell-service identifiers stable?

| Field | Value |
| --- | --- |
| Phase raised | 4 |
| Raised on | 2026-10-02 |
| Priority | Low |
| Status | Open |
| Owning phase | 6 — Execution Abstraction Investigation |
| Blocks | Nothing. |
| Evidence | `phase-04/evidence.md` P4-A54, P4-A84, P4-A85, P4-A93 |

### Unknown

The SDK's own shell service is registered with `tag =
"eu.darken.porter.sdk.extras.shell"`, `processNameSuffix = "porter_shell"` and
`version = 2` (P4-A54). These are internal implementation details of `sdk-extras`,
not part of the documented public API. If this project ever needs its own service to
coexist with, or be distinguished from, the SDK's shell service, does it get to rely
on those strings?

### Why unknown

No documentation promises stability of `ShellCalls.args()`. The general rule
(P4-A93) is that identity is `tag`, else class name — which is public and stable. The
specific strings the SDK chooses are not.

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |
| `SDK sdk-extras/.../PorterShell.kt:151-159` | L1 | `0.9.0` | The SDK's own service identity | tag / suffix / `version = 2` (P4-A54) |
| `SDK sdk/.../UserServiceArgs.kt:16,19` | L1 | `0.9.0` | Public identity rule | `tag`, else class name; bump `version` to replace (P4-A85, P4-A93) |
| `SDK docs/` | L2 | `0.9.0` | A stability promise for those strings | **None** |

### What would resolve it

An explicit Phase 6 decision: either depend only on the public `tag`/`version`
contract, or treat the SDK's internal tag as private and never collide with it. A
device test confirming that bumping `version` replaces the service correctly would
support either choice.

### Current best hypothesis

**None recorded.** "Private" is the safer default but is a design choice for Phase 6,
not a finding.

### Cross-phase dependencies

Phase 6 owns it. Recorded as P4-F09 in the Phase 4 report.

---

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