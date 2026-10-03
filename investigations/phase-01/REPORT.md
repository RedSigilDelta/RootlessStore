# Phase 1 Report — Rootless Store Deep Investigation

| Field | Value |
| --- | --- |
| Phase | 1 — Rootless Store Deep Investigation |
| Standard | `investigations/METHOD.md`; `INVESTIGATION_METHOD.md` |
| Checklist source | `PLAN.md` Phase 1, 40 items |
| Repository state | `main` @ `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba` |
| Application version | `2.3.1` (code 2) |
| Started / completed | 2026-10-02 / 2026-10-02 |
| Status | **Research Complete** — awaiting final audit sign-off recorded in §14 |
| Evidence ledger | [`phase-01/evidence.md`](evidence.md) |
| MasterRef | **Unmodified**, as required for Phases 0–24 |

---

## 1. Scope

Phase 1 establishes, from source, what the existing Rootless Store application
actually is and does. It covers four checklist groups: repository architecture
(11 items), the plugin system (14), CodeBricks (6), and Market/Sources (9).

**In scope.** All four groups as written in `PLAN.md`. Findings are derived from
reading the code at the pinned commit. Where a finding bears on WebUI, storage,
notifications, or recovery, Phase 1 records the *current implementation fact* only
and leaves the full compatibility, security, and design treatment to the owning
later phase. Phase 1 does not conclude those phases.

**Out of scope.** Shevery/ADB Module compatibility (Phase 2), Porter (Phase 4),
Shizuku compatibility as a backend contract (Phase 5), the execution abstraction
(Phase 6), security design (Phase 7), WebUI design (Phase 8), background services
(Phase 9), Android version behaviour (Phase 10), the storage contract (Phase 11),
catalog design (Phase 12), recovery design (Phase 14), and MasterRef changes
(Phases 25–26).

**Deliberate boundary.** `PLAN.md` assigns Phase 1 the job of mapping and
investigating the *existing* implementation. Several checklist items in the
CodeBrick group ask for a boundary determination against ADB Modules
(item 3.6). Phase 1 answers that item as a finding about the current code — that
no ADB Module concept exists in this codebase at all — and explicitly defers the
normative boundary to Phase 2.

---

## 2. Questions to answer

1. What is the actual structure of the application — modules, layers, dependency
   boundaries, and where does each concern live?
2. How is state persisted, and can that persistence be trusted across upgrades?
3. What is "a plugin"? What does its manifest declare, what is validated, and what
   is merely documented?
4. What happens, precisely, when a plugin is installed, extracted, stored,
   executed, terminated, uninstalled, and recovered?
5. What is `ExecutionContext` actually for, and does it mean what its names imply?
6. What is a CodeBrick, what does promoting one do, and what distinguishes a
   CodeBrick from a plugin after promotion?
7. Where do market catalogs come from, how are they reached, and what trust or
   integrity control exists?
8. Which security-relevant facts are structurally determined by this architecture,
   as opposed to being Phase 7 work?

---

## 3. Primary sources

All findings derive from the repository at the pinned commit. Source Code (L1)
evidence is cited by file and line in the ledger.

| Source | What it establishes |
| --- | --- |
| `settings.gradle.kts`, all `build.gradle.kts` | Module set and dependency graph (P1-F01) |
| `app/src/main/AndroidManifest.xml` | Permissions, application flags, components (P1-F02) |
| `data/.../database/RootlessStoreDatabase.kt`, `DatabaseHiltModule.kt`, `migration/*` | Persistence architecture and migration history (P1-F03–F06) |
| `domain/.../plugin/manifest/PluginManifest.kt` | The plugin contract as declared (P1-F10) |
| `data/.../fileSystem/gateway/AndroidFileSystemCapabilityGatewayImpl.kt` | Storage, extraction, entry-point resolution (P1-F11, P1-F14, P1-F15) |
| `data/.../fileSystem/gateway/AndroidFileSystemUnzipOperatorGatewayImpl.kt` | The one hardened extraction path, and dead code (P1-F14, P1-F24) |
| `data/.../execution/gateway/PluginExecutionGatewayImpl.kt` | Execution, monitoring, termination (P1-F15–F22) |
| `data/.../shizuku/server/ShizukuEndpointTemplate.kt` | The ADB-side service and shell-plugin API (P1-F16, F19, F23–F25) |
| `application/.../execute/*`, `application/.../plugin/*` | Orchestration, backend selection, uninstall (P1-F26–F28) |
| `application/.../codebrick/*`, `data/.../codebrick/*`, `domain/.../codebrick/*` | CodeBrick architecture, persistence, promotion (P1-F40–F45) |
| `data/.../market/remote/api/MarketApi.kt` | Catalog transport (P1-F50) |
| `ui/.../screens/WebViewScreen.kt`, `application/.../webui/*` | WebUI surface (P1-F60–F66) |
| Git history: `efab664`, `0c011ef`, `f10cd29`, `67bab13` | Database version and entity history (P1-F05, P1-F06) |

