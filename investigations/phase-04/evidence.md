# ADB Modules App — Phase 04 Evidence Ledger

Phase: `04 — Porter Investigation`
**Standard:** `investigations/METHOD.md` §9 (evidence ledger), template
`investigations/templates/EVIDENCE-LEDGER.md`
**Method authority:** `INVESTIGATION_METHOD.md` §19 (Porter Investigation Method)
**Opened by:** Phase 4
**Last updated:** 2026-10-02

---

## 0. Pins used by every row in this ledger

| Alias | Repository | Commit | Date | Role |
| --- | --- | --- | --- | --- |
| `PORTER` | `https://github.com/d4rken-org/porter` | `2d88f34bc348552b7fb22eb73ccaba5b9922cce5` | 2026-10-02 | The Porter **application** (manager + server + shell loader) |
| `SDK` | `https://github.com/d4rken-org/porter-api` | `2f2805226a33d6742c646a5efe022aa33d4c63ae` | 2026-09-30 | The Porter **SDK** an app integrates |
| `LOCAL` | this project | `6df93ae8d2c3dbb84b461b4eecb0c7f65de8a5b2` | 2026-10-02 | Integration surface this project must satisfy |

`PORTER`'s `main` HEAD **equals** the commit Phase 2 pinned as S-204, so Phase 2's
negative result ("Porter has no module subsystem") is re-verified at the same commit
rather than re-derived. `SDK` HEAD **equals** tag `0.9.0`.

Evidence types, per `INVESTIGATION_METHOD.md` §11 and the capability classification
`AGENTS.md` §11 mandates for Porter:

- `L1-SOURCE` — implementation source read directly.
- `L2-DOC` — official project documentation, quoted as a *claim* and labelled
  `Documented but not source-verified` unless a source row confirms it.
- `NEGATIVE` — a search that returned nothing.
- `TEST` — a test in the upstream repository that pins behaviour.

Every Porter capability row carries one of the five `AGENTS.md` §11 labels:
`Verified`, `Documented`, `Inferred`, `Conceptual`, `Unknown`.

---

## 1. Identity, version, release channel

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A01 | Porter is `d4rken-org/porter`, application id `eu.darken.porter`, self-described as "A minimal, maintained fork of Shizuku that gives Android apps ADB access through the Shizuku APIs, with optional root support." It is **not affiliated** with the original Shizuku maintainers | `README.md:5-11`, `manager/build.gradle.kts:73` | L1-SOURCE | Verified |
| P4-A02 | Application version is `0.8.0-rc0`, versionCode `800000` (`VERSION` file and `version.properties`), while `main` HEAD is dated 2026-10-02 and the `v0.8.0-rc0` tag is dated 2026-09-29 — so HEAD is **after** the tag | `VERSION`, `version.properties`, `git tag -l` | L1-SOURCE | Verified |
| P4-A03 | Application tags are `v0.1.1-beta0`, `v0.1.1-beta1`, `v0.7.0-rc0`, `v0.8.0-rc0` — all pre-release; **no stable release tag exists in this repository** | `git tag` (4 tags) | L1-SOURCE | Verified |
| P4-A04 | The SDK is a **separate repository**, `d4rken-org/porter-api`, consumed from JitPack under group `com.github.d4rken-org.porter-api` | `docs/developers.md:14-32` | L2-DOC | Verified (co-ordinate confirmed by `settings.gradle.kts` of SDK) |
| P4-A05 | Published SDK artifacts are `sdk`, `sdk-extras`, `shizuku-compat`, `shizuku-bridge`. Modules `aidl`, `shared`, `protocol`, `manager-protocol`, `server-shared`, `porsh` are internal and are **not** published for integrators | `settings.gradle.kts:36-77` (SDK) | L1-SOURCE | Verified |
| P4-A06 | SDK release tags are `0.1.0`, `0.7.0`, `0.8.0`, `0.9.0`; SDK `0.9.0` **is** the current HEAD. SDK version numbers are explicitly **independent** of the application version | `git tag` (SDK); `docs/developers.md:364-374` | L1-SOURCE + L2-DOC | Verified |
| P4-A07 | Porter's `main` **consumes the SDK as source**, via `includeSdkModule(...)` in `settings.gradle.kts`, and `server/build.gradle.kts` depends on `:server-shared`, `:protocol`, `:aidl`, `:shared`, `:porsh` | `PORTER settings.gradle.kts:62-77`; `server/build.gradle.kts:27-36` | L1-SOURCE | Verified |
| P4-A08 | Consequently the server's authorization core `eu.darken.porter.core.PorterCore` is **not vendored in the application repo** — it lives in `porter-api/server-shared`. Reading it requires the SDK repository, not the app repository | `PORTER server/.../PorterServer.kt:25,76-77`; `SDK server-shared/.../PorterCore.kt` | L1-SOURCE | Verified |
| P4-A09 | Licence: application source Apache-2.0; the bundled Shizuku API is MIT; attribution in `NOTICE` | `README.md:61-62`, `LICENSE`, `NOTICE` | L1-SOURCE | Verified (Phase 18 owns the full audit) |
| P4-A10 | There is a **Porter Compatibility companion**, a separate APK with application id `moe.shizuku.privileged.api` and its own versionCode `700000` | `compat/build.gradle.kts:16`; `version.properties` (`project.compat.version`) | L1-SOURCE | Verified |
| P4-A11 | The companion "uses Shizuku's Android app identity to support older apps. Android treats them as competing installations" — it therefore **cannot be installed alongside Shizuku** | `docs/compatibility.md:43` | L2-DOC | Documented |
| P4-A12 | Porter bundles a compatibility companion APK in the FOSS build; a standalone download exists from the same release | `docs/compatibility.md:23,35` | L2-DOC | Documented |
| P4-A13 | Documentation site is `https://porter.darken.eu/`; developers guide at `/developers`; API reference at `d4rken-org/porter-api/blob/main/docs/api-reference.md` | `README.md`, `docs/developers.md:391-392` | L2-DOC | Verified |

