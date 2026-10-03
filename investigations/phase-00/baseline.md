# Phase 0 — Verified Project Baseline

Facts established by Phase 0 that later phases may reuse. Reuse is governed by
`INVESTIGATION_METHOD.md` §48: reuse the evidence, reference this file, and
verify it remains current.

**Every entry below is `VERIFIED` from local repository source (L1) at the
recorded commit.** Nothing in this file describes ADB Module, Porter, or Shevery
behaviour — those are Phases 1, 2, 4, and 5.

| Field | Value |
| --- | --- |
| Repository | `/mnt/sdcard/Git/RootlessStore` |
| Remote (`origin`) | `https://github.com/RedSigilDelta/RootlessStore.git` |
| Branch inspected | `main` |
| Commit inspected | `4b35c36b7b2a7ec810850a29edaec1ea0f5840ba` |
| Commit date | `Fri Oct 2 14:31:02 2026 -0400` |
| Date of inspection | 2026-10-02 |
| Working tree at inspection | Modified: `README.md` (Phase 0 status block, see C-003); untracked: `investigations/` |
| Currency | `CURRENT` for this commit |

---

## 1. Project identity

| Fact | Value | Evidence |
| --- | --- | --- |
| Fork of | `Resilien-Mobile/RootlessStore` | `README.md:127`, `README.md:327` |
| Upstream URL | `https://github.com/Resilien-Mobile/RootlessStore` | `README.md:327` |
| Fork URL | `https://github.com/RedSigilDelta/RootlessStore` | `README.md:329` |
| Official wiki (as linked by the project) | `https://resilien-mobile.github.io/RootlessStore_WiKi/` | `README.md:6` |
| License of this fork | GNU AGPL-3.0 | `LICENSE:1-2` |
| Upstream license | `Unknown` — see U-002 | — |

---

## 2. Application configuration

| Fact | Value | Evidence |
| --- | --- | --- |
| Gradle namespace | `com.baidaidai.rootless_store` | `app/build.gradle.kts:23` |
| `applicationId` | `com.baidaidai.rootless_store` | `app/build.gradle.kts:38` |
| `versionName` | `2.3.1` | `app/build.gradle.kts:42` |
| `versionCode` | `2` | `app/build.gradle.kts:41` |
| `minSdk` | `26` | `app/build.gradle.kts:39` |
| `targetSdk` | `28` | `app/build.gradle.kts:40` |
| `compileSdk` | `release(37)` | `app/build.gradle.kts:24-26` |
| Test instrumentation runner | `androidx.test.runner.AndroidJUnitRunner` | `app/build.gradle.kts:44` |
| Signing config | `github`, reading `release.jks` and `RELEASE_*` env vars | `app/build.gradle.kts:28-35` |
| Lint suppression present | `ExpiredTargetSdkVersion` disabled | `app/build.gradle.kts:20` |

### 2.1 Why `targetSdk 28` is recorded here

`targetSdk 28` is unusually low relative to `compileSdk 37`, and the build
explicitly suppresses the `ExpiredTargetSdkVersion` lint check
(`app/build.gradle.kts:20`). Platform restrictions introduced after API 28 behave
differently for an app that targets API 28 than for one that targets the current
API — for example notification runtime permission, foreground-service type
requirements, granular media/photo permissions, and several background-execution
restrictions.

Phase 0 records this as a **configuration fact only**. It makes no Android
behaviour claim. Phases 9, 10, 11, and 16 must state the `targetSdk` condition
whenever an Android behaviour claim could depend on it
(`investigations/METHOD.md` §7.1 rule 4).

---

## 3. Gradle modules

`settings.gradle.kts:27-34` includes exactly eight modules:

| Module | Path | Phase 1 relevance |
| --- | --- | --- |
| `:app` | `app/` | Application entry point, DI wiring, packaging |
| `:illusioncube` | `illusioncube/` | Purpose not established by Phase 0 |
| `:data` | `data/` | Data sources, gateways, AIDL, Shizuku integration |
| `:domain` | `domain/` | Domain models and repository contracts |
| `:application` | `application/` | Use cases |
| `:ui` | `ui/` | Compose UI, activities, view models |
| `:core` | `core/` | Shared primitives |
| `:service` | `service/` | Likely the Shizuku-side user service host |

All eight use the `com.baidaidai.rootless_store` package root. Module purposes
beyond the obvious are `Unknown` and belong to Phase 1.

---

## 4. Build toolchain