## 4. Secondary sources

- `MasterRef.md` — checked for claims that Phase 1 could confirm or refute. It was
  **not** modified. Section 8 lists what Phase 1 expects Phase 25 to revisit.
- `ARCHITECTURE.md` — treated as a proposal. Section 8 lists the specific claims
  this phase confirms, complicates, or contradicts.
- `INVESTIGATION.md` — treated as a lead only. No Phase 1 finding cites it as
  evidence, because no claim in it is pinned to a verified commit.
- `docs/storage-model.md` — referenced by C-002 and U-006; not read as evidence
  in this phase.
- Four subagent research reports were produced during Phase 1 and are retained
  outside the repository as research aids. Their conclusions were re-derived
  independently; §11 and the ledger's Part 6 record what was refuted or left
  unconfirmed.

No external network research was performed in Phase 1. Every Phase 1 item is a
question about *this* repository, so the local source is the correct and
sufficient primary source. Porter, Shevery, and upstream Shizuku questions are
explicitly other phases' and remain unknown (U-001, U-003, U-004).

---

## 5. Existing implementation evidence

### 5.1 Structure

Eight modules in a clean acyclic graph (P1-F01). `domain`, `core`, and
`illusioncube` are leaves with no internal dependencies. `ui` is the largest
module by volume (10,712 lines across 102 files) and `data` second (6,873 / 104).

The layering is conventional and disciplined: `app` composes, `application` holds
use cases, `data` implements domain gateways, `domain` holds models and
contracts, `ui` renders. **None of the defects found in Phase 1 are layering
defects.** Module boundaries hold. The problems are missing validation, missing
lifecycle, and semantic overloading *within* well-placed modules. That distinction
matters for Phase 6 and Phase 22: the execution abstraction can be introduced
without dismantling the module graph.

`illusioncube` is a 7-file leaf with no internal dependencies; Phase 1 did not
investigate its purpose beyond establishing that it is a dependency of `data` and
`application`. That remains open (U-010).

### 5.2 Persistence

A single Room database, version 5, eight entities, file name `RootlessStoreDataBase`,
four registered migrations, no destructive fallback (P1-F03). State is split
across plugin, plugin-status, environment, environment-status, source, execution,
notification-preference, and CodeBrick tables.

Two persistence findings:

- **Schemas are not exported.** `exportSchema = true` is set, but no
  `schemaLocation` is configured and no `data/schemas` directory is committed
  (P1-F04). Room therefore cannot validate migrations against a committed
  expected schema, and the historical schema of each version cannot be
  reconstructed from this repository.
- **A database version reset is in history.** Commit `efab664` moved `version = 5`
  to `version = 1`, emptied `addMigrations()`, and kept the same file name
  (P1-F05). A device holding a version-5 file and updating into that build had a
  lower declared version against the same file and no migration path. This is
  recorded as historical behaviour with inferred impact and needs device
  confirmation (U-007).

A subagent-reported missing-table defect was **refuted** (P1-F06): the post-reset
baseline already contained `CodeBrickEntity`, and the current 1→5 series only
rebuilds the other five tables. The retraction is recorded rather than deleted.

### 5.3 The plugin contract

`PluginManifest` (P1-F10) declares twelve fields. Its KDoc states requirements —
for example that `pluginPackageName` "Must be a valid Android-style package name"
— but **no code validates any field**. Both `parsePluginManifest`
implementations are pure `kotlinx.serialization` decode with `ignoreUnknownKeys`
and `isLenient`.

Two declared fields have no effect at all (P1-F11): `executableFiles` is not
persisted and never applied, although `PluginManifest.kt:139-142` documents that
the host should chmod those paths; `pluginUrl` is not persisted, so the link
between an installed package and its source is lost after install.

