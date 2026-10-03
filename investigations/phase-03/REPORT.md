# ADB Modules App — Phase 03 — Investigation Report

Phase: `03 — ADB Module Lifecycle`
Status: **Audited** (self-audit recorded in Appendix D; see §11 for the one item that
prevents `Complete`)
Started: 2026-10-02
Last updated: 2026-10-02
Checklist: **17 / 17 addressed** (16 items + the state-model requirement)
Evidence ledger: [`evidence.md`](evidence.md) — 137 evidence rows + 14 negative results + 6 seam records
Depends on: Phase 1 (local lifecycle), Phase 2 (reference compatibility contract)
Feeds: Phase 4, 5, 6, 7, 9, 11, 13, 14, 22
Leads to next phase: yes — Phase 4 (Porter). This phase found that the reference's
"session" concept is Shizuku-specific and UI-triggered (P3-A62, P3-A64), so Phase 4
must determine what Porter's equivalent runtime event is before any state model can
be backend-neutral.

| Layer label | Meaning | Used throughout |
| --- | --- | --- |
| `REFERENCE` | Shevery/Nightzuku ADB Module implementation — the compatibility contract | §5–§8, `P3-A01`–`P3-A60`, `P3-A72`–`P3-A99`, `P3-A105`–`P3-A133` |
| `LOCAL` | This repository's Rootless Plugin / CodeBrick implementation | §5–§8, `P3-A13`–`P3-A20`, `P3-A30`–`P3-A58`, `P3-A70`–`P3-A71`, `P3-A77`, `P3-A89`, `P3-A93`–`P3-A94`, `P3-A100`–`P3-A104`, `P3-A108`–`P3-A123`, `P3-A129`–`P3-A130`, `P3-A134`–`P3-A137` |

`MasterRef.md` **not modified** (Phases 0–24 prohibition).

---

## 0. Scope decisions (recorded before research)

Four ambiguities were identified before Phase 3 research began, because `PLAN.md` is
a checklist rather than a specification and Phase 2 established that this repository
has **no** ADB Module subsystem (P2-A77). They are retained verbatim from the phase
setup so a later reader sees what was chosen and why, rather than inferring it.

### D1 — Investigate both layers, split by layer

`PLAN.md` titles this phase *ADB Module Lifecycle*, yet the ADB Module subsystem does
not exist in this repository. Two subjects were in play:

| Layer | Subject | Available material |
| --- | --- | --- |
| `REFERENCE` | Shevery / Nightzuku module lifecycle | Pinned clones at `bfc55ce9` / `60a8feb6` |
| `LOCAL` | This repository's Rootless Store plugin and CodeBrick lifecycle | Real source: `PluginState`, `PluginProcessMonitor`, `UninstallPluginUseCase`, 31 `isEnabled` references |

**Decision: investigate both, kept explicitly separated.** They are not merged and not
presented as equivalent. The reference layer defines the compatibility contract this
project would have to match; the local layer describes what exists today.

Grounds: the local layer is not optional — `PLAN.md` assigns Phase 3 ownership of
U-009 and U-011, both purely local questions that no amount of reference reading can
answer. The reference layer is not optional either — lifecycle is meaningless without
the contract it must satisfy, and Phase 2 explicitly deferred lifecycle to this phase.
`AGENTS.md` §14 requires these distinctions preserved rather than collapsed.

**Consequence: discharged.** Every finding below is labelled `REFERENCE` or `LOCAL`.
No statement in this report says "the ADB Module lifecycle" without naming a layer.

### D2 — Six seam items investigated at the boundary, depth deferred

Six of the sixteen checklist items fall inside phases owned by other investigations:

| Item | Owning phase | Phase 3 treatment |
| --- | --- | --- |
| discovery / download | 12 — Catalog / Sources | Seam only |
| verification | 12 — Catalog / Sources (U-008) | Seam only |
| module updates | 13 — Updates / Rollback | Seam only |
| module rollback | 13 — Updates / Rollback | Seam only |
| background execution | 9 — Background Services | Seam only |
| crash / stale-runtime / reboot-session | 14 — Runtime Recovery | Source-read facts; runtime behaviour deferred |

**Decision: investigate the seam, defer the depth.** For each, Phase 3 documents what
the lifecycle boundary *requires* at the handoff and labels the remainder as owned by
the later phase.

**Consequence: discharged.** `evidence.md` §17 carries six seam records (`SEAM-1` …
`SEAM-6`) plus an explicit assignment of the three recovery items to Phase 14. No seam
item is marked Complete on the strength of another phase's mechanism.

### D3 — Recovery items are source-read only

Checklist items 14–16 describe what happens after a process dies or a device reboots.
No device or emulator is available.

**Decision: source-read only, each runtime claim labelled `Needs device testing`.**

Grounds: `INVESTIGATION_METHOD.md` §10 requires device, Android version, app version,
dependency versions, configuration and procedure for an experimental finding;
`AGENTS.md` §21 forbids generalising from a single experiment; §16 forbids converting
"I think" into "the system does". A static read of recovery code cannot establish
runtime behaviour.

**Consequence: partially discharged.** Items 14–16 are recorded `Partial (source-read
only)` with an explicit unknown, not `Complete`. The *static* guarantees — what the code
provably does and does not do — are established (P3-A105–P3-A131); the *runtime*
behaviour is not. See §9 U-019.

### D4 — The state model is PROPOSED, not decided

`PLAN.md` requires determining whether the linear model
`Discovered → Downloaded → Verified → Installed → Enabled → Running → Stopped → Disabled → Uninstalled`
is sufficient, or whether lifecycle state must be represented across multiple
independent dimensions.

**Decision: Phase 3 records the answer as `PROPOSED`, with evidence.** Phase 3 can
determine what the reference and local implementations actually do. It cannot decide
what this project should do: `AGENTS.md` §24 forbids using one backend's semantics to
define the architecture, and `INVESTIGATION_METHOD.md` §40 requires an explicit
architecture decision rather than a silent edit. Architecture confirmation follows
Phase 26 and is not Phase 27.

**Consequence: discharged.** §8 records the finding as `VERIFIED` for the two observed
implementations and `PROPOSED` for this project's target model.

---

## 1. Scope

Covering `PLAN.md` §178–221: 16 lifecycle items plus the state-model requirement.

Technologies: the two reference forks' module subsystem (2 × ~2 300 LOC), and this
repository's `application`, `data`, `domain` and `ui` modules.

Explicitly **out of scope** and assigned elsewhere:

