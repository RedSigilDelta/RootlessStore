# ADB Modules App — Phase 03 Evidence Ledger

Phase: `03 — ADB Module Lifecycle`
**Standard:** `investigations/METHOD.md` §9 (evidence ledger), template
`investigations/templates/EVIDENCE-LEDGER.md`
**Method authority:** `INVESTIGATION_METHOD.md` §34
**Opened by:** Phase 3
**Last updated:** 2026-10-02

---

## 0. Pins used by every row in this ledger

| Layer | Repository | Commit | Date | Why this pin |
| --- | --- | --- | --- | --- |
| `LOCAL` | this project (Rootless Store fork) | `6df93ae8d2c3dbb84b461b4eecb0c7f65de8a5b2` | 2026-10-02 | Current `HEAD`; Phase 1 worked at `4b35c36`, Phase 2/3 at `6df93ae` |
| `REFERENCE` | `HmnDev-Tech/shevery` | `bfc55ce9c8898043f1a5c4896be276d154d08243` | 2026-10-02 | Same pin Phase 2 used; keeps the two phases comparable |
| `REFERENCE` | `kerneldroid/Nightzuku` | `60a8feb65d1a9c95692624222ef26afb3063b9d3` | 2026-07-20 | Format origin; same pin as Phase 2 |

Per `INVESTIGATION_METHOD.md` §51, every finding carries its layer label.
`REFERENCE` = the Shevery/Nightzuku ADB Module implementation this project would
have to be compatible with. `LOCAL` = this repository's Rootless Plugin /
CodeBrick implementation. They are **not** interchangeable and are never merged.

Evidence types used (from `INVESTIGATION_METHOD.md` §11 source hierarchy, mapped
onto the project vocabulary in `investigations/METHOD.md`):

- `L1-SOURCE` — implementation source, read directly.
- `L2-DOC` — official project documentation, quoted as a *claim*.
- `NEGATIVE` — a search that returned nothing; recorded so it is not re-run.
- `INFERENCE` — derived from L1 rows, not directly stated by any of them.

---

## 1. Discovery

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A01 | REFERENCE | Discovery is GitHub-API-only. `ModuleValidator.validateRepo` probes `https://api.github.com/repos/$owner/$repo/contents/module.prop`; if absent it scans root dirs, then second-level dirs | `manager/.../module/discovery/ModuleValidator.kt:16-24,64-66,148-151` | L1-SOURCE | VERIFIED | High |
| P3-A02 | REFERENCE | Discovery "validation" is exactly two things: `module.prop` is reachable, and `id` matches `[A-Za-z][A-Za-z0-9._-]{1,63}` | `ModuleValidator.kt:178-183` | L1-SOURCE | VERIFIED | High |
| P3-A03 | REFERENCE | Discovery supports three sources: topic/keyword repo search, per-owner search, and a fixed official list | `ModuleDiscoveryManager.kt:90,172,189,227` | L1-SOURCE | VERIFIED | High |
| P3-A04 | REFERENCE | Discovery results are cached with a `lastChecked` timestamp and served stale when `forceRefresh` is false | `ModuleDiscoveryManager.kt:46,75,81`; `discovery/DiscoveryCache.kt` | L1-SOURCE | VERIFIED | High |
| P3-A05 | REFERENCE | An optional GitHub token is attached as an `Authorization` header to GitHub requests; absence is not an error | `ModuleDiscoveryManager.kt:32`; `ModuleValidator.kt:31-34` | L1-SOURCE | VERIFIED | High |
| P3-A06 | REFERENCE | Rate-limit state is tracked from response headers and fed back into request pacing | `discovery/RateLimitTracker.kt`; `ModuleValidator.kt:26,58,84` | L1-SOURCE | VERIFIED | Medium |
| P3-A07 | REFERENCE | Nightzuku has the same discovery surface (`discovery/` tree present, same file set) | `/tmp/nightzuku/.../discovery/ModuleValidator.kt` (same 4 files) | L1-SOURCE | VERIFIED | Medium |

**Seam note (item 1).** Discovery is owned by **Phase 12** in `PLAN.md`. Phase 3
records only what the lifecycle boundary *requires* of it: a discovered entry must
carry `moduleId`, `repoFullName`, `subPath`, `version`, `versionCode`, so that
`installModule(moduleId, owner, repo, subPath)` and `checkUpdate(module, …)` can
be driven from it (P3-A36, P3-A44). What Phase 12 must still establish: source
trust, auth, integrity, duplicate IDs, offline behaviour, caching policy.
→ Seam record `SEAM-1`.

---

## 2. Download

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A08 | REFERENCE | Two install modes, chosen by a user setting: `sources` (zip the repo tree via `SourceZipBuilder`) and `release` (download a release asset) | `ModuleSettings.kt:417-427`; `update/ModuleInstaller.kt:44-52` | L1-SOURCE | VERIFIED | High |
| P3-A09 | REFERENCE | Release download selects the first asset ending `.zip` whose name contains `moduleId` or `repo`, else the first `.zip` asset | `ModuleInstaller.kt:187-193` | L1-SOURCE | VERIFIED | High |
| P3-A10 | REFERENCE | The downloaded ZIP is streamed to `cacheDir/module_zips/<moduleId>-release.zip` with **no** size cap, no content-type check, and no hash comparison | `ModuleInstaller.kt:196-232` | L1-SOURCE | VERIFIED | High |
| P3-A11 | REFERENCE | Cached ZIPs are pruned by mtime older than 24 h, and only in `cacheDir/module_zips` | `ModuleInstaller.kt:233-246` | L1-SOURCE | VERIFIED | High |
| P3-A12 | REFERENCE | `cleanupOldZips` runs at the *start* of every `installModule`, so pruning is opportunistic, not scheduled | `ModuleInstaller.kt:39` | L1-SOURCE | VERIFIED | High |
| P3-A13 | LOCAL | Market download streams the package body into the extractor with no size cap and no integrity check | `data/.../plugin/gateway/PluginGatewayImpl.kt:27-34` | L1-SOURCE | VERIFIED | High |
| P3-A14 | LOCAL | `InstallPluginFromMarketUseCase` passes a **catalog-supplied** `pluginUrl` and a **catalog-supplied** `PluginManifest` directly to the installer; the manifest is trusted for identity and entry point, never re-derived from the archive | `application/.../plugin/InstallPluginFromMarketUseCase.kt:15-23` | L1-SOURCE | VERIFIED | High |

**Seam note (item 2).** Download is owned by **Phase 12** (and, for updates,
**Phase 13**). The lifecycle-relevant fact is P3-A14: on the market path the
*archive* is not the authority on what will be installed — the *catalog* is. That
is a trust-model input, not a lifecycle one, and it is why P3-A14 is carried into
Phase 12/7 rather than resolved here.
→ Seam record `SEAM-2`.

---