**This resolves U-001's identity and version question.** See `REPORT.md` §11.

---

## 2. Supported Android versions

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A14 | Porter application: `minSdk = 24`, `targetSdk = 37`, `compileSdkVersion(37)` | `PORTER build.gradle.kts:19,23-24` | L1-SOURCE | Verified |
| P4-A15 | Porter SDK: "needs Android 7.0 (API 24) or newer", and is "coroutines and `Flow` throughout; use from Java is not supported" | `docs/developers.md:40-41` | L2-DOC | Documented |
| P4-A16 | Device requirement for starting the service is Android 7.0+, **plus** one of: a computer (USB debugging), Android 11+ wireless debugging, or an already-rooted device | `README.md:23`; `docs/setup.md:23-28`; `docs/index.md:19` | L2-DOC | Documented |
| P4-A17 | Wireless debugging requires Android 11+; "Some device manufacturers restrict wireless debugging" | `docs/setup.md:31` | L2-DOC | Documented |
| P4-A18 | Porter carries an `Android17Compat` shim with fallbacks for `getPackageInfo`, `getApplicationInfo`, `checkPermission(String,String,int)`, `checkPermission(String,int)`, `grantRuntimePermission`, `revokeRuntimePermission` — i.e. explicit Android 17 (API 37) hidden-API breakage handling | `server/.../util/Android17Compat.kt:98,137,167,197,224,253` | L1-SOURCE | Verified (source); runtime behaviour **Unverified** |
| P4-A19 | The application requests `ACCESS_LOCAL_NETWORK` and, on API 33+, `NEARBY_WIFI_DEVICES` | `manager/src/main/AndroidManifest.xml:12-20` | L1-SOURCE | Verified |
| P4-A20 | No Porter documentation states a **maximum** Android version, and no row here establishes Android 12/13/14/15/16/17 support individually | — | NEGATIVE | **Unknown** |

**P4-A20 is deliberate.** `PLAN.md` Phase 10 owns the per-version matrix. Phase 4
records only what the source and docs state; asserting "supported on Android 14"
would be an unverified claim. `targetSdk 37` (P4-A14) is evidence of *intent* to run
on Android 17, not evidence of behaviour on any specific device.

---

## 3. Supported execution modes and privilege identity

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A21 | Two privilege identities: ADB shell (`uid` 2000) and root (`uid` 0). `connection.uid` "is `2000` for ADB and `0` for root" | `docs/developers.md:251`; `sdk/.../PorterConnection.kt:145-146` | L2-DOC + L1-SOURCE | Verified |
| P4-A22 | "shell can do far less than root: it cannot read other apps' data, and its Android permissions are those of the Shell package" | `docs/developers.md:251-253` | L2-DOC | Documented |
| P4-A23 | The server reports its own SELinux context at attach, exposed as `connection.seLinuxContext`; "For adb this is `u:r:shell:s0`; for root it depends on the su implementation" | `sdk/.../PorterConnection.kt:151-154`; `docs/developers.md` (referenced) | L1-SOURCE + L2-DOC | Verified |
| P4-A24 | Three documented startup modes: wireless debugging (Android 11+), USB debugging with a computer, and root | `docs/setup.md:23-28,68-73` | L2-DOC | Documented |
| P4-A25 | "Stop the current Porter service before switching between root and debugging access" — the identity is a property of the running server, not of the app | `docs/setup.md:74` | L2-DOC | Documented |
| P4-A26 | A third-party identity (`IShizukuService` on a Shizuku server) is reachable by the same SDK. `PorterBackend` is `{ PORTER, SHIZUKU }` and is explicitly "not the product the server belongs to" | `sdk/.../PorterBackend.kt:1-8`; `sdk/.../PorterConnection.kt:47-50` | L1-SOURCE | Verified |

**P4-A26 is architecturally significant:** one SDK, two wires. Phase 5's Shizuku
backend and Phase 4's Porter backend can share an SDK-provided abstraction layer.

---

## 4. Initialization requirements

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A27 | The SDK's entry point is the singleton `Porter`. A connection arrives by the application calling `Porter.onBinderReceived(newBinder, packageName)` | `sdk/.../Porter.kt:33,205` | L1-SOURCE | Verified |
| P4-A28 | There is nothing to add to the manifest: "There is nothing to add to your manifest; the SDK brings its own entries." | `docs/developers.md:43` | L2-DOC | Documented |
| P4-A29 | Consumers must **collect** `Porter.state: StateFlow<PorterConnectionState>` rather than read it once, because "a new one [connection] arrives whenever the user restarts Porter while your app is alive" | `sdk/.../Porter.kt:130`; `docs/developers.md:51-53,75-78` | L1-SOURCE + L2-DOC | Verified |
| P4-A30 | Multi-process apps must call `PorterApiProvider.requestBinderForNonProviderProcess(context)` in each non-receiving process. "Calling it in the process that receives the connection does nothing, so one call site for every process is fine." | `docs/developers.md:277-290` | L2-DOC | Documented |
| P4-A31 | `shizuku-bridge` requires exactly one `PorterShizukuBridge.start(appScope)` per process, with a scope that lives as long as the process (e.g. `Application.onCreate`). Calling it again while its scope runs does nothing | `docs/api-reference.md:143-152` | L1-SOURCE (doc shipped in SDK repo) | Verified |
| P4-A32 | No `Application`, `ContentProvider`, `Service` or `Activity` initialisation is required beyond what the SDK's manifest merges provide | `sdk/src/main/AndroidManifest.xml` (absent from listing) | NEGATIVE | Verified (absence) |