- Discovery, source trust, catalog auth — **Phase 12**.
- Package authenticity, integrity, signature — **Phase 7** (with U-008).
- Update/rollback design — **Phase 13**.
- Background-service mechanism depth — **Phase 9**.
- Porter runtime events — **Phase 4** (U-001 remains open).
- Shizuku binder/process semantics — **Phase 5**.
- Storage location decision — **Phase 11**.
- Android-version-specific behaviour — **Phase 10** (U-015).

**Known limitation of this scope:** no device, no emulator, no Porter, and this
repository has no ADB Module subsystem to exercise.

---

## 2. Questions to Answer

| # | From checklist item | Question | Answered? |
| --- | --- | --- | --- |
| 1 | discovery | Where does a module enter the system from, and what is checked before it is offered as installable? | Yes |
| 2 | download | How is the package bytes obtained, into what, and under what bounds? | Yes (seam) |
| 3 | verification | What, if anything, is checked about authenticity or integrity? | Yes (seam) |
| 4 | installation | What is the exact sequence, and is it atomic? | Yes |
| 5 | enable/disable | How is the enabled state represented, and what does it actually gate? | Yes |
| 6 | action execution | What triggers an action, through which gates, with what limits? | Yes |
| 7 | service execution | What triggers a service, and how does it differ from an action? | Yes |
| 8 | background execution | What permits unattended execution, and what re-arms it? | Yes (seam) |
| 9 | WebUI availability | What makes a WebUI available, and is that a state or a derivation? | Yes |
| 10 | module updates | How is an update found, compared, and applied? | Yes (seam) |
| 11 | module rollback | Can a failed update be undone? | Yes (seam) |
| 12 | uninstall | What is removed, and what is left behind? | Yes |
| 13 | data cleanup | What state survives uninstall, and for how long? | Yes |
| 14 | crash recovery | What happens to runtime state when a process dies? | Partial — source-read |
| 15 | stale-runtime recovery | Is persisted runtime state reconciled with reality? | Partial — source-read |
| 16 | reboot/session | What happens at reboot, and what is a "session"? | Partial — source-read |
| 17 | state model | Is the linear model sufficient? | Yes |

---

## 3. Primary Sources

| Source | Level | Version / commit | Location inspected | Used for |
| --- | --- | --- | --- | --- |
| `HmnDev-Tech/shevery` | L1 | `bfc55ce9` | `manager/src/main/java/moe/shizuku/manager/module/` (12 files) | Items 1–13, 16 |
| `HmnDev-Tech/shevery` | L1 | `bfc55ce9` | `.../module/update/` (10 files) | Items 10, 11 |
| `HmnDev-Tech/shevery` | L1 | `bfc55ce9` | `.../module/discovery/` (5 files) | Item 1 |
| `HmnDev-Tech/shevery` | L1 | `bfc55ce9` | `.../home/HomeActivity.kt`, `.../receiver/BootCompleteReceiver.kt` | Items 7, 8, 16 |
| `kerneldroid/Nightzuku` | L1 | `60a8feb6` | `module/`, `module/update/`, `receiver/BootCompleteReceiver.kt` | Divergence checks, items 10, 16 |
| This repository | L1 | `6df93ae` | `application/…/{plugin,execute,runtime,codebrick}` | Items 4–7, 12–16 |
| This repository | L1 | `6df93ae` | `data/…/{plugin,execution,fileSystem,monitor}` | Items 4–7, 13, 15 |
| This repository | L1 | `6df93ae` | `domain/…/{plugin,execution,codebrick}`, `ui/…/PluginScreen.kt` | Items 5, 17 |

Same pins as Phase 2, so the two phases are directly comparable.

---

## 4. Secondary Sources

None used as evidence. Phase 2's documentation findings (P2-B01…P2-B17) were reused
where they bear on lifecycle, but no new L2/L3 source was consulted, and no L5/L6
source was relied upon. This is a deliberate consequence of `INVESTIGATION_METHOD.md`
§6: for a source-available lifecycle, documentation is strictly weaker than the code.

---

## 5. Existing Implementation Evidence

137 behaviour rows plus 14 recorded negative results in [`evidence.md`](evidence.md). The findings that change a design decision:

### 5.1 `REFERENCE` — installation is destructive, not transactional

The whole install path is one critical section under a per-id mutex, and its order is
`staging.deleteRecursively()` → extract → chmod → **`target.deleteRecursively()`** →
`staging.renameTo(target)` → `readModule(target) ?: error(...)`
(P3-A21–P3-A23). The target is therefore gone before the rename is attempted, and
before the post-rename readability check. Two consequences follow directly:

- **An update is a destructive replace.** No previous version is retained, so there is
  no rollback path and no undo (P3-A24, P3-A90, P3-A92).
- **A failed update leaves the module uninstalled**, which is worse than leaving the
  old version in place. This is a design defect in the reference, not a property of the
  format.

Two smaller consequences that are easy to miss:

- The `disable` marker lives *inside* the module directory, so replacing the directory
  wholesale **silently re-enables a disabled module on update** (P3-A87).
- Everything the module wrote under its own directory — including `logs/` — is
  discarded by an update that does not ship it (P3-A86).

### 5.2 `REFERENCE` — there is no `Running → Stopped` transition

`setEnabled` writes or deletes a marker file and does nothing else (P3-A36, P3-A39).
No process handle is retained anywhere in the module subsystem (P3-D05). The only
termination call in the whole subsystem is `remote.destroy()` on the 120-second script
timeout (P3-A40). So the reference cannot stop a module service, cannot report one as
stopped, and cannot tell the user that anything is still running.

This is the single most important compatibility fact in the phase: **`service.sh` is
not a daemon, and the reference does not model it as one.** It runs once per binder
session under a 120-second timeout (P3-A51, P3-A62). `ARCHITECTURE.md` §33 and
`MasterRef.md` §31 already warn against treating it as a Rootless daemon; this phase
confirms the reference goes further and does not track it at all.

### 5.3 `REFERENCE` — service start is UI-triggered, and does not happen at boot

`runEnabledServicesIfAllowed` is called from exactly one place: a Compose
`LaunchedEffect` inside `HomeActivity`, keyed on the Shizuku service resource
(P3-A64). `BootCompleteReceiver` starts the *server* and returns; it contains no module
calls at all (P3-A65, P3-A124). Therefore:

> **Boot alone never starts `service.sh`.** The user must open the app after the
> Shizuku server is up.

The guard is reset only by `Shizuku.OnBinderDeadListener` (P3-A63), so "session" means
one Shizuku binder session and nothing else. `resetServiceRunGuard()` is public but has
no other caller (P3-A69), so a service that failed for lack of background permission
is not retried in that session even after the user grants permission.

The whole block is wrapped in `catch (_: Throwable) {}` (P3-A66), so a service-start
failure is discarded with no log and no user signal. And because `.map` is used rather
than `mapCatching` (P3-A68), one failing service aborts the whole batch — the modules
after it never run, with no indication that they were skipped.