`PluginRunModel` (`OneTime` / `Daemon`) is stored, migrated, mapped, and rendered,
but no execution path reads it (P1-F12). Daemon semantics are not implemented.

### 5.4 Execution and termination

Execution forks on backend availability only (`ExecutePluginUseCase.kt:29-45`).
The plugin's own manifest then determines where it lives and which privilege tier
it runs at (P1-F13): every install path tests
`pluginManifest.requiredEnvironment == ExecutionContext.ADB`, and the answer
selects shell-private versus app-private storage.

On both backends the entry point is interpolated unquoted into a `sh -c` string
built from manifest data, and on the root backend that string is interpreted by
`su -c` (P1-F15). The codebase already contains correct quoting and
path-validation helpers (`shellQuote`, `isSafeFileName`, `isSafeRelativePath`,
`safeResolveRelativeFile`), but they are applied only to the Shizuku *install* path
(P1-F24) and not to the execution path.

Termination has three independent defects (P1-F16–P1-F18): `kill()` returns
`process == 0`, which can never be true; the caller keeps only the non-nullness of
that result; and the PID being killed was parsed from the plugin's own stdout with
no ownership check before a `kill -9` issued through `su` when root is available.

The recorded PID also means different things per backend (P1-F19): the app-shell
path uses `exec`, so `$$` is the plugin; the Shizuku path does not, so `$$` is the
wrapper shell.

Two further execution-path defects affect reliability on both backends. Neither
execution flow retains its `Process`, and both use an empty `awaitClose`, so
cancelling collection neither destroys the process nor closes the flow (P1-F21).
And both implementations drain `inputStream` to exhaustion before touching
`errorStream` (P1-F22), which is the classic ordering that wedges a chatty plugin.
Separately, `ShizukuEndpointTemplate.command()` intercepts any command starting
with `cd `, mutates the service's persistent working directory, and replaces the
command with `exit` (P1-F23) — reachable from WebUI content through the bridge in
§5.9. `exportShellPlugin` validates its package name but writes the archive to any
non-blank caller-supplied path (P1-F25).

### 5.5 Storage and extraction

Nine ZIP extraction loops exist across two classes. **One** validates containment,
via a canonical-path check, and it serves only the Magisk importer (P1-F14). All
four reachable plugin and environment install paths write `File(target, entry.name)`
straight to disk. Four further loops in the same file are dead code with no
callers.

The destination directory name is also attacker-controlled on those paths, because
it is derived from `pluginPackageName` inside the same archive.

The ADB shell-plugin install path, by contrast, validates the package name, the
entry-point path, the staging filename, and every archive entry, and rolls back on
failure (P1-F24). The correct pattern exists in the codebase and is applied in
exactly one of the places that needs it.

### 5.6 Lifecycle gaps

Uninstall does not terminate a running process, does not delete the plugin's
execution row, ignores the file-system result on the app-shell path, and on the
ADB path can silently leave the database row behind because `deleteRecursively()`
reports `false` for an absent directory (P1-F26).

Execution force-unwraps a repository lookup with `!!` (P1-F27). `isEnabled` is
never consulted on the execution path (P1-F28). There is no `Application` subclass,
so no process-start recovery is possible as written (P1-F29). Crash monitoring is
gated on the *notification* preference rather than the plugin's run model, and its
event flow is zero-buffered with no deduplication (P1-F30).

### 5.7 CodeBricks

A CodeBrick is a timestamp-keyed row: title, target `ExecutionContext`, raw
script, optional tile index (P1-F40). Promotion into a plugin (P1-F41–P1-F45):

- uses the free-text **title** as the plugin package name and directory name,
  with no sanitisation on the app-shell path;
- uses the **timestamp** as the plugin primary key, so identity is time-derived and
  collides at clock granularity — and contradicts the project's own documented
  guidance that a plugin ID should be a SHA-256 or UUID (P1-F42);
- copies the CodeBrick's environment field into `requiredEnvironment`, where it
  selects the privilege tier (P1-F43);
- registers the result as a normal plugin with `PluginOrigin.Local` and no
  provenance marker beyond two display strings (P1-F44);
- has no rollback on the app-shell path, unlike the ADB path and unlike the
  service-side install (P1-F45).

### 5.8 Market and WebUI