---

## 5. Permission requirements

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A33 | `eu.darken.porter.permission.API` is declared `android:protectionLevel="dangerous"` — a **runtime** permission the user grants per app | `manager/src/main/AndroidManifest.xml:48-54` | L1-SOURCE | Verified |
| P4-A34 | `eu.darken.porter.permission.MANAGER` is `android:protectionLevel="signature"`, and the manager deliberately removes its own use of the client permission: `tools:node="remove"` | `manager/src/main/AndroidManifest.xml:43-59` | L1-SOURCE | Verified |
| P4-A35 | "A connection is not access." `checkPermission()` returns `PermissionState.Granted` or `PermissionState.Denied(permanentlyDenied)`; `requestPermission()` suspends until the user answers | `sdk/.../PorterConnection.kt:203,245-269`; `sdk/.../PermissionState.kt` | L1-SOURCE | Verified |
| P4-A36 | Permission state is a `StateFlow<PermissionState>` on the connection, kept current by server pushes, and "The user can revoke access at any time, so handle a refused call rather than trusting an earlier check" | `sdk/.../PorterConnection.kt:87,123-136`; `docs/developers.md:137-138` | L1-SOURCE + L2-DOC | Verified |
| P4-A37 | Revoking the permission **kills the app**: "Porter's permission is a runtime permission, and Android kills an app when it revokes one the app holds." | `docs/developers.md:325-327` | L2-DOC | Documented |
| P4-A38 | Server-side gate: `PorterCore.callingPermissionDenial` denies in three cases — policy refusal, **caller not attached** ("is not an attached client"), or "not allowed and exemption does not waive". Holding the Android permission alone admits nothing | `SDK server-shared/.../PorterCore.kt:41-63` | L1-SOURCE | Verified |
| P4-A39 | Manager-only operations are gated separately by `enforceManagerPermission`, which admits the server's own pid and otherwise requires `policy.checkCallerManagerPermission` | `SDK server-shared/.../PorterCore.kt:65-76` | L1-SOURCE | Verified |
| P4-A40 | The manager is the **user-0** installation only; "The manager is the user 0 installation; the same package in another user is an app" | `SDK server-shared/.../PorterCore.kt:80`; `PORTER server/src/test/.../ServiceAuthorizationTest.kt:263-273` | L1-SOURCE + TEST | Verified |
| P4-A41 | `CallerExemption` is a per-wire hook, so the core "never learns which wire a call arrived on". `CallerExemption.None` waives nothing | `SDK server-shared/.../CallerExemption.kt` | L1-SOURCE | Verified |
| P4-A42 | `checkRemotePermission(permission)` short-circuits to `true` when `serverUid == 0` (root), otherwise queries the server | `sdk/.../PorterConnection.kt:297-300` | L1-SOURCE | Verified |
| P4-A43 | The manager is discovered **by the permission it declares**, not by package name, "so a renamed fork is found too". `PorterAvailability.packageName` "does not prove that package served the binder" | `sdk/.../PorterAvailability.kt:8-10,17-19` | L1-SOURCE | Verified |
| P4-A44 | No permission is required of the integrating app for the Shizuku bridge beyond what upstream already needs; but `shizuku-compat` requires `moe.shizuku.manager.permission.API_V23` and the `V3_SUPPORT` meta-data in the integrating app's manifest | `docs/developers.md:296-314` | L2-DOC | Documented |

---

## 6. Command execution behaviour

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A45 | `PorterConnection.exec(vararg command: String, dir: String? = null): PorterShellResult` — runs at the connection's identity, returns once the command exits | `sdk-extras/.../PorterShell.kt:43` | L1-SOURCE | Verified |
| P4-A46 | Arguments are passed as an **argv array**, not a shell string. There is no implicit shell interpretation, so quoting and injection hazards are the caller's to manage | `sdk-extras/.../PorterShell.kt:43,84` (`service.startProcess(command, dir)`) | L1-SOURCE (from signature) | Verified |
| P4-A47 | A command that is not found exits **127**, "as in a shell" | `sdk-extras/.../PorterShell.kt:36-37`; `docs/developers.md:151-152` | L1-SOURCE + L2-DOC | Verified |
| P4-A48 | An **empty** command throws `IllegalArgumentException("No command to run")` | `sdk-extras/.../PorterShell.kt:83` | L1-SOURCE | Verified |
| P4-A49 | A `dir` that does not exist throws `PorterShellException` | `sdk-extras/.../PorterShell.kt:37-39` | L1-SOURCE | Verified |
| P4-A50 | `exec` **closes stdin immediately** (`process.outputStream.close()`): "The command gets no input." | `sdk-extras/.../PorterShell.kt:47` | L1-SOURCE | Verified |
| P4-A51 | Output is read as **UTF-8**; binary output or a command needing input must use `startProcess` | `sdk-extras/.../PorterShell.kt:34-35,106-113` | L1-SOURCE | Verified |
| P4-A52 | **There is no output-size cap and no built-in timeout in `exec`.** `PorterShellResult` holds `output` and `errors` as unbounded `String`s | `sdk-extras/.../PorterShellResult.kt:4-10` | L1-SOURCE (absence) | Verified (absence) |
| P4-A53 | Cancellation bounds a command: "Cancelling returns at once and, while the command runs, kills it and its process group with SIGKILL, so `withTimeout` bounds a command that does not end." | `sdk-extras/.../PorterShell.kt:33-38,56-59` | L1-SOURCE | Verified |
| P4-A54 | **Every `exec`/`startProcess` call is implemented on top of a user service** the SDK starts for the app: `PorterShellService`, tag `eu.darken.porter.sdk.extras.shell`, process suffix `porter_shell`, `version = 2` | `sdk-extras/.../PorterShell.kt:151-159,169-186` | L1-SOURCE | Verified |
| P4-A55 | That shell-service binding is **one per connection**, cached in a `WeakHashMap<PorterConnection, ShellBinding>`, and is **not re-bound** for subsequent calls | `sdk-extras/.../PorterShell.kt:127-131,168-186` | L1-SOURCE | Verified |
| P4-A56 | "One binding for every call, because a Shizuku server older than 13.4 keeps each binding it was given until the app's process dies" — a Shizuku-backend-specific workaround | `sdk-extras/.../PorterShell.kt:164-166` | L1-SOURCE | Verified |
| P4-A57 | A dead shell service is detected via `isBinderAlive` and the binding is forgotten so the next call re-binds | `sdk-extras/.../PorterShell.kt:86-91` | L1-SOURCE | Verified |
| P4-A58 | `PorterShellException` is a `RuntimeException` and is **not** a `PorterException` — it is a distinct failure channel from a server refusal | `sdk-extras/.../PorterShellException.kt:9` | L1-SOURCE | Verified |
| P4-A59 | `exec` requires the grant: "It needs the permission granted, and a refusal throws the SDK's `PorterSecurityException`." | `sdk-extras/.../PorterShell.kt:39-40` | L1-SOURCE | Verified |