### 5.4 `REFERENCE` — no integrity verification exists, at any stage

A search for `sha256|checksum|signature|hash` across the entire module subsystem
returns two hits, both `java.util.concurrent.ConcurrentHashMap` (P3-A15). The
`updateJson` response model has no digest field at all (P3-A16), so a publisher has
nowhere to put a hash even if they wanted to.

Verification at install is limited to: `module.prop` at archive root, `id` regex,
≤ 2048 entries, ≤ 200 MiB extracted, and two-layer path containment (P3-A17). Those
are *safety* limits. None of them establishes that the package is the one that was
published. A module fetched from any URL is trusted by construction.

### 5.5 `REFERENCE` — trust survives deletion and is inherited on reinstall

`AdbModuleManager.delete` removes the directory and nothing else (P3-A95). Trust lives
in a global preference string-set that delete never touches (P3-A97), and install never
touches it either (P3-A88). So the trust set grows monotonically with every id ever
trusted, and **a delete-then-install of the same id silently restores prior trust**
(P3-A98). `clearTrustedModules()` exists but is explicit and unused.

### 5.6 `REFERENCE` — update failures are indistinguishable from "up to date"

`checkUpdate` wraps its entire body in `try/catch` and returns `hasUpdate = false` on
any exception (P3-A83). A broken `updateJson`, a rate-limited GitHub API, a malformed
response and a genuinely current module all produce the same user-visible result.

Version comparison prefers `versionCode` (`latest > current`, so downgrades are never
offered) and falls back to a digit-run comparison (P3-A81). A module with neither
`versionCode` nor `version` on both sides is **permanently un-updatable** via
`updateJson` (P3-A82).

Nightzuku has strictly less: no `url`/`updateJson`/`updateInfo` field on its model at
all (P3-A80), and its fallback **assumes the repository name equals `module.id`**
(P3-A79).

### 5.7 `LOCAL` — U-009 resolved: the enabled flag is not enforced at execution

This was assigned to Phase 3 and is now answered. `ExecutePluginUseCase` reads the
plugin manifest and store status and **never reads `PluginStatus.isEnabled`**
(P3-A43). In the UI, the card's execute button calls `onExecuteOneTimePlugin(pluginId)`
with no enabled check; the *only* place `isEnabled` is tested is the adjacent
navigation handler (`PluginScreen.kt:276`), and toggling the switch off aborts the
process as a UI side effect rather than as a gate (P3-A43, P3-D08).

So: a disabled plugin can be executed. The switch is a UI affordance, not a security or
lifecycle control. For an ADB Module this matters directly, because the reference
*does* gate execution on `module.enabled` (`AdbModuleManager.kt:176`) — so matching the
reference contract requires an enforcement point this repository does not have.

### 5.8 `LOCAL` — U-011 resolved: `PluginState` has one live value

The only `PluginState` value ever written to the database in the entire project is
`Great`, at three insert sites (P3-A114). `PermissionProblems` appears once as a
hardcoded UI display value and is never persisted. `Stop`,
`PluginRuntimeProblems` and `RootlessStoreRuntimeProblems` are dead enum values, so any
UI switching on `PluginState` can only ever observe `Great` (P3-A115).

### 5.9 `LOCAL` — recovery kills; it does not reconcile

`RecoverPluginRuntimeStateUseCase` runs once from `MainActivity`, and for every
remembered execution row it issues `kill -9` and then disables the plugin and deletes
the row (P3-A117, P3-A120). There is **no liveness check first**: no `/proc` probe, no
`kill -0`, no cmdline match against the entry point, no start-time comparison to detect
PID reuse (P3-A121). So a legitimately running plugin is terminated across every app
restart, and a stale row whose PID has been recycled kills an unrelated process.

`MasterRef.md` §59 and `ARCHITECTURE.md` §36 both state that persisted process
information must be distinguished from verified active state. That check does not
exist in the code.

The two branches have **opposite** failure semantics: the non-ADB branch always cleans
up, while the ADB branch cleans up **only if the kill reported success**, so when
Shizuku is unavailable the row and the enabled flag both survive and accumulate with no
retry (P3-A118, P3-A123).

### 5.10 `LOCAL` — the declared lifecycle API is partly fictional

`updatePluginExecutionStateByPluginId`, `observePluginExecutionStateByPluginId`,
`disableAllPlugins()` and `deleteAllPluginExecutions()` are declared and never invoked
(P3-A113, P3-A137, P3-D13). This is the same defect class as C-006 — a documented or
declared contract that nothing enforces — observed on the lifecycle side.

### 5.11 `LOCAL` — update is a merge, not a replace

Locally, re-installing over an existing `pluginPackageName` `mkdirs()` the target and
overwrites files in place, with no staging, no delete and no rollback (P3-A93). A
failed local update therefore leaves a **mixture** of old and new files in one
directory with no way to tell them apart, and the DB is still updated (P3-A94). This
is the opposite failure mode from the reference: the reference loses the module, the
local path silently corrupts it.

---

## 6. Compatibility Findings

Phrased per `INVESTIGATION_METHOD.md` §25 — "compatible with X under conditions A, B,
C; behaviour D unverified".