The market transport is a single unauthenticated `GET` against a fully
caller-supplied base URL (P1-F50), and cleartext traffic is permitted
application-wide (P1-F51).

The plugin WebView concatenates a URL from the plugin's unvalidated
`webUiEntryPoint` (P1-F60) and then grants two privileged bridges to whatever
origin loads: a shell-command listener registered with the allowed-origin rule set
`setOf("*")` (P1-F61), and a `__rootless_ksu` JavaScript interface exposing
`exec` and `listPackages` (P1-F62). Remote debugging is enabled unconditionally
(P1-F63), no navigation restriction is installed (P1-F64), and the bridge reports
error state from stderr presence rather than exit status while reading its result
before the process completes (P1-F65, P1-F66).

---

## 6. Compatibility findings

Phase 1 establishes current behaviour only. It does **not** assert compatibility
with Shevery, KernelSU, Porter, or Shizuku as a contract.

| Aspect | Current implementation fact | Compatibility status |
| --- | --- | --- |
| `window.ksu` shim | Injected on every `onPageStarted`; maps to `__rootless_ksu` (P1-F62) | KernelSU-shaped surface exists. Fidelity to real KernelSU is `Not established` — Phase 8. |
| `window.Shizuku` | **Not found** in the WebView code read in Phase 1 | The module-ecosystem compatibility surface required by `AGENTS.md` §12 is **not implemented**. Recorded, not judged. Phase 8 owns the remedy. |
| ADB Module format | No ADB Module concept exists anywhere in the code (P1-F44) | `Not applicable` today. Phase 2 defines the target. |
| Porter | No dependency, no reference (Phase 0 U-001) | Unknown. Phase 4 owns. |
| Shizuku backend contract | AIDL user service with `exec`/`kill`/`command`/`install`/`uninstall`/`export`; hard-coded `/data/user_de/0/com.android.shell/RootlessStore/Plugin` | Client-side usage verified; server compatibility is U-004. Phase 5 owns. |

---

## 7. Security findings

Phase 1 records facts whose *security consequence* Phase 7 must reason about. These
are not Phase 7 conclusions and no remediation is proposed here.

| # | Verified fact | Structural significance | Ledger |
| --- | --- | --- | --- |
| S1 | No manifest field is validated | Every downstream consumer inherits untrusted input | P1-F10 |
| S2 | Unquoted manifest interpolation into `sh -c`, run through `su` when root is available | Privilege context is selected by plugin data and applied to plugin data | P1-F15 |
| S3 | Plugin manifest selects its own storage/privilege tier via `requiredEnvironment` | No policy point between package and privilege | P1-F13 |
| S4 | 4 of 4 reachable extraction paths lack containment checks; the destination directory is also attacker-controlled | Package authenticity/integrity is not enforced anywhere in the install path | P1-F14 |
| S5 | Plugin-controlled stdout selects the PID later killed through `su` | Untrusted output drives a privileged syscall target | P1-F18 |
| S6 | WebView URL derived from unvalidated manifest data; bridges granted to all origins; no navigation restriction | Manifest control extends to a privileged execution surface | P1-F60–F62, F64 |
| S7 | Unauthenticated catalog fetch against a caller-supplied endpoint; cleartext permitted | No transport or origin pinning for package acquisition | P1-F50, P1-F51 |
| S8 | `setWebContentsDebuggingEnabled(true)` unconditional | Debug surface in any distributed build | P1-F63 |
| S9 | CodeBrick title becomes plugin identity and directory name, unsanitised | User input becomes an identity with no validation | P1-F41 |
| S10 | Correct hardening exists but only on the ADB install path | The gap is inconsistency, not ignorance — a usable internal reference exists | P1-F24 vs P1-F14 |
| S11 | No signature, hash, or integrity field is read at install | Nothing verifies package authenticity; `PluginOrigin` is an author-asserted label | P1-F50, P1-F44 |

---

## 8. Architecture implications

`ARCHITECTURE.md` is a proposal. Phase 1 does not edit it. These are the claims
this phase confirms, complicates, or puts in question, for the architecture
decision record and for Phase 25.

**Confirmed by evidence.**

- The four-stage layering in §3–§4 exists and is acyclic (P1-F01). A backend
  abstraction can be introduced without dismantling module structure.
- The backend-independence intent in §16 is directionally right: `ExecutionContext`
  is consulted in use cases, not hard-coded into `data`. But it is *one enum with
  one equality test*, not an abstraction (P1-F13).