---

## 7. stdin / stdout / stderr behaviour

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A60 | `startProcess(vararg command, dir): PorterShellProcess`, where `PorterShellProcess : java.lang.Process` — so the project's runtime code could use familiar `Process` semantics | `sdk-extras/.../PorterShell.kt:80`; `sdk-extras/.../PorterShellProcess.kt:27` | L1-SOURCE | Verified |
| P4-A61 | The three streams are **pipes** to the remote command, obtained as `ParcelFileDescriptor`s: `getOutputStream()` is stdin, `getInputStream()` stdout, `getErrorStream()` stderr | `sdk-extras/.../PorterShellProcess.kt:37-45,58-62` | L1-SOURCE | Verified |
| P4-A62 | The documentation warns: "Read its output as it comes, or the command blocks once a pipe is full." | `docs/developers.md:158-159` | L2-DOC | Documented |
| P4-A63 | Server-side, `ServerProcess.getInputStream()`/`getOutputStream()` "answer with the same descriptor every time; `getErrorStream()` opens a fresh pipe and a fresh transfer thread on each call, so two callers end up competing for the same bytes" | `SDK server-shared/.../ServerProcess.kt:9-16` | L1-SOURCE | Verified |
| P4-A64 | `stdin` is a real pipe, unlike `exec`. `exec` closes it; `startProcess` leaves it open, so an interactive command is possible | `sdk-extras/.../PorterShellProcess.kt:37,58`; `sdk-extras/.../PorterShell.kt:47` | L1-SOURCE | Verified |
| P4-A65 | On the Shizuku bridge, `newProcess` differs: "Closing the process's output stream ends the command's input", and streams stay open until the app closes them — "also after `destroy()`" | `docs/api-reference.md:161` | L1-SOURCE (doc in SDK repo) | Verified |

---

## 8. Exit-code behaviour

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A66 | `PorterShellResult(exitCode, output, errors)`; `exitCode` is the process's own exit code, surfaced by `Process.waitFor()` | `sdk-extras/.../PorterShellResult.kt:4-10`; `sdk-extras/.../PorterShell.kt:48` | L1-SOURCE | Verified |
| P4-A67 | `exitValue()` throws `IllegalThreadStateException("the process has not exited")` while the command is alive — standard `Process` contract | `sdk-extras/.../PorterShellProcess.kt:64-67` | L1-SOURCE | Verified |
| P4-A68 | `waitFor()` is implemented in **250 ms slices** against the remote service, so no binder thread of the shell service is held for a whole run, and it is cancellable via `Thread.interrupted()` → `InterruptedException` | `sdk-extras/.../PorterShellProcess.kt:11,56-62` | L1-SOURCE | Verified |
| P4-A69 | **Porter defines no synthetic timeout exit code.** The reference implementation uses `124` for its 120 s timeout; Porter has no equivalent, because it has no built-in timeout (P4-A52) | absence across `sdk-extras/` | NEGATIVE | Verified (absence) |

---

## 9. PID and process handling

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A70 | **`PorterShellProcess.pid: Int?` is exposed directly.** It is read from the server and is null where the shell service could not read it | `sdk-extras/.../PorterShellProcess.kt:35,42` | L1-SOURCE | Verified |
| P4-A71 | The pid is taken as `remote.pid().takeIf { it > 0 }`, so a non-positive value becomes null rather than a bogus pid | `sdk-extras/.../PorterShellProcess.kt:42` | L1-SOURCE | Verified |
| P4-A72 | This removes the local project's need to parse a pid out of stdout (`ExecutePluginByShizukuUseCase.parsePid` against the `PID:$$` regex) — a direct simplification of `Phase 3` finding P3-A53/P3-A54 | comparison: `LOCAL application/.../ExecutePluginByShizukuUseCase.kt:20,39-48` | INFERENCE (cross-source) | Inferred |
| P4-A73 | `PorterShellProcess` also exposes `alive()` internally (`remote.alive()`), used by `exitValue()` | `sdk-extras/.../PorterShellProcess.kt:64-67` | L1-SOURCE | Verified |