| Fact | Value | Evidence |
| --- | --- | --- |
| Android Gradle Plugin | `9.2.1` | `gradle/libs.versions.toml:3` |
| Kotlin | `2.4.0` | `gradle/libs.versions.toml:11` |
| Kotlin serialization | `2.4.0` | `gradle/libs.versions.toml:44` |
| Kotlin metadata JVM | `2.4.0` | `gradle/libs.versions.toml:16` |
| kotlinx-serialization-json | `1.11.0` | `gradle/libs.versions.toml:45` |
| kotlinx-coroutines-android | `1.11.0` | `gradle/libs.versions.toml:39` |
| Compose plugin | enabled via `org.jetbrains.kotlin.plugin.compose` | `gradle/libs.versions.toml:56` |
| Ktor serialization kotlinx JSON | referenced in catalog | `gradle/libs.versions.toml:119` |

`compileSdk release(37)` with AGP `9.2.1` indicates a preview/future SDK
identifier in active use. Phase 10 must record which Android platform release
corresponds to SDK 37 before making any API-level claim about it
(`INVESTIGATION_METHOD.md` §8 forbids inventing version numbers).

---

## 5. Privilege and execution dependencies

### 5.1 Shizuku client API

| Fact | Value | Evidence |
| --- | --- | --- |
| `dev.rikka.shizuku:api` | `13.1.5` | `gradle/libs.versions.toml:4`, `:80` |
| `dev.rikka.shizuku:provider` | `13.1.5` (same `api` version ref) | `gradle/libs.versions.toml:121`, version ref `:4` |
| AIDL interface for the shell service | `IShellService.aidl` | `data/src/main/aidl/IShellService.aidl` |
| AIDL callback | `IShellCallback.aidl` | `data/src/main/aidl/IShellCallback.aidl` |

`IShellService` declares (verbatim from
`data/src/main/aidl/IShellService.aidl`):

```
void exec(String pluginDirectory, String pluginEntryPoint, boolean shouldMonitor, IShellCallback callback);
void command(String commandContent, IShellCallback callback, boolean shouldJumpToDirectory);
boolean kill(int progressPid);
boolean installShellPlugin(String shellPluginStagingFilePath, String pluginPackageName, String entryPoint);
boolean uninstallShellPlugin(String pluginPackageName);
boolean exportShellPlugin(String pluginPackageName, String shellPluginExportZipPath);
```

Phase 0 records the interface shape only. Behaviour, error semantics, and
correctness are Phase 1 (implementation) and Phase 5 (Shizuku compatibility)
subjects.

### 5.2 libsu (root shell)

| Fact | Value | Evidence |
| --- | --- | --- |
| `com.github.topjohnwu.libsu:core` | `6.0.0` | `gradle/libs.versions.toml:6`, `:83` |
| Artifact scope | `core` only — no `service`, no `nio` module declared | `gradle/libs.versions.toml:83` |
| libsu license | `Unknown` — not verified in Phase 0 | — |

Direct `com.topjohnwu.superuser.Shell` imports appear in five files:

| File | Line |
| --- | --- |
| `application/src/main/kotlin/com/baidaidai/rootless_store/application/status/ObserveExecutionContextUseCase.kt` | 9 |
| `data/src/main/kotlin/com/baidaidai/rootless_store/data/execution/gateway/PluginExecutionGatewayImpl.kt` | 10 |
| `data/src/main/kotlin/com/baidaidai/rootless_store/data/status/datasource/KernelVersionDataSource.kt` | 3 |
| `data/src/main/kotlin/com/baidaidai/rootless_store/data/status/datasource/SeLinuxStatusDataSource.kt` | 5 |
| `data/src/main/kotlin/com/baidaidai/rootless_store/data/status/gateway/StoreStatusGatewayImpl.kt` | 28 |

### 5.3 Privilege mechanisms actually present in the codebase

Verified presence of two distinct privilege paths:

1. **Shizuku user service** — a `UserService` bound through the Shizuku provider
   and communicated with over AIDL (`data/src/main/aidl/`,
   `data/.../data/shizuku/server/ShizukuEndpointTemplate.kt`,
   `data/.../data/shizuku/gateway/ShizukuUserServiceGatewayImpl.kt`).
2. **Root shell via libsu** — `com.topjohnwu.superuser.Shell` used directly in the
   files listed above.

Phase 0 records only that both paths exist. It makes **no** claim about their
privilege level, because privilege depends on how the Shizuku server was started
(`INVESTIGATION.md:894`, `AGENTS.md` §12, `ARCHITECTURE.md` §21). That is a
Phase 5 / Phase 21 question.

### 5.4 Porter

| Fact | Value | Evidence |
| --- | --- | --- |
| Porter dependency declared | **None** | `gradle/libs.versions.toml` (full read); all `build.gradle.kts` files |
| Porter references in Kotlin/Gradle/TOML | **None found** | repository-wide search for `porter` across `.kt`, `.kts`, `.toml` |
| Porter integration in code | **None found** | same search |

