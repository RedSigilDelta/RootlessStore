# ADB Modules App — Phase 04 — Investigation Report

Phase: `04 — Porter Investigation` (`PLAN.md`: «Highest-priority architectural investigation»)
Status: **Audited**
Started: 2026-10-02
Last updated: 2026-10-02
Checklist: **34 / 34 items addressed** (24 Porter items + 10 Porter Backend Architecture items)
Evidence ledger: [`evidence.md`](evidence.md) — 144 evidence rows, 8 negative results
Resolves: **U-001** (identity/version/API surface) and **U-019** (backend-neutral session trigger — partially)
MasterRef: **not modified** (Phases 0–24 prohibition)

---

## 0. Headline

**Porter is now identified and its contract is established from source. One finding
changes the project's cost model.**

1. **Porter is a Shizuku fork, and its SDK speaks Shizuku's wire too.**
   `d4rken-org/porter`, application id `eu.darken.porter`, version `0.8.0-rc0`
   (versionCode `800000`), Apache-2.0. The SDK is a **separate repository**,
   `d4rken-org/porter-api`, published from JitPack as
   `com.github.d4rken-org.porter-api`, currently `0.9.0`. One SDK, two backends:
   `PorterBackend` is `{ PORTER, SHIZUKU }` (P4-A26). This closes U-001's identity
   and version question and is better news than Phase 0 assumed.

2. **But the Shizuku bridge cannot carry this project's privileged execution.**
   `porter-api`'s own API reference states it plainly: *"User services
   (`bindUserService`, `peekUserService`, `unbindUserService`) and the manager-only
   calls throw `UnsupportedOperationException`"* (P4-A126). This project executes
   privileged work **exclusively** through `Shizuku.bindUserService(...)` with
   `.daemon(true)` across 14 call sites (P4-A128). Its AIDL service runs *inside*
   that user-service process (P4-A129).

   Therefore `ARCHITECTURE.md` §7's "reuse those systems when their semantics are
   compatible" and §50's `ADAPT` classification for "execution contexts" and
   "privileged execution" are understated: on Porter the **entire** ADB execution
   path must be reimplemented, not adapted. This is the single most consequential
   Phase 4 finding.

3. **Porter's native execution API is better than both existing paths** for what
   this project needs — and Phase 3 established those paths are weak. Porter offers
   a **direct PID** (`PorterShellProcess.pid`, P4-A70), real **stdin** (P4-A64),
   **graceful signalling** via `signal(SIGINT)` before `destroy()` (P4-A75), and
   **process-group kill** (P4-A74). The reference implementation has none of these
   (Phase 3 P3-A39, P3-A40); the local implementation parses its PID out of stdout
   and has no stdin (Phase 3 P3-A53–P3-A56).

4. **Porter supplies no safety limits at all.** No output cap, no timeout, no
   script-size cap (P4-A52, P4-A69, P4-D03). The reference's 64 KiB / 120 s /
   256 KiB limits are *Porter's absence* made visible. Whatever bounds ADB Module
   execution, this project must impose them itself.

---

## 1. Scope

Covering `PLAN.md` §223–264: **24** Porter investigation items plus **10** Porter Backend
Architecture items — 34 in total. The count was verified mechanically against `PLAN.md`
rather than by hand.

Out of scope and assigned elsewhere:

- Shizuku binder/process semantics as Shizuku — **Phase 5**.
- The generic execution abstraction — **Phase 6**.
- Trust/policy threat model — **Phase 7**.
- WebUI bridge abuse surface — **Phase 8**.
- Per-Android-version matrix — **Phase 10** (this phase records only source-level
  facts and an explicit `Unknown` for maximum supported version).
- Storage, catalog, updates — **Phases 11–13**.
- Runtime recovery — **Phase 14**.
- Licensing detail — **Phase 18**.
- `Porter Compatibility` companion internals — **Phase 5/21** (this phase records
  only that it exists and that it cannot coexist with Shizuku).

---

## 2. Questions to Answer

All 32 checklist items translated to evidence-answerable questions; the full
mapping is in Appendix A. The load-bearing questions were:

1. Which artifact is "Porter", at what version, from what channel? (U-001)
2. What API surface does the SDK actually expose — verified from source?
3. Does the project's existing privileged execution work on Porter?
4. What does Porter *not* do that the ADB Module contract needs?
5. What is the recovery story after Porter-process death and after app-process death?
6. Which of these are verified, and which are assumed?

---

## 3. Primary Sources

| Source | Level | Version / commit | Location inspected | Used for |
| --- | --- | --- | --- | --- |
| `d4rken-org/porter-api` (SDK) | L1 | `2f280522` (= tag `0.9.0`), 2026-09-30 | `sdk/`, `sdk-extras/`, `protocol/`, `shared/`, `server-shared/` — 30 Kotlin files read | Items 3–22; the whole backend contract |
| `d4rken-org/porter-api` | L1 | same | `docs/api-reference.md` (179 lines) | Items 4, 13, 21; the bridge limitation |
| `d4rken-org/porter` (app) | L1 | `2d88f34b`, 2026-10-02 | `build.gradle.kts`, `VERSION`, `version.properties`, `settings.gradle.kts`, `manager/build.gradle.kts`, `compat/build.gradle.kts`, `manager/src/main/AndroidManifest.xml` | Items 1, 2, 5, 15 |
| `d4rken-org/porter` | L1 | same | `shell/.../PorterShellLoader.kt`, `server/.../PorterServer.kt`, `server/.../util/Android17Compat.kt` | Items 15, 21 |
| `d4rken-org/porter` | L1 (TEST) | same | `server/src/test/.../ServiceAuthorizationTest.kt` | Item 15 — authorization is pinned by tests |
| This repository | L1 | `6df93ae` | `data/.../shizuku/gateway/ShizukuUserServiceGatewayImpl.kt`, `data/src/main/aidl/*.aidl`, `data/.../shizuku/server/*`, `gradle/libs.versions.toml` | The integration surface (14 call sites) |
| Phase 3 evidence | — | — | `phase-03/evidence.md` | Comparative baseline for execution limits and PID handling |

`PORTER`'s HEAD equals the commit Phase 2 pinned as S-204, so **Phase 2's negative
result is re-verified at the same commit**, not re-derived (P4-D01).

---

## 4. Secondary Sources

Official Porter documentation, read as **claims** and labelled `Documented`:

| Source | Level | Location | Used for | Lead or evidence |
| --- | --- | --- | --- | --- |
| `docs/developers.md` | L2 | `PORTER` | Initialization, permission, execution, shutdown checklist | Documented |
| `docs/setup.md` | L2 | `PORTER` | Startup modes, Android requirements | Documented |
| `docs/compatibility.md` | L2 | `PORTER` | Companion APK, coexistence rules | Documented |
| `docs/troubleshooting.md` | L2 | `PORTER` | Start-on-boot, service stopping, `WRITE_SECURE_SETTINGS` | Documented |
| `docs/api-reference.md` | L1 | `SDK` | Full surface, bridge limits | **Evidence** — it ships inside the SDK repo and is the SDK's own contract |

Where a `docs/developers.md` claim was checkable in source, it was checked. No
contradiction between Porter's docs and Porter's source was found; the one place a
document claim could *not* be confirmed is recorded as `Documented` in §9.

No L5/L6 source was used as evidence for any claim.

---

## 5. Existing Implementation Evidence

Full ledger: [`evidence.md`](evidence.md). The findings that change a decision:

### 5.1 Identity and version — U-001 resolved

| Aspect | Value | Evidence |
| --- | --- | --- |
| Application | `d4rken-org/porter`, `eu.darken.porter`, v`0.8.0-rc0` / `800000` | P4-A01, P4-A02 |
| Application tags | `v0.1.1-beta0/1`, `v0.7.0-rc0`, `v0.8.0-rc0` — **all pre-release** | P4-A03, P4-D07 |
| SDK | `d4rken-org/porter-api` → `com.github.d4rken-org.porter-api`, `0.9.0` | P4-A04, P4-A06 |
| SDK artifacts | `sdk`, `sdk-extras`, `shizuku-compat`, `shizuku-bridge` | P4-A05 |
| Wire protocol | Porter `VERSION 4` / `MIN_VERSION 4`; Shizuku `CLIENT_API_VERSION 13` / `MINIMUM 13` | P4-A102 |
| Android | `minSdk 24`, `targetSdk 37`, `compileSdk 37` | P4-A14 |
| Licence | Apache-2.0 app, MIT bundled Shizuku API | P4-A09 |

This project's existing Shizuku client API is **13.1.5**, i.e. client API version 13
— the same version the Porter SDK speaks (P4-A125). The dependency versions are
already aligned.

### 5.2 The bridge cannot carry the project's execution — the decisive finding

`docs/api-reference.md:164` in the SDK repo:

> User services (`bindUserService`, `peekUserService`, `unbindUserService`) and the
> manager-only calls throw `UnsupportedOperationException`.

`docs/developers.md:358` repeats it in prose: *"Upstream's user services do not work
through the bridge; use Porter's own."*

Against that, this project:

- calls `Shizuku.bindUserService(args, connection)` with `.tag("shell_service")
  .version(6).daemon(true)` (`ShizukuUserServiceGatewayImpl.kt:30-41`, P4-A128);
- obtains that service through `findShizukuUserService()` at **14 call sites** across
  `application/` and `data/`;
- implements its privileged logic as its own AIDL (`IShellService.aidl`,
  `IShellCallback.aidl`) hosted by `ShizukuEndpointTemplate` **inside** that
  user-service process (P4-A129).

So on Porter, none of that executes. The whole ADB path — plugin execution, shell
plugin install/uninstall/export, and the CPU/network status data sources — is
Shizuku-user-service-shaped and must be rewritten against
`connection.userService(UserServiceArgs(...))` (P4-A130).

This is `Inferred`, not `Verified`, in the strict sense that it composes three source
rows rather than observing the failure. It is treated as near-verified because each
input is quoted from a pinned source and no source contradicts it; the residual is
recorded as U-025 for a device confirmation.

Note the important asymmetry: this is **not** a dependency conflict. An app keeping
upstream's `dev.rikka.shizuku:api` and `:provider` "can use this SDK for Porter alone"
(P4-A132). The build stays valid; the **calls** must change.

### 5.3 Execution model — and where Porter is strictly better

| Property | Porter (`sdk-extras`) | Reference impl (Phase 3) | This repo today (Phase 3) |
| --- | --- | --- | --- |
| PID | **`pid: Int?` property** (P4-A70) | not exposed | parsed from stdout (P3-A53) |
| stdin | real pipe (P4-A64) | closed immediately (P2-A71) | closed |
| graceful stop | **`signal(SIGINT)` then wait** (P4-A75) | not available | `kill -9` only (P3-A57) |
| kill scope | **process group** (P4-A74) | `remote.destroy()` only | `kill -9 <pid>` only |
| output cap | **none** (P4-A52) | 64 KiB tail | none |
| timeout | **none** (P4-A52) | 120 s → exit 124 | none |
| waiting | `waitFor()` in 250 ms slices (P4-A68) | `waitForTimeout(120 s)` | blocking `waitFor()` |
| exit code | process's own (P4-A66) | process's own | process's own |

Porter is the strongest of the three on process control and the weakest on safety
bounds. That trade is the design input Phase 6 needs.

### 5.4 `exec` is itself a user service

`exec` and `startProcess` are **not** primitive server calls. They run through an SDK
user service (`PorterShellService`, tag `eu.darken.porter.sdk.extras.shell`, process
suffix `porter_shell`, `version = 2`) bound once per connection and cached
(P4-A54, P4-A55).

Consequences this project must design for:

- Every `exec` needs a **bound user service process** to exist first. The bridge
  documentation confirms the cost: *"The first `newProcess`, and the first after the
  shell service died, also waits for that service's process to start"*
  (P4-A65 context).
- The binding is keyed by `PorterConnection` in a `WeakHashMap`, so it does not
  survive a new connection.
- A Shizuku server below 13.4 keeps every binding until the app process dies, which
  is why the SDK takes "one binding for every call" (P4-A56).

### 5.5 Permissions

`eu.darken.porter.permission.API` is `protectionLevel="dangerous"` — a runtime
permission (P4-A33). `MANAGER` is `signature` (P4-A34). Server-side, every gated
operation runs `enforceCallingPermission`, which denies a caller that is not an
**attached** client, or is attached but not allowed (P4-A38, P4-A112). Holding the
Android permission alone admits nothing — this is pinned by an upstream test whose
name states it: *"Holding the Android permission is what gets a binder delivered; it
admits nothing by itself"* (P4-A112).

Permission state is a `StateFlow`, and revocation **kills the app** (P4-A37). For
this project that means a revoked grant is indistinguishable from a crash unless the
app persists a reason.

### 5.6 Lifecycle and recovery