---

## 10. Process termination

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A74 | `destroy()` sends **SIGKILL** to the command and its **process group**, "or to the command alone on a device without `/system/bin/setsid`" | `sdk-extras/.../PorterShellProcess.kt:14-19,85-96` | L1-SOURCE | Verified |
| P4-A75 | `destroy()` gives the command no chance to clean up. For a command that must finish, the documented sequence is `signal(OsConstants.SIGINT)` and **wait for exit** before `destroy()` | `sdk-extras/.../PorterShellProcess.kt:16-19`; `docs/developers.md:172-174` | L1-SOURCE + L2-DOC | Verified |
| P4-A76 | `signal(signal: Int)` sends an `OsConstants.SIG*` value **to the command only** — "the processes it started do not get it" — and leaves an already-exited command alone. Cancelling stops the wait, not a signal already sent | `sdk-extras/.../PorterShellProcess.kt:69-76` | L1-SOURCE | Verified |
| P4-A77 | `signal` throws `IllegalArgumentException` for a non-signal value, and `PorterShellException` where `pid` is null or delivery fails | `sdk-extras/.../PorterShellProcess.kt:70-75` | L1-SOURCE | Verified |
| P4-A78 | **Once the command has exited, `destroy()` only closes the pipes** — "what it left running in its group is no longer reached, by `destroy()` or by this app's process dying" | `sdk-extras/.../PorterShellProcess.kt:17-19` | L1-SOURCE | Verified |
| P4-A79 | `destroy()` closes the three local streams first "so a service that stopped answering must not keep these open", then attempts the remote destroy and swallows the failure | `sdk-extras/.../PorterShellProcess.kt:85-96` | L1-SOURCE | Verified |
| P4-A80 | Server-side termination of a service: `stopUserService(args)` sends `UserServiceArgs.TRANSACTION_DESTROY` (`16777114`) and "Porter's server kills a host process still running three seconds after the removal" | `sdk/.../PorterConnection.kt:479-486`; `sdk/.../UserServiceArgs.kt:29-32`; `docs/developers.md:241-243` | L1-SOURCE + L2-DOC | Verified |
| P4-A81 | A service is expected to implement `destroy()` itself to clean up and `exitProcess(0)`; Porter does not kill it directly on `stopUserService`, only as a backstop after 3 s | `docs/developers.md:186-198,241-243` | L2-DOC | Documented |

---

## 11. Service / background behaviour

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A82 | `connection.userService(args, start = true): Flow<IBinder>` is a **cold flow**: collecting binds the service and starts it unless `start` is false | `sdk/.../PorterConnection.kt:328-357`; `docs/developers.md:214-217` | L1-SOURCE | Verified |
| P4-A83 | The flow "completes when the server reports the service died, and when this connection is replaced or dies, whether or not the service is still running". A flow started on an already-lost connection completes at once | `sdk/.../PorterConnection.kt:332-337,368-371` | L1-SOURCE | Verified |
| P4-A84 | Bindings are shared by service identity — `UserServiceArgs.tag` where set, else the service class name — **among the collectors of that one connection**. The same identity collected on another connection is a different binding, on that connection's server | `sdk/.../PorterConnection.kt:339-342` | L1-SOURCE | Verified |
| P4-A85 | `UserServiceArgs` fields: `componentName`, `processNameSuffix`, `tag`, `version`, `debuggable`, `daemon` | `sdk/.../UserServiceArgs.kt:9-32` | L1-SOURCE | Verified |
| P4-A86 | **`daemon` semantics:** "A daemon service is not tied to the app's process. A non-daemon one is stopped when the last process still collecting it dies; one whose collections were all cancelled first runs on like a daemon." Either ends when it is stopped, the app's permission is revoked, the app is uninstalled from every user, or the server stops | `sdk/.../UserServiceArgs.kt:22-29`; `docs/developers.md:243-245` | L1-SOURCE + L2-DOC | Verified |
| P4-A87 | A daemon service therefore **survives app-process death**, whereas a `startProcess` command **does not** (P4-A94). These are two different survival models in one SDK | P4-A86 vs `sdk-extras/.../PorterShellProcess.kt:12-13` | INFERENCE (cross-source) | Inferred |
| P4-A88 | "A Shizuku server below 13.4 keeps a cancelled binding, so there a non-daemon service still stops with that process" — a fork/wire-specific divergence | `sdk/.../UserServiceArgs.kt:28`; `sdk/.../PorterConnection.kt:343-344` | L1-SOURCE | Verified |
| P4-A89 | **Unbinding does not kill the service.** Only `stopUserService` does | `sdk/.../PorterConnection.kt:345-348`; `docs/developers.md:241` | L1-SOURCE | Verified |
| P4-A90 | "Cancelling the collection does not stop the process." | `docs/developers.md:242-243` | L2-DOC | Documented |
| P4-A91 | A service is **per Android user**: "a work profile's copy of an app is served by its own process, whatever the personal profile's copy is running. Both run as the server's uid, not the profile's" | `sdk/.../PorterConnection.kt:350-352` | L1-SOURCE | Verified |
| P4-A92 | "The service process is not a valid Android application process. A `Context` obtained there cannot register receivers or reach a content resolver." | `sdk/.../PorterConnection.kt:354-355`; `docs/developers.md:254-255` | L1-SOURCE + L2-DOC | Verified |
| P4-A93 | Bumping `version` makes the server replace a running instance; identity is `tag`, or class name when no tag is set, "so set a stable tag if the class is obfuscated" | `sdk/.../UserServiceArgs.kt:16,19`; `docs/developers.md:247-249` | L1-SOURCE | Verified |