## 3. Verification

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A15 | REFERENCE | **No** signature, hash, checksum, or provenance verification exists anywhere in the module subsystem. A repository-wide search for `sha256|checksum|signature|hash` under `manager/.../module/` returns only `java.util.concurrent.ConcurrentHashMap` | `grep -rni "sha256\|checksum\|signature\|hash" --include=*.kt manager/src/main/java/moe/shizuku/manager/module/` → 2 hits, both `ConcurrentHashMap` | NEGATIVE | VERIFIED | High |
| P3-A16 | REFERENCE | `updateJson` responses are decoded into `UpdateJsonResponse(version, versionCode, zipUrl, changelog)` — **no** digest field is even modelled, so a publisher cannot supply one | `update/UpdateChecker.kt:333-339` | L1-SOURCE | VERIFIED | High |
| P3-A17 | REFERENCE | Verification at install is limited to: `module.prop` at archive root, `id` regex, entry count ≤ 2048, extracted bytes ≤ 200 MiB, two-layer path containment | `AdbModuleManager.kt:24-27,73-79,89-112,360-375` | L1-SOURCE | VERIFIED | High |
| P3-A18 | REFERENCE | Every failure inside `install` surfaces only as `IllegalStateException`/`IllegalArgumentException` message text; the caller receives `Result.failure(e)` and the message is the only user-visible signal | `AdbModuleManager.kt:76,79,91,106,116,117`; `ModuleInstaller.kt:65-66` | L1-SOURCE | VERIFIED | High |
| P3-A19 | LOCAL | Local install verification is a JSON deserialise with `ignoreUnknownKeys`/`isLenient`; a schema mismatch throws and is wrapped as "Can't parse plugin manifest" | `data/.../AndroidFileSystemCapabilityGatewayImpl.kt:397-403`; `application/.../InstallPluginUseCase.kt:21-29` | L1-SOURCE | VERIFIED | High |
| P3-A20 | LOCAL | Four of five local extraction loops build `File(dir, entry.name)` with no path validation, no entry-count cap and no byte cap; a crash or a hostile archive is not survivable by construction | `AndroidFileSystemCapabilityGatewayImpl.kt:184,233,266,297` (the fifth loop is `unzipFromFileToDirectory`, hardened) | L1-SOURCE | VERIFIED | High |

**Seam note (item 3).** Package authenticity/integrity is owned by **Phase 7**
(Package Security), with U-008 (`High`, Phase 12) covering source authentication.
Phase 3 records the negative result (P3-A15) because it is a *lifecycle*
observation: an update that reaches `install` is trusted purely because it was
fetched, and the install path has no stage at which a bad update could be refused
on integrity grounds. → Seam record `SEAM-3`.

---

## 4. Installation

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A21 | REFERENCE | Install is a single critical section under a per-`id` `Mutex` from a `ConcurrentHashMap`; two concurrent installs of the same id serialise, different ids do not | `AdbModuleManager.kt:33,81` | L1-SOURCE | VERIFIED | High |
| P3-A22 | REFERENCE | Order inside the lock is: `staging.deleteRecursively()` → `staging.mkdirs()` → extract → `markScriptsExecutable` → **`target.deleteRecursively()`** → `staging.renameTo(target)` → `readModule(target) ?: error(...)` | `AdbModuleManager.kt:84-117` | L1-SOURCE | VERIFIED | High |
| P3-A23 | REFERENCE | **The target is deleted before the rename, and the rename is not atomic from the caller's perspective.** If `renameTo` fails (`check` throws) or the post-rename `readModule` returns null, the previously installed module is already gone | `AdbModuleManager.kt:115-117` | L1-SOURCE | VERIFIED | High |
| P3-A24 | REFERENCE | Consequently an update is a **destructive replace**, not a transaction: there is no previous version retained, no rollback path, and no undo. A failed update leaves the module uninstalled | `AdbModuleManager.kt:115-116` | INFERENCE | VERIFIED | High |
| P3-A25 | REFERENCE | Staging dirs are `.<id>.installing` inside the modules root, and are swept only at the **start of the next install** (`cleanupStagingDirs`, called from `install`). A crash mid-install therefore leaves debris until some later install happens | `AdbModuleManager.kt:35-39,65,83-85` | L1-SOURCE | VERIFIED | High |
| P3-A26 | REFERENCE | `listModules` filters on `file.isDirectory` only — a `.foo.installing` staging directory *is* a directory, so it is passed to `readModule`, which returns null for it (no `module.prop`) and it is dropped by `mapNotNull`. Staging debris is invisible in the module list but still occupies disk | `AdbModuleManager.kt:56-62,295-300` | L1-SOURCE | VERIFIED | High |
| P3-A27 | REFERENCE | Module order is enabled-first then case-insensitive name; there is no persistent registry — **the filesystem is the registry** | `AdbModuleManager.kt:60` | L1-SOURCE | VERIFIED | High |
| P3-A28 | REFERENCE | Metadata is re-read from `module.prop` on **every** `listModules`/`readModule`, so a hand-edited `module.prop` changes runtime behaviour with no reinstall | `AdbModuleManager.kt:295-347` | L1-SOURCE | VERIFIED | High |
| P3-A29 | REFERENCE | Install is idempotent-by-overwrite: installing the same id twice replaces the directory wholesale, and any file not in the new archive is gone | `AdbModuleManager.kt:93,115` | INFERENCE | VERIFIED | High |
| P3-A30 | LOCAL | Local install extracts, then chmods, then writes **two** Room rows (`plugin` + `pluginStatus`) with no wrapping transaction; a crash between them leaves an orphaned directory or a manifest row with no status row | `application/.../InstallPluginUseCase.kt:36-59` | L1-SOURCE | VERIFIED | High |
| P3-A31 | LOCAL | The local package-type decision is made from the *manifest field* `requiredEnvironment`, read out of the archive before install — not from content inspection | `InstallPluginUseCase.kt:31-33` | L1-SOURCE | VERIFIED | High |
| P3-A32 | LOCAL | Local install of an `ExecutionContext.ADB` plugin delegates to `InstallShellPluginUseCase`, i.e. to the Shizuku `UserService`, giving a **second storage root** and a **second install authority** | `InstallPluginUseCase.kt:31-32`; `application/.../plugin/InstallShellPluginUseCase.kt` | L1-SOURCE | VERIFIED | High |
| P3-A33 | LOCAL | `InstallPluginFromMarketUseCase` ignores install errors entirely — `installPluginFromMarket`, `setPluginEntryPointExecutable`, `addPlugin`, `registerPluginStatus` all return `Unit` and none is checked | `InstallPluginFromMarketUseCase.kt:19-22` | L1-SOURCE | VERIFIED | High |
| P3-A34 | LOCAL | `MagiskProp` (id/name/version/author/description) is declared as a data class but is **never read anywhere** | `domain/.../plugin/manifest/MagiskProp.kt:6-12`; `grep -rn "MagiskProp" --include=*.kt .` → declaration only | NEGATIVE | VERIFIED | High |
| P3-A35 | LOCAL | CodeBrick promotion synthesises a `PluginManifest` with `pluginId = codeBrickConfig.unixTimestamp.toString()` and `pluginPackageName = codeBrickConfig.codeBrickTitle` — the **directory name is the user-visible title**, so two CodeBricks with the same title collide on disk while differing in DB id | `application/.../codebrick/InstallDaemonPluginFromCodeBrickUseCase.kt:42-54,68-74` | L1-SOURCE | VERIFIED | High |