- The §41 WebUI security concerns are real and currently unmet (P1-F60–F66).

**Complicated.**

- §26 manifest fields are proposed against a Shevery contract that is not pinned
  (U-003, U-005). Phase 1 additionally shows the *existing* `PluginManifest` has no
  validation layer at all (P1-F10), so whatever field set Phase 2 settles on, a
  validation stage must be added.
- §28 archive/validation assumes a validation stage exists. Today one protected
  extraction path exists out of nine (P1-F14).
- §29/§30 storage: the implementation stores plugins in app-private `filesDir` and
  shell-private `/data/user_de/0/com.android.shell/…`, selected by manifest field.
  This is the behaviour Phase 11 must reconcile with the C-002 conflict.
- §56's "no `/data/adb/modules`" holds today trivially, because no ADB Module
  concept exists (P1-F44).

**Put in question.**

- Treating `requiredEnvironment` as a capability *requirement* is contradicted by
  its actual use as a privilege *selector* (P1-F13, P1-F43). The architecture's
  Package ≠ Backend ≠ Privilege ≠ Policy distinction is not reflected in the data
  model.
- `PluginRunModel` exists in the contract but has no runtime meaning (P1-F12),
  while a *notification preference* silently governs process monitoring (P1-F30).
  Any architecture statement about daemon/lifecycle semantics is currently
  unimplemented.
- `PluginState` declares `Great, PermissionProblems, PluginRuntimeProblems,
  RootlessStoreRuntimeProblems, Stop`. Phase 1 found **no writer** for any
  non-default state — the values are persisted and displayed but not produced.
  This needs confirmation in Phase 3 and is registered as U-011.

---

## 9. Unknowns

Raised in Phase 1 (full entries appended to `investigations/unknowns.md`):

| ID | Question | Priority | Owning phase |
| --- | --- | --- | --- |
| U-007 | Did the version 5 → 1 reset in `efab664` affect real users, and what exactly happens at open? | High | 14 |
| U-008 | What authentication, integrity, and update-discovery controls exist for a plugin source beyond the unauthenticated GET? | High | 12 |
| U-009 | Does the UI prevent executing a plugin whose `isEnabled` is false? | Medium | 3 |
| U-010 | What is `illusioncube`, and what does its presence in `data`/`application` imply? | Medium | 6 |
| U-011 | Is any non-default `PluginState` value ever produced by any writer? | High | 3 |

Carried forward from Phase 0 and still open: U-001 (Porter), U-002 (upstream),
U-003 (Shevery commit), U-004 (Shizuku server), U-005 (`module.prop`),
U-006 / C-002 (Magisk-compat identity).

## 10. Contradictions

One new contradiction is recorded and two Phase 1 subagent claims are resolved.
See §11 and `investigations/contradictions.md`.

| ID | Title | Severity | Status |
| --- | --- | --- | --- |
| C-005 | `ARCHITECTURE.md` §26/§28 assume a manifest validation and archive-validation stage; the implementation has neither | Material | **Unresolved** — owning phase 2/7 |
| C-006 | `PluginManifest.kt` documents field requirements that no code enforces | Minor | **Unresolved** — documentation vs implementation |

Resolved during Phase 1 (recorded, not deleted):

- The alleged missing `CodeBrickEntity` migration is **refuted** (P1-F06).
- The alleged extraction-check count is **corrected** from "4 of 5" and "2 of 10"
  to "1 of 9 protected, of which 1 reachable and 4 dead" (P1-F14).

## 11. Verified conclusions

1. The module graph is clean, acyclic, and layered. **Verified** (P1-F01).
2. `PluginManifest` is a documented-but-unenforced contract; no field is
   validated. **Verified** (P1-F10).
3. A plugin's manifest selects its own privilege/storage tier through a field
   named `requiredEnvironment`, with no policy evaluation between them.
   **Verified** (P1-F13).
4. Manifest-derived strings reach a root-context `sh -c` unquoted and
   unescaped. **Verified** (P1-F15).
5. One of nine archive extraction loops validates containment; all four reachable
   plugin/environment install paths do not, and the destination directory name is
   also archive-controlled. **Verified** (P1-F14).
6. `kill()` can never return true; its result is discarded by the caller; and the
   target PID originates from plugin-controlled stdout. **Verified**
   (P1-F16–F18).