This is a verified negative: the proposed primary backend
(`AGENTS.md` §11, `ARCHITECTURE.md` §16) is not yet present in any form. See
U-001.

---

## 6. Shizuku-related files (inventory for Phase 1 / Phase 5)

Present at this commit:

```
application/src/main/kotlin/com/baidaidai/rootless_store/application/execute/ExecutePluginByShizukuUseCase.kt
application/src/main/kotlin/com/baidaidai/rootless_store/application/shizuku/EnsureShizukuPermissionUseCase.kt
application/src/main/kotlin/com/baidaidai/rootless_store/application/shizuku/StartShizukuUserServiceUseCase.kt
application/src/main/kotlin/com/baidaidai/rootless_store/application/status/GetShizukuAvailabilityUseCase.kt
data/src/main/kotlin/com/baidaidai/rootless_store/data/shizuku/gateway/ShizukuPermissionGatewayImpl.kt
data/src/main/kotlin/com/baidaidai/rootless_store/data/shizuku/gateway/ShizukuUserServiceGatewayImpl.kt
data/src/main/kotlin/com/baidaidai/rootless_store/data/shizuku/server/ShizukuEndpointCallback.kt
data/src/main/kotlin/com/baidaidai/rootless_store/data/shizuku/server/ShizukuEndpointTemplate.kt
ui/src/main/kotlin/com/baidaidai/rootless_store/ui/ShizukuActivity.kt
ui/src/main/kotlin/com/baidaidai/rootless_store/ui/components/shizukuAdbScreen/ShizukuAdbScreenNecessaryComponents.kt
ui/src/main/kotlin/com/baidaidai/rootless_store/ui/model/RootlessStoreShizukuAdbScreenViewModel.kt
ui/src/main/kotlin/com/baidaidai/rootless_store/ui/navigation/model/ShizukuAdbScreenKey.kt
ui/src/main/kotlin/com/baidaidai/rootless_store/ui/screens/ShizukuAdbScreen.kt
ui/src/main/res/drawable/material_shizuku_icon.xml
asset/picture/zh-rCN/AdbShizukuScreen.png
```

This is an inventory, not a behaviour finding. The presence of
`ShizukuAdbScreen*` and `AdbShizukuScreen.png` indicates the existing app already
presents an "ADB + Shizuku" mode to users; what that mode actually does is a
Phase 1 question.

---

## 7. Documentation files present in the repository

| File | Role |
| --- | --- |
| `AGENTS.md` | Agent rules — authority for agent behaviour |
| `PLAN.md` | Authoritative 27-phase (0–26) investigation checklist |
| `INVESTIGATION_METHOD.md` | Methodology — 56 numbered sections |
| `ARCHITECTURE.md` | Current **proposed** architecture, 71 numbered sections |
| `INVESTIGATION.md` | Original pre-MasterRef investigation, 44 numbered sections, historical |
| `MasterRef.md` | Consolidated reference — **protected**, unmodified by this program |
| `README.md` | Project overview, fork relationship, program status |
| `docs/code-style.md` | Repository code conventions |
| `docs/storage-model.md` | Current storage layout documentation (see contradiction C-002 / unknown U-006) |

`INVESTIGATION.md:23` states the document "does not define the authoritative
investigation phase order" — consistent with `PLAN.md` (see retracted C-001).

---

## 8. Tooling notes for later phases

| Note | Detail |
| --- | --- |
| `rg` unavailable | `/usr/bin/bash: line 1: rg: command not found`. Use `grep -rn` / `find` / `git grep`. `git grep` is the fastest option for tracked files. |
| No network fetches performed in Phase 0 | External source identification is limited to search-result reconnaissance, recorded as `LEAD` only. |
| No build executed in Phase 0 | `./gradlew` was not run. No compilation, lint, or test evidence exists from Phase 0. Any Phase 17 (Testing) claim about test executability must establish this independently. |

---

## 9. What this baseline deliberately does not claim

| Not claimed | Why | Owning phase |
| --- | --- | --- |
| That the app has any ADB Module support | No ADB Module code, manifest, or resource was located; the feature is `PROPOSED` in `ARCHITECTURE.md` §25–§34 | 2 |
| Any Porter behaviour or API | No dependency exists; artifact identity unresolved | 4 (U-001) |
| Any Shevery compatibility | No reference commit pinned | 2 (U-003) |
| Any Shizuku server-side behaviour | Only the client API version is pinned; the server is user-supplied | 5 (U-004) |
| Any Android runtime behaviour | No device was used; no platform source was inspected | 9, 10 |
| That `docs/storage-model.md` matches the code | Documentation was read; the implementing code was not | 1 (U-006) |
| Any licence other than this fork's AGPL-3.0 | Upstream, libsu, Shevery, Shizuku, and Porter licences unverified | 18 |