---

## 5. Enable / disable

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A36 | REFERENCE | Enabled state is the **absence of a file named `disable`** in the module directory. Enable = `marker.delete()`; disable = `marker.writeText("disabled\n")`. The written content is never read | `AdbModuleManager.kt:24,125-132,344` | L1-SOURCE | VERIFIED | High |
| P3-A37 | REFERENCE | There is no separate registry of enabled state, and no index, so enable/disable is O(1) but has no atomicity guarantee across a crash mid-write | `AdbModuleManager.kt:125-132` | INFERENCE | VERIFIED | High |
| P3-A38 | REFERENCE | `enabled` gates **script execution** (`check(module.enabled)` in `runModuleScriptStreaming`) and the WebUI bridge (`module.enabled && …` in `ModuleWebViewActivity`), but **not** the module's presence in the list, and **not** action availability in the UI — `ModulesScreen` still offers the action control and the failure surfaces as an exception message | `AdbModuleManager.kt:176`; `ModuleWebViewActivity.kt:44-48`; `ModulesScreen.kt:234,346` | L1-SOURCE | VERIFIED | High |
| P3-A39 | REFERENCE | Disabling does **not** stop anything. Nothing in `setEnabled` kills a running process, and no process handle is retained anywhere in the module subsystem | `AdbModuleManager.kt:125-132`; `grep -rn "destroy()\|kill(" manager/.../module/` → only the 120 s timeout `destroy()` at `AdbModuleManager.kt:264` | NEGATIVE | VERIFIED | High |
| P3-A40 | REFERENCE | There is therefore **no** `Running → Stopped` transition available to the reference implementation for module services: a service that has been started is bounded only by its own exit or the 120 s timeout | `AdbModuleManager.kt:260-268` | L1-SOURCE | VERIFIED | High |
| P3-A41 | LOCAL | Local enable/disable is a Room boolean column (`pluginStatus.enabled`), written by one `UPDATE … WHERE pluginID = :pluginId`, and there is a bulk `updateAllPluginsEnabled` used by `disableAllPlugins()` | `data/.../plugin/database/PluginStatusDao.kt:30-34`; `data/.../plugin/repository/PluginStatusRepositoryImpl.kt:52-62` | L1-SOURCE | VERIFIED | High |
| P3-A42 | LOCAL | A newly installed plugin is registered **disabled** (`isEnabled = false`) | `PluginStatusRepositoryImpl.kt:21-30` | L1-SOURCE | VERIFIED | High |
| P3-A43 | LOCAL | **U-009 resolved: the enabled flag is not enforced at execution.** `ExecutePluginUseCase` reads only the plugin manifest and store status, never `PluginStatus.isEnabled`; and the card's execute button calls `onExecuteOneTimePlugin(pluginId)` with **no** enabled check, while the *navigation* handler next to it is the only place that tests `pluginStatus?.isEnabled == true`. Toggling the switch off does abort the process, but that is a UI side effect, not an execution gate | `application/.../execute/ExecutePluginUseCase.kt:15-27`; `ui/.../screens/PluginScreen.kt:263-277`; `ui/.../pluginScreen/InstalledManifestCard.kt:118` | L1-SOURCE | VERIFIED | High |

---

## 6. Action execution

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A44 | REFERENCE | Action is **not** a separate subsystem. `runAction` and `runActionStreaming` call the same `runModuleScript*` as services, differing only in the `live` flag and the log file (`action-last.log`) | `AdbModuleManager.kt:138-148,172-173` | L1-SOURCE | VERIFIED | High |
| P3-A45 | REFERENCE | Action availability is `module.hasAction` (`actionScript?.isFile == true`); the script resolves through the 5-name chain `action` prop → `action.sh` → `run.sh` → `main.sh` → `exec.sh` | `AdbModule.kt:38-39`; `AdbModuleManager.kt:327-334` | L1-SOURCE | VERIFIED | High |
| P3-A46 | REFERENCE | Action is gated by `ModuleSettings.canRunAction(module)` = `isModuleTrusted(id) \|\| modeGate`, and additionally by `check(module.enabled)` inside the runner | `AdbModuleManager.kt:139,176`; `ModuleSettings.kt:120-122` | L1-SOURCE | VERIFIED | High |
| P3-A47 | REFERENCE | Execution is `sh -c <whole file text>` through `IShizukuService.newProcess(argv, env, "/data/local/tmp")` — identical for action and service | `AdbModuleManager.kt:214-218` | L1-SOURCE | VERIFIED | High |
| P3-A48 | REFERENCE | The action path has **no** user confirmation step in the manager. A "recommand"/ReCommand review exists (`ModuleCommandReview.kt`, `ModulesScreen.kt:490-500` dialog) but it is scoped to the WebUI bridge, not to `runAction` | `ModulesScreen.kt:234,490-500`; `module/ModuleCommandReview.kt` | L1-SOURCE | VERIFIED | High |
| P3-A49 | REFERENCE | Bounded execution: script ≤ 256 KiB else `exitCode = -1`; wall clock ≤ 120 s else `destroy()` + synthetic `exitCode = 124`; stdout/stderr truncated to the last 64 KiB | `AdbModuleManager.kt:28-30,179-181,260-268,284-285` | L1-SOURCE | VERIFIED | High |
| P3-A50 | REFERENCE | Execution is **synchronous and blocking** — `withContext(Dispatchers.IO)` + `waitForTimeout` + `Thread.join(1000)`. A module action cannot be backgrounded, cannot be cancelled by the user, and occupies its caller for up to ~121 s. Only the *streaming* variant updates a `MutableStateFlow` for live UI | `AdbModuleManager.kt:138-148,260-270`; `AdbModule.kt:42-49` | L1-SOURCE | VERIFIED | High |
| P3-A51 | REFERENCE | The 120 s timeout applies to `service.sh` exactly as it does to `action.sh` — a "service" cannot outlive 120 s. This is the sharpest confirmation that `service.sh` is not a daemon | `AdbModuleManager.kt:154,260` | L1-SOURCE | VERIFIED | High |
| P3-A52 | REFERENCE | Log is `logs/action-last.log`, **overwritten** every run, with a fixed header (`module`, `script`, `finished`, `exitCode`, `mode`, `trusted`) then `[stdout]`/`[stderr]` sections. There is no rotation and no history | `AdbModule.kt:51-52`; `AdbModuleManager.kt:425-449` | L1-SOURCE | VERIFIED | High |
| P3-A53 | LOCAL | Local one-time execution builds `ProcessBuilder(sh|-su, "-c", "cd <pkgDir> ;echo PID:$$;exec <entry>")` — i.e. `cd` + `echo PID` + `exec` — so the app **parses its own PID back out of stdout** via `^\s*-\s*PID:(\d+)\s*$` | `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:38-40`; `application/.../execute/ExecutePluginByShizukuUseCase.kt:20,39-48` | L1-SOURCE | VERIFIED | High |
| P3-A54 | LOCAL | The PID is persisted only **after** it appears on stdout and only once (`pidSaved` latch). If the plugin never prints a matching line, **no execution record is ever written**, so a long-running process becomes untrackable | `ExecutePluginByShizukuUseCase.kt:39-49` | L1-SOURCE | VERIFIED | High |
| P3-A55 | LOCAL | The local `pidRegex` expects a leading `- ` because the gateway prefixes every line with `"- "`; the emitted `echo PID:$$` therefore matches only because of that prefix. The two contracts are coupled by string formatting, not by contract | `PluginExecutionGatewayImpl.kt:69-72,101-104`; `ExecutePluginByShizukuUseCase.kt:20` | L1-SOURCE | VERIFIED | High |
| P3-A56 | LOCAL | Local execution has **no** timeout, **no** output cap, and **no** script-size cap. `awaitClose {}` with an empty body means the flow never completes on its own; termination is only via the persisted PID | `PluginExecutionGatewayImpl.kt:87-88,124,127-133` | L1-SOURCE | VERIFIED | High |
| P3-A57 | LOCAL | Local termination is `kill -9 <pid>` via the local shell, or `UserService.kill(pid)` for ADB-context plugins; **neither** checks the result for the local path, and `abortPluginProcessByShizuku` returns `true` whenever the call was non-null, i.e. even if the kill failed | `PluginExecutionGatewayImpl.kt:127-149` | L1-SOURCE | VERIFIED | High |
| P3-A58 | LOCAL | Local execution **ignores `PluginRunModel`**. `OneTime` and `Daemon` are declared and persisted but never branch behaviour; both are executed the same way and both are monitorable | `domain/.../PluginRunModel.kt:3`; grep for `PluginRunModel` consumers → construction sites only | NEGATIVE | VERIFIED | High |