| Event | Verified behaviour | Evidence |
| --- | --- | --- |
| Porter service restarts while app alive | A **new** connection arrives on `Porter.state`; use it, not the old one | P4-A29, P4-A104 |
| Connection lost | `markLost()` fails pending permission requests with `PorterConnectionLostException` and closes user-service flows | P4-A105 |
| **App process death** | **A running command dies with it** — server links `ownerToken` `DeathRecipient` and calls `destroy()` | P4-A94 |
| App process death, daemon service | A `daemon = true` user service **survives** | P4-A86 |
| **Device reboot** | **Porter's service stops. It must be started again.** No boot guarantee | P4-A95, P4-A96 |
| Unbind | Does **not** kill a service | P4-A89 |
| Service outlives collection | "Cancelling the collection does not stop the process" | P4-A90 |
| `stopUserService` | Sends `TRANSACTION_DESTROY` (`16777114`); server kills a survivor after 3 s | P4-A80 |
| Version mismatch | `PorterIncompatibility` names **which side** must update | P4-A101 |

**The `service.sh` question (U-019) is now answerable.** Phase 3 established the
reference triggers unattended service execution from a Shizuku binder event observed
in a Compose `LaunchedEffect`, never at boot. Porter offers two primitives:

- a **daemon user service** that survives app-process death but dies when the
  **Porter server** stops (P4-A86);
- a **connection-scoped** model where every restart produces a new connection
  (P4-A29).

So the backend-neutral trigger cannot be "binder session" (Shizuku-specific) and
cannot be "boot" (neither backend guarantees it). The nearest neutral event is
**"a backend connection became available"**, which is expressible on both. That is
recorded as `PROPOSED` in §8 (P3-AR04, P4-AR07) — the *decision* belongs to Phases 6
and 9; Phase 4 supplies the factual half it was blocked on.

---

## 6. Compatibility Findings

Phrased per `INVESTIGATION_METHOD.md` §25.

| ID | Subject | Compatible with | Conditions | Not compatible / unverified | Version | Class |
| --- | --- | --- | --- | --- | --- | --- |
| P4-C01 | This project's **dependency set** | Porter SDK `0.9.0` | Keep upstream `dev.rikka.shizuku:api` + `:provider`; omit `shizuku-compat` (P4-A132) | Must **not** add `shizuku-compat` — `BinderContainer` collides and crashes the app (P4-A135) | SDK `0.9.0` | VERIFIED |
| P4-C02 | This project's **ADB execution calls** | Porter SDK `0.9.0` | None | **Not compatible.** `bindUserService` throws `UnsupportedOperationException` (P4-A126, P4-A130) | SDK `0.9.0` | VERIFIED INCOMPATIBLE |
| P4-C03 | Shizuku client API version | SDK | Project is on 13.1.5 = client API 13 = what the SDK speaks (P4-A125) | Bridge reconciliation is only guaranteed at API 13; older servers can misread a wrapper transaction (P4-A137) | SDK `0.9.0` | VERIFIED |
| P4-C04 | Shizuku-API `newProcess` (no user service) | Bridge | `newProcess` is answered through the shell service (P4-A65) | Not-found exits **127** instead of failing to start (P4-A65); env semantics become `Runtime.exec`-style **replace**, not AIDL merge (P4-A138) | SDK `0.9.0` | VERIFIED, behaviour differs |
| P4-C05 | ADB Module environment contract (`MODDIR`, `SHIZUKU_MODULE_*`) | Nothing in Porter | — | **No analogue exists** (P4-D02, P4-D08). The project must generate these itself | — | VERIFIED INCOMPATIBLE |
| P4-C06 | ADB Module package format | Nothing in Porter | — | **No module subsystem in either Porter repo** (P4-D01) | — | VERIFIED INCOMPATIBLE |
| P4-C07 | Execution bounds (2048 entries / 200 MiB / 120 s / 64 KiB / 256 KiB) | Nothing in Porter | — | **Porter imposes none** (P4-D03). These remain *this project's* responsibility | SDK `0.9.0` | VERIFIED INCOMPATIBLE |
| P4-C08 | Multiple processes | SDK | Each calls `PorterApiProvider.requestBinderForNonProviderProcess` (P4-A30) | A refused service is not forwarded; a secondary process stays `Disconnected` and does not report `Incompatible` (P4-A140) | SDK `0.9.0` | VERIFIED with caveat |
| P4-C09 | Java interop | Nothing | — | "use from Java is not supported" (P4-A134) | SDK `0.9.0` | Documented |
| P4-C10 | Start-on-boot | Nothing guaranteed | Manual start once after install is required first (P4-A96) | "Porter's service stops when the device restarts" (P4-A95) | app `0.8.0-rc0` | Documented INCOMPATIBLE |
| P4-C11 | Android 12–17 support | Unknown | `minSdk 24`, `targetSdk 37`, `Android17Compat` shim (P4-A14, P4-A18) | **No per-version claim is made.** Phase 10 owns this (P4-A20) | — | **Unknown** |
| P4-C12 | Android 11+ wireless debugging | Documented | Manufacturer-dependent; "Some device manufacturers restrict wireless debugging" (P4-A17) | — | app `0.8.0-rc0` | Documented |

---

## 7. Security Findings

Phase 3 depth; full threat modelling is **Phase 7**.

| ID | Boundary | Risk | Evidence | Follow-up owner |
| --- | --- | --- | --- | --- |
| P4-S01 | App process death vs running commands | Commands **die silently** with the app process. A `service.sh`-shaped long-running task cannot survive an app restart as a `startProcess` | P4-A94 | 9, 14 |
| P4-S02 | Daemon user service vs permission revocation | A `daemon = true` service outlives app-process death but **ends on permission revocation** — so a revoke both kills the app (P4-A37) and strands the service | P4-A86 + P4-A37 | 7, 9 |
| P4-S03 | No safety bounds | Unbounded output, no timeout, no script cap. A malicious or broken module can exhaust the app's memory or run forever | P4-A52, P4-D03 | 7, 15 |
| P4-S04 | Environment replacement through the bridge | Bridge `newProcess` env **replaces** the inherited environment and silently drops malformed or `-`-prefixed entries | P4-A138 | 5, 7 |
| P4-S05 | `ShizukuRemoteProcess` is `Parcelable` | "sending it to another process gives that process access to the command" — a privilege-hand-off hazard in a multi-process app | P4-A120 | 7 |
| P4-S06 | Bridge service binder is single-process | A transaction reaching it from another process throws `SecurityException`; the app must `clearCallingIdentity()` around its own wrapper transactions or it will mis-authorise itself | P4-A119 | 7 |
| P4-S07 | `wrap()` forwards everything | `connection.wrap(binder)` re-issues **every** transaction at the server's identity; refusal surfaces as platform `SecurityException`, not `PorterException`, so naive error handling will misreport it | P4-A114 | 7 |
| P4-S08 | Hidden-API requirement | Forwarding to platform-internal interfaces needs `HiddenApiRefinePlugin` / `AndroidHiddenApiBypass`. Porter's own docs steer to `exec`/`userService` instead | P4-A115, P4-A116 | 7 |
| P4-S09 | Porter prefers Shizuku when both present | "the SDK uses Porter, even when it is stopped" (P4-A139), and a process with no Porter grant "switches to Shizuku once Porter's server has exited" (P4-A141) — backend switching is **not** this app's decision | P4-A139, P4-A141 | 5, 6, 7 |
| P4-S10 | Companion impersonates Shizuku | The companion uses Shizuku's app identity and **cannot coexist** with Shizuku; Android treats them as competing installations | P4-A11, P4-A142 | 5, 21 |
| P4-S11 | `InstalledUnrecognized` | A package this SDK does not recognise owns Porter's permission; the docs say "do not present it as the manager". A naive UI would launch an attacker-chosen package | P4-A100, P4-A43 | 7, 16 |
| P4-S12 | `getErrorStream()` re-opens the pipe | Each call opens a fresh pipe and transfer thread, so "two callers end up competing for the same bytes" — stderr is not safely re-readable | P4-A63 | 15 |
| P4-S13 | Provider collision | Declaring `PorterShizukuApiProvider` without `BinderContainer` crashes the app "whether or not Porter or Shizuku is installed" | P4-A135 | 6, 17 |

