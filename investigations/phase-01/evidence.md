# Phase 1 Evidence Ledger — Rootless Store Deep Investigation

Phase of origin: **Phase 1 — Rootless Store Deep Investigation**
Standard: `investigations/METHOD.md`, template `investigations/templates/EVIDENCE-LEDGER.md`
Repository state: `main` @ `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba`, app version `2.3.1`
Dates: opened 2026-10-02

## Reading rules for this ledger

1. Every entry below was verified by direct inspection of the file and line cited,
   during Phase 1, by the primary investigation process. Subagent reports were
   used only to locate candidate files; no subagent conclusion was adopted without
   independent re-reading.
2. `Evidence type` uses `INVESTIGATION_METHOD.md` categories. All Phase 1 entries
   are `Source Code` unless stated otherwise.
3. `Confidence` distinguishes:
   - **Verified** — the cited code does what the finding says, read directly.
   - **Inferred** — the code is verified but the runtime consequence requires
     execution on a device or an Android platform guarantee not read in this phase.
   - **Not established** — claimed by a Phase 1 subagent but NOT independently
     confirmed here. Recorded so the next phase does not mistake it for evidence.
4. Line numbers are for the cited commit. Where a finding depends on a file that
   has no callers, that fact is part of the finding.
5. This ledger records **current behaviour**. Historical behaviour is labelled.

---

## Part 1 — Repository architecture (checklist group 1)

### P1-F01 — Project module graph is acyclic and layered

| Field | Value |
| --- | --- |
| Claim | Eight Gradle modules with a strict, acyclic internal dependency graph. |
| Source | `settings.gradle.kts`; each module's `build.gradle.kts` project dependencies |
| Evidence location | `settings.gradle.kts`; `app/`, `application/`, `core/`, `data/`, `domain/`, `illusioncube/`, `service/`, `ui/` `build.gradle.kts` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `app → application, domain, service, ui`; `application → core, data, domain, illusioncube`; `data → core, domain, illusioncube`; `service → application, domain`; `ui → application, core, domain`. `domain`, `core`, `illusioncube` declare no internal project dependencies. No module depends back on `app`. |
| Kotlin file count | `app` 3 / 202 lines; `application` 81 / 2,699; `core` 2 / 44; `data` 104 / 6,873; `domain` 65 / 935; `illusioncube` 7 / 185; `service` 6 / 112; `ui` 102 / 10,712. Total 370 files. |
| Notes | Layering is clean at the module level. The defects in this phase are **not** layering defects; they are missing-validation and missing-lifecycle defects inside `data`, `application`, and `ui`. |

### P1-F02 — The app declares one manifest, and it is not restricted

| Field | Value |
| --- | --- |
| Claim | Exactly one `AndroidManifest.xml` exists, in `app`. Permissions and flags are permissive. |
| Source | `app/src/main/AndroidManifest.xml` |
| Evidence location | whole file; repository-wide search for `AndroidManifest.xml` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Declared permissions: `POST_NOTIFICATIONS`, `INTERNET`, `ACCESS_NETWORK_STATE`, `MANAGE_EXTERNAL_STORAGE`, `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`. Application flags include `usesCleartextTraffic="true"` and `allowBackup="true"`. No foreground-service permission is declared. |
| Caveat | The content of `backup_rules.xml` / `data_extraction_rules.xml` was **not** read in this phase. What is actually backed up is `Not established`. |

### P1-F03 — Room database: version 5, eight entities, no destructive fallback

| Field | Value |
| --- | --- |
| Claim | A single Room database, `version = 5`, eight entities, four registered migrations, no `fallbackToDestructiveMigration()`. |
| Source | `RootlessStoreDatabase.kt`; `DatabaseHiltModule.kt`; migration directory |
| Evidence location | `data/src/main/kotlin/com/baidaidai/rootless_store/data/database/RootlessStoreDatabase.kt:24-51`; `data/src/main/kotlin/com/baidaidai/rootless_store/data/di/DatabaseHiltModule.kt:26-32` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Entities: `PluginEntity`, `PluginStatusEntity`, `PluginSourceEntity`, `PluginExecutionEntity`, `EnvironmentEntity`, `EnvironmentStatusEntity`, `NotificationPreferenceEntity`, `CodeBrickEntity`. Database file name `"RootlessStoreDataBase"`. Builder registers `MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5` and nothing else. |
| Notes | Absent fallback means any unhandled version path throws at open rather than silently resetting user data. That is the correct default choice, and it is why P1-F05 matters. |

### P1-F04 — Room schema history is not exported or committed

| Field | Value |
| --- | --- |
| Claim | `exportSchema = true` is declared, but no schema location is configured and no schema JSON is committed. |
| Source | `RootlessStoreDatabase.kt`; `data/build.gradle.kts`; `app/build.gradle.kts` |
| Evidence location | `RootlessStoreDatabase.kt:37`; `data/build.gradle.kts:47-49`; `app/build.gradle.kts:113-115`; repository search for a `schemas` directory |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Neither `data/build.gradle.kts` nor `app/build.gradle.kts` sets a Room `schemaLocation` KSP argument. No `data/schemas` directory exists in the repository. |
| Consequence | Room cannot validate migrations against a committed expected schema, and the historical schema of versions 1–5 cannot be reconstructed from this repository. Migration correctness is therefore **not independently checkable** here, and `MigrationTestHelper` tests cannot be written without first exporting schemas. |

### P1-F05 — Historical: the database version was reset 5 → 1 while keeping the same file name