---

## 7. Service execution and background execution

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A59 | REFERENCE | Service script resolves `service.sh` → `late_start.sh`. There is **no `service` prop key** | `AdbModuleManager.kt:335-339` | L1-SOURCE | VERIFIED | High |
| P3-A60 | REFERENCE | A service is gated by **three** conditions: `module.enabled`, `hasService`, `ModuleSettings.canRunService(module)`, plus a **fourth**, `canRunBackground(module)` | `AdbModuleManager.kt:150-155` | L1-SOURCE | VERIFIED | High |
| P3-A61 | REFERENCE | `canRunBackground(module) = isModuleTrusted(id) \|\| allowBackgroundActions()`, and `allowBackgroundActions()` defaults to **`false`** | `ModuleSettings.kt:83-85,172-174` | L1-SOURCE | VERIFIED | High |
| P3-A62 | REFERENCE | Auto-start of eligible services is a **once-per-binder-session** effect, guarded by a process-scoped `@Volatile var servicesStartedForBinder`. It short-circuits if already run, or if `!Shizuku.pingBinder()` | `AdbModuleManager.kt:31,157-166` | L1-SOURCE | VERIFIED | High |
| P3-A63 | REFERENCE | The guard is reset **only** by `Shizuku.OnBinderDeadListener`, i.e. a service run happens again only after the Shizuku server dies and comes back | `AdbModuleManager.kt:168-170`; `home/HomeActivity.kt:163-167` | L1-SOURCE | VERIFIED | High |
| P3-A64 | REFERENCE | The trigger is a **Compose `LaunchedEffect` inside `HomeActivity`**, keyed on `serviceResource?.status`/`uid`, gated on `SUCCESS && isRunning`. Enabled services therefore start only while that Activity's composition is alive | `HomeActivity.kt:270-277` | L1-SOURCE | VERIFIED | High |
| P3-A65 | REFERENCE | **Consequence (important): `service.sh` does not run at boot.** `BootCompleteReceiver` starts the *Shizuku server* and returns; it never calls `runEnabledServicesIfAllowed`. Services run only once the user brings up `HomeActivity` after the server is up | `receiver/BootCompleteReceiver.kt` (whole file; no module calls); `grep -rn "runEnabledServicesIfAllowed" --include=*.kt .` → `HomeActivity.kt:275` only | NEGATIVE | VERIFIED | High |
| P3-A66 | REFERENCE | The whole service-start block is wrapped in `catch (_: Throwable) {}` — a failure to start services is silently discarded | `HomeActivity.kt:275-278` | L1-SOURCE | VERIFIED | High |
| P3-A67 | REFERENCE | `runEnabledServicesIfAllowed` runs eligible services **sequentially** with `.map { module to runService(module) }`, each up to 120 s, so a queue of N services can block for N×120 s on the IO dispatcher inside a `LaunchedEffect` | `AdbModuleManager.kt:163-165` | L1-SOURCE | VERIFIED | High |
| P3-A68 | REFERENCE | One failing service aborts the whole batch: `.map` is not `mapCatching`, and `runService` uses `check(...)` which throws | `AdbModuleManager.kt:165`; `AdbModuleManager.kt:151-152` | INFERENCE | VERIFIED | High |
| P3-A69 | REFERENCE | `resetServiceRunGuard()` is public with **no other caller** than the binder-dead listener, so the guard cannot be reset deliberately (e.g. after the user grants background permission) — a module whose service failed for lack of background permission will not be retried in that session | `AdbModuleManager.kt:168-170`; grep → `HomeActivity.kt:165` only | NEGATIVE | VERIFIED | High |
| P3-A70 | LOCAL | Rootless Plugins have **no** background/service execution path at all. `PluginRunModel.Daemon` exists as a declaration (P3-A58) but nothing starts a plugin automatically, on boot, or on a schedule | grep across `application/`, `data/`, `app/` for boot receivers / schedulers → none | NEGATIVE | VERIFIED | High |
| P3-A71 | LOCAL | `RecoverPluginRuntimeStateUseCase` is invoked from `MainActivity` only | `app/.../MainActivity.kt:53`; grep for the use case → `MainActivity` + its own declaration | L1-SOURCE | VERIFIED | High |

---