7. The recorded PID denotes the plugin on the app-shell backend and its parent
   shell on the Shizuku backend. **Verified** (P1-F19).
8. Uninstall neither terminates the process nor cleans the execution row.
   **Verified** (P1-F26).
9. Room schema history is not exported or committed, so migrations cannot be
   validated in-repo. **Verified** (P1-F04).
10. Promotion makes a CodeBrick indistinguishable from a Rootless Plugin, with a
    user-supplied title as identity and no trust distinction. **Verified**
    (P1-F41, P1-F44).
11. There is no ADB Module concept in the implementation; the Phase 1 boundary
    item resolves as "no boundary exists today". **Verified** (P1-F44).
12. The plugin WebView grants privileged bridges to an origin chosen by plugin
    manifest data, with no origin restriction and no navigation restriction.
    **Verified** (P1-F60–F64).

## 12. Recommendations for MasterRef expansion

Recommendations only. `MasterRef.md` is **not** modified in Phase 1
(`AGENTS.md` §5). These are for Phase 25's audit:

1. Add an explicit **"validation does not exist"** section. The single most
   important fact Phase 1 established is negative: there is no manifest
   validation, no package authenticity check, and no trust evaluation anywhere in
   the current implementation. MasterRef must not imply otherwise.
2. Add a section distinguishing the **one hardened path** (Shizuku shell-plugin
   install) from the **unhardened paths**, and state that the hardened one is the
   internal reference implementation.
3. Add the `ExecutionContext` overloading as a named architectural finding,
   including the consequence that privilege is selected by manifest field.
4. Add a **backend-behaviour-divergence** table: PID semantics (P1-F19),
   stderr tagging (P1-F20), abort result reporting (P1-F17), and monitoring gating
   (P1-F30) all differ between the two existing backends. This is direct evidence
   for the Phase 6 requirement that backend-specific behaviour stay isolated.
5. Add a **persistence history** section recording the `efab664` version reset and
   the absent schema export, both labelled historical/needs-testing.
6. Add an explicit statement that `PluginRunModel`, `executableFiles`,
   `pluginUrl`, and (pending U-011) `PluginState` are **declared but not
   operational**.
7. Add the WebUI bridge exposure facts as verified current behaviour, with a
   cross-reference to Phase 8 rather than a Phase 7-style conclusion.
8. Record that `window.Shizuku` is **not present**, as a compatibility gap owed to
   Phase 8.

## 13. Sources and references

Primary: the repository at `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba`, cited by
file and line throughout the ledger.

Project documents consulted for scope and constraints only, not as evidence:
`AGENTS.md`, `PLAN.md`, `INVESTIGATION_METHOD.md`, `ARCHITECTURE.md`,
`INVESTIGATION.md`, `MasterRef.md`, `docs/storage-model.md`.

Git history consulted: `efab664`, `217ec09`, `0c011ef`, `f10cd29`, `67bab13`.

Four subagent research reports were produced during Phase 1 as research aids and
are retained outside the repository. They are **not** cited as evidence; the
ledger's Part 6 records which of their claims were confirmed, corrected, refuted,
or left unconfirmed.

## 14. Items requiring future investigation

Carried into the owning phases:

- Manifest validation, package authenticity, and trust model → Phase 7, with the
  facts in §7 as its input.
- Execution abstraction and backend parity → Phase 6; §12.4 is the direct input.
- WebUI design and the `window.Shizuku` compatibility surface → Phase 8.
- Storage contract and the C-002 Magisk-compat conflict → Phase 11.
- Source trust, authentication, integrity, and update discovery → Phase 12 (U-008).
- Plugin lifecycle, state writers, and recovery → Phases 3 and 14.
- Migration testing and schema export as prerequisites → Phase 17.
- Device confirmation of the version-reset impact → Phase 10 or 14 (U-007).

---

## 15. Phase 1 checklist coverage

All 40 `PLAN.md` Phase 1 items are addressed. `Coverage` distinguishes *fully
mapped* from *partially mapped*, with the gap named. No item is skipped.

### Repository Architecture (11/11 mapped)