| Field | Value |
| --- | --- |
| Claim | Commit `efab664` ("chore(database): Remove legacy room migrations") changed `version = 5` to `version = 1`, emptied `addMigrations()`, and left the database file name unchanged. |
| Source | Git history |
| Evidence location | `git show efab664` — `RootlessStoreDatabase.kt` (`version = 5` → `version = 1`); `app/src/main/java/com/baidaidai/rootless_store/data/di/DataBaseHiltModule.kt` (`name = "RootlessStoreDataBase"` unchanged; `addMigrations(MIGRATION_1_2..MIGRATION_4_5)` → `addMigrations()`) |
| Evidence type | Source Code (git history) |
| Confidence | **Verified** for the code change; **Inferred** for user impact |
| Findings | The commit message states the intent explicitly: "Reset the database version to the new major baseline. Stop registering legacy database migrations." The current tree has since been rebuilt to version 5 with a fresh post-reset migration series (`Migration1To2` … `Migration4To5` living under `data/`). |
| Inferred consequence | A device that had already opened a version-5 database and then updated into the reset-baseline build opened the *same file* with a *lower* declared version and no migration path. Room's `onDowngrade`/`onUpgrade` path has no 5→1 migration, so database open would fail. |
| Limits | Not confirmed on a device. Which released builds span this window, and how many users are affected, is unknown. Recorded as **historical** behaviour plus a `Needs testing` item (U-007). |

### P1-F06 — The current migration series never creates three of the eight tables

| Field | Value |
| --- | --- |
| Claim | No migration SQL in the current tree creates `CodeBrickEntity`, `PluginExecuteStatusEntry`, or the notification-preference table. |
| Source | `data/.../database/migration/` |
| Evidence location | `Migration1To2.kt:11-31` (creates `pluginInfo_new` only); `Migration2To3.kt:13` (creates `pluginSource_new` only); `Migration3To4.kt:13,35` (creates `pluginStatus`, `pluginInfo_new`); `Migration4To5.kt:13,35` (creates `environmentStatus`, `environmentInfo_new`); repository search for `codebrick` in the migration directory returns no hits |
| Evidence type | Source Code |
| Confidence | **Verified** (fact about the SQL); **refuted** as a live bug — see Resolution |
| **Resolution** | A Phase 1 subagent reported this as a crash-causing missing table. That claim is **incorrect for the current chain** and is withdrawn. The post-reset baseline version 1 is created by Room's `onCreate` from an entity list that already contained `CodeBrickEntity`, `PluginExecutionEntity`, and `NotificationPreferenceEntity` (verified: entity list at `0c011ef`, version 3, contains all three). Migrations 1→5 only rebuild `pluginInfo`, `pluginSource`, `pluginStatus`, `environmentInfo`, `environmentStatus`, leaving the other three intact. A fresh install and a full 1→5 upgrade both therefore end with all eight tables. |
| Notes | Retained in the ledger because it is the reason U-007 exists and because it demonstrates that the absence of schema export (P1-F04) makes this class of question hard to settle. |

---

## Part 2 — Plugin system (checklist group 2)

### P1-F10 — `PluginManifest` is documented as constrained but validated nowhere

| Field | Value |
| --- | --- |
| Claim | `PluginManifest` fields carry KDoc "Requirements"/"Recommendations", but no code validates any of them. |
| Source | `PluginManifest.kt`; `parsePluginManifest` |
| Evidence location | `domain/.../plugin/manifest/PluginManifest.kt:32-42` (package-name requirements), `:116` (`entryPoint`), `:134` (`webUiEntryPoint`), `:144` (`executableFiles`); `data/.../fileSystem/gateway/AndroidFileSystemCapabilityGatewayImpl.kt:397-403`; `data/.../fileSystem/gateway/AndroidFileSystemReadOperatorGatewayImpl.kt:96-102` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Both `parsePluginManifest` implementations configure `Json { ignoreUnknownKeys = true; isLenient = true }` and call `json.decodeFromString<PluginManifest>(jsonContent)`. That is structural deserialization only. There is no format check on `pluginPackageName`, no path check on `entryPoint`, no path check on `webUiEntryPoint`, no URL scheme check on `iconUri` or `pluginUrl`. |
| Notes | `PluginManifest` also implements `MarketManifest`, so a single shape is used both for an installed package's own `PluginManifest.json` and for market catalog metadata. |

### P1-F11 — `executableFiles` and `pluginUrl` are parsed and then discarded

| Field | Value |
| --- | --- |
| Claim | `executableFiles` and `pluginUrl` have no runtime effect. |
| Source | `PluginMapper.kt`; repository-wide search |
| Evidence location | `data/.../plugin/mapper/PluginMapper.kt:39` — comment: "executableFiles / pluginUrl are not persisted"; repository search for `executableFiles` and `pluginUrl` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `executableFiles` appears only in `PluginManifest.kt` (declaration), `PluginMapper.kt:39` (the "not persisted" comment), and test fixtures. No chmod loop exists on the client path. `PluginManifest.kt:139-142` documents that "the host should chmod these paths before running the plugin" — this is not implemented client-side. |
| Contrast | The Shizuku service *does* chmod, but only the single entry point: `ShizukuEndpointTemplate.kt:163-164`. So ADB-installed plugins get their entry point made executable; app-shell-installed plugins rely on whatever bits survive extraction, and additional declared executables are never handled on any path. |

### P1-F12 — `PluginRunModel` is metadata only

| Field | Value |
| --- | --- |
| Claim | `PluginRunModel` (`OneTime` / `Daemon`) is stored, migrated, mapped, and rendered, but never drives runtime behaviour. |
| Source | Repository-wide search for `pluginRunModel` / `PluginRunModel` |
| Evidence location | 60+ hits, all in entity/migration/mapper/UI/CodeBrick-construction code; none in an execution decision |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | No execution code path reads `pluginManifest.pluginRunModel`. The actual runtime fork is `shouldMonitor = settingPreferencesRepositoryImpl.observePluginStatusNotificationEnabled().first()` (`ExecutePluginByAppShellUseCase.kt:29`, `ExecutePluginByShizukuUseCase.kt:27`), i.e. **whether a process is monitored and crash-notified is governed by the user's notification setting, not by the plugin's declared run model.** |