## 8. WebUI availability

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A72 | REFERENCE | WebUI availability is a computed property, not a state: `hasWebUi = webRoot?.resolve("index.html")?.isFile == true` | `AdbModule.kt:35-36` | L1-SOURCE | VERIFIED | High |
| P3-A73 | REFERENCE | `ModuleWebViewActivity` re-resolves the module from disk by id and **finishes immediately** if `module == null \|\| index?.isFile != true`. So a module whose WebUI is deleted while the Activity is being launched simply fails to open | `ModuleWebViewActivity.kt:34-40` | L1-SOURCE | VERIFIED | High |
| P3-A74 | REFERENCE | Bridge exposure requires **four** conditions: `module.enabled && canExposeWebBridge(module) && (module.declaresShellBridge \|\| trusted) && (!webNetworkAllowed \|\| trusted)` | `ModuleWebViewActivity.kt:44-48` | L1-SOURCE | VERIFIED | High |
| P3-A75 | REFERENCE | The bridge is only attached when all four hold; otherwise the WebUI loads with **no** `Shizuku` interface at all, i.e. `window.Shizuku` is `undefined` rather than present-but-refusing | `ModuleWebViewActivity.kt:66-80` | L1-SOURCE | VERIFIED | High |
| P3-A76 | REFERENCE | Availability is recomputed on every Activity creation and is **not** persisted; the trust and mode inputs are persisted, the derivation is not | `ModuleWebViewActivity.kt:43-48`; `ModuleSettings.kt:73-196` | L1-SOURCE | VERIFIED | High |
| P3-A77 | LOCAL | `PluginManifest.webUiEntryPoint` is declared and documented (`webroot/index.html` convention) and is persisted in the entity, but there is **no** ADB-Module-style `webRoot` fallback chain and no bridge-gating equivalent. `ResolvePluginWebUiUriUseCase` concatenates the package directory with `webUiEntryPoint!!` — a hard non-null assertion, so a plugin with no WebUI throws rather than returning "unavailable". WebUI availability is therefore neither resolved like the reference nor guarded | `domain/.../PluginManifest.kt:134`; `data/.../plugin/mapper/PluginMapper.kt:38`; `application/.../plugin/ResolvePluginWebUiUriUseCase.kt:16` | L1-SOURCE | VERIFIED | High |

---

## 9. Module updates

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A78 | REFERENCE | Update precedence is three-tier in Shevery: (1) `updateJson` from `module.prop`, (2) catalog match by `moduleId` (case-insensitive), (3) `url`/`repo` prop → GitHub Releases. First tier that yields `hasUpdate` wins; an `updateJson` that reports no update **falls through** to catalog and repo | `update/UpdateChecker.kt:25-83` | L1-SOURCE | VERIFIED | High |
| P3-A79 | REFERENCE | Nightzuku has only two tiers: `updateJson`, then a fallback that **assumes the repository name equals `module.id`** | `nightzuku/.../update/UpdateChecker.kt:24-31,128-131` | L1-SOURCE | VERIFIED | High |
| P3-A80 | REFERENCE | Nightzuku's `AdbModule` has **no** `url`, `updateJson` or `updateInfo` field, so prop-driven update state is not representable in its model at all | `nightzuku/.../module/AdbModule.kt:5-20` | L1-SOURCE | VERIFIED | High |
| P3-A81 | REFERENCE | Version comparison prefers `versionCode` when both sides have one (`latest > current`, so **downgrades are never offered**); falls back to `compareSemanticVersions`, which strips `v`, extracts digit runs, compares numerically component-wise, then falls back to a case-insensitive string compare | `UpdateChecker.kt:125-131,310-325` | L1-SOURCE | VERIFIED | High |
| P3-A82 | REFERENCE | If neither `versionCode` nor `version` is present on both sides, `hasUpdate = false` — a module with neither is permanently un-updatable via `updateJson` | `UpdateChecker.kt:125-131` | L1-SOURCE | VERIFIED | High |
| P3-A83 | REFERENCE | `checkUpdate` wraps everything in `try/catch` and returns `hasUpdate = false` on any exception, logging `Update check failed for <id>`. A broken endpoint is indistinguishable from "up to date" in the UI | `UpdateChecker.kt:29-83` | L1-SOURCE | VERIFIED | High |
| P3-A84 | REFERENCE | Update **detection** is a pull: `UpdateFrequency` ∈ {`manual`, `daily`, `weekly`}, default **`manual`**. No module-level auto-update worker exists (the auto-update worker in `module/update/` is for the *Shevery app itself*, keyed on `AppUpdateChannel`/`AppUpdateFrequency`) | `ModuleSettings.kt:405-415,428-436`; `update/SheveryAutoUpdateWorker.kt`; `ModuleSettings.kt:456-505` | L1-SOURCE | VERIFIED | High |
| P3-A85 | REFERENCE | Update **installation** re-enters the same destructive `install` path (P3-A22–P3-A24). There is no separate update code path at all: `ModuleInstaller.installModule` calls `AdbModuleManager.install(context, zipUri)` | `ModuleInstaller.kt:57-61` | L1-SOURCE | VERIFIED | High |
| P3-A86 | REFERENCE | An update therefore **discards** every file the new archive does not contain, including `logs/`, a `disable` marker, and any module-written state under the module directory | `AdbModuleManager.kt:93,115` | INFERENCE | VERIFIED | High |
| P3-A87 | REFERENCE | Because the `disable` marker lives *inside* the module directory and install replaces that directory wholesale, an update of a disabled module silently **re-enables** it | `AdbModule.kt`+`AdbModuleManager.kt:344` + `AdbModuleManager.kt:115` | INFERENCE | VERIFIED | High |
| P3-A88 | REFERENCE | Trust is stored in a global preference string-set keyed by module id and is **not** touched by install or delete — so it survives both, and a re-installed module id inherits its former trust | `ModuleSettings.kt:176-196`; `AdbModuleManager.kt:64-136` (no trust calls) | L1-SOURCE | VERIFIED | High |
| P3-A89 | LOCAL | Local auto-update exists only as a boolean preference `ENABLE_AUTO_UPDATE` with `SetAutoUpdateEnabledUseCase`; **no** update check, download, or replacement path for plugins was found in `application/` or `data/` | `data/.../setting/repository/SettingPreferencesRepositoryImpl.kt:91-94`; `application/.../setting/SetAutoUpdateEnabledUseCase.kt` | NEGATIVE | VERIFIED | High |

---