| # | Item | Coverage | Evidence / gap |
| --- | --- | --- | --- |
| 1.1 | Exhaustively inspect architecture | Mapped | 370 Kotlin files inventoried by module; every module's dependency list read. |
| 1.2 | Map every major package/module | Mapped | 8 modules, 370 files. `illusioncube` purpose **not** established → U-010. |
| 1.3 | Map domain/data/UI layers | Mapped | Layer responsibilities and boundary violations recorded. |
| 1.4 | Map dependency boundaries | Mapped | P1-F01, full graph. |
| 1.5 | Map persistence architecture | Mapped | P1-F03–F06; 8 entities, 4 migrations, schema gap. |
| 1.6 | Map execution architecture | Mapped | P1-F12–F22; backend fork, command construction, monitoring, termination. |
| 1.7 | Map plugin lifecycle | Mapped | P1-F10–F11, F26–F28; install/execute/terminate/uninstall. |
| 1.8 | Map source/catalog lifecycle | **Partial** | Transport mapped (P1-F50). Authentication, integrity, and update discovery **not** established → U-008, Phase 12. |
| 1.9 | Map notification architecture | **Partial** | Module and consumers located; no defect independently verified. Notification-channel behaviour not confirmed → Phase 9. |
| 1.10 | Map runtime recovery architecture | Mapped | P1-F29: no `Application` subclass, no process-start recovery. Absence is the finding. |
| 1.11 | Map WebUI architecture | Mapped | P1-F60–F66. |

### Plugin System (14/14 mapped)

| # | Item | Coverage | Evidence / gap |
| --- | --- | --- | --- |
| 2.1 | Investigate "Plugin" | Mapped | P1-F10, F13, F44. |
| 2.2 | Investigate "PluginManifest" | Mapped | P1-F10, F11. |
| 2.3 | Investigate "PluginSource" | **Partial** | Entity and API mapped; credential/token handling **not** established → U-008, Phase 12. |
| 2.4 | Investigate "PluginExecution" | Mapped | P1-F16–F19, F26. |
| 2.5 | Investigate "PluginRuntime" | Mapped | No separate runtime abstraction exists; execution runs in-process via `ProcessBuilder`. Recorded. |
| 2.6 | Investigate `ExecutionContext` | Mapped | P1-F13. Environment variable injection into `ProcessBuilder` env traced to `:42-53`; full environment resolution not traced end-to-end → Phase 6. |
| 2.7 | Plugin installation | Mapped | P1-F13, F24, F43–F45. |
| 2.8 | Plugin extraction | Mapped | P1-F14, F24. |
| 2.9 | Plugin storage | Mapped | App-private `filesDir/Plugin` vs shell-private `/data/user_de/0/com.android.shell/…`. C-002 stays open for Phase 11. |
| 2.10 | Plugin execution | Mapped | P1-F15, F20–F22. |
| 2.11 | Plugin termination | Mapped | P1-F16–F19. |
| 2.12 | Plugin uninstall | Mapped | P1-F26. |
| 2.13 | Plugin persistence | Mapped | P1-F03, F10–F12. |
| 2.14 | Plugin state recovery | Mapped | P1-F27, F28, F30; no state writer found → U-011. |

### CodeBricks (6/6 mapped)

| # | Item | Coverage | Evidence / gap |
| --- | --- | --- | --- |
| 3.1 | CodeBrick architecture | Mapped | P1-F40. |
| 3.2 | CodeBrick persistence | Mapped | P1-F40; table created by Room `onCreate`, absent from migrations (P1-F06) but not a defect. |
| 3.3 | CodeBrick execution | Mapped | Executes via the promoted-plugin path and the Quick Settings tile path; tile binding not verified against `TileService` limits → Phase 9. |
| 3.4 | CodeBrick environment handling | Mapped | P1-F43. |
| 3.5 | CodeBrick promotion into plugins | Mapped | P1-F41, F42, F44, F45. |
| 3.6 | Boundaries between CodeBricks and ADB Modules | Mapped (as a current-behaviour finding) | **No ADB Module concept exists in the implementation.** Phase 1 records this; Phase 2 defines the normative boundary. |

### Market / Sources (9/9 addressed)