---

## 12. Lifecycle behaviour, failure modes, unavailable states

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A94 | **A running command dies with the app process that started it**: "A running command also dies with the app process that started it." Server-side this is implemented as `ServerProcess` linking an `IBinder.DeathRecipient` to the owner's token and calling `destroy()` when the owner dies | `docs/developers.md:174-176`; `SDK server-shared/.../ServerProcess.kt:20-38` | L2-DOC + L1-SOURCE | Verified |
| P4-A95 | "Porter's service stops when the device restarts. Start it again afterwards." — there is **no** boot-start guarantee | `README.md:24`; `docs/troubleshooting.md:9` | L2-DOC | Documented |
| P4-A96 | "Start Porter manually once after installation before relying on **Start on boot**. A successful debugging start grants the Android setting permission needed for later automatic starts." | `docs/troubleshooting.md:11-16` | L2-DOC | Documented |
| P4-A97 | "Porter keeps stopping" is a documented, expected failure mode with causes listed (device restart, debugging disabled, OEM battery/background settings) | `docs/troubleshooting.md:41-45` | L2-DOC | Documented |
| P4-A98 | Three connection states: `Disconnected`, `Connected(connection)`, `Incompatible(incompatibility)`. `Disconnected` means "No connection is held, and no server this process refused is still running" | `sdk/.../PorterConnectionState.kt` | L1-SOURCE | Verified |
| P4-A99 | Five availability states: `NotInstalled`, `InstalledUnrecognized`, `InstalledNotConnected`, `Incompatible`, `Connected` — distinguishing "behind the cases a connection never arrives" | `sdk/.../PorterAvailability.kt`; `docs/developers.md:87-108` | L1-SOURCE | Verified |
| P4-A100 | `InstalledUnrecognized` means "a package this SDK does not recognize owns Porter's permission; do not present it as the manager" | `sdk/.../PorterAvailability.kt:15-18`; `docs/developers.md:107-108` | L1-SOURCE | Verified |
| P4-A101 | Version incompatibility is **first-class**: `PorterIncompatibility` carries `backend`, `serverVersion`, `serverMinVersion`, `clientVersion`, `clientMinVersion`, and two booleans `serverTooOld` / `clientTooOld` saying **which side must move** | `sdk/.../PorterIncompatibility.kt` | L1-SOURCE | Verified |
| P4-A102 | Wire protocol versions are pinned and floor-based: Porter protocol `VERSION = 4`, `MIN_VERSION = 4`; SDK speaks Shizuku `CLIENT_API_VERSION = 13` with `MINIMUM_VERSION = 13` | `SDK protocol/.../PorterProtocol.kt:26,29`; `SDK shared/.../ShizukuProtocol.kt:28-33` | L1-SOURCE | Verified |
| P4-A103 | Versions are **cumulative**: "a peer at a higher version still speaks every version from its floor up, so a newer peer is never inherently incompatible". Optional functionality is announced through a `REPLY_CAPABILITIES` bitmask, not through the version, and "A bit is allocated when the capability ships and is never reused" | `SDK protocol/.../PorterProtocol.kt:20-26,65-70` | L1-SOURCE | Verified |
| P4-A104 | `PorterConnection.generation` is "Never reset, so a connection of this process is never mistaken for a later one" — stale-callback discrimination is designed in | `sdk/.../PorterConnection.kt:37-38,521-523` | L1-SOURCE | Verified |
| P4-A105 | On connection loss, `markLost()` fails every pending permission request with `PorterConnectionLostException` and closes the user-service flows; uncaught, that exception "ends the coroutine collecting `Porter.state`, and the replacement connection is never handled" | `sdk/.../PorterConnection.kt:271-292`; `docs/developers.md:134-135` | L1-SOURCE | Verified |
| P4-A106 | `PorterConnectionLostException` is distinct from `PorterSecurityException` (server refused) and `PorterRemoteException` (binder failed / server threw) | `sdk/.../PorterConnection.kt:30-34` | L1-SOURCE | Verified |
| P4-A107 | Every server-reaching call **suspends and is main-thread safe**; cancelling returns at once, "so a timeout around it works against a server that stopped answering; a call already sent still reaches the server and takes effect there" | `sdk/.../PorterConnection.kt:30-34`; `docs/developers.md:80-83` | L1-SOURCE | Verified |
| P4-A108 | `isAlive()` is `binder.pingBinder()` behind `Porter.serverCall` | `sdk/.../PorterConnection.kt:156-157` | L1-SOURCE | Verified |
| P4-A109 | A service that "stops answering" throws `PorterShellException`, wrapped from `RemoteException` or `IllegalStateException` by `shellCall` | `sdk-extras/.../PorterShell.kt:189-196` | L1-SOURCE | Verified |
| P4-A110 | **A refused server is published as `Porter.state`** (SDK 0.9.0, commit subject `feat(sdk): publish a refused server as Porter.state`) — an incompatibility is surfaced even though no connection is held | `git log` tag `0.9.0`; `sdk/.../PorterConnectionState.kt:26-33` | L1-SOURCE | Verified |

---