## 10. Module rollback

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A90 | REFERENCE | **There is no rollback in either fork.** No previous version is retained, no backup is taken before `target.deleteRecursively()`, and no version history exists to roll back to | `AdbModuleManager.kt:115-116`; grep for `rollback\|backup\|previousVersion` under `manager/.../module/` → no hits | NEGATIVE | VERIFIED | High |
| P3-A91 | REFERENCE | The only recovery artefact is the staging directory, and it is deleted on the *next* install (`staging.deleteRecursively()` before `mkdirs()`), so it cannot serve as a rollback source for the module it was staging | `AdbModuleManager.kt:84-85,35-39` | L1-SOURCE | VERIFIED | High |
| P3-A92 | REFERENCE | Because install is a single non-transactional critical section, rollback of a failed update requires the user to reinstall the previous ZIP from wherever they obtained it. There is no in-app path | `AdbModuleManager.kt:81-119` | INFERENCE | VERIFIED | High |
| P3-A93 | LOCAL | Local "update" is literally re-install over the same `pluginPackageName` directory: `unzipFromFile`/`unzipFromUri` `mkdirs()` the target and overwrite, with no staging, no delete, and no rollback | `AndroidFileSystemCapabilityGatewayImpl.kt:174-176,257-259` | L1-SOURCE | VERIFIED | High |
| P3-A94 | LOCAL | A local failed update therefore leaves a **mixture** of old and new files in one directory, with no way to tell which is which. `InstallPluginUseCase` then still runs `addPlugin`/`registerPluginStatus` on success of extraction, so the DB records the new version | `InstallPluginUseCase.kt:36-59`; `InstallPluginFromMarketUseCase.kt:19-22` | L1-SOURCE | VERIFIED | High |

---

## 11. Uninstall and data cleanup

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A95 | REFERENCE | Uninstall is `module.directory.deleteRecursively()` and nothing else | `AdbModuleManager.kt:134-136`; `ModulesScreen.kt:468` | L1-SOURCE | VERIFIED | High |
| P3-A96 | REFERENCE | A confirmation dialog precedes it, and there is no check for a running service or process — because no such handle exists (P3-A39) | `ModulesScreen.kt:459-484` | L1-SOURCE | VERIFIED | High |
| P3-A97 | REFERENCE | **Trust is not cleaned.** `adb_modules_trusted_modules` is a global string-set; `AdbModuleManager.delete` never touches `ModuleSettings` | `ModuleSettings.kt:176-196`; `AdbModuleManager.kt:134-136` | L1-SOURCE | VERIFIED | High |
| P3-A98 | REFERENCE | So a delete-then-install of the same id restores prior trust, and the trust set grows monotonically with every id ever trusted unless `clearTrustedModules()` is called explicitly | `ModuleSettings.kt:194-196` | INFERENCE | VERIFIED | High |
| P3-A99 | REFERENCE | Discovery cache, catalog state and `cacheDir/module_zips/*.zip` are also not cleaned on uninstall; ZIPs persist up to 24 h (P3-A11) and the cache has no other purge | `ModuleInstaller.kt:233-246`; `discovery/DiscoveryCache.kt` | L1-SOURCE | VERIFIED | High |
| P3-A100 | LOCAL | Local uninstall deletes the package directory and **two** Room rows, in that order, non-transactionally | `application/.../plugin/UninstallPluginUseCase.kt:23-24,39-42` | L1-SOURCE | VERIFIED | High |
| P3-A101 | LOCAL | For `ExecutionContext.ADB` plugins, the Shizuku uninstall is called first and, **if it returns false, the method returns early and the DB rows are left intact** — so a module whose files are already gone can still appear installed | `UninstallPluginUseCase.kt:30-37` | L1-SOURCE | VERIFIED | High |
| P3-A102 | LOCAL | Local uninstall does **not** abort a running process, does not delete execution rows (`pluginStatus`/`PluginExecuteStatusEntry`), and does not remove the plugin's logs | `UninstallPluginUseCase.kt:23-24,39-42` | L1-SOURCE | VERIFIED | High |
| P3-A103 | LOCAL | Therefore a stale `PluginExecuteStatusEntry` row can survive the uninstall of its plugin, and `RecoverPluginRuntimeStateUseCase` will later `disablePlugin(pluginId)` and `deletePluginExecutionByPluginId` for an id that no longer exists | `RecoverPluginRuntimeStateUseCase.kt:17-29`; `PluginExecutionDao.kt` | INFERENCE | VERIFIED | High |
| P3-A104 | LOCAL | CodeBrick delete removes the CodeBrick row only; it does not remove a plugin previously promoted from it | `application/.../codebrick/DeleteCodeBrickUseCase.kt` | L1-SOURCE | VERIFIED | High |

---

## 12. Crash recovery

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A105 | REFERENCE | The reference has **no crash-recovery mechanism at all** for module executions: no persisted execution record, no PID, no journal. The only recovery-ish artefact is `cleanupStagingDirs` at install time | `AdbModuleManager.kt:35-39`; grep for a module execution table/DAO under `manager/.../module/` → none | NEGATIVE | VERIFIED | High |
| P3-A106 | REFERENCE | A crashed `service.sh` is detected only insofar as its exit code is non-zero, and that information goes into a log file the user must open. `runModuleScriptStreaming` does not treat a non-zero service exit as an error worth surfacing | `AdbModuleManager.kt:261-262,425-449` | L1-SOURCE | VERIFIED | High |
| P3-A107 | REFERENCE | Unexpected service death is not observed at all: `runEnabledServicesIfAllowed` awaits completion and returns results; the caller ignores them | `AdbModuleManager.kt:157-166`; `HomeActivity.kt:275-278` | L1-SOURCE | VERIFIED | High |
| P3-A108 | LOCAL | Local crash detection is `PluginProcessMonitor`: it `waitFor()`s and, on non-zero exit, emits `ExecutionError("Plugin Crashed", exitCode)` on a `MutableSharedFlow` | `data/.../monitor/PluginProcessMonitor.kt:13-45` | L1-SOURCE | VERIFIED | High |
| P3-A109 | LOCAL | The monitor is conditional on a **user preference**: `shouldMonitor = observePluginStatusNotificationEnabled().first()` | `ExecutePluginByShizukuUseCase.kt:27,34`; `PluginExecutionGatewayImpl.kt:60-62` | L1-SOURCE | VERIFIED | High |
| P3-A110 | LOCAL | Turning off plugin-status notification therefore also disables crash monitoring — a security/observability control is coupled to a notification preference | `ExecutePluginByShizukuUseCase.kt:27` | INFERENCE | VERIFIED | High |
| P3-A111 | LOCAL | `MutableSharedFlow<ExecutionError>` has **no replay and no subscriber is required**; with no collector the emission is dropped and the crash is lost. It also has no buffer, so `emit` suspends until a collector appears | `PluginProcessMonitor.kt:16` | L1-SOURCE | VERIFIED | High |
| P3-A112 | LOCAL | On the ADB/Shizuku path the monitor is called with an `exitCode` rather than a `Process`, from the `onProcessExit` callback | `PluginExecutionGatewayImpl.kt:115-117` | L1-SOURCE | VERIFIED | High |
| P3-A113 | LOCAL | No DB row is written or updated on crash; `executionState` is `PluginState.Great` at insert (P3-A116) and **never** updated afterwards. The DAO *declares* the mutation (`updatePluginExecutionStateByPluginId`) and the matching observer (`observePluginExecutionStateByPluginId`), but **neither has a single caller** anywhere in `app/`, `application/`, `data/` or `ui/` — they are declared-but-not-operational | `data/.../execution/database/PluginExecutionDao.kt:21-24`; grep across all modules → declaration only | NEGATIVE | VERIFIED | High |
| P3-A114 | LOCAL | **U-011 resolved: no non-default `PluginState` is ever produced by any writer.** The only `PluginState` value written to the database anywhere in the project is `Great`, at three insert sites. `PermissionProblems` appears once, as a hardcoded UI value for display, never persisted | `PluginStatusRepositoryImpl.kt:26`; `PluginExecutionEntity.kt:27`; `EnvironmentStatusRepositoryImpl.kt:24`; `ui/.../InstalledManifestCard.kt:330` | L1-SOURCE | VERIFIED | High |
| P3-A115 | LOCAL | Consequently `Stop`, `PermissionProblems`, `PluginRuntimeProblems` and `RootlessStoreRuntimeProblems` are **dead enum values**, and any UI that switches on `PluginState` can only ever observe `Great` | P3-A114 | INFERENCE | VERIFIED | High |