| ID | Subject | Compatible with | Conditions | Not compatible / unverified | Version | Class |
| --- | --- | --- | --- | --- | --- | --- |
| P3-C01 | Installed-module state | Shevery's filesystem-derived model | Module directory exists, `module.prop` present and `id` valid, metadata re-read per access | No persistent registry exists, so no registry can be synchronised with | Shevery `bfc55ce9` / Nightzuku `60a8feb6` | VERIFIED |
| P3-C02 | Enabled state | A `disable`-marker file | Marker path is `<moduleDir>/disable`; content never read | Local `pluginStatus.enabled` boolean is **not** the same shape and is not enforced | both forks | VERIFIED |
| P3-C03 | Execution gate on enabled | Shevery's `check(module.enabled)` | Requires an enforcement point in the execution path | Local path has none (P3-A43) — incompatible | — | VERIFIED |
| P3-C04 | Action execution | `sh -c <contents>` via `IShizukuService.newProcess` | Shizuku binder up; backend is Shizuku | No Porter equivalent verified; U-001 open | Shevery `bfc55ce9` | VERIFIED (Shizuku only) |
| P3-C05 | Action/service timeout | 120 s, synthetic exit 124 | Applies identically to `service.sh` | Not enforced locally at all (P3-A56) | both forks | VERIFIED |
| P3-C06 | Output retention | last 64 KiB per stream | Requires bounded capture | Local path has no output cap | Shevery `bfc55ce9` | VERIFIED |
| P3-C07 | Unattended service start | once per Shizuku binder session | Binder up, `pingBinder()` true, `HomeActivity` composed, `canRunBackground` true | **No backend-neutral equivalent exists.** Do not port "binder session" to Porter (P3-A62, P3-A64) | Shevery `bfc55ce9` | VERIFIED (Shizuku-bound) |
| P3-C08 | Service start at boot | Nothing | — | **Incompatible.** Boot never starts services (P3-A65) | both forks | VERIFIED INCOMPATIBLE |
| P3-C09 | WebUI availability | `webRoot/index.html` is a file | `webui`→`webroot`→`webui`→`web` chain | Not a stored state; a deletion between listing and open silently fails | both forks | VERIFIED |
| P3-C10 | `window.Shizuku` presence | Bridge attached only when 4 conditions hold | enabled ∧ policy ∧ declaration-or-trust ∧ network-or-trust | Absent entirely otherwise, so `typeof window.Shizuku` is `"undefined"`, not a refusing object | Shevery `bfc55ce9` | VERIFIED |
| P3-C11 | Update discovery | Shevery's 3-tier precedence | `updateJson` → catalog → `url`/`repo` → Releases | Nightzuku has 2 tiers and a `repoName == module.id` heuristic (P3-A79) | Shevery `bfc55ce9` | VERIFIED (divergent) |
| P3-C12 | Update application | Destructive replace via the install path | — | **Not rollback-compatible.** No previous version exists (P3-A90) | both forks | VERIFIED INCOMPATIBLE |
| P3-C13 | Uninstall | Directory removal | — | Trust, catalog cache and cached ZIPs all survive (P3-A97, P3-A99) | both forks | VERIFIED |
| P3-C14 | Boot receiver Android gates | Shevery's API 33/37 permission gating | Nightzuku lacks `ACCESS_LOCAL_NETWORK`, Device-Owner arming and pre-S keyguard deferral (P3-A126, P3-A127) | Fork-divergent; Android 17 behaviour is Phase 10's | Shevery `bfc55ce9` | VERIFIED (divergent) |

---

## 7. Security Findings

Recorded at Phase 3 depth. Full threat modelling is **Phase 7**. Per
`INVESTIGATION_METHOD.md` §23, each row states the boundary, the abuse case, and who owns
the follow-up.

| ID | Boundary | Risk | Evidence | Class | Follow-up owner |
| --- | --- | --- | --- | --- | --- |
| P3-S01 | Update channel | `updateJson` is an unauthenticated remote input that names a `zipUrl`. Combined with the total absence of integrity checking (P3-A15), a hijacked endpoint yields arbitrary privileged code | P3-A15, P3-A16, `UpdateChecker.kt:333-339` | VERIFIED | 7, 12, 13 |
| P3-S02 | Install transaction | A failed update leaves the module **gone**. A hostile or merely broken update is therefore a denial of service against the user's module set, with no in-app remedy | P3-A23, P3-A24, P3-A92 | VERIFIED | 13 |
| P3-S03 | Update replaces local state | The `disable` marker lives inside the replaced directory, so an update re-enables a deliberately disabled module (P3-A87), and the module's own persisted data under that directory is lost (P3-A86) | P3-A86, P3-A87 | VERIFIED | 13 |
| P3-S04 | Trust persistence | Trust is keyed by module id and survives delete; a re-installed module of the same id inherits trust the user granted to different code (P3-A97, P3-A98) | P3-A97, P3-A98 | VERIFIED | 7 |
| P3-S05 | Trust does not survive an update positively — but the *code* does not either | Combined with P3-S01: an attacker who controls the update endpoint inherits the trust already granted to that id | P3-A88 + P3-S01 | INFERENCE | 7 |
| P3-S06 | Unbounded download | Release assets are streamed to cache with no size cap, before any limit is applied — the 200 MiB cap applies to *extraction*, not to the fetch (P3-A10, P3-A17) | P3-A10 | VERIFIED | 12 |
| P3-S07 | Silent update failure | Failures degrade to "up to date" (P3-A83), so a compromised or broken channel is invisible to the user | P3-A83 | VERIFIED | 13, 15 |
| P3-S08 | Disable is not containment | Disabling does not stop anything and no process is tracked (P3-A39), so "disabled" does not mean "not running" | P3-A39, P3-A40 | VERIFIED | 9 |
| P3-S09 | Uninstall is not revocation | Deleting a module leaves its trust entry behind, and the module's cached ZIP may remain on disk for up to 24 h (P3-A97, P3-A99) | P3-A97, P3-A99 | VERIFIED | 11 |
| P3-S10 | Local: enabled flag unenforced | A disabled plugin remains executable, and the switch is the only visible control (P3-A43) | P3-A43 | VERIFIED | 7 |
| P3-S11 | Local: PID kill without validation | `kill -9 <pid>` on a persisted, unverified, possibly recycled PID, with no liveness or identity check (P3-A121, P3-A122) | P3-A121, P3-A122 | VERIFIED | 14 |
| P3-S12 | Local: crash monitor coupled to a notification preference | Disabling "plugin status notification" also disables crash monitoring, and the shared `MutableSharedFlow` drops emissions when uncollected (P3-A109, P3-A110, P3-A111) | P3-A109–P3-A111 | VERIFIED | 15 |
| P3-S13 | Local: install errors swallowed | `InstallPluginFromMarketUseCase` checks no return value from any of its four steps (P3-A33), so a failed install can still register rows | P3-A33 | VERIFIED | 7 |
| P3-S14 | Local: manifest identity trusted from catalog | The market install path takes identity and entry point from the catalog manifest rather than re-deriving from the archive (P3-A14) | P3-A14 | VERIFIED | 12 |
| P3-S15 | Local: CodeBrick title as directory name | Directory name is user-visible text, so two same-titled CodeBricks collide on disk while differing in DB id (P3-A35) | P3-A35 | VERIFIED | 11 |

---

## 8. Architecture Implications

`ARCHITECTURE.md` is **proposed/changeable**. Labels from `investigations/METHOD.md` §3.2.