## 13. Security boundaries

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A111 | Every gated operation takes a `CallerExemption` from its wire; enforcement is per-transaction, logging the denial then throwing `SecurityException` | `SDK server-shared/.../PorterCore.kt:41-63` | L1-SOURCE | Verified |
| P4-A112 | A caller must be **attached** (have a client record) as well as allowed; an unattached caller holding the Android permission is refused | `SDK server-shared/.../PorterCore.kt:48,55-57`; `PORTER server/src/test/.../ServiceAuthorizationTest.kt:275-280` | L1-SOURCE + TEST | Verified |
| P4-A113 | A process attaches through **one endpoint only**; a second attach from the same process is refused with "is attached through another endpoint" | `SDK server-shared/.../PorterCore.kt:80-110` | L1-SOURCE | Verified |
| P4-A114 | `connection.wrap(binder)` forwards **every** transaction on a system-service binder at the server's identity. A refusal arrives as the platform's `SecurityException`, **not** a `PorterException` | `sdk/.../PorterConnection.kt:309-321`; `docs/developers.md:267-269` | L1-SOURCE | Verified |
| P4-A115 | Forwarding to platform-internal interfaces such as `IPackageManager` "needs compile-time stubs and a way past the non-SDK interface restrictions, such as HiddenApiRefinePlugin and AndroidHiddenApiBypass" | `docs/developers.md:271-275` | L2-DOC | Documented |
| P4-A116 | "For file or process access, a shell command or your own service needs neither" — Porter's own docs prefer `exec`/`startProcess`/`userService` over hidden-API forwarding | `docs/developers.md:274-275` | L2-DOC | Documented |
| P4-A117 | The server itself runs through hidden APIs: the `porsh` loader is "Launched by app_process from the porsh script as `eu.darken.porter.shell.PorterShellLoader`" and imports `android.app.ActivityManagerNative`, `stub.dalvik.system.VMRuntimeHidden`, `rikka.hidden.compat.PackageManagerApis` | `PORTER shell/.../PorterShellLoader.kt:1-22` | L1-SOURCE | Verified |
| P4-A118 | `docs/troubleshooting.md:13` confirms the `WRITE_SECURE_SETTINGS` dependency for later automatic starts | `docs/troubleshooting.md:11-16` | L2-DOC | Documented |
| P4-A119 | On the Shizuku bridge, the service binder "answers with this app's grant, so it serves only its own process. A transaction that reaches it from another process … throws `SecurityException`" — including a `ShizukuBinderWrapper` transaction the app makes while handling another process's binder call, which requires `Binder.clearCallingIdentity()` around the transact | `docs/api-reference.md:168-178` | L1-SOURCE (doc in SDK repo) | Verified |
| P4-A120 | "A `ShizukuRemoteProcess` is `Parcelable`: sending it to another process gives that process access to the command." A genuine privilege-hand-off hazard the integrating app must avoid | `docs/api-reference.md:179` | L1-SOURCE (doc in SDK repo) | Verified |

---

## 14. Version compatibility of the integration

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A121 | "An app built against SDK 0.7.0 or a later `0.x` keeps working with a newer Porter, and a newer SDK does not require a newer Porter unless a release note says so. An app built against 0.1.0 does not connect to Porter 0.7.0 or newer" | `docs/developers.md:364-374` | L2-DOC | Documented |
| P4-A122 | "Compatibility is at source level only: a library compiled against an earlier `0.x` has to be recompiled against the new SDK." | `docs/developers.md:373-374` | L2-DOC | Documented |
| P4-A123 | While the SDK is `0.x`, "A minor release can add API and change behaviour this guide documents. Read the release notes before bumping." | `docs/developers.md:366-369` | L2-DOC | Documented |
| P4-A124 | The official guidance is to **pin an exact version rather than `+`**, "so your build does not move under you" | `docs/developers.md:36-38` | L2-DOC | Documented |
| P4-A125 | This project already depends on `dev.rikka.shizuku:api` / `:provider` **13.1.5**, whose client API version is 13 — the same `CLIENT_API_VERSION` the SDK speaks | `LOCAL gradle/libs.versions.toml:80,121`; `SDK shared/.../ShizukuProtocol.kt:28` | L1-SOURCE (both sides) | Verified |

---

## 15. The decisive compatibility finding — user services

| ID | Finding | Evidence location | Type | Capability label |
| --- | --- | --- | --- | --- |
| P4-A126 | **"User services (`bindUserService`, `peekUserService`, `unbindUserService`) and the manager-only calls throw `UnsupportedOperationException`."** — that is, on the Shizuku bridge | `docs/api-reference.md:164` (in `SDK` repo) | L1-SOURCE (doc in SDK repo) | Verified |
| P4-A127 | Corroborated by the developer guide: "Upstream's user services do not work through the bridge; use Porter's own." | `docs/developers.md:358` | L2-DOC | Documented |
| P4-A128 | **This project executes privileged work exclusively through a Shizuku user service.** `ShizukuUserServiceGatewayImpl.startShizukuUserService()` calls `Shizuku.bindUserService(args, connection)` with `.tag("shell_service").version(6).daemon(true)`, and 14 call sites obtain it via `findShizukuUserService()` | `LOCAL data/.../shizuku/gateway/ShizukuUserServiceGatewayImpl.kt:30-41`; 14 call sites across `application/`, `data/` | L1-SOURCE | Verified |
| P4-A129 | The project's own AIDL (`IShellService.aidl`, `IShellCallback.aidl`) is implemented by `ShizukuEndpointTemplate` running **inside** that user-service process, at the server's identity | `LOCAL data/src/main/aidl/IShellService.aidl`; `data/.../shizuku/server/ShizukuEndpointTemplate.kt` | L1-SOURCE | Verified |
| P4-A130 | Therefore: **the project's existing privileged-execution mechanism cannot be used on Porter through the Shizuku bridge.** It must be reimplemented against `connection.userService(UserServiceArgs(...))` and `connection.exec`/`startProcess` | P4-A126 + P4-A128 + P4-A129 | INFERENCE (cross-source, three independent inputs) | **Inferred** — but see `REPORT.md` §11.3 for why this is treated as near-verified |
| P4-A131 | The migration is not a drop-in: `UserServiceArgs` lacks `use32BitAppProcess`, requires `processNameSuffix`, and its `destroy` contract is a fixed aidl transaction id the service must implement itself | `sdk/.../UserServiceArgs.kt`; comparison with `Shizuku.UserServiceArgs` usage in P4-A128 | INFERENCE (cross-source) | Inferred |
| P4-A132 | An app keeping upstream's `dev.rikka.shizuku:api` and `:provider` "can use this SDK for Porter alone. Leave out `shizuku-compat` and keep upstream's `ShizukuProvider`" — so this project's existing dependency set is compatible **as a dependency**, and the conflict is purely in the calls it makes | `docs/developers.md:330-333` | L2-DOC | Documented |