---

## 13. Stale-runtime recovery

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A116 | REFERENCE | There is **no** stale-runtime concept in the reference, because nothing persists runtime state | P3-A105 | NEGATIVE | VERIFIED | High |
| P3-A117 | LOCAL | Recovery is `RecoverPluginRuntimeStateUseCase`, called once from `MainActivity`, iterating `listPluginExecutionStatuses()` | `application/.../runtime/RecoverPluginRuntimeStateUseCase.kt:14-17`; `app/.../MainActivity.kt:53` | L1-SOURCE | VERIFIED | High |
| P3-A118 | LOCAL | For `ExecutionContext.ADB` it calls `abortPluginProcessByShizuku(pid)` and **only if that returns true** does it disable the plugin and delete the execution row. If Shizuku is unavailable the function returns `null` → the coerced `processAbortResult != null` yields `false` → **the row is left in place and the plugin is left enabled** | `RecoverPluginRuntimeStateUseCase.kt:18-23`; `PluginExecutionGatewayImpl.kt:135-149` | L1-SOURCE | VERIFIED | High |
| P3-A119 | LOCAL | For non-ADB contexts it aborts unconditionally and always deletes the row — the two branches have **opposite** failure semantics | `RecoverPluginRuntimeStateUseCase.kt:24-28` | L1-SOURCE | VERIFIED | High |
| P3-A120 | LOCAL | **Recovery kills.** It does not reconcile. On every app start, every remembered daemon PID is `kill -9`'d and the plugin is set to disabled — so a legitimately running plugin across an app restart is terminated, not re-attached | `RecoverPluginRuntimeStateUseCase.kt:19-28` | L1-SOURCE | VERIFIED | High |
| P3-A121 | LOCAL | No liveness check precedes the kill: no `/proc/<pid>` probe, no `kill -0`, no cmdline match against the plugin's entry point, and no start-time comparison to detect PID reuse. `MasterRef.md` §59 and `ARCHITECTURE.md` §36 both require persisted process info to be distinguished from verified active state; neither check exists | `RecoverPluginRuntimeStateUseCase.kt:14-29`; `PluginExecutionGatewayImpl.kt:127-133` | L1-SOURCE | VERIFIED | High |
| P3-A122 | LOCAL | `kill -9 <pid>` is built by string interpolation into a shell command with no validation that `pid` is a plausible PID; the value is typed `Int` so it cannot be empty, but it is not range-checked and not quoted | `PluginExecutionGatewayImpl.kt:129-131` | L1-SOURCE | VERIFIED | High |
| P3-A123 | LOCAL | Recovery is gated on Shizuku availability with no retry, no user prompt, and no degraded-mode path; if the user has not started Shizuku, stale rows accumulate indefinitely | `RecoverPluginRuntimeStateUseCase.kt:18-23` | INFERENCE | VERIFIED | High |

---

## 14. Reboot / session behaviour

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A124 | REFERENCE | Nothing module-related happens at boot. `BootCompleteReceiver` starts the Shizuku server (ADB arming or root) and returns; it contains **no** module calls | `receiver/BootCompleteReceiver.kt:29-118`; grep → no `AdbModule` reference in the file | NEGATIVE | VERIFIED | High |
| P3-A125 | REFERENCE | Module `service.sh` runs only after a Shizuku server is up **and** `HomeActivity` composition observes `SUCCESS && isRunning`. Therefore reboot alone never starts services; the user must open the app | P3-A64, P3-A65 | INFERENCE | VERIFIED | High |
| P3-A126 | REFERENCE | Shevery's receiver has extra Android-version gating that Nightzuku lacks: `NEARBY_WIFI_DEVICES` on API 33+, `ACCESS_LOCAL_NETWORK` on API 37+ (Android 17), Device-Owner/`AdbArm` fallbacks, and a pre-S keyguard deferral with a 120 s timeout | `shevery/.../BootCompleteReceiver.kt:57-116,120-183` vs `nightzuku/.../BootCompleteReceiver.kt:37-50` | L1-SOURCE | VERIFIED | High |
| P3-A127 | REFERENCE | Nightzuku gates ADB boot-start on `WRITE_SECURE_SETTINGS` + `lastLaunchMode == ADB` and has **no** keyguard deferral and **no** Android-17 local-network gate; Shevery gates on `getStartOnBootAdb()` plus several device conditions | as above | L1-SOURCE | VERIFIED | High |
| P3-A128 | REFERENCE | Session semantics are therefore backend-bound: "session" = one Shizuku binder session, and the only signal is `pingBinder()` plus `OnBinderDeadListener` | `AdbModuleManager.kt:160,163`; `HomeActivity.kt:163-167` | L1-SOURCE | VERIFIED | High |
| P3-A129 | LOCAL | Local recovery runs from `MainActivity` only, so it happens on app launch, not on boot. There is no `BOOT_COMPLETED` receiver in this project | `app/.../MainActivity.kt:53`; grep for `BOOT_COMPLETED` across the project → no hits | NEGATIVE | VERIFIED | High |
| P3-A130 | LOCAL | Shizuku binder-death handling: grep for `OnBinderDead` / `binderDead` in the project returns no module-lifecycle equivalent | grep across `app/`, `application/`, `data/` → no hits | NEGATIVE | VERIFIED | Medium |
| P3-A131 | REFERENCE | After a reboot, all persisted module state (directory, `disable`, `logs/`, trust) survives, because it is all in app-private storage and a global pref; only the in-memory `servicesStartedForBinder` guard resets | `AdbModuleManager.kt:31,52-53`; `ModuleSettings.kt:176-181` | INFERENCE | VERIFIED | High |

---

## 15. Lifecycle state model