| ID | Implication | Label | Evidence | Affected section | Recommendation |
| --- | --- | --- | --- | --- | --- |
| P3-AR01 | The linear `PLAN.md` model is **insufficient**. Lifecycle state must be represented across independent dimensions | PROPOSED (for this project); VERIFIED (for both observed implementations) | P3-A132–P3-A136 | §48 Runtime State Model | Record the multi-dimensional model as the target; the linear chain remains useful only as a diagram of *the installation path* |
| P3-AR02 | The reference already has **six** independent dimensions and persists only two | VERIFIED | P3-A132, P3-A133 | §48 | Adopt the dimension list as the vocabulary; do not assume a state enum |
| P3-AR03 | **"Running" is not a state this project can inherit from the reference.** The reference never tracks a module process | CONTRADICTED (vs `ARCHITECTURE.md` §33 and `MasterRef.md` §31, which imply tracked services) | P3-A39, P3-A40, P3-A105 | §33, §36, §37, §38 | Revise §33: `service.sh` is a bounded, once-per-session invocation, **not** a tracked long-running process |
| P3-AR04 | "Session" is Shizuku-specific and UI-triggered. It must not be ported to Porter | VERIFIED | P3-A62–P3-A65 | §33 | §33 must name the runtime event per backend, or admit that unattended service start is Shizuku-only |
| P3-AR05 | Background execution cannot be triggered at boot under the reference contract | VERIFIED INCOMPATIBLE | P3-A65, P3-A124 | §33 | Explicitly record the boot limitation rather than implying boot-start |
| P3-AR06 | The local `PluginState` third dimension does not function (one live value of four) | CONTRADICTED (vs the intent in §48) | P3-A114, P3-A115 | §48 | Either implement the dimension or stop declaring it; a dead enum misleads every future reader |
| P3-AR07 | The local layer has no execution-time enforcement of the enabled flag | VERIFIED | P3-A43 | §34 Module Enable State | §34's "Module enabled state is persistent package state" is accurate but incomplete; add that it must be enforced at the execution boundary, matching the reference's `check(module.enabled)` |
| P3-AR08 | The local recovery path terminates rather than reconciles, with no liveness check | CONTRADICTED (vs `ARCHITECTURE.md` §36, §38 and `MasterRef.md` §59) | P3-A120, P3-A121 | §36, §37, §38 | §38 must state that reconciliation requires an actual liveness/identity check, and that this is currently absent |
| P3-AR09 | Uninstall must remove trust state, not just files | PROPOSED | P3-A95, P3-A97 | §75 Package Removal | §75 lists "persistent configuration" abstractly; add trust, catalog cache and cached downloads as concrete obligations |
| P3-AR10 | Install must be transactional or must retain the previous version; the reference model is not acceptable to copy | PROPOSED | P3-A23, P3-A24, P3-A90 | §30 Module Installation, §45 Update Flow | §30 currently says "A failed installation must not leave a partially installed module that appears valid" — extend to "must not destroy the previously installed version" |
| P3-AR11 | Update must preserve state that lives inside the module directory, or the module directory must not hold state | PROPOSED | P3-A86, P3-A87 | §73, §74 Package Update | Move the `disable` marker out of the replaced directory, or state that updates reset enablement |
| P3-AR12 | The compatibility target needs an explicit decision on Shevery's **defects**, not just its format | PROPOSED | P3-A23, P3-A83, P3-A87, P3-A90 | §59 Compatibility Boundary | §59 currently treats the fork as the contract. Add: format compatibility and defect compatibility are separate decisions |
| P3-AR13 | Lifecycle persistence must not reuse `PluginStatus`/`PluginExecuteStatusEntry` as-is: the mutation path is declared and never called | PROPOSED | P3-A113, P3-A137 | §37 Runtime Persistence | Note the declared-but-uninvoked methods so an implementer does not assume execution state is maintained |
| P3-AR14 | WebUI availability is a **derivation**, not a state; it must not be persisted | PROPOSED | P3-A72, P3-A73, P3-A76 | §39 WebUI Architecture | §39 is silent on this; add that availability is recomputed and can vanish between listing and open |