### P1-F13 — `ExecutionContext` is overloaded across three unrelated roles, and it selects privilege

| Field | Value |
| --- | --- |
| Claim | The single enum `ExecutionContext { LIMITED, PERMISSIVE, ADB, ROOTD }` is used as a runtime-capability level, as the install-storage/privilege selector, and as the store-wide available-backend state. A plugin's own manifest chooses its privilege tier. |
| Source | `ExecutionContext.kt`; install/uninstall/share/CodeBrick use cases |
| Evidence location | `domain/.../status/model/ExecutionContext.kt:6-8`; `InstallPluginUseCase.kt:31`; `UninstallPluginUseCase.kt:20`; `ResolvePluginShareUriUseCase.kt:27`; `InstallMagiskPluginUseCase.kt:223`; `InstallPluginFromCodeBrickUseCase.kt:51,57`; `InstallDaemonPluginFromCodeBrickUseCase.kt:51,57`; `ExecutePluginUseCase.kt:31,44`; `AbortPluginProcessUseCase.kt:14,27` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | In every install path the test is literally `if (pluginManifest.requiredEnvironment == ExecutionContext.ADB)`. When true, the package is installed into the Shizuku shell-private directory (`/data/user_de/0/com.android.shell/RootlessStore/Plugin/…`); when false, into app-private `filesDir/Plugin/…`. **The manifest field named "required environment" therefore determines which privilege tier the code lands in, with no policy evaluation and no user confirmation at that point.** The same enum is simultaneously the store-wide "which backend is available" signal (`observeAvailableExecutionContext`) and the user preference when the chooser is enabled. |
| Architecture impact | `ARCHITECTURE.md` separates package, backend, privilege, and policy. In the implementation these are one enum and one equality test. This is a direct, evidenced confirmation that those boundaries are conceptual only. |

### P1-F14 — Zip-Slip: one extraction path is protected, all primary install paths are not

| Field | Value |
| --- | --- |
| Claim | Nine distinct ZIP extraction loops exist across two classes. Exactly one validates that an entry stays inside the target directory, and that one serves only the Magisk importer. |
| Source | `AndroidFileSystemCapabilityGatewayImpl.kt`; `AndroidFileSystemUnzipOperatorGatewayImpl.kt`; caller search |
| Evidence location | See table below |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Protected | `AndroidFileSystemUnzipOperatorGatewayImpl.unzipFromFileToDirectory` — canonical-path check at `:52-57`. Sole caller: `application/.../plugin/InstallMagiskPluginUseCase.kt:125`. |
| Unprotected — reachable | `AndroidFileSystemCapabilityGatewayImpl.unzipFromFile` (outFile at `:184`) → caller `data/.../plugin/gateway/PluginGatewayImpl.kt:56` — plugin install from a content URI. `AndroidFileSystemCapabilityGatewayImpl.unzipFromUri` (outFile at `:266`) → caller `PluginGatewayImpl.kt:68` — plugin install from a market download. `AndroidFileSystemCapabilityGatewayImpl.unzipEnvironmentFromFile` (outFile at `:233`) → caller `data/.../environment/gateway/EnvironmentGatewayImpl.kt:81`. `AndroidFileSystemCapabilityGatewayImpl.unzipEnvironmentFromUri` (outFile at `:298`) → caller `EnvironmentGatewayImpl.kt:97`. |
| Unprotected — dead | `AndroidFileSystemUnzipOperatorGatewayImpl.unzipFromFile` (`:104`), `unzipEnvironmentFromFile` (`:151`), `unzipFromUri` (`:184`), `unzipEnvironmentFromUri` (`:221`). These four have **no callers anywhere in the repository**; the class is injected only into `InstallMagiskPluginUseCase`, which uses only the protected method. |
| Notes | In each unprotected loop the pattern is `File(createdFileDirectory, entry.name)` followed directly by `FileOutputStream(outFile)`. `entry.name` is attacker-controlled for a hostile archive. |
| Second-order | The destination directory name itself is also attacker-controlled: `unzipFromFile` derives it from `parsePluginManifest(json).pluginPackageName` read out of the same archive (`AndroidFileSystemCapabilityGatewayImpl.kt:165-167`). So a hostile archive controls both the parent directory name and the entry names. |
| Limits | Exploitability was not demonstrated on a device. The path-traversal primitive is verified from source; the resulting write target is bounded by the app process's own permissions. Recorded as **Verified (defect)** with **Inferred (impact)**. |

### P1-F15 — Shell command injection from manifest data on both backends

| Field | Value |
| --- | --- |
| Claim | `pluginPackageName` and `entryPoint`, both taken from the plugin's own manifest, are interpolated unquoted into a `sh -c` string that is then executed with root or ADB-shell privilege. |
| Source | `AndroidFileSystemCapabilityGatewayImpl.kt`; `PluginExecutionGatewayImpl.kt`; `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../fileSystem/gateway/AndroidFileSystemCapabilityGatewayImpl.kt:64-69` (`resolvePluginEntryPoint` returns `"$dir/$pluginPackageName/$pluginEntryPoint"`); `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:38-40` (`ProcessBuilder(resolveLocalShellExecutable(), "-c", "cd $pluginPackageDirectory ;echo PID:$$;exec $pluginEntryPoint")`); `data/.../shizuku/server/ShizukuEndpointTemplate.kt:30` (`ProcessBuilder("sh","-c","echo PID:$$;cd …/RootlessStore/Plugin/$pluginDirectory && ./$pluginEntryPoint")`) |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `resolveLocalShellExecutable()` returns `su` when `Shell.getShell().isRoot` (`PluginExecutionGatewayImpl.kt:25-32`), so on the root backend this string is interpreted by `su -c`, i.e. **as root**. Neither `resolvePluginEntryPoint` nor either `ProcessBuilder` call quotes, escapes, or validates. Because `parsePluginManifest` performs no validation (P1-F10), a manifest `entryPoint` containing shell metacharacters becomes part of a root-context command string. |
| Contrast | The same file set *does* contain correct quoting helpers — `ShizukuEndpointTemplate.shellQuote()` (`:238-240`) and `isValidShellVariableName()` (`:234-236`) — but they are used only for building the environment export string (`:222-232`) and for validating shell-plugin *install* arguments (`:242-256`). They are not applied on the execution path. |
| Limits | No payload was executed on a device. Verified as an unescaped-interpolation defect; impact depends on who can install a plugin (see P1-F56 on source trust). |