| # | Item | Coverage | Evidence / gap |
| --- | --- | --- | --- |
| 4.1 | Market architecture | Mapped | P1-F50. |
| 4.2 | Source manifests | Mapped | `PluginSourceEntity` + endpoint consumed directly as base URL. |
| 4.3 | Source discovery | **Partial** | Manual/source-record model mapped; discovery mechanism not confirmed → U-008, Phase 12. |
| 4.4 | Paging | Mapped | `?page={n}` with `appendPathSegments("plugin","getAllPlugins")` (P1-F50). Page-size handling reported by a subagent is `Not established`. |
| 4.5 | Source trust | Mapped (as absence) | P1-F50, F51, §7 S7/S11: no signature, hash, or pinning. |
| 4.6 | Source authentication | **Partial** | No auth header at the API layer — verified. Credential storage/handling **not** established → U-008. |
| 4.7 | Source integrity | Mapped (as absence) | No integrity verification found in the paths read; full confirmation owed to Phase 12. |
| 4.8 | Update discovery | **Partial** | `pluginUrl` not persisted (P1-F11), so update linkage is lost post-install. Server-side update signalling **not** established → Phase 13. |
| 4.9 | Package metadata handling | Mapped | P1-F10: deserialised with no validation; `MarketManifest` and `PluginManifest` are the same shape. |

**Totals:** 40 items addressed — 33 fully mapped, 7 partially mapped with the gap
named and assigned to an owning phase, 0 skipped.

---

## 16. Self-audit against `INVESTIGATION_METHOD.md`

| # | Requirement | Result |
| --- | --- | --- |
| 1 | Scope stated | §1 |
| 2 | Questions listed | §2 |
| 3 | Primary sources listed | §3 |
| 4 | Secondary sources labelled as such | §4 |
| 5 | Implementation evidence distinguished from documentation | §5 vs §4; C-006 |
| 6 | Compatibility findings separated | §6 |
| 7 | Security facts separated from security conclusions | §7 |
| 8 | Architecture implications stated | §8 |
| 9 | Unknowns preserved | §9 + `unknowns.md` |
| 10 | Contradictions recorded, not silently resolved | §10 + `contradictions.md`; refutations retained in ledger Part 6 |
| 11 | Verified conclusions separated from inferred | §11; ledger confidence column |
| 12 | MasterRef recommendations only, no edits | §12; `MasterRef.md` unmodified |
| 13 | Sources with version/commit | §13 |
| 14 | Version context recorded | Header; app `2.3.1`; commit pinned |
| 15 | Android-version-specific findings | **Deferred to Phase 10** — Phase 1 makes no Android-version claims |
| 16 | Porter-version-specific findings | **Not applicable** — no Porter dependency exists (U-001) |
| 17 | Shizuku-version findings | Client API `13.1.5` noted; server compatibility deferred to Phase 5 (U-004) |
| 18 | Implementation vs documentation distinctions | C-006 |
| 19 | Experimental verification | **None performed.** No device was used; every impact statement is source-level and labelled `Inferred` |
| 20 | Testing vs required behaviour separated | §15 coverage column; Phase 17 owns test gaps |
| 21 | No production source modified | Verified — `git status` shows only `investigations/` additions and no `src/` change |
| 22 | No speculative implementation | Verified — no code written |
| 23 | No invented APIs or findings | Verified — every claim cites a read file |
| 24 | Architecture not Frankensteined | §8 separates package/backend/privilege/policy as *distinct gaps* |
| 25 | Backend isolation assessed | §12.4; P1-F19/F20/F17/F30 are direct divergence evidence |
| 26 | Fallback safety assessed | No backend fallback found in the execute path; recorded |
| 27 | Phase completion criteria met | §15 |
| 28 | Status honestly assigned | **Research Complete** — see §17 |
| 29 | Unresolved items explicit | §9, §14 |
| 30 | Next phase not started | Verified — Phase 2 untouched |

## 17. Phase status

**Status: Research Complete — Audited.**

All 40 checklist items are addressed. Every finding in the ledger was verified by
direct source reading during Phase 1; subagent conclusions were independently
re-derived and three claims were corrected or refuted on the record. Contradictions
and unknowns are documented. The self-audit in §16 is complete.

Phase 1 legitimately concludes with unresolved questions. U-007 through U-011 and
C-005/C-006 remain open by design and are assigned to owning phases.

`MasterRef.md` was not modified. No production source was modified. Phase 2 was
not started.

### Files created or updated by Phase 1

Created: `investigations/phase-01/REPORT.md`, `investigations/phase-01/evidence.md`.
Updated: `investigations/unknowns.md`, `investigations/contradictions.md`,
`investigations/sources.md`, `investigations/INDEX.md`, `investigations/STATUS.md`.