| ID | Layer | Finding | Evidence location | Type | Class | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| P3-A132 | REFERENCE | The reference's actual state is **filesystem-derived and multi-dimensional**: present/absent (directory), enabled/disabled (`disable` file), script availability (`hasAction`/`hasService`/`hasWebUi`), policy (`AccessMode` × `CustomPermissions` × trust × background flag), session (binder up/down), and last-result (log file + exit code in memory) | `AdbModule.kt:15-56`; `AdbModuleManager.kt:344,295-347`; `ModuleSettings.kt:112-181` | INFERENCE | VERIFIED | High |
| P3-A133 | REFERENCE | Of these, only **present/absent** and **enabled/disabled** are durable. Everything else is recomputed or lost on process death | P3-A28, P3-A31, P3-A132 | INFERENCE | VERIFIED | High |
| P3-A134 | LOCAL | The local plugin state is `PluginStatus(pluginId, isEnabled, state, origin)` plus a separate `PluginExecutionEntity(pluginId, executionState, executionPid, executionContext)` — i.e. already two dimensions, with `PluginState` intended as the third | `domain/.../PluginStatus.kt:3-8`; `data/.../PluginExecutionEntity.kt:11-22` | L1-SOURCE | VERIFIED | High |
| P3-A135 | LOCAL | Of the four `PluginState` values, one is produced (P3-A114), so the intended third dimension does not function. Effective local state is binary: an execution row exists or it does not | P3-A114, P3-A134 | INFERENCE | VERIFIED | High |
| P3-A136 | LOCAL | The linear model is therefore **doubly** insufficient for the local layer: it is insufficient for the reference layer (P3-A132), and the local implementation already violates it while claiming to satisfy it (P3-A135) | P3-A132, P3-A135 | INFERENCE | VERIFIED | High |
| P3-A137 | LOCAL | The lifecycle API surface is also **partly fictional**: `PluginExecutionDao.updatePluginExecutionStateByPluginId`, `PluginExecutionDao.observePluginExecutionStateByPluginId`, `PluginStatusRepository.disableAllPlugins()` and `PluginExecutionRepositoryImpl.deleteAllPluginExecutions()` are all declared and never invoked. This is the same class of defect as C-006 (declared manifest fields nothing enforces), observed on the lifecycle side | `data/.../execution/database/PluginExecutionDao.kt:21-24,37-40`; `data/.../plugin/repository/PluginStatusRepositoryImpl.kt:60-62`; `data/.../execution/repository/PluginExecutionRepositoryImpl.kt:59-62` | NEGATIVE | VERIFIED | High |

---

## 16. Cross-cutting negative results

Recorded so they are not repeated by a later phase.

| ID | Query | Result |
| --- | --- | --- |
| P3-D01 | Any signature/hash/checksum verification in the module subsystem | **None** (P3-A15) |
| P3-D02 | Any rollback or previous-version retention | **None** (P3-A90) |
| P3-D03 | Any persisted module execution record or PID in the reference | **None** (P3-A105) |
| P3-D04 | Any module-related `BOOT_COMPLETED` handling | **None** (P3-A124) |
| P3-D05 | Any process termination on module disable or uninstall in the reference | **None** (P3-A39, P3-A96) |
| P3-D06 | Any consumer of `PluginRunModel` that branches behaviour | **None** (P3-A58) |
| P3-D07 | Any consumer of `MagiskProp` | **None** (P3-A34) |
| P3-D08 | Any reader of `PluginStatus.isEnabled` that gates execution | **None** (P3-A43) |
| P3-D09 | Any **caller** of `updatePluginExecutionStateByPluginId` (the method is declared in `PluginExecutionDao:22` but never invoked) | **None** (P3-A113) |
| P3-D13 | Any caller of `observePluginExecutionStateByPluginId`, `disableAllPlugins()`, or `deleteAllPluginExecutions()` | **None** — all three are declared and never invoked (P3-A113) |
| P3-D10 | Any local plugin update/download path beyond the market install | **None** (P3-A89) |
| P3-D11 | Any liveness/PID-reuse check before local termination | **None** (P3-A121) |
| P3-D12 | Any local `BOOT_COMPLETED` receiver | **None** (P3-A129) |
| P3-D14 | Any `BroadcastReceiver`, `WorkManager`, `AlarmManager` or `JobScheduler` use anywhere in `app/`, `application/`, `data/`, `service/`, `ui/`, `domain/`, `core/` | **None** — confirms the local layer has no scheduled or event-driven execution of any kind, including no background-service analogue of `service.sh` (P3-A70) |

---

## 17. Seam records

Six `PLAN.md` items are owned by other phases. Each seam record names the owning
phase, the data contract the lifecycle boundary requires at the handoff, and the
residual risk. Per `investigations/phase-03/REPORT.md` §0 D2, **no seam item may
be marked Complete on the strength of another phase's mechanism.**

| Seam | Item | Owning phase | Data contract required at the handoff | Residual risk |
| --- | --- | --- | --- | --- |
| `SEAM-1` | Discovery | 12 | `moduleId`, `repoFullName`, `subPath`, `version`, `versionCode`, `author`, `description`, `lastChecked` | A discovered entry is trusted to name the right repo; no provenance (P3-A02) |
| `SEAM-2` | Download | 12 | A local `Uri` to a ZIP on `content://` or `file://` | Local install takes the manifest from the catalog, not the archive (P3-A14); unbounded download (P3-A10) |
| `SEAM-3` | Verification | 7, 12 | Verdict before `install` is entered | No integrity gate exists at all (P3-A15); a fetched ZIP is trusted by construction |
| `SEAM-4` | Module updates | 13 | `UpdateResult(moduleId, hasUpdate, currentVersion, currentVersionCode, latestVersion, latestVersionCode, downloadUrl, changelog)` | Update = destructive replace (P3-A24); failures are silent (P3-A83); disabled modules silently re-enable (P3-A87) |
| `SEAM-5` | Module rollback | 13 | — (no artefact exists to hand over) | **No in-app rollback is possible** (P3-A90, P3-A92) |
| `SEAM-6` | Background execution | 9 | `canRunBackground(module)`, `AccessMode`, trust, and the session signal that re-arms the guard | The session signal is Shizuku-specific and UI-triggered (P3-A62, P3-A64); no Porter equivalent exists |

Crash recovery, stale-runtime recovery and reboot/session behaviour are **owned by
Phase 14** but are not deferrable, because the local layer's recovery path is
directly observable in source and is a live architectural constraint. Phase 3
records the source-read facts (P3-A105–P3-A131) and assigns the runtime behaviour
to Phase 14; each such finding is labelled `Needs device testing`.

---

## 18. Experiments

**None performed.** No Android device or emulator is available in this
environment. `INVESTIGATION_METHOD.md` §10 requires device, Android version, app
version, dependency versions, configuration, procedure and observed result for an
experimental finding; none of those can be honestly supplied here. Every
runtime-behaviour row above is source-read and labelled as such. The specific
claims that remain untested are listed in `REPORT.md` §9.