### P1-F16 — `ShizukuEndpointTemplate.kill()` always returns false

| Field | Value |
| --- | --- |
| Claim | The AIDL `kill` implementation compares a `java.lang.Process` object to the integer `0`, so it can never report success. |
| Source | `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../shizuku/server/ShizukuEndpointTemplate.kt:62-71` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `val process = ProcessBuilder(...).start().waitFor()` binds `process` to the `Process` returned by `waitFor()`, then `return process == 0`. The correct value — the exit status — is discarded by the binding. `process == 0` is a reference/`equals` comparison against `Int`, which is always `false`. |
| Consequence | Termination through the Shizuku backend is never acknowledged as successful, regardless of outcome. Compounded by P1-F17. |

### P1-F17 — Shizuku abort reports reachability, not success

| Field | Value |
| --- | --- |
| Claim | `abortPluginProcessByShizuku` returns whether a service reference was obtained, discarding the service's own boolean. |
| Source | `PluginExecutionGatewayImpl.kt` |
| Evidence location | `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:135-149`, specifically `:145` `processAbortResult != null` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | The value returned by `shizukuUserServiceGatewayImpl.findShizukuUserService()?.kill(pid)` is logged and then tested only for non-nullness. Given P1-F16 that inner value is always `false` anyway, so even a correct `kill` implementation would have its result discarded. |
| Contrast | The app-shell abort (`:127-133`) returns `Unit`, so neither backend's abort result reaches a caller that could act on it. |

### P1-F18 — A plugin can nominate the PID the app later kills

| Field | Value |
| --- | --- |
| Claim | The PID recorded for a plugin is parsed from that plugin's own stdout, then later used as the argument to `kill -9`. |
| Source | `ExecutePluginByAppShellUseCase.kt`; `ExecutePluginByShizukuUseCase.kt`; `PluginExecutionGatewayImpl.kt`; `AbortPluginProcessUseCase.kt` |
| Evidence location | `application/.../execute/ExecutePluginByAppShellUseCase.kt:22,40-52`; `application/.../execute/ExecutePluginByShizukuUseCase.kt:20,38-50`; `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:127-133`; `application/.../plugin/AbortPluginProcessUseCase.kt:13-35` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `pidRegex = Regex("""^\s*-\s*PID:(\d+)\s*$""")` is matched against `executionResult.output`, i.e. a line the plugin printed. The first match is stored via `createPluginExecution(..., executionPid = pid)` and is later read back and passed to `kill -9 $pluginProcessPid` executed through `resolveLocalShellExecutable()`, which is `su` when root is available. |
| Missing control | There is no check that the PID is a descendant of the process the app started, no check that it belongs to the plugin's own session, and no ownership verification before the kill. |
| Consequence | Plugin-controlled output selects the target of a privileged kill. Verified as an absent control; exploitation not attempted. |

### P1-F19 — Recorded PID means different things on the two backends

| Field | Value |
| --- | --- |
| Claim | The app-shell backend uses `exec`, so the recorded PID is the plugin. The Shizuku backend does not, so the recorded PID is the wrapper shell. |
| Source | `PluginExecutionGatewayImpl.kt`; `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:39` — `"cd $dir ;echo PID:$$;exec $pluginEntryPoint"`; `data/.../shizuku/server/ShizukuEndpointTemplate.kt:30` — `"echo PID:$$;cd …/Plugin/$pluginDirectory && ./$pluginEntryPoint"` |
| Evidence type | Source Code |
| Confidence | **Verified** (textual difference); **Inferred** (termination consequence) |
| Findings | On the app-shell path `$$` is the PID of the `sh` process that is immediately replaced by the plugin via `exec`, so `$$` is the plugin's own PID. On the Shizuku path `$$` is the PID of the wrapper `sh`, and the plugin runs as `./$pluginEntryPoint`, a child. Killing the wrapper does not by itself kill the child. |
| Consequence | The same persisted field means "the plugin process" on one backend and "its parent shell" on the other. Termination semantics are therefore backend-dependent and not equivalent. |

### P1-F20 — The app-shell backend cannot distinguish stderr from stdout

| Field | Value |
| --- | --- |
| Claim | `executePluginEntryPoint` tags stderr lines as `ExecutionResultTag.Normal`. |
| Source | `PluginExecutionGatewayImpl.kt` |
| Evidence location | `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:75-84`, specifically `:80` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | The stdout loop (`:65-74`) emits `ExecutionResultTag.Normal`; the stderr loop (`:75-84`) also emits `ExecutionResultTag.Normal`. The Shizuku path correctly uses `ExecutionResultTag.Error` for errors (`PluginExecutionGatewayImpl.kt:107-113`). Error state is therefore unrepresentable on the app-shell backend. |

### P1-F21 — Execution flows never release their process