Sections confirmed **unchanged** by this phase: §29 Module Storage (P2-A52/P2-B13
still hold), §31 Module Environment (P2-A55/A56 unchanged), §42 Execution Limits
(P3-A49 confirms 120 s / 64 KiB / 256 KiB), §34 enablement as a distinct concept
(P3-A36 confirms the reference's mechanism differs but the distinction is right).

---

## 9. Unknowns

New: **U-019 … U-024**. Resolved: **U-009**, **U-011**.

| ID | Unknown | Why unresolved | What would resolve it | Owner |
| --- | --- | --- | --- | --- |
| U-009 | ~~Whether the UI prevents executing a plugin whose `isEnabled` is false~~ | **RESOLVED (Phase 3).** It does not. `ExecutePluginUseCase` never reads the flag; the execute button has no check; only the adjacent navigation handler tests it. The switch is a UI affordance, not a gate | — | — |
| U-011 | ~~Whether any non-default `PluginState` value is ever produced~~ | **RESOLVED (Phase 3).** No. The only persisted value is `Great` at three insert sites. `PermissionProblems` is a hardcoded UI value; the other three are dead | — | — |
| U-019 | Whether the reference's session-triggered service start ever fires before the user opens the app on a Porter-like backend, and what the correct neutral trigger is | The reference's trigger is a Shizuku binder event observed from a Compose `LaunchedEffect` (P3-A62–P3-A64). There is no backend-neutral equivalent to observe, and Porter's runtime events are unknown (U-001) | Phase 4 establishing Porter's lifecycle events, then an explicit Phase 6/9 architecture decision | 4, 6, 9 |
| U-020 | Runtime behaviour of the local recovery path — whether `kill -9` on a recycled PID actually kills an unrelated process on a real device, and whether the ADB-branch row accumulation is user-visible | Source-read only; `INVESTIGATION_METHOD.md` §10 requires a device for such claims. No device available | Device test on Android 12–17 with a forced PID reuse and a Shizuku-unavailable run | 14 |
| U-021 | Whether any real third-party module depends on `service.sh` returning within 120 s, or on the module directory being replaced wholesale by an update | Catalog contents were not analysed (same limitation as U-014); no module corpus is available offline | Phase 12 catalog corpus analysis, then Phase 21 interoperability runs | 12, 21 |
| U-022 | Whether GitHub release assets in practice carry any digest or signature that a future integrity check could consume | `downloadRelease` selects by filename and reads nothing else (P3-A09); asset metadata was not enumerated for the ecosystem | Phase 12 source/integrity design, informed by a release-asset survey | 12 |
| U-023 | Whether `disable`-marker write interruption can leave a half-written marker that reads as "enabled" | `marker.writeText("disabled\n")` is not atomic and the content is never read, so the only risk is a torn write. Not observable statically | Device test interrupting the write | 11 |
| U-024 | Whether the abandoned `disableAllPlugins()` / `deleteAllPluginExecutions()` API was intended as a real feature that regressed, or was scaffolding | They are declared with no caller (P3-A137); git history for their introduction was not examined in this phase | `git log -S` on the DAO and repository methods | 6, 22 |

Carried forward unchanged and still relevant: **U-001** (Porter — blocks nothing in
this phase), **U-008** (source authentication, Phase 12 — now sharper: P3-A15 shows
there is nothing to authenticate *into*), **U-014**, **U-015**, **U-018**.

---

## 10. Contradictions

New: **C-014 … C-018**. Full entries in [`../contradictions.md`](../contradictions.md).
No contradiction is silently resolved.

| ID | Contradiction | Severity | Status | Interpretation adopted |
| --- | --- | --- | --- | --- |
| C-014 | `ARCHITECTURE.md` §33 and `MasterRef.md` §31 imply tracked module service processes; the reference tracks none | Material | **Unresolved** — recorded as CONTRADICTED in §8 | The implementation governs the description of the *reference*. What this project should do is a Phase 6/9 decision |
| C-015 | `ARCHITECTURE.md` §36/§38 and `MasterRef.md` §59 require persisted process state to be distinguished from verified active state; the local code does not verify anything before killing | Material | **Unresolved** | Same treatment as C-014 |
| C-016 | Shevery and Nightzuku enforce the *same* lifecycle limits by non-equivalent means, and diverge on update tiers | Material | **Unresolved** — extends C-010 | Neither fork is authoritative; the portable intersection is the target |
| C-017 | The reference's update path cannot fail safely, yet the docs present update as an ordinary operation | Minor | **Unresolved** | Source governs; the reference's update behaviour is a defect, not a contract |
| C-018 | Locally, an update silently merges old and new files; in the reference, an update destructively replaces. Neither is a safe update | Minor | **Unresolved** | Both are defects; the project must design a third behaviour |

---

## 11. Verified Conclusions

Only statements whose classification supports the wording.

1. `REFERENCE` — Install is a single non-transactional critical section that deletes
   the target before attempting the rename (P3-A22, P3-A23). `VERIFIED`, High.
2. `REFERENCE` — An update is therefore a destructive replace; a failed update leaves
   the module uninstalled and no rollback exists (P3-A24, P3-A90, P3-A92). `VERIFIED`, High.
3. `REFERENCE` — An update silently re-enables a disabled module, because the `disable`
   marker lives inside the replaced directory (P3-A87). `VERIFIED`, High.
4. `REFERENCE` — Nothing tracks a module process; disabling and uninstalling stop
   nothing (P3-A39, P3-A96, P3-D05). `VERIFIED`, High.
5. `REFERENCE` — `service.sh` is bounded by the same 120 s timeout as `action.sh`, runs
   at most once per Shizuku binder session, and is **not** started at boot (P3-A51,
   P3-A62, P3-A65, P3-A124). `VERIFIED`, High.
6. `REFERENCE` — The service-start trigger is UI composition observing a Shizuku
   resource, not a backend event (P3-A64). `VERIFIED`, High.
7. `REFERENCE` — No signature, hash or checksum verification exists anywhere in the
   module subsystem, and the update response model has no field for one (P3-A15,
   P3-A16). `VERIFIED`, High.
8. `REFERENCE` — Update failures are reported as "no update available" (P3-A83).
   `VERIFIED`, High.
9. `REFERENCE` — Trust survives module deletion and is inherited by a later install of
   the same id (P3-A97, P3-A98). `VERIFIED`, High.
10. `REFERENCE` — WebUI availability and bridge exposure are derivations recomputed from
    disk and current policy, not stored states (P3-A72, P3-A76). `VERIFIED`, High.
11. `LOCAL` — The enabled flag is not enforced at execution; a disabled plugin can run
    (P3-A43). `VERIFIED`, High. **Resolves U-009.**
12. `LOCAL` — `PluginState` has exactly one live value; three of its four values are
    dead (P3-A114, P3-A115). `VERIFIED`, High. **Resolves U-011.**
13. `LOCAL` — Recovery terminates remembered PIDs without any liveness or identity
    check, and cleans up in the ADB branch only on kill success (P3-A118, P3-A120,
    P3-A121). `VERIFIED`, High.
14. `LOCAL` — Local update is an in-place merge, so a failed update leaves a mixed
    directory (P3-A93, P3-A94). `VERIFIED`, High.
15. `LOCAL` — Four lifecycle APIs are declared and never invoked (P3-A137). `VERIFIED`, High.
16. The `PLAN.md` linear lifecycle model is **insufficient**; lifecycle state requires
    independent dimensions (P3-A132–P3-A136). `VERIFIED` for the two implementations,
    `PROPOSED` for this project's target model.

---

## 12. Recommendations for MasterRef Expansion

For Phase 25 to consider. **Nothing written to `MasterRef.md` in this phase.**

| ID | Current MasterRef section | Recommended change | Reason | Intended classification |
| --- | --- | --- | --- | --- |
| P3-M01 | §31 Service Session Model | Record that `service.sh` is bounded by a 120 s timeout and runs once per binder session, and that the reference tracks **no** service process | Easy to state wrongly; `MasterRef.md` §31 currently implies a tracked service | Verified fact (reference behaviour) |
| P3-M02 | §31 | Add that services do **not** start at boot, and that the trigger is UI-observed | Directly contradicts an intuitive reading of "background execution" | Verified fact |
| P3-M03 | New subsection under §35 Module Installation | Record the non-transactional install order and its two consequences (destructive replace, no rollback) | Currently unrecorded and load-bearing for Phase 13 | Verified fact |
| P3-M04 | §74 Update Safety | Add that an update replaces the module directory wholesale, discarding module-written state and the `disable` marker | The existing text says updates should not "blindly overwrite"; the actual failure mode is sharper | Verified fact + documented intent |
| P3-M05 | §75 Package Removal | Add trust state, catalog cache and cached downloads as concrete removal obligations | §75 is abstract; the reference leaks all three | Verified fact |
| P3-M06 | §59 PID Handling / §62 Runtime Recovery | Record that no liveness or PID-identity check exists locally, and that recovery kills rather than reconciles | The document asserts the opposite as a requirement; the gap should be visible | Verified fact |
| P3-M07 | §26/§30 (state model) | Add the multi-dimensional lifecycle model and state that the linear chain describes only the install path | `PLAN.md` asks the question; MasterRef should hold the answer | Conceptual architecture + verified reference behaviour |
| P3-M08 | §25 REFERENCE SAFETY LIMITS | Add the 256 KiB script cap (already in Phase 2's evidence) and clarify the 200 MiB cap applies to extraction, not to download | §25 lists limits without saying what they bound | Verified fact |
| P3-M09 | §38 Execution Permission | Note that the reference enforces `module.enabled` at the execution boundary, and that this project needs an equivalent enforcement point | The one place the reference is stricter than the local fork | Verified fact + proposed design |

---

## 13. Sources / References

Full records are in [`../sources.md`](../sources.md). Pins used by every row:

| Layer | Repository | Commit | Date |
| --- | --- | --- | --- |
| `LOCAL` | this project | `6df93ae8d2c3dbb84b461b4eecb0c7f65de8a5b2` | 2026-10-02 |
| `REFERENCE` | `HmnDev-Tech/shevery` | `bfc55ce9c8898043f1a5c4896be276d154d08243` | 2026-10-02 |
| `REFERENCE` | `kerneldroid/Nightzuku` | `60a8feb65d1a9c95692624222ef26afb3063b9d3` | 2026-07-20 |

Shevery and Nightzuku pins are identical to Phase 2's, so §5's lifecycle findings and
Phase 2's format findings describe the same code.

---

## 14. Items Requiring Future Investigation

| ID | Item | Why deferred | Owning phase |
| --- | --- | --- | --- |
| P3-F01 | Runtime behaviour of recovery under PID reuse and Shizuku unavailability | Needs a device (`INVESTIGATION_METHOD.md` §10) | 14 (U-020) |
| P3-F02 | A backend-neutral equivalent of the binder-session trigger | Depends on Porter's lifecycle events | 4, 6, 9 (U-019) |
| P3-F03 | Safe install/update transaction design | Phase 3 can establish the defect, not choose the fix | 13 |
| P3-F04 | Trust revocation on uninstall, and trust inheritance across updates | Security decision, not lifecycle | 7 |
| P3-F05 | Integrity/signature scheme for update packages | Phase 7 owns package security; Phase 12 owns sources | 7, 12 (U-008, U-022) |
| P3-F06 | Whether any real module depends on 120 s service bounds or on directory replacement | Needs a module corpus | 12, 21 (U-021) |
| P3-F07 | Android-version-specific behaviour of the boot receiver gates and of `setExecutable` | Platform-dependent | 10 (U-015) |
| P3-F08 | Git history of the declared-but-uninvoked lifecycle APIs | Not required to establish current behaviour | 6, 22 (U-024) |
| P3-F09 | Whether `disable`-marker writes can tear | Not observable statically | 11 (U-023) |

---

## Appendix A — Checklist Coverage

All 16 items plus the state-model requirement. An item is complete when it has a
verified answer, documented answer, verified limitation, verified incompatibility,
verified historical behaviour, or an explicit unresolved question
(`INVESTIGATION_METHOD.md` §41).

| # | `PLAN.md` checklist item (verbatim) | Addressed in | Outcome | Classification |
| --- | --- | --- | --- | --- |
| 1 | Investigate discovery | §5, `evidence.md` §1 | **Complete (seam)** — GitHub-API discovery, `id`-regex validation only; SEAM-1 | VERIFIED + seam to 12 |
| 2 | Investigate download | §5.4, `evidence.md` §2 | **Complete (seam)** — unbounded stream to cache, no integrity check; SEAM-2 | VERIFIED + seam to 12 |
| 3 | Investigate verification | §5.4, §7, `evidence.md` §3 | **Complete (seam)** — **negative result**: nothing exists; SEAM-3 | VERIFIED INCOMPATIBLE |
| 4 | Investigate installation | §5.1, §5.11, `evidence.md` §4 | **Complete** — sequence, atomicity failure, staging, fs-as-registry | VERIFIED |
| 5 | Investigate enable/disable | §5.2, §5.7, `evidence.md` §5 | **Complete** — marker file; gates script + bridge but stops nothing | VERIFIED |
| 6 | Investigate action execution | §5.2, `evidence.md` §6 | **Complete** — gates, limits, blocking, log overwrite | VERIFIED |
| 7 | Investigate service execution | §5.2, §5.3, `evidence.md` §7 | **Complete** — 4 gates, 120 s bound, once-per-session | VERIFIED |
| 8 | Investigate background execution | §5.3, §7 (P3-S08), `evidence.md` §7 | **Complete (seam)** — `canRunBackground` defaults false, trust overrides, guard reset only on binder death; SEAM-6 | VERIFIED + seam to 9 |
| 9 | Investigate WebUI availability | §8 (P3-AR14), `evidence.md` §8 | **Complete** — a derivation, not a state; 4-condition bridge gate | VERIFIED |
| 10 | Investigate module updates | §5.6, `evidence.md` §9 | **Complete (seam)** — 3-tier discovery, destructive apply; SEAM-4 | VERIFIED + seam to 13 |
| 11 | Investigate module rollback | §5.1, `evidence.md` §10 | **Complete (seam)** — **verified incompatible**: no rollback exists; SEAM-5 | VERIFIED INCOMPATIBLE + seam to 13 |
| 12 | Investigate uninstall | §5.5, `evidence.md` §11 | **Complete** — directory removal only, no process check | VERIFIED |
| 13 | Investigate data cleanup | §5.5, §7 (P3-S09), `evidence.md` §11 | **Complete** — trust, catalog cache and ZIPs all survive | VERIFIED |
| 14 | Investigate crash recovery | §5.8–§5.9, `evidence.md` §12 | **Partial (source-read)** — no reference mechanism at all; local monitor is preference-gated and lossy. Runtime behaviour → Phase 14 | VERIFIED (static) + U-020 |
| 15 | Investigate stale-runtime recovery | §5.9, `evidence.md` §13 | **Partial (source-read)** — recovery kills without liveness check; opposite branch semantics. Runtime behaviour → Phase 14 | VERIFIED (static) + U-020 |
| 16 | Investigate reboot/session behavior | §5.3, `evidence.md` §14 | **Partial (source-read)** — boot never starts services; session = Shizuku binder; Android-version gates diverge by fork. Runtime behaviour → Phase 14 | VERIFIED (static) + U-019 |
| 17 | State model: is the linear model sufficient? | §8 (P3-AR01, P3-AR02), §11 conclusion 16, `evidence.md` §15 | **Complete** — **No.** Multi-dimensional is required; six dimensions observed, two durable | VERIFIED (observed) + PROPOSED (target) |

**Total: 17 / 17 addressed.** 14 complete, 3 partial with the residual explicitly assigned
to Phase 14 and recorded as U-019/U-020.

---

## Appendix B — Version Context

| Item | Version / commit | Date checked | Currency |
| --- | --- | --- | --- |
| This project (Rootless Store fork) | `6df93ae8d2c3dbb84b461b4eecb0c7f65de8a5b2` (`main`) | 2026-10-02 | Current `HEAD`. Phase 1 worked at `4b35c36`; the commit is Phase 2's documentation-only "phase 2" commit, so lifecycle source is unchanged from Phase 1's reading |
| Upstream Rootless Store | Unknown (U-002) | — | Not required by this phase |
| Shevery | `bfc55ce9c8898043f1a5c4896be276d154d08243` | 2026-10-02 | Same pin as Phase 2 |
| Nightzuku | `60a8feb65d1a9c95692624222ef26afb3063b9d3` | 2026-07-20 | Same pin as Phase 2; ~2.5 months older than the Shevery pin, so divergence findings are **directional**, not necessarily current on both sides |
| Shizuku client API | `dev.rikka.shizuku:api` **13.1.5** (this project); reference uses `IShizukuService` AIDL | 2026-10-02 | Phase 0 baseline for the local side |
| Porter | **None declared** — U-001 open | 2026-10-02 | No Porter dependency exists; Phase 3 makes no Porter claim |
| Android | Project `minSdk 26`, `targetSdk 28`, `compileSdk 37` | 2026-10-02 | Phase 0 baseline |

**Android-version context.** Phase 3's lifecycle findings are source-level and
platform-independent: none of the discovered behaviour depends on the Android version.
Two rows carry Android-version content and are **not** verified as behaviour:
P3-A126/P3-A127 record the *source branches* in `BootCompleteReceiver` that gate ADB
boot-start on API 33 (`NEARBY_WIFI_DEVICES`) and API 37 (`ACCESS_LOCAL_NETWORK`), and
on pre-S keyguard state. Whether those branches fire correctly on Android 12–17 is
**Phase 10** (and U-015). Recorded here as `Source-verified, runtime-unverified`; no
`Supported / Partially supported / Unsupported` claim is made for any Android version
in this phase.

---

## Appendix C — Experiment Records

**No experiments performed in this phase.** No Android device or emulator is available
in this environment, so `INVESTIGATION_METHOD.md` §10's required fields (device,
manufacturer/model, Android version, app version, dependency versions, backend,
configuration, permissions, trust state, network state, battery state, procedure,
expected result, observed result, logs, limitations) cannot be honestly supplied.

Every finding in this report is therefore **source-read**. Claims that specifically
depend on runtime observation rather than static reading are enumerated as U-019,
U-020, U-021, U-023 and in Appendix B. None of them is presented as verified behaviour.

---

## Appendix D — Self-Audit

Audited against `PLAN.md` §178–221 and `INVESTIGATION_METHOD.md` §42.

| Criterion | Result |
| --- | --- |
| Every checklist item addressed | **17 / 17** (Appendix A) |
| Appendix A maps every item to a section and an outcome | Yes |
| Primary sources reviewed and recorded | Yes — 3 pinned repositories, per-file locations in `evidence.md` |
| Implementation evidence inspected where it exists | Yes — 137 behaviour rows plus 14 negative results; every checklist item has at least one L1 row |
| Important claims carry version context | Yes — Appendix B |
| Android/Porter/Shizuku version-specific findings follow §7 | Yes — Android recorded as not-applicable plus two source-verified/unverified rows; **no Porter claim made**, U-001 open |
| Documentation vs implementation distinguished | Yes — no L2 source was used as evidence in this phase |
| Conceptual vs verified architecture distinguished | Yes — §8 uses `VERIFIED` / `PROPOSED` / `CONTRADICTED` |
| Verified behaviour separated from assumptions | Yes — `L1-SOURCE` vs `INFERENCE` vs `NEGATIVE` in `evidence.md` |
| Contradictions documented in the central register | Yes — C-014…C-018 appended; 5 new, none resolved |
| Unknowns documented in the central register | Yes — U-019…U-024 appended; U-009 and U-011 **resolved with evidence** |
| Compatibility findings documented with conditions | Yes — §6, 14 rows, phrased per §25 |
| Security implications documented, including abuse cases | Yes — §7, 15 rows with follow-up owners |
| Architecture implications documented, conflicts marked `CONTRADICTED` | Yes — §8, 14 rows, 4 marked `CONTRADICTED` |
| Evidence IDs cited in the report all exist in the ledger | Yes — verified mechanically; every `P3-*` citation resolves (151 ledger rows, 61 report-side rows, 0 dangling) |
| Reverse direction (every ledger row cited in the report) | **No, and not claimed.** Most of the 151 rows are cited only by ID range or only in the ledger. Phase 2 claimed bidirectional traceability; that claim does **not** hold here and is not repeated. The ledger is the authority for row-level detail; the report cites the ranges and the rows that carry a decision |
| `MasterRef.md` not modified | Verified — `git status --porcelain MasterRef.md` returns empty |
| `ARCHITECTURE.md` not modified either | Verified — Phase 3 records that it is contradicted; the revision belongs to Architecture Confirmation / Revision after Phase 26 |
| No implementation work performed | Verified — `git status --porcelain` shows only `investigations/**` |
| Phase 0–26 structure unchanged | Verified |
| Register mirrors consistent | Verified — `python3 investigations/tools/check_register_mirrors.py` exits 0, 4 mirrors, 24 unknown rows and 18 contradiction rows each |
| Remaining limitations explicitly recorded | Yes — Appendix C, U-019…U-024, §5 weaknesses |

### Weaknesses stated plainly

1. **No device testing.** Items 14–16 are source-read only. The single most
   consequential unverified claim is P3-A121: whether a recycled PID is actually killed
   is a runtime fact, and only the absence of a check is verified here.
2. **Nightzuku is ~2.5 months behind the Shevery pin.** Divergence findings (P3-A79,
   P3-A80, P3-A126, P3-A127) describe the two forks at different dates, so they show
   divergence, not necessarily which side moved.
3. **The reference subsystem is small.** ~2 300 LOC across 12 files, and it was read in
   full. That raises confidence for `REFERENCE` findings and lowers it for anything
   about a host-shell-script variant that may exist outside the module package.
4. **Reference defects were analysed as defects, not as requirements.** §8 marks several
   `ARCHITECTURE.md` statements `CONTRADICTED`, but deciding what this project should do
   instead is deliberately left to Phases 6, 9 and 13 — Phase 3 records the choice as
   `PROPOSED`, not made.
5. **No Porter evidence.** Phase 4 has not run, so §8's `P3-AR04` can say the session
   trigger "must not be ported to Porter" but cannot say what should replace it.

### Self-audit result

**PASS on coverage; `Complete` withheld.** All 17 checklist items are addressed with
evidence, 14 complete and 3 partial with the residual explicitly assigned to Phase 14
and registered as U-019/U-020. Four of the seventeen items (14, 15, 16 and the runtime
half of 19) cannot be closed without a device or a Porter dependency, neither of which
exists in this environment.

Status is therefore recorded as **Audited**, not **Complete**. Under
`INVESTIGATION_METHOD.md` §43, `Complete` requires that remaining limitations are
explicitly recorded *and* that nothing further is required; here something further is
required. The correct next action is Phase 4, which resolves U-001 and with it U-019 —
at which point the three partial items can be revisited.