---

## 16. Known limitations recorded by the sources

| ID | Limitation | Evidence location | Type |
| --- | --- | --- | --- |
| P4-A133 | No output cap and no built-in timeout in `exec`/`startProcess`; unbounded `String` results (P4-A52) | `sdk-extras/.../PorterShellResult.kt` | L1-SOURCE |
| P4-A134 | Java interop unsupported: "use from Java is not supported" | `docs/developers.md:41` | L2-DOC |
| P4-A135 | `dev.rikka.shizuku:provider` and `PorterShizukuApiProvider` cannot both be in one app, "including through another library", because they ship the same `moe.shizuku.api.BinderContainer` class; declaring the provider without that class on the classpath crashes the app **whether or not Porter or Shizuku is installed** | `docs/developers.md:316-320` | L2-DOC |
| P4-A136 | Shizuku-API-version floors bite twice: a server older than API 11 makes upstream switch to its old parcel layout permanently, after which "a `ShizukuBinderWrapper` transaction that reaches the bridge throws `IllegalStateException`" | `docs/api-reference.md:154` | L1-SOURCE (doc) |
| P4-A137 | Reconciliation supports servers of Shizuku API 13; with an **older** server running next to Porter, "a `ShizukuBinderWrapper` transaction built while upstream held that server's reply and delivered just after the bridge restored its own can be misread" | `docs/api-reference.md:154` | L1-SOURCE (doc) |
| P4-A138 | **Bridge env semantics differ from the Shizuku AIDL**: "An environment array for `newProcess` replaces the inherited environment, as with `Runtime.exec`. Entries that are not `NAME=VALUE`, or that start with `-`, are dropped." | `docs/api-reference.md:162` | L1-SOURCE (doc) |
| P4-A139 | When both Porter and Shizuku are installed, "the SDK uses Porter, even when it is stopped" | `docs/developers.md:322-323` | L2-DOC |
| P4-A140 | A refused service is not forwarded between processes: a secondary process fetching its connection this way stays `Disconnected`, and its `Porter.availability` "does not report `Incompatible` either" | `docs/developers.md:288-290` | L2-DOC |
| P4-A141 | Uninstalling Porter ends the app "if the user allowed it in Porter" (P4-A37); a process holding no Porter permission keeps running and switches to Shizuku once Porter's server has exited | `docs/developers.md:325-328` | L2-DOC |
| P4-A142 | Companion cannot coexist with Shizuku (P4-A11); "Removing it interrupts apps that need compatibility support; direct Porter apps keep working" | `docs/compatibility.md:37,43` | L2-DOC |
| P4-A143 | The companion "covers common Shizuku discovery methods. Apps that depend on specific Shizuku screens, internal components or much older APIs may need an update" | `docs/compatibility.md:51` | L2-DOC |
| P4-A144 | No stable application release tag exists; all four tags are pre-release (P4-A03) | `git tag` | L1-SOURCE |

---

## 17. Negative results

Recorded so they are not repeated.

| ID | Query | Result |
| --- | --- | --- |
| P4-D01 | Any ADB Module / `module.prop` subsystem anywhere in either Porter repository | **None** — confirms Phase 2's P2-C05 at the same commits (P4-A07) |
| P4-D02 | Any Porter-side equivalent of `module.prop`, `action.sh`, `service.sh`, WebUI or `window.Shizuku` | **None.** Per-token counts over `*.kt`, `*.java`, `*.md`, `*.xml` in `PORTER`: `module.prop` 0, `action.sh` 0, `webui` 0, `window.Shizuku` 0, `SHIZUKU_MODULE` 0. `service.sh` returned **1**, which was inspected and is a **false positive** — `service.showPermissionConfirmation(...)` in `ServiceAuthorizationTest.kt:1015`, where `service.sh` is a substring of `service.show…`. The same six tokens over the `SDK` repository also return 0 |
| P4-D03 | Any built-in script timeout, output cap or script-size cap in the SDK | **None** (P4-A52, P4-A69, P4-A133) |
| P4-D04 | Any Porter-side documentation promising start-on-boot | **None**; the opposite is documented (P4-A95, P4-A96) |
| P4-D05 | Any maximum supported Android version statement | **None** (P4-A20) |
| P4-D06 | Any native Porter implementation of the *Shizuku* `bindUserService` API | **None** — it throws `UnsupportedOperationException` (P4-A126) |
| P4-D07 | Any stable (non-`rc`/`beta`) Porter application tag | **None** (P4-A144) |
| P4-D08 | Any Porter-side analogue of `SHIZUKU_MODULE_*` environment variables | **None** (P4-D02) |

---

## 18. Experiments

**None performed.** No Android device or emulator is available, so nothing about
Porter's behaviour was exercised. Every row above is source-read or documentation-read
at the pinned commits. Claims that specifically require a device are collected in
`REPORT.md` §9 and in U-001's residual-risk block.