| Field | Value |
| --- | --- |
| Claim | Both execution flows use `callbackFlow` with an empty `awaitClose`, so cancelling collection neither destroys the process nor closes the flow. |
| Source | `PluginExecutionGatewayImpl.kt` |
| Evidence location | `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:87-88` and `:124` |
| Evidence type | Source Code |
| Confidence | **Verified** (empty `awaitClose`); **Inferred** (lifetime consequence) |
| Findings | No `Process` handle is retained on `ProducerScope`, and there is no `invokeOnClose { process.destroy() }`. The `Process` created at `:58` becomes unreachable once the block returns. |

### P1-F22 — stdout and stderr are drained sequentially, not concurrently

| Field | Value |
| --- | --- |
| Claim | Both execution implementations drain `inputStream` to exhaustion before touching `errorStream`. |
| Source | `PluginExecutionGatewayImpl.kt`; `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../execution/gateway/PluginExecutionGatewayImpl.kt:64-85`; `data/.../shizuku/server/ShizukuEndpointTemplate.kt:34-50` |
| Evidence type | Source Code |
| Confidence | **Verified** (ordering); **Inferred** (deadlock) |
| Findings | Standard pipe behaviour is that a process writing more than the OS pipe buffer to stderr blocks until stderr is drained. Because stderr is only read after stdout closes, a chatty plugin can wedge. `INVESTIGATION_METHOD.md` requires this to stay separated from the verified ordering claim. |

### P1-F23 — The `cd ` command mutates persistent service state and then does nothing

| Field | Value |
| --- | --- |
| Claim | `ShizukuEndpointTemplate.command()` intercepts any command starting with `cd `, changes the service's persistent working directory, and replaces the command with `exit`. |
| Source | `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../shizuku/server/ShizukuEndpointTemplate.kt:84-88`, with `prepareWorkingDirectoryCommand` at `:339-352` and field `currentDirectory` initialised at `:19` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `val targetDirectory = commandContent.removePrefix("cd ").trim(); prepareWorkingDirectoryCommand(targetDirectory); commandContent = "exit"`. `prepareWorkingDirectoryCommand` reassigns `currentDirectory`, which is used as the default working directory for every later `command()` call in that service instance. |
| Notes | A Phase 1 subagent reported this correctly. It is recorded here as independently re-verified. |

### P1-F24 — The Shizuku shell-plugin install path is the one hardened path in the codebase