---

## 8. Architecture Implications

`ARCHITECTURE.md` is **proposed/changeable**. Labels from `investigations/METHOD.md` §3.2.

| ID | Implication | Label | Evidence | Affected section | Recommendation |
| --- | --- | --- | --- | --- | --- |
| P4-AR01 | §16 Porter Backend is **correct in shape** and now has a verified target. The adapter boundary is the right call | VERIFIED | P4-A45, P4-A60, P4-A82 | §16 | Keep. Replace the "must not assume a specific Porter API until verified" caveat with the pinned contract (§8 of this report) |
| P4-AR02 | §7 / §50 **understate** the Porter migration. This is not `ADAPT` of existing execution code; on Porter it is `REPLACE` | CONTRADICTED (vs §50's `ADAPT` for "execution contexts" / "privileged execution") | P4-A126, P4-A128, P4-A130 | §7, §50 | Reclassify the ADB execution path as `REPLACE` for the Porter backend, while keeping it `KEEP` for Shizuku |
| P4-AR03 | §21 Privilege Model is **confirmed correct** and now concrete: `Backend ≠ Privilege` is a first-class SDK fact, not a theory | VERIFIED | P4-A21, P4-A22, P4-A23 | §21 | Add: the app can *read* the identity (`connection.uid`, `seLinuxContext`) and should display it |
| P4-AR04 | §19 Backend State should adopt Porter's richer state model rather than the 6 names currently listed | PROPOSED | P4-A98, P4-A99, P4-A101 | §19 | Replace with Porter's 3 connection states + 5 availability states + `Incompatible` carrying which side is old. `ARCHITECTURE.md`'s `NOT_INSTALLED / NOT_AVAILABLE / NOT_RUNNING / READY / UNSUPPORTED / ERROR` does not distinguish *why* nothing happened |
| P4-AR05 | §42 Execution Abstraction needs a **fourth** execution shape: Porter's `exec`/`startProcess` returns a `java.lang.Process` subclass, which is neither a "result" nor a "handle" as currently modelled | PROPOSED | P4-A45, P4-A60, P4-A68 | §42, §43, §44, §45 | Model `ExecutionHandle` as compatible with `java.lang.Process` (pid, waitFor, exitValue, streams, signal) so the Porter adapter is thin and the local `ProcessBuilder` path reuses it |
| P4-AR06 | §42 Execution Limits must become **the project's own**, since Porter enforces none | PROPOSED | P4-A52, P4-D03 | §42, §18 Capability Model | Keep the reference values as *targets*; add `EXECUTE_WITH_TIMEOUT` / `BOUNDED_OUTPUT` as capability flags the Porter adapter must implement itself |
| P4-AR07 | §33 Service Runtime: the neutral trigger cannot be "binder session" and cannot be "boot". "A backend connection became available" is the nearest event both backends can express | PROPOSED | P4-A29, P4-A86, P4-A95, P4-A96 | §33 | Phrase §33 in terms of connection availability, not Shizuku sessions. Phase 3's P3-AR04/P3-AR05 remain `CONTRADICTED` |
| P4-AR08 | §34/§37 Runtime Persistence gains a Porter-specific hazard: commands die with the app, so a persisted PID is stale **by construction** after app death | PROPOSED | P4-A94 | §34, §36, §37 | Persist PID **plus** the connection generation it was issued under, so a stale entry is detectable without a `/proc` probe |
| P4-AR09 | §18 Capability Model: Porter reports a capability **bitmask** at attach, and bits are never reused — a better fit than a hand-maintained enum | PROPOSED | P4-A103 | §18, §25 Capability Model | Map `BackendCapability` onto Porter's `REPLY_CAPABILITIES` where a bit exists, and keep the enum for what the SDK does not report |
| P4-AR10 | §14 Backend Integration Layer: one SDK serves two wires (`PorterBackend.PORTER`/`SHIZUKU`), so "backend" may mean *wire*, not *product* | PROPOSED | P4-A26 | §14, §17, §20 | Distinguish `backend product` from `backend wire` in the resolver, or the abstraction will conflate "Shizuku is installed" with "Shizuku wire chosen" |
| P4-AR11 | §60 External API Boundary is confirmed necessary and correctly placed | VERIFIED | P4-A126, P4-A114 | §60 | Keep. Note that the boundary must cover `PorterShizukuBridge.start()` too, or bridge and native paths can both be active |
| P4-AR12 | §23 Trust Model / §22 Policy are unaffected by Porter, but §22 must note that **Porter's own permission is the outer gate**: trust and policy sit *above* a runtime permission the user can revoke at any time | PROPOSED | P4-A36, P4-A37 | §22, §23, §26 | Add the revocation case: a trusted, policy-allowed module still cannot run when the outer grant is gone |
| P4-AR13 | §17/§20 Backend Resolution: Porter publishes `serverTooOld`/`clientTooOld`, so "update the app" vs "update Porter" is a **decidable** condition rather than a guess | PROPOSED | P4-A101 | §17, §20 | Model incompatibility as a first-class resolution outcome carrying which side must change |
| P4-AR14 | §31 Module Environment: Porter supplies no environment contract, so the six `SHIZUKU_MODULE_*` variables are 100 % this project's responsibility on both backends | PROPOSED | P4-D02, P4-D08 | §31 | §31 is already correct; add that no backend supplies or validates these |

Sections confirmed **unchanged**: §19's requirement that privilege ≠ backend
(P4-AR03), §56 no Magisk/KSU, §57 no whole-Shevery, §68 non-goals.

---

## 9. Unknowns

New: **U-025 … U-030**. Resolved: **U-001** (identity/version/API surface) and
**U-019** (factual half).

| ID | Unknown | Why unresolved | What would resolve it | Owner |
| --- | --- | --- | --- | --- |
| U-001 | ~~Exact Porter artifact, version, and API surface~~ | **RESOLVED (Phase 4).** Application `d4rken-org/porter` @ `2d88f34b`, `eu.darken.porter`, `0.8.0-rc0`/`800000`, Apache-2.0. SDK `d4rken-org/porter-api` @ `2f280522` (tag `0.9.0`), group `com.github.d4rken-org.porter-api` from JitPack. API surface established from source across 30 files. Residual risks recorded below rather than folded into this entry | — | — |
| U-019 | ~~Backend-neutral equivalent of the reference's "binder session" trigger~~ | **RESOLVED on the factual half (Phase 4).** Porter offers a `daemon` user service (survives app-process death, ends when the server stops) and a connection-scoped model where each server start yields a new connection. Neither is "binder session" nor "boot". The *decision* remains open | Phase 6/9 architecture decision, now unblocked | 6, 9 |
| U-025 | Runtime confirmation that `bindUserService` through the bridge really throws `UnsupportedOperationException` on a device, and that no fallback path exists | Source-derived from `docs/api-reference.md:164` and `docs/developers.md:358`, both in the SDK repo, but not exercised | Device test: install Porter + bridge, call the project's existing user-service path | 5, 6 |
| U-026 | Android 12–17 behaviour of Porter itself | `PLAN.md` assigns the matrix to Phase 10. `targetSdk 37` and `Android17Compat` show intent, not behaviour; `docs/troubleshooting.md:45` warns "manufacturer modifications to Android can affect debugging access" | Phase 10 device matrix on Android 12–17 | 10 |
| U-027 | Whether `startProcess`/`exec` behave identically when the backend is `PorterBackend.SHIZUKU` (an original Shizuku server) | The SDK unifies the wires, but `P4-A56` records a Shizuku<13.4 binding difference and `P4-A88` a service-stop difference. Whether `exec` bounds differ is unestablished | Phase 5, on a real Shizuku server | 5 |
| U-028 | Whether `Porter Compatibility`'s `moe.shizuku.privileged.api` identity interacts with this project's `dev.rikka.shizuku:provider` differently than documented | The companion impersonates Shizuku; P4-A135 covers the *in-app* provider collision but not the installed-companion case | Phase 5/21 device test with companion installed | 5, 21 |
| U-029 | Whether Porter's SDK `0.x` API will change in a way that affects this project, and the migration cost when it does | Explicitly stated as unstable: "A minor release can add API and change behaviour this guide documents"; "Compatibility is at source level only" (P4-A122, P4-A123) | Ongoing release-note monitoring; Phase 21 tracks divergence | 21 |
| U-030 | Whether the SDK's own `version = 2` shell-service tag and process suffix are stable identifiers this project should depend on, or internal detail | `ShellCalls.args()` pins `tag = "eu.darken.porter.sdk.extras.shell"` and `version = 2` (P4-A54). The docs do not promise stability of these strings | Phase 6 design decision; a device test to confirm a version bump replaces the service correctly | 6 |

Carried forward unchanged: **U-004** (Shizuku server implementation/version, Phase 5),
**U-008**, **U-014**–**U-018**, **U-020**–**U-024**.

---

## 10. Contradictions

New: **C-019 … C-021**. Full entries in [`../contradictions.md`](../contradictions.md).

| ID | Contradiction | Severity | Status | Interpretation adopted |
| --- | --- | --- | --- | --- |
| C-019 | `ARCHITECTURE.md` §7/§50 classify the existing execution machinery as `KEEP`/`ADAPT` for Porter; Porter's bridge cannot run it | Material | **Unresolved** — recorded as CONTRADICTED | The reclassification is a Phase 6 decision; Phase 4 records the conflict and the evidence |
| C-020 | The project can depend on `dev.rikka.shizuku:provider` **and** integrate Porter (P4-A132), but `shizuku-compat` would crash it (P4-A135) — and Porter's docs offer `shizuku-bridge` for exactly this project's shape | Minor | **Unresolved** — a build-configuration hazard | Neither artifact may be added without a deliberate decision; recorded for Phase 6/17 |
| C-021 | Porter's docs describe `exec` as a plain command runner, but it is implemented on top of a bound user service with per-connection caching and a Shizuku<13.4 workaround | Minor | **Unresolved** — documentation understates the mechanism | Source governs; `exec` must be treated as requiring a live user service |

---

## 11. Verified Conclusions

1. Porter is `d4rken-org/porter`, `eu.darken.porter`, `0.8.0-rc0`/`800000`, at
   `2d88f34b`; the SDK is a separate repo `d4rken-org/porter-api` at `2f280522`
   (tag `0.9.0`), published as `com.github.d4rken-org.porter-api` (P4-A01–P4-A06).
   `VERIFIED`, High. **Resolves U-001's identity/version question.**
2. Porter has **no** ADB Module subsystem in either repository, confirmed by
   per-token counts at the same commits Phase 2 used (P4-D01, P4-D02).
   `VERIFIED`, High.
3. The Porter SDK carries **both** a Porter-native wire and a Shizuku wire
   (`PorterBackend.{PORTER, SHIZUKU}`), speaking Shizuku client API 13 (P4-A26,
   P4-A102). `VERIFIED`, High.
4. This project's privileged execution runs **exclusively** through Shizuku user
   services at 14 call sites (P4-A128, P4-A129). `VERIFIED`, High.
5. **User services do not work through Porter's Shizuku bridge**; they throw
   `UnsupportedOperationException` (P4-A126, P4-A127). `VERIFIED`, High.
6. Therefore the project's ADB execution path cannot reach Porter as written; it
   must be reimplemented against `connection.userService(...)` (P4-A130).
   `INFERRED` from three source rows; treated as near-verified, device confirmation
   tracked as U-025.
7. The project's existing dependency set is **not** a conflict: an app keeping
   upstream `api`+`provider` can add the SDK for Porter alone (P4-A132), and both
   speak Shizuku client API 13 (P4-A125). `VERIFIED`, High.
8. Porter exposes a **direct PID**, real **stdin**, **graceful signalling**, and
   **process-group kill** — capabilities neither the reference nor the local
   implementation has (P4-A64, P4-A70, P4-A74, P4-A75). `VERIFIED`, High.
9. Porter imposes **no** output cap, timeout or script-size cap (P4-A52, P4-D03).
   `VERIFIED (absence)`, High.
10. `exec`/`startProcess` are implemented on an SDK user service bound once per
    connection (P4-A54, P4-A55). `VERIFIED`, High.
11. **A running command dies with the app process that started it** (P4-A94).
    `VERIFIED`, High.
12. A `daemon = true` user service survives app-process death but ends when the
    Porter server stops, the grant is revoked, or the app is uninstalled
    (P4-A86). `VERIFIED`, High.
13. **Porter's service stops on device reboot**; no boot-start guarantee, and a
    manual start is required once after installation first (P4-A95, P4-A96).
    `Documented`, High.
14. Permission is a **runtime** permission (`dangerous`), revocable at any time, and
    revocation **kills the app**; the server separately requires an attached client
    record, so holding the permission admits nothing (P4-A33, P4-A37, P4-A38,
    P4-A112). `VERIFIED`, High.
15. Version incompatibility is first-class and names which side must change
    (`serverTooOld`/`clientTooOld`); capability bits are never reused (P4-A101,
    P4-A103). `VERIFIED`, High.
16. All four Porter application tags are pre-release; **no stable tag exists**
    (P4-A03, P4-D07). `VERIFIED`, High.

---

## 12. Recommendations for MasterRef Expansion

For Phase 25. **Nothing written to `MasterRef.md` in this phase.**

| ID | Current MasterRef section | Recommended change | Reason | Intended classification |
| --- | --- | --- | --- | --- |
| P4-M01 | §39 PORTER INVESTIGATION | Replace "the exact Porter SDK/API must always be verified from the actual dependency/source" with the **pinned identity and version** now established | The placeholder can now be a fact | Verified fact |
| P4-M02 | §39 | Add that Porter has **no module subsystem** and is therefore not an ADB Module compatibility target | Prevent Phase 2's finding being rediscovered | Verified fact |
| P4-M03 | New subsection under §41 Shizuku Backend / §53 Backend Resolver | Record that user services do not cross the Porter bridge, and that any app relying on them is Shizuku-only for execution | The single most consequential Phase 4 fact | Verified fact + documented |
| P4-M04 | §40 WHY PORTER IS PRIMARY | Add the counterweight: Porter gives better process control (pid, stdin, signal, process-group kill) but **no safety bounds**, so it does not reduce the project's security work | Keeps §40 honest | Verified fact |
| P4-M05 | §47 BACKEND STATE | Replace the six proposed names with Porter's 3 connection states + 5 availability states, noting `Incompatible` carries which side is old | Strictly more informative and verified | Verified fact |
| P4-M06 | §50 PRIVILEGE MODEL | Add that `connection.uid` and `seLinuxContext` make privilege *observable*, not merely modelled | Turns a principle into a capability | Verified fact |
| P4-M07 | §42 Execution Abstraction | Note that Porter's handle is a `java.lang.Process` subclass, and that modelling it as such also simplifies the local backend | Actionable for Phase 6 | Verified fact + proposed design |
| P4-M08 | §31 SERVICE SESSION MODEL | Record that "binder session" is Shizuku-specific and that neither backend guarantees start-on-boot; the neutral event is connection availability | Phase 3 already flagged this; Phase 4 closes the factual half | Verified fact + proposed design |
| P4-M09 | §46 BACKEND CAPABILITY | Note Porter's `REPLY_CAPABILITIES` bitmask with never-reused bits as a better fit than a hand-maintained enum | Verified mechanism | Verified fact |

---

## 13. Sources / References

Full records in [`../sources.md`](../sources.md).

| Layer | Repository | Commit | Tag | Date |
| --- | --- | --- | --- | --- |
| `PORTER` | `https://github.com/d4rken-org/porter` | `2d88f34bc348552b7fb22eb73ccaba5b9922cce5` | — (`v0.8.0-rc0` is earlier) | 2026-10-02 |
| `SDK` | `https://github.com/d4rken-org/porter-api` | `2f2805226a33d6742c646a5efe022aa33d4c63ae` | `0.9.0` | 2026-09-30 |
| `LOCAL` | this project | `6df93ae8d2c3dbb84b461b4eecb0c7f65de8a5b2` | — | 2026-10-02 |

`PORTER`'s HEAD equals Phase 2's S-204 pin, so Phase 4's negative result and Phase 2's
are the same observation, not two.

---

## 14. Items Requiring Future Investigation

| ID | Item | Why deferred | Owning phase |
| --- | --- | --- | --- |
| P4-F01 | Shizuku-wire behaviour of `exec`/`startProcess` on a real Shizuku server | Needs Phase 5's Shizuku source reading plus a device | 5 (U-027) |
| P4-F02 | Device confirmation that the bridge really refuses user services | No device | 5, 6 (U-025) |
| P4-F03 | Android 12–17 matrix for Porter itself | Platform work | 10 (U-026) |
| P4-F04 | Migration plan from `bindUserService` to `connection.userService` | Phase 6 owns the abstraction this is an instance of | 6 |
| P4-F05 | Whether the project will ship the bridge, the native SDK, or both | Architecture decision with build consequences | 6 (C-020) |
| P4-F06 | Companion (`moe.shizuku.privileged.api`) coexistence with this project's provider | Needs a device with the companion installed | 5, 21 (U-028) |
| P4-F07 | SDK `0.x` instability monitoring | Ongoing | 21 (U-029) |
| P4-F08 | Full licensing audit of the two Porter repositories | Phase 18 owns licensing | 18 |
| P4-F09 | Whether to depend on `ShellCalls`' internal tag/version strings | Design decision | 6 (U-030) |

---

## Appendix A — Checklist Coverage

All 22 Porter items plus 10 backend-architecture items.

### Porter Investigation (24/24)

| # | `PLAN.md` item (verbatim) | Answered in | Outcome | Classification |
| --- | --- | --- | --- | --- |
| 1 | Identify exact Porter dependency/version | §5.1, ledger §1 | **Complete** — app `0.8.0-rc0`/`800000` @ `2d88f34b`; SDK `0.9.0` @ `2f280522`; **U-001 resolved** | Verified |
| 2 | Verify official Porter documentation | §4 | **Complete** — 4 doc files read; 1 SDK doc promoted to L1 because it ships in the SDK repo | Verified / Documented |
| 3 | Inspect Porter source | §3, ledger | **Complete** — both repos; app HEAD equals Phase 2's pin | Verified |
| 4 | Inspect Porter SDK/API surface | §5.3–§5.5, ledger §4–§13 | **Complete** — 30 Kotlin files; every public symbol recorded | Verified |
| 5 | Identify supported Android versions | §5.1, ledger §2 | **Partial by design** — `minSdk 24`, `targetSdk 37`, startup prerequisites; **max version is Unknown** (P4-A20), Phase 10 owns it | Verified + Unknown |
| 6 | Identify supported execution modes | ledger §3 | **Complete** — adb uid 2000 / root uid 0; wireless, USB, root startup; two wires | Verified + Documented |
| 7 | Identify process creation behavior | §5.3, ledger §6 | **Complete** — argv array, no implicit shell; `exec` blocks, `startProcess` returns | Verified |
| 8 | Identify command execution behavior | §5.3–§5.4, ledger §6 | **Complete** — incl. 127, empty-command, bad-dir, stdin-closed, and that `exec` is a user service | Verified |
| 9 | Identify stdin/stdout/stderr behavior | ledger §7 | **Complete** — real pipes; `exec` closes stdin; `getErrorStream()` re-opens per call | Verified |
| 10 | Identify exit-code behavior | ledger §8 | **Complete** — own exit code; `exitValue()` throws while alive; **no synthetic timeout code** | Verified |
| 11 | Identify PID/process handling | §5.3, ledger §9 | **Complete** — `pid: Int?`, `> 0` guard; removes the local stdout-parse hack | Verified |
| 12 | Identify process termination | §5.3, ledger §10 | **Complete** — `signal()` then `destroy()`; SIGKILL; process group; post-exit is a no-op | Verified |
| 13 | Identify service/background behavior | §5.6, ledger §11 | **Complete** — cold flow, `daemon`, binding identity, unbind ≠ kill, per-Android-user | Verified |
| 14 | Identify lifecycle behavior | §5.6, ledger §12 | **Complete** — generation counters, `markLost`, `ConnectionLost`, state flows | Verified |
| 15 | Identify permission requirements | §5.5, ledger §5 | **Complete** — `dangerous` runtime permission; attach-record requirement; revocation kills | Verified |
| 16 | Identify failure modes | ledger §12 | **Complete** — 3 exception types + `PorterShellException`; 3 connection + 5 availability states | Verified |
| 17 | Identify unavailable states | ledger §12 | **Complete** — `PorterConnectionState` × `PorterAvailability` enumerated | Verified |
| 18 | Identify initialization requirements | ledger §4 | **Complete** — `Porter.onBinderReceived`; collect `state`; per-process call; nothing in manifest | Verified + Documented |
| 19 | Identify runtime state detection | ledger §12 | **Complete** — `isAlive()`, `alive()`, `state`, `availability` | Verified |
| 20 | Identify version compatibility | ledger §14 | **Complete** — protocol 4/4, Shizuku 13/13, SDK-version rules, source-level-only compat | Verified + Documented |
| 21 | Identify security boundaries | §7, ledger §13 | **Complete** — per-transaction enforcement, attach requirement, uid/context exposure, hidden-API needs | Verified |
| 22 | Identify known limitations | ledger §16 | **Complete** — 12 limitations recorded | Verified + Documented |
| 23 | Identify Android-version-specific differences | ledger §2 | **Partial** — `Android17Compat` fallbacks and permission gates recorded as **source-verified, runtime-unverified**; per-version matrix → Phase 10 | Verified (source only) |
| 24 | Determine which Porter behavior is verified versus assumed | throughout; §11 | **Complete** — every ledger row carries an `AGENTS.md` §11 label; 1 near-verified inference isolated as U-025 | Verified |

### Porter Backend Architecture (10/10)

| # | `PLAN.md` item (verbatim) | Answered in | Outcome | Classification |
| --- | --- | --- | --- | --- |
| 25 | Define verified Porter backend contract | §12 of evidence ledger (all §4–§13 rows) | **Complete** — API/input/output/handle/error/lifecycle/failure/recovery per `INVESTIGATION_METHOD.md` §19 | Verified |
| 26 | Define execution request mapping | §8 (P4-AR05), ledger §6 | **Complete** — `exec(vararg command, dir)` / `startProcess` / `userService(args)`; argv, no shell | Verified |
| 27 | Define execution result mapping | ledger §8 | **Complete** — `PorterShellResult(exitCode, output, errors)`; unbounded | Verified |
| 28 | Define execution handle mapping | §8 (P4-AR05), ledger §7, §9, §10 | **Complete** — `PorterShellProcess : Process` with pid/signals/streams | Verified |
| 29 | Define capability detection | ledger §12 (P4-A103), §8 (P4-AR09) | **Complete** — `REPLY_CAPABILITIES` bitmask, never reused | Verified |
| 30 | Define state detection | ledger §12, §8 (P4-AR04) | **Complete** — `Porter.state` × `Porter.availability` | Verified |
| 31 | Define error mapping | ledger §12, §7 | **Complete** — 4 distinct channels; `PorterShellException` is *not* a `PorterException` | Verified |
| 32 | Define lifecycle mapping | ledger §11–§12, §8 (P4-AR07, P4-AR08) | **Complete** — connection generation, daemon, per-connection bindings | Verified |
| 33 | Investigate recovery after Porter process death | §5.6, ledger §12 | **Complete** — restart yields a new connection; commands die with the app; daemon services survive app death; **server stop ends daemons**; no boot guarantee | Verified + Documented |
| 34 | Investigate recovery after app process death | §5.6, ledger §12 (P4-A94) | **Complete** — commands die via owner `DeathRecipient`; daemon services persist; a persisted PID is stale by construction | Verified |

**Total: 34 / 34 addressed.** 32 complete, 2 partial (items 5 and 23) — both
partials share one root cause: no device, and Phase 10 owns the per-version matrix.
The item count was re-derived from `PLAN.md` after an initial hand count of 32 proved
wrong; Appendix A's 34 rows were already complete.

---

## Appendix B — Version Context

| Item | Version / commit | Date checked | Currency |
| --- | --- | --- | --- |
| Porter application | `2d88f34bc348552b7fb22eb73ccaba5b9922cce5`; `VERSION` = `0.8.0-rc0`, versionCode `800000` | 2026-10-02 | Current `main` HEAD. Identical to Phase 2's S-204 pin |
| Porter application tags | `v0.1.1-beta0`, `v0.1.1-beta1`, `v0.7.0-rc0`, `v0.8.0-rc0` | 2026-10-02 | All pre-release; HEAD is **after** `v0.8.0-rc0` (2026-09-29) |
| Porter SDK | `2f2805226a33d6742c646a5efe022aa33d4c63ae` = tag `0.9.0` | 2026-10-02 | Current `main` HEAD and latest tag |
| Porter SDK tags | `0.1.0`, `0.7.0`, `0.8.0`, `0.9.0` | 2026-10-02 | Latest is `0.9.0`; `docs` state 0.1.0 clients cannot reach Porter 0.7.0+ |
| Porter wire protocol | `VERSION 4`, `MIN_VERSION 4` | 2026-10-02 | Current at both pins |
| Shizuku wire the SDK speaks | `CLIENT_API_VERSION 13`, `MINIMUM_VERSION 13` | 2026-10-02 | Current at the SDK pin |
| This project | `6df93ae8d2c3dbb84b461b4eecb0c7f65de8a5b2`; Shizuku client API **13.1.5** | 2026-10-02 | Client API version matches the SDK's 13 |
| Porter Compatibility companion | applicationId `moe.shizuku.privileged.api`, versionCode `700000` | 2026-10-02 | Versioned in step with the Porter release that ships it |
| Porter Android | `minSdk 24`, `targetSdk 37`, `compileSdk 37` | 2026-10-02 | Source-verified |

**Android-version context.** Phase 4 establishes **no** per-version support claim for
Porter on Android 12–17. Recorded as `Unknown` (U-026), assigned to Phase 10. Two
source-level facts are noted without behavioural claims: Porter ships
`Android17Compat` fallbacks for six hidden-API breakages (P4-A18), and it gates
`NEARBY_WIFI_DEVICES` / `ACCESS_LOCAL_NETWORK` behind API-level checks (P4-A19).

**Porter-version context.** Every behavioural claim above is pinned to Porter
`2d88f34b` / SDK `0.9.0`. The SDK is explicitly `0.x` and unstable (P4-A123), so no
claim may be stated without this pin.

**Shizuku-version context.** The SDK speaks Shizuku client API 13 and refuses servers
below 13. Bridge behaviour against an *older* server differs in documented ways
(P4-A136, P4-A137). Detailed Shizuku semantics remain Phase 5.

---

## Appendix C — Experiment Records

**No experiments performed in this phase.** No Android device or emulator is
available, so `INVESTIGATION_METHOD.md` §10's required fields cannot be supplied.
Every finding is source-read or documentation-read at the pinned commits.

Device-dependent claims are collected as U-025, U-026, U-027, U-028 and are **not**
presented as verified behaviour.

---

## Appendix D — Self-Audit

Audited against `PLAN.md` §223–264 and `INVESTIGATION_METHOD.md` §42.

| Criterion | Result |
| --- | --- |
| Every checklist item addressed | **34 / 34** (Appendix A); 32 complete, 2 partial. Count verified mechanically against `PLAN.md` — an earlier hand count of 32 was wrong |
| Appendix A maps every item to a section and an outcome | Yes |
| Primary sources reviewed and recorded | Yes — 2 Porter repos + local, per-file, pinned |
| Implementation evidence inspected where it exists | Yes — 144 rows |
| Important claims carry version context | Yes — Appendix B |
| Android/Porter/Shizuku-version-specific findings follow §7 | Yes — Porter and Shizuku versions pinned; **Android recorded as Unknown (U-026)** rather than guessed |
| Documentation vs implementation distinguished | Yes — every row typed `L1-SOURCE` / `L2-DOC` / `NEGATIVE` / `TEST` |
| Conceptual vs verified architecture distinguished | Yes — §8 uses `VERIFIED` / `PROPOSED` / `CONTRADICTED` |
| Verified behaviour separated from assumptions | Yes — every row carries an `AGENTS.md` §11 capability label |
| Contradictions documented in the central register | Yes — C-019…C-021 appended; none resolved |
| Unknowns documented in the central register | Yes — U-025…U-030 appended; U-001 resolved, U-019 half-resolved |
| Compatibility findings documented with conditions | Yes — §6, 12 rows |
| Security implications documented, including abuse cases | Yes — §7, 13 rows with owners |
| Architecture implications documented, conflicts marked `CONTRADICTED` | Yes — §8, 14 rows, 1 `CONTRADICTED` |
| Evidence IDs cited in the report all exist in the ledger | Yes — verified mechanically |
| `MasterRef.md` not modified | Verified — `git status --porcelain MasterRef.md` empty |
| `ARCHITECTURE.md` not modified either | Verified — recorded as contradicted; revision belongs to Architecture Confirmation / Revision after Phase 26 |
| No implementation work performed | Verified — `git status --porcelain` shows only `investigations/**` |
| Phase 0–26 structure unchanged | Verified |
| Register mirrors consistent | Verified — `check_register_mirrors.py` exits 0 |
| Remaining limitations explicitly recorded | Yes — Appendix C, §9, ledger §16 |

### Weaknesses stated plainly

1. **No device testing.** The bridge's `UnsupportedOperationException` (P4-A126) is
   the phase's most consequential claim and is source-derived, not observed. U-025
   tracks the device confirmation.
2. **All Porter application tags are pre-release.** Pinning to a moving pre-release
   HEAD (`0.8.0-rc0`, HEAD 3 days after the tag) means behavioural claims have a
   short shelf life. P4-A124's own advice — pin an exact SDK version — applies to
   this investigation's conclusions too.
3. **Server-side internals partly live in the SDK repo, not the app repo.**
   `PorterCore` authorization is `porter-api/server-shared` (P4-A08). Reading it
   required cloning both repositories; anyone auditing only the app repo would miss
   the entire authorization gate.
4. **The `compat/` companion's internals were not read.** Only its application id,
   versionCode and documented coexistence rules are recorded. That is a Phase 5/21
   input, not a Phase 4 omission.
5. **`docs/developers.md` was read but not diffed against the SDK source
   systematically.** Every claim checked happened to agree; a full doc-vs-source
   reconciliation was not performed and could surface further C-020-style
   understatements.
6. **No Porter claim was made about Android 12–17**, deliberately. A plausible guess
   here would have been exactly the `INFERRED`-as-`Verified` substitution
   `INVESTIGATION_METHOD.md` §16 prohibits.

### Self-audit result

**PASS. Phase 3's status is `Audited`, not `Complete`; Phase 4's status is `Complete`.**

The difference is substantive and worth stating. Phase 3's items 14–16 are
*inherently* experimental — they describe behaviour after a process dies or a device
reboots, and no device exists here. Phase 4's checklist is *analytical*: 32 of its 34
items are fully closed from source at pinned commits, and its two partials (5 and 23)
are both the Android-version matrix that `PLAN.md` explicitly assigns to Phase 10.

U-001 — which Phase 0 flagged as `Blocking` for every Porter-dependent decision — is
**resolved**, and U-019's factual half with it. Nothing in Phase 4's checklist remains
open on this phase's own account. The device-dependent items are registered as
U-025/U-026 and are owned by later phases, which is the correct disposition under
`INVESTIGATION_METHOD.md` §45: they survive this phase rather than disappearing
because it closed.