| Field | Value |
| --- | --- |
| Claim | `installShellPlugin` validates the package name, the entry point path, the staging filename, and every ZIP entry, and rolls back on failure. |
| Source | `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../shizuku/server/ShizukuEndpointTemplate.kt:112-170`; helpers `isSafeFileName` `:242-247`, `isSafeRelativePath` `:249-256`, `safeResolveRelativeFile` `:258-273`, `unzipShellPluginPackage` `:275-301` (throws at `:285`), rollback `:167` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Name must be non-blank and free of `/`, `\`, `..`; entry point must be a relative path with no `.`/`..`/blank segments; the staging file must literally be named `_template_.zip`; every extracted entry is resolved canonically and rejected if it escapes the package directory; on any throwable the package directory is deleted and `false` is returned. |
| Significance | This is the correct pattern, applied only to the ADB install path. It is the internal reference for what the unprotected client-side paths in P1-F14 are missing. |

### P1-F25 — Shell-plugin export does not validate its output path

| Field | Value |
| --- | --- |
| Claim | `exportShellPlugin` validates the package name but writes the archive to any non-blank path the caller supplies. |
| Source | `ShizukuEndpointTemplate.kt` |
| Evidence location | `data/.../shizuku/server/ShizukuEndpointTemplate.kt:184-220` (`:192-194` blank-check only); write at `:309` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Notes | Lower severity than P1-F14: the caller is the app itself over its own binder. Recorded for completeness of the shell-plugin API surface. |

### P1-F26 — Uninstall does not terminate, and leaves inconsistent state

| Field | Value |
| --- | --- |
| Claim | `UninstallPluginUseCase` never terminates a running process, never removes the plugin's execution row, and ignores the file-system result on the app-shell path. |
| Source | `UninstallPluginUseCase.kt`; `ShizukuEndpointTemplate.kt` |
| Evidence location | `application/.../plugin/UninstallPluginUseCase.kt:17-42`; `data/.../shizuku/server/ShizukuEndpointTemplate.kt:181` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | (a) No call to any abort path — a running plugin keeps executing after its package is deleted. (b) `deletePlugin` (`:39-42`) removes only the `pluginInfo` and `pluginStatus` rows; `PluginExecutionEntity` rows are orphaned. (c) On the app-shell path the result of `pluginFileSystemGateway.uninstallPlugin(...)` at `:23` is discarded, so the DB row is deleted even if the directory removal failed. (d) On the ADB path, `uninstallShellPlugin` returns early at `:34` when the service reports failure, leaving the DB row in place; and `deleteRecursively()` at `ShizukuEndpointTemplate.kt:181` returns `false` for a non-existent directory, so uninstalling an already-absent package silently never deletes the row. |

### P1-F27 — Execution dereferences a repository lookup with `!!`

| Field | Value |
| --- | --- |
| Claim | Both execute use cases force-unwrap the result of `findPlugin`. |
| Source | Execute use cases |
| Evidence location | `application/.../execute/ExecutePluginByAppShellUseCase.kt:28`; `application/.../execute/ExecutePluginByShizukuUseCase.kt:26` — `pluginRepositoryImpl.findPlugin(pluginId)!!` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Notes | Any caller holding a stale plugin id — a race with uninstall, a recovered notification, a restored backup row — throws `NullPointerException` rather than producing an execution error. |

### P1-F28 — `isEnabled` is not enforced on the execution path

| Field | Value |
| --- | --- |
| Claim | No execution use case or gateway reads the plugin's enabled flag. |
| Source | Execute use cases; `PluginExecutionGatewayImpl.kt` |
| Evidence location | `ExecutePluginUseCase.kt` (entire file); `ExecutePluginByAppShellUseCase.kt` (entire file); `ExecutePluginByShizukuUseCase.kt` (entire file); `PluginExecutionGatewayImpl.kt` (entire file) |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `PluginStatus.isEnabled` is persisted and rendered in the UI. Execution dispatches on backend availability alone (`ExecutePluginUseCase.kt:29-45`). Whether the UI prevents invoking a disabled plugin was **not** established here and is listed as `Not established` (U-009). |
| Related | `PluginManifest.kt:94-96` documents that the host "should validate this before installation or before enabling" for `requiredEnvironment`. No such validation exists on the execution path. |

### P1-F29 — There is no `Application` subclass, so nothing runs at process start

| Field | Value |
| --- | --- |
| Claim | The app declares no custom `Application` class, and therefore performs no process-start recovery. |
| Source | `app/src/main/AndroidManifest.xml`; repository search |
| Evidence location | `app/src/main/AndroidManifest.xml` (`android:name` on `<application>`); search for `*Application.kt` returns no files; `MainActivity.kt:76` is the only `onCreate` override found in `app`/`application` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Any "recovery on launch" behaviour cannot be implemented by this code as written. This constrains what Phase 14 can assume and is recorded as an architecture gap rather than a defect claim. |

### P1-F30 — Crash monitoring is coupled to the notification preference

| Field | Value |
| --- | --- |
| Claim | `PluginProcessMonitor` is invoked only when the plugin-status notification setting is on, and its event flow has no buffer. |
| Source | Execute use cases; `PluginProcessMonitor.kt` |
| Evidence location | `application/.../execute/ExecutePluginByAppShellUseCase.kt:29,38`; `ExecutePluginByShizukuUseCase.kt:27,34`; `data/.../monitor/PluginProcessMonitor.kt:16,33-45` |
| Evidence type | Source Code |
| Confidence | **Verified** (coupling and declaration); **Inferred** (emit behaviour) |
| Findings | (a) `shouldMonitor` is `observePluginStatusNotificationEnabled()`, so disabling notifications silently disables crash detection. (b) `unexpectedExitNotificationPoster` is declared `MutableSharedFlow<ExecutionError>()` with no `replay` and no `extraBufferCapacity`, so it is zero-buffered; `emit` on a zero-buffered shared flow with no subscriber suspends. (c) No deduplication: a crash-looping plugin emits repeatedly with no suppression. |

---

## Part 3 — CodeBricks (checklist group 3)

### P1-F40 — CodeBrick model and storage

| Field | Value |
| --- | --- |
| Claim | A CodeBrick is a timestamp-keyed row of title, target `ExecutionContext`, raw script content, and an optional Quick Settings tile index. |
| Source | `CodeBrickConfig.kt`; `CodeBrickEntity.kt` |
| Evidence location | `domain/.../codebrick/model/CodeBrickConfig.kt:5-11`; `data/.../codebrick/database/CodeBrickEntity.kt:8-19` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `CodeBrickEntity` has `@PrimaryKey unixTimestamp: Long` and no `tableName`, so the table is `CodeBrickEntity`. Fields map 1:1 to `CodeBrickConfig`. The primary key is a creation timestamp, not a caller-supplied identity. |

### P1-F41 — A CodeBrick title becomes the plugin package name and the directory name

| Field | Value |
| --- | --- |
| Claim | Promotion uses the free-text CodeBrick title directly as `pluginPackageName` and as the on-disk directory name. |
| Source | `InstallPluginFromCodeBrickUseCase.kt`; `AndroidFileSystemCapabilityGatewayImpl.kt` |
| Evidence location | `application/.../codebrick/InstallPluginFromCodeBrickUseCase.kt:46` (`pluginPackageName = codeBrickConfig.codeBrickTitle`) and `:69-74` (`resolveChildFile(pluginRootDirectory, childName = codeBrickConfig.codeBrickTitle)`); `data/.../fileSystem/gateway/AndroidFileSystemCapabilityGatewayImpl.kt:39-41` (`resolveChildFile` is a bare `File(parent, child)`) |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `resolveChildFile` performs no containment check. The same title also flows into the Shizuku path at `:107-109`, where it does pass through the service-side `isSafeFileName()` check (P1-F24). The app-shell path has no equivalent. |
| Severity qualifier | The title is user-entered locally, so this is a self-inflicted containment gap rather than a remote attack surface. It matters because the same unvalidated title becomes a plugin identity and is later interpolated into shell commands (P1-F15). |

### P1-F42 — The CodeBrick timestamp becomes the plugin primary key

| Field | Value |
| --- | --- |
| Claim | `pluginId` for a promoted plugin is `codeBrickConfig.unixTimestamp.toString()`. |
| Source | `InstallPluginFromCodeBrickUseCase.kt` |
| Evidence location | `application/.../codebrick/InstallPluginFromCodeBrickUseCase.kt:42` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Plugin identity is therefore time-derived, not content- or author-derived. Two CodeBricks created within the same clock granularity produce the same key, and `addPlugin` at `:94` has no conflict handling. This also means promoted plugins are not distinguishable from installed packages by ID shape. |
| Contrast | `PluginManifest.kt:44-56` explicitly *recommends* a SHA-256 or UUID primary key. The promotion path does not follow the project's own guidance. |

### P1-F43 — A CodeBrick's environment field selects its privilege tier

| Field | Value |
| --- | --- |
| Claim | `codeBrickEnvironment` is copied into `requiredEnvironment` and then used to choose between app-private and shell-private installation. |
| Source | CodeBrick install use cases |
| Evidence location | `application/.../codebrick/InstallPluginFromCodeBrickUseCase.kt:51,57`; `application/.../codebrick/InstallDaemonPluginFromCodeBrickUseCase.kt:51,57` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | Same mechanism as P1-F13, applied to CodeBricks. This is the direct evidence for the Phase 1 checklist item "Investigate CodeBrick environment handling". |
| Naming note | `codeBrickEnvironment` is a *target selection* field, not a capability requirement, despite sharing a type with `requiredEnvironment`. |

### P1-F44 — Promotion produces a plugin indistinguishable from an installed one

| Field | Value |
| --- | --- |
| Claim | A promoted CodeBrick is registered as a first-class plugin with `PluginOrigin.Local` and no marker of provenance beyond that origin value. |
| Source | CodeBrick install use cases |
| Evidence location | `application/.../codebrick/InstallPluginFromCodeBrickUseCase.kt:94-95`; `InstallDaemonPluginFromCodeBrickUseCase.kt` equivalent |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `author` is hard-coded `"CodeBrick"` (`:49`) and `pluginDescription` is `"Generated from CodeBrick"` (`:50`). Origin is `Local`, the same value used for ordinary local installs. There is no trust tier, no capability record, and no distinction from a market-sourced package beyond two display strings. |
| Boundary conclusion | This is the evidence for the checklist item "Determine boundaries between CodeBricks and ADB Modules": **no boundary exists in the implementation.** A CodeBrick and an ADB Module are unrelated concepts in this codebase — there is no ADB Module concept at all — while a CodeBrick becomes indistinguishable from a Rootless Plugin on promotion. |

### P1-F45 — CodeBrick promotion has no rollback on the app-shell path

| Field | Value |
| --- | --- |
| Claim | On the app-shell path, files are written and the DB row inserted with no failure handling. |
| Source | `InstallPluginFromCodeBrickUseCase.kt` |
| Evidence location | `application/.../codebrick/InstallPluginFromCodeBrickUseCase.kt:66-96` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Notes | Contrast with the ADB path at `:137-160`, which checks `isShellPluginInstallSuccessful` and returns without inserting on failure, and with `ShizukuEndpointTemplate.kt:167`, which deletes the package directory on throwable. The app-shell path is the only one of the three with neither. |

---

## Part 4 — Market / sources (checklist group 4)

### P1-F50 — Market listing is a single GET against a caller-supplied endpoint

| Field | Value |
| --- | --- |
| Claim | The market API performs `GET {pluginSourceEndpoint}/plugin/getAllPlugins?page={n}` and adds no authorization header. |
| Source | `MarketApi.kt` |
| Evidence location | `data/.../market/remote/api/MarketApi.kt:18-30`, specifically `:22` `client.request(pluginSourceEndpoint)`, `:26` `appendPathSegments("plugin","getAllPlugins")`, `:27` `parameters.append("page", …)` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | The base URL is fully caller-supplied, so every market request targets whatever endpoint the stored `PluginSource` row contains. No auth header, no signature, no integrity field, and no certificate pinning is applied at this layer. |
| Refutation of a subagent claim | A Phase 1 subagent reported a "market runtime endpoint `getAllPlugins` with paging, pageSize 10, and a TODO refresh key" including a token/authentication scheme. The **endpoint and paging parameter are verified**. The **pageSize, refresh-key TODO, and token scheme are `Not established`** — not confirmed in this ledger and not relied on by any finding here. |

### P1-F51 — Cleartext traffic is enabled application-wide

| Field | Value |
| --- | --- |
| Claim | `usesCleartextTraffic="true"` is set on the application element. |
| Source | `app/src/main/AndroidManifest.xml` |
| Evidence location | `app/src/main/AndroidManifest.xml` (application element) |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Consequence | A market or source endpoint may be plain HTTP with no transport protection. This is the transport half of the source-trust items (P1-F50 plus source authentication/integrity, which remain `Not established`). |

---

## Part 5 — WebUI (supports checklist items 1 and 2; full treatment owed to Phase 8)

### P1-F60 — The WebView target URL is unvalidated manifest data

| Field | Value |
| --- | --- |
| Claim | The URL loaded into the plugin WebView is built by string concatenation from the plugin's own manifest field, with no scheme or origin restriction. |
| Source | `ResolvePluginWebUiUriUseCase.kt`; `WebViewScreen.kt` |
| Evidence location | `application/.../plugin/ResolvePluginWebUiUriUseCase.kt:16-18` (`pluginManifest.webUiEntryPoint!!` → `"$pluginPackageDirectory/$webUiEntryPoint"`); `ui/.../screens/WebViewScreen.kt:81` (`loadUrl(webUri)`) |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `webUiEntryPoint` is unvalidated (P1-F10). A value that is not a relative path makes the concatenated string a URL of the plugin's choosing. `WebViewScreen` performs no scheme allow-list and installs no `WebViewAssetLoader`, so local assets and remote origins are not distinguished. |
| Consequence | Because the WebView is granted the bridges in P1-F61 and P1-F62 regardless of origin, control of `webUiEntryPoint` is equivalent to control of a privileged execution surface. |

### P1-F61 — The shell bridge is exposed to every origin

| Field | Value |
| --- | --- |
| Claim | `addWebMessageListener` is registered with the allowed-origin rule set `setOf("*")`, and its messages are executed as app-shell commands. |
| Source | `WebViewScreen.kt`; `ExecuteAppShellUseCase.kt` |
| Evidence location | `ui/.../screens/WebViewScreen.kt:59-65`; `application/.../webui/ExecuteAppShellUseCase.kt:12-16` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `addWebMessageListener(this, "AppShell", setOf("*")) { … webViewScreenViewModel.executeAppShell(message.data!!) … }` → `ExecuteShellGatewayImpl.executeCommandByAppShell`. The `*` rule set permits any origin loaded in the WebView to post to this listener. |

### P1-F62 — A KernelSU-shaped bridge with arbitrary command execution is injected

| Field | Value |
| --- | --- |
| Claim | `addJavascriptInterface` exposes `__rootless_ksu` with `exec(command)` and `listPackages(type)`. |
| Source | `WebViewScreen.kt`; `KernelSuJavaScriptBridge.kt` |
| Evidence location | `ui/.../screens/WebViewScreen.kt:67-70`; `application/.../webui/KernelSuJavaScriptBridge.kt:23-58` (`exec`), `:60-78` (`listPackages`) |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | `exec` forwards its argument verbatim to `shizukuUserService.command(command, callback, false)`. `listPackages` enumerates all installed packages with system/user filtering. A compatibility shim at `WebViewScreen.kt:19-31` maps these onto `window.ksu`, injected on every `onPageStarted`. |
| Additional state effect | `exec` passes `shouldJumpToDirectory = false`, so the `cd ` interception in P1-F23 remains reachable: WebView content can send `cd /some/path`, which mutates the user service's persistent working directory for later calls. |

### P1-F63 — WebView remote debugging is enabled unconditionally

| Field | Value |
| --- | --- |
| Claim | `setWebContentsDebuggingEnabled(true)` is called with no build-type condition. |
| Source | `WebViewScreen.kt` |
| Evidence location | `ui/.../screens/WebViewScreen.kt:57` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Notes | No `BuildConfig.DEBUG` gate is present in the file. Whether a release build is currently distributed is out of scope for this phase. |

### P1-F64 — No navigation restriction is installed

| Field | Value |
| --- | --- |
| Claim | The `WebViewClient` overrides only `onPageStarted`; there is no `shouldOverrideUrlLoading` and no `WebViewAssetLoader`. |
| Source | `WebViewScreen.kt` |
| Evidence location | `ui/.../screens/WebViewScreen.kt:72-79` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Findings | A page may navigate anywhere, and every navigated-to origin inherits the bridges granted at `:59-70`. `javaScriptEnabled` and `domStorageEnabled` are both on (`:55-56`). |

### P1-F65 — The bridge reports error state from stderr presence, not exit status

| Field | Value |
| --- | --- |
| Claim | `errno` is `1` when stderr is non-blank and `0` otherwise. |
| Source | `KernelSuJavaScriptBridge.kt` |
| Evidence location | `application/.../webui/KernelSuJavaScriptBridge.kt:54` |
| Evidence type | Source Code |
| Confidence | **Verified** |
| Notes | A command that writes to stderr but succeeds is reported as failed; a command that fails silently is reported as successful. `onProcessExit = {}` at `:44` discards the real exit code. |

### P1-F66 — The bridge reads output before the process has finished

| Field | Value |
| --- | --- |
| Claim | `exec` builds its result JSON immediately after invoking `command()`, without awaiting completion. |
| Source | `KernelSuJavaScriptBridge.kt`; `ShizukuEndpointTemplate.kt` |
| Evidence location | `application/.../webui/KernelSuJavaScriptBridge.kt:47-57`; `data/.../shizuku/server/ShizukuEndpointTemplate.kt:73-110` (the AIDL method returns `void` and streams via callbacks) |
| Evidence type | Source Code |
| Confidence | **Verified** (absence of any wait); **Inferred** (observable race) |
| Findings | `command()` returns immediately after starting the `ProcessBuilder` and starting to drain streams. `exec` then serialises whatever `stdout`/`stderr` contain at that instant. The AIDL signature has no completion callback that `exec` waits on. |

---

## Part 6 — Claims deliberately NOT recorded as findings

The following were reported by Phase 1 subagents and were either refuted or
left unconfirmed. They are listed so a later phase does not rediscover them as
"new", and so they are not mistaken for evidence.

| Subagent claim | Disposition |
| --- | --- |
| Missing Room migration for `CodeBrickEntity` causes a crash | **Refuted** — see P1-F06 Resolution. The reset baseline already contained the entity. |
| 4 of 5 or 2 of 10 extraction functions lack a Zip-Slip check | **Corrected** — 1 of 9 extraction loops is protected; 4 reachable, 4 dead. See P1-F14. |
| Market page size 10, refresh-key TODO, token authentication | **Not established** — endpoint and `page` parameter verified (P1-F50); the rest is unconfirmed. |
| Shell-plugin `cd` interception bug | **Verified** — P1-F23. |
| Room DB version/identity conflict with Magisk-compat storage | **Not established in this phase** — see C-002, which stays open for Phase 11. |
| Notification-channel and settings-consumer defects | **Not established** — the notification module was mapped (item 1.9) but no defect was independently confirmed here. |
| WebUI file/network access settings defects | **Superseded** — the WebView uses `loadUrl` with no asset loader (P1-F60, P1-F64), so per-origin file access settings are not the operative control. |

---

## Evidence gaps carried forward

| Gap | Consequence | Registered as |
| --- | --- | --- |
| Room schemas not exported | Migration correctness unverifiable in-repo | P1-F04; Phase 17 |
| No `MigrationTestHelper` tests found | Migrations are unexercised | Phase 17 |
| No backup-rules XML read | Backup exposure unknown | P1-F02 caveat; Phase 11 |
| No device test performed | All impact statements are source-level | `Needs testing`; Phase 10/14 |
| Notification module not defect-verified | Checklist item 1.9 partially covered | Phase 9 |
| Environment config resolution not traced end-to-end | Checklist item 2.6 partial | Phase 6 |
| Quick Settings tile binding not verified against `TileService` limits | Checklist item 3.4/3.6 partial | Phase 9 |
| Source authentication / integrity / update discovery not verified | Checklist items 4.5–4.8 partial | U-008; Phase 12 |