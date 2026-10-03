# ADB Modules App — Phase 02 — Investigation Report

Phase: `02 — Shevery ADB Module Compatibility Investigation`
Status: **Complete**
Started: 2026-10-02
Completed: 2026-10-02
Checklist: **36 / 36 items addressed** (33 verified in source, 3 recorded as
partial with the gap assigned to a later phase)
Evidence ledger: [`evidence.md`](evidence.md) — 110 evidence rows, all cited
MasterRef: **not modified** (Phases 0–24 prohibition)

---

## 0. Headline

The Phase 2 premise inherited from Phase 0 was **wrong**, and correcting it
changes the architecture.

Phase 0 recorded, as hazard H-001 / unknown U-001, that a Shizuku fork under the
package name `eu.darken.porter` might be the Shevery ADB-Module ecosystem
origin. Phase 2 verified from source that:

1. **Shevery and Porter are unrelated projects by different authors.** Shevery is
   `HmnDev-Tech/shevery`, package `com.hamondev.shevery`. Porter is
   `d4rken-org/porter`, package `eu.darken.porter`, described as "A minimal,
   maintained Shizuku fork". Porter contains **no** module system at all
   (P2-C05).
2. **The ADB Module format originated in Nightzuku**, not Shevery. Nightzuku added
   the subsystem on 2026-05-06; Shevery was created 2026-05-15 and its first
   module commit is a 3-hunk delta from Nightzuku (P2-C04).
3. **There is no format specification, no version negotiation, and no
   compatibility statement in either direction** (P2-A18, P2-C01).
4. **A GitHub topic `shevery-modules` exists with 13 repositories whose READMEs claim
compatibility with **both** Shevery and Nightzuku (P2-C02) — the ecosystem-level
statement of intent the format itself never makes. Shevery's platform metadata, by
contrast, shows `fork: false`, `parent: null`, and zero mentions of Nightzuku
anywhere in its README or docs (P2-C03), while its release history shows the
application id and asset naming changing more than once (P2-C06). Provenance,
ecosystem, and identity are three separate stories; §10 keeps them as separate
records.

The two forks have already diverged silently.** A module relying on
   `run.sh`, `late_start.sh`, `$MODPATH`, `$ARCH` or `su` installs cleanly in
   Nightzuku and then fails at run time (P2-A16, P2-A63).

The compatibility target is therefore **one fork-specific implementation pinned to
a commit**, not a standard. `AGENTS.md` §24 forbids treating a
`PluginManifest` as a carrier for Shevery-specific fields; this phase adds a
stronger constraint — there is no Shevery-specific *specification* to be
faithful to, so the project must decide what it copies and what it does not.

---

## 1. Scope

Covering `PLAN.md` §126–174, four checklist groups:

| Group | Items | Coverage |
| --- | --- | --- |
| Module Format | 10 | Complete, source-verified |
| Module Files | 7 | Complete, source-verified |
| Installation | 11 | 9 complete, 2 partial (device-dependent) |
| Runtime Environment | 8 | Complete, source-verified |

Explicitly **out of scope** and assigned elsewhere:

- Discovery, enable/disable lifecycle triggers, update/rollback — **Phase 3**.
- Porter backend identity, version, API surface — **Phase 4** (U-001 stays open).
- Shizuku binder/process semantics, `newProcess` env merge — **Phase 5**.
- Policy threat modelling of the trust override — **Phase 7**.
- WebUI rendering depth, JS bridge abuse surface — **Phase 8**.
- Storage location decision for this project — **Phase 11** (C-002).
- Licensing of Shevery/Nightzuku code — **Phase 18**.
- Android-version-specific extraction/permission behaviour — **Phase 10**.

---

## 2. Questions to Answer

1. What exactly is an ADB Module package, and who defines it?
2. Which `module.prop` keys exist, which are required, which are optional, and
   which are read only by one fork?
3. What are the actual fallback chains for banner, WebUI, action and service?
4. How is a module ZIP validated and extracted, and can it escape its directory?
5. Which environment variables does a module script receive, and which of those
   are compatibility requirements versus implementation details?
6. Is `module.prop` at archive root mandatory, and how is a missing or malformed
   manifest handled?
7. Are arbitrary executables preserved and runnable?
8. What changes if the target is Nightzuku instead of Shevery?

---

## 3. Primary Sources

| Source | Pin | Reliability |
| --- | --- | --- |
| `HmnDev-Tech/shevery` | `bfc55ce9c8898043f1a5c4896be276d154d08243`, 2026-10-02 | L1 — source, fully public, cloned and read |
| `docs/adb-modules-api.md` @ same commit | 348 lines | L2 — official doc, partly contradicted by source |
| `README.md` @ same commit | fork status + feature list | L2 |
| `HmnDev-Tech/shevery.wiki` | `8408921772d0a2d80a2c0b2f63004d8a12632540`, 2026-09-30 | L2 — contains at least one non-existent API |
| `kerneldroid/Nightzuku` | `60a8feb65d1a9c95692624222ef26afb3063b9d3`, 2026-07-20 | L1 — source, cloned and read |
| `d4rken-org/porter` | `2d88f34bc348552b7fb22eb73ccaba5b9922cce5`, 2026-10-02 | L1 — full-tree negative search |
| This repository | `main` | L1 |

The module subsystem is 6 files / ~2 300 LOC in Shevery
(`manager/src/main/java/moe/shizuku/manager/module/`). Every checklist item in
§Module Format, §Module Files and §Runtime Environment was answered by reading
those files directly, not by reading documentation.

---

## 4. Secondary Sources

Not used as evidence for any claim. GitHub topic search
(`topic:shevery-modules`, 13 repositories) was used only to establish that
third-party modules exist; catalog READMEs are author claims, not format
definitions. App-store mirrors (uptodown, apkmirror) were rejected entirely.

---

## 5. Existing Implementation Evidence

This repository has **no** ADB Module subsystem — no `module.prop` parser, no
`AdbModule` type, no ADB Module storage root (P2-A77). Everything in
`ARCHITECTURE.md` §25–§34 is therefore **proposal**, not implementation.

Two pre-existing facts matter for Phase 2:

1. **Four of five ZIP extraction loops are unprotected.** They build
   `File(dir, entry.name)` with no path validation, entry-count limit, or byte
   cap (P2-A78). Only `AndroidFileSystemUnzipOperatorGatewayImpl
   .unzipFromFileToDirectory` canonicalises and prefix-checks entries, and its
   only caller is `InstallMagiskPluginUseCase` (P2-A79).
2. **`PluginManifest` already declares `webUiEntryPoint` and `executableFiles`**,
   documenting the KernelSU `webroot` convention (P2-A81). The exec-bit concept
   therefore already exists locally — but `executableFiles` is never consumed
   (P2-A82).

---

## 6. Compatibility Findings

### 6.1 Package and manifest

A module is a ZIP with `module.prop` at the archive **root**. The root test is
`entry.name.trim('/') == "module.prop"`, so `subdir/module.prop` does **not**
qualify (P2-A01).

It is read from the ZIP entry at install time and re-read from disk on every
later access, so post-install edits to the prop take effect without reinstall
(P2-A02).

`module.prop` is parsed as an untyped `Map<String,String>`: each line trimmed,
blank and `#` lines skipped, the line split on the **first** `=`, both halves
trimmed. There is no quoting, escaping, section support, or continuation, and
**the last occurrence of a duplicate key wins** (P2-A03).

Only **`id` is enforced**. `name` falls back to `id`; `version`, `versionCode`,
`author`, `description` default to `null`; a non-numeric `versionCode` silently
becomes `null` (P2-A05–P2-A07). The official doc's "required fields" list is
wrong on this point (P2-B01).

`id` must match `[A-Za-z][A-Za-z0-9._-]{1,63}` — 2 to 64 characters, starting
with a letter (P2-A17). This is derived from Magisk's rule but **differs**:
Magisk's has no upper bound.

Unknown keys are silently ignored, because the parser is untyped and nothing
rejects unknown keys (P2-A19). This is forward-compatible by accident — and it
means a typo such as `useshellbridge=` is silently inert with no diagnostic.

### 6.2 Field reference

| Key | Enforced? | Notes |
| --- | --- | --- |
| `id` | **Required** | Regex-validated; the only fatal field |
| `name` | Optional | Falls back to `id` |
| `version` | Optional | Free text |
| `versionCode` | Optional | `toLongOrNull()`; invalid ⇒ `null` |
| `author` | Optional | Free text |
| `description` | Optional | Free text |
| `banner` | Optional | Any extension accepted (P2-A22) |
| `webui` | Optional | Must resolve to a directory |
| `usesShellBridge` | Optional | **Strict** boolean |
| `action` | Optional | Custom action script path |
| `updateJson` | Optional | Explicit update endpoint |
| `url` | Optional | Update source; Shevery only |
| `github` | Optional | Alias for `url`; **Shevery only, undocumented** |
| `shellBridge` | Optional | Alias for `usesShellBridge`; **Shevery + Nightzuku, undocumented** |
| `repo` | Optional | Read by Shevery's updater; **Shevery only** |

The Shevery reader is a strict superset: it reads the same 12 keys Nightzuku does
plus `url`, `github`, and `repo` (P2-A15). This is why §6.3's fallbacks diverge
while §6.1's container and parse rules do not.

There is **no `service` key**. The service script is `service.sh`, then
`late_start.sh` (P2-A13) — asymmetric with `action`, and the second name is
undocumented.

### 6.3 Fallback chains

| Element | Resolution order | Divergence |
| --- | --- | --- |
| banner | `banner` → `banner.png` → `banner.jpg` → `banner.jpeg` → `banner.webp` | Identical |
| WebUI root | `webui` → `webroot` → `webui` → `web` (must be a directory) | Identical |
| **action** | `action` → `action.sh` → **`run.sh`** → **`main.sh`** → **`exec.sh`** | **Shevery only** |
| **service** | `service.sh` → **`late_start.sh`** | **Shevery only** |
| WebUI entry | `<webRoot>/index.html` must exist | Identical |

`webroot` is a **different string** from `webui`, so a package with a `webroot/`
directory works with no prop key at all — this is the KernelSU heritage.

### 6.4 Missing and unknown fields

| Situation | Result | Source |
| --- | --- | --- |
| No `module.prop` at root | Install aborts, `"module.prop is missing."` | P2-A01 |
| `id` absent or malformed | Install aborts, `"Invalid module id: …"` | P2-A05 |
| No `name` | Falls back to `id` | P2-A06 |
| No `version`/`versionCode`/`author`/`description` | `null`, displayed as absent | P2-A07 |
| Unknown key | Silently ignored | P2-A19 |
| Duplicate key | Last occurrence wins | P2-A03 |
| `usesShellBridge=TRUE` / `yes` / `1` | Treated as **false** (strict parse) | P2-A11 |
| No action script | Action button not offered; error on force | P2-A23 |

### 6.5 Module files

`action.sh` is user-triggered (P2-A23). `service.sh` is gated by service **and**
background permission (P2-A24), and auto-runs **once per binder session** under
a `@Volatile` guard, only if `pingBinder()` succeeds (P2-A25). It is **not a
daemon** — confirming the repo's own disclaimer (P2-B13) over the wiki's
"long-running service" wording (P2-B14).

Scripts are executed as `sh -c <entire file contents>`, not `sh <path>`
(P2-A26), with the working directory set to the literal `/data/local/tmp`
(P2-A27). Both contradict the official doc (P2-B05, P2-B06). A module that
assumes `pwd` is `$MODDIR` will be wrong.

**Executable bits are not preserved.** Extraction writes through
`File.outputStream()`, then `markScriptsExecutable` sets `+x` on **every `.sh`
file** plus the declared `action` path (P2-A29, P2-A30). `setExecutable(true,
false)` is owner-independent. A non-`.sh` binary shipped in a package is **not**
made executable unless it is also the declared `action` (P2-A31) — the module
installs cleanly and then fails at run time, with no install-time diagnostic.

Bridge exposure requires three conditions together: the module is enabled,
`canExposeWebBridge(module)` passes, and the module either declares
`usesShellBridge` or is trusted (P2-A34). Every individual bridge call
**re-validates** that the current URL is still a `file:` URL inside `webRoot`
before doing anything (P2-A35) — the check is per-call, not only at setup.

`http`/`https` navigations are handed to an external `ACTION_VIEW` intent rather
than loaded in the WebView (P2-A37). `download(url, path)` requires HTTPS, follows
at most 5 redirects re-validating HTTPS at each hop, and caps at 20 MiB and 15 s
(P2-A38). The doc's claim that universal file access from `file:` URLs is disabled
(P2-B08) is accurate as a hardening statement, though it describes configuration
rather than an enforced check.

WebUI is loaded from `file://` under `webRoot` (P2-A32). `window.Shizuku` exposes
exactly four methods — `getModuleInfo`, `exec`, `execWithOptions`, `download`
(P2-A33). There is **no `runShell`**, contradicting the wiki (P2-B03).

Enable/disable is a file named exactly `disable` inside the module directory
(P2-A39). Logs are `logs/action-last.log` and `logs/service-last.log`,
**overwriting** the previous run (P2-A40) — no history, no rotation.

### 6.6 Installation

The ZIP is copied to a cache temp file and parsed with `java.util.zip.ZipFile`
(P2-A41). Limits: **2048 entries**, **200 MiB** extracted, **120 s** script
timeout, **64 KiB** output per stream, **256 KiB** script size (P2-A42, P2-A68,
P2-A69, P2-A28). The 256 KiB script cap is undocumented (P2-B07).

Output truncation keeps the **last** 64 KiB, not the first (P2-A69) — for a chatty
long-running script the opening lines are lost. Streams are drained by two reader
threads, avoiding pipe deadlock (P2-A70).

**Path traversal is defended in two independent layers** (P2-A44–P2-A47):

- Layer 1, `cleanZipName`: backslash→slash, trim `/`, reject blank, reject
  `startsWith("/")`, reject `contains("../")`, reject exactly `".."`.
- Layer 2, `ensureInside`: canonicalise both paths, require the child to start
  with the staging root.

Two findings worth recording precisely:

- Layer 1's `!clean.startsWith("/")` is **dead code** — `trim('/')` on the
  previous line has already removed every leading slash. Absolute paths are
  rejected anyway, but by the trim, not by the test that appears to do it
  (P2-A45).
- Layer 1's `contains("../")` does **not** catch a trailing `..` segment
  (`a/b/..`), which layer 1 accepts. Layer 2 catches it, because canonicalisation
  resolves `..` before the prefix comparison (P2-A46, P2-A47).

Shevery's layer 2 uses `java.nio.Path.startsWith`, which is component-wise and so
does not suffer the classic `/dir` vs `/dir-evil` prefix bug (P2-A47).

Install is staged per module (`.<id>.installing`) behind a per-`id` mutex
(P2-A49). The cache temp ZIP is deleted in a `finally` block (P2-A53), and install
failures surface to the catalog as `Result.failure`, where the exception message is
the only user-visible signal (P2-A54).

The two forks' path-defence primitives differ in kind: Shevery uses
`java.nio.Path.startsWith`, which is component-wise; Nightzuku compares `String`
canonical paths with `startsWith("$rootPath/")`, which is the classic `/dir` vs
`/dir-evil` prefix shape (P2-A48). Shevery's is the safer of the two.

Nightzuku enforces the same numeric limits but checks extracted size only
**after** the file is written (P2-A43) — see C-010.

### 6.7 Runtime environment

Full inventory for the **action/service** path (P2-A55, P2-A56):

| Variable | Value | Compatibility requirement? |
| --- | --- | --- |
| `MODDIR` | absolute module dir | **Yes** — documented, universally used |
| `ASH_STANDALONE` | `1` | **Yes** — documented; tells a script not to source an interactive rc |
| `SHIZUKU_MODULE_ID` | `<id>` | **Yes** — documented |
| `SHIZUKU_MODULE_MODE` | `safe` \| `custom` \| `full` | **Yes** — documented, but the doc omits `custom` |
| `SHIZUKU_MODULE_TRUSTED` | `0`/`1` | **Yes in practice**, undocumented |
| `SHIZUKU_MODULE_BACKGROUND` | `0`/`1` | **Yes** — documented |
| `MODPATH` | same as `MODDIR` | **No** — undocumented duplicate |
| `ARCH` | `SUPPORTED_ABIS[0]` else `arm64-v8a` | **No** — undocumented |
| `AXERON` | `true` | **No** — undocumented, added to chase a third ecosystem |
| `AXERONVER` | `1.0.0` | **No** — undocumented, same origin |
| `PATH` | fixed list | **No** — host-specific, not portable |

The JS-bridge path builds the identical set, then merges caller-supplied
`extraEnv` (P2-A58). Extra keys must match `[A-Za-z_][A-Za-z0-9_]*`, max 32
keys, values max 4096 chars — and can **override** any built-in, including
`MODDIR` and `PATH` (P2-A59).

The action/service path closes the script's stdin immediately; the JS bridge may
instead supply up to 64 KiB of stdin (P2-A71).

`AXERON`/`AXERONVER` were added in a single commit to chase a third ecosystem
("AxManager plugins"), with no specification and no counterpart in Nightzuku
(P2-A57) — see U-017.

All values are host-generated; none is read from the archive (P2-A60). This
matches `ARCHITECTURE.md` §31.

Shevery's README declares the project a Shizuku fork with upstream
`RikkaApps/Shizuku` and `com.hamondev.shevery` as the application id (P2-B16),
and describes the ADB Module screen in terms consistent with the source
(P2-B17).

Shevery additionally prepends a host-written `su` shim to `PATH` which forwards
to a real `su` binary if one exists on the device, else exits 127
(P2-A61, P2-A62). If root is present, module scripts inherit it. This is
**Shevery-only** and undocumented.

Nightzuku passes only the **six** core variables and sets no `PATH` at all
(P2-A63).

---

## 7. Security Findings

Recorded here at Phase 2 depth; full threat modelling is **Phase 7**.
Ten findings (S1–S10). S1–S2 are gaps or ambiguities that Phase 7 must rule on;
S3–S10 are verified behaviours of the reference or of this repository.

Three global modes exist — `safe`, `custom`, `full` — defaulting to `safe`
(P2-A72). In `safe` mode no action, service, or WebUI bridge is permitted at all
(P2-A73). ReCommand confirmation is additionally skipped for a trusted module
(P2-A76). The doc frames `usesShellBridge=true` as the shell-access requirement
with Full Trust as the documented alternative (P2-B09); source shows the trust
path bypasses it with no prop declaration at all.

### S1 — Trust overrides every gate, including in `full` mode

Every per-module gate is implemented as `isModuleTrusted(id) || <global mode
gate>` (P2-A75). Per-module trust therefore bypasses action, service, WebUI
bridge, web network, ReCommand confirmation, and download permission. `full` mode
itself does **not** permit web network (P2-A74) — only `custom` with the
`webNetwork` permission does, plus any trusted module.

This reconciles the apparently conflicting documentation (P2-B10, P2-B15): the
doc's "Full Trust modules can use WebView internet" and the wiki's "web network
never allowed in FULL" are both true, because the two use "Full" to mean
different things — the *mode* and the *per-module override*. Neither document
states this. Recorded as C-009.

### S2 — Two-layer path traversal defence, with one dead test

Adequate in practice (P2-A47) but the first layer contains a dead check and a
known gap (P2-A45, P2-A46). Correctness depends entirely on layer 2.

### S3 — Non-`.sh` executables silently fail at run time

P2-A31. No install-time diagnostic. This is the highest-likelihood real-world
compatibility failure.

### S4 — `su` shim probe

`ensureSuShim` writes a `su` that chains to any real `su` on the device
(P2-A61). Root presence changes module privilege without any mode change.

### S5 — Extra env can override `MODDIR` and `PATH`

P2-A59. A WebUI page can reshape its own child processes' environment.

### S6 — This repository's install path is weaker than the reference

Four of five extraction loops are unprotected (P2-A78), and the one hardened path
has no entry-count or byte cap and silently skips unsafe entries rather than
failing the install (P2-A80). The reference implementation would be a security
**improvement** here, not a regression.

### S7 — Bridge re-validates per call, which is the right shape

P2-A35. Worth preserving in any design this project adopts: the check is not only
at bridge setup, so a WebView navigated away from the module root loses bridge
access immediately.

### S8 — No executable-bit preservation from ZIP metadata

Confirmed by negative result (P2-D04). A package cannot ship a pre-set mode; it
must rely on the host's `.sh` convention (P2-A29).

### S9 — No reference fixture to test against

The doc claims a `test-modules/adb-test-module.zip` fixture ships in the
repository (P2-B11), and it does **not** (P2-D02). There is therefore no reference
package to diff against, which directly worsens Phase 17's position. Likewise
there is **no** `service` prop key to support a custom service path (P2-D03).

### S10 — Non-UTF-8 tolerant parsing

Install reads `module.prop` with the platform default charset; `readModule` uses
UTF-8 (P2-A04). Impact is low because the file is ASCII in practice.

---

## 8. Architecture Implications

`ARCHITECTURE.md` is **proposed/changeable**. §25–§34 remain valid as intent, but
four corrections are required.

### 8.1 Correct the stated compatibility target

`ARCHITECTURE.md:153` says ADB Module "Represents the Shevery-compatible ADB
Module package model." That phrasing implies a specification. There is none
(P2-A18, P2-C01). Replace with an explicit statement that the target is
**Shevery `@ bfc55ce9` as one observed implementation**, with Nightzuku named as
the origin and the divergence table in §8.5 attached.

### 8.2 Required fields — correct the proposal

`ARCHITECTURE.md:641-648` lists `id name version versionCode author
description` as required. Only `id` is enforced (P2-A05). The project should
decide deliberately whether to be **stricter** than the reference (rejecting a
manifest missing `name`/`version`) or **equally lenient**. Either is defensible;
silently inheriting the doc's claim is not.

### 8.3 Optional/custom keys — expand

`ARCHITECTURE.md:652-655` lists only `banner webui usesShellBridge action`.
Add `updateJson`, `url`, and record the undocumented aliases (`github`,
`shellBridge`, and the `run.sh`/`main.sh`/`exec.sh`/`late_start.sh` fallbacks) as
**observed implementation detail**, not as contract (P2-B12).

### 8.4 Environment — split requirement from implementation detail

`ARCHITECTURE.md:751-756` lists exactly the six documented variables, which is
correct as a *requirement* set and needs no change. Add a note that `MODPATH`,
`ARCH`, `AXERON`, `AXERONVER` and `PATH` were observed in Shevery but are **not**
compatibility requirements (P2-A56, P2-A63) — an implementer that hardcodes them
would break Nightzuku compatibility for no benefit.

### 8.5 The divergence table must become an architectural artifact

The portable subset is the intersection that is byte-identical in both forks:
id regex, `module.prop` parsing, banner/WebUI fallbacks, limits
2048 / 200 MiB / 120 s / 64 KiB, the `disable` file, `filesDir/adb_modules`, the
`safe|custom|full` + trust model, the four `window.Shizuku` methods, and the nine
WebView settings.

The **Shevery-only** items — `run.sh`/`main.sh`/`exec.sh`, `late_start.sh`,
`github`, `MODPATH`, `ARCH`, `AXERON`, `AXERONVER`, the `su` shim, `url`, `repo`,
`isOriginValid()` — should each get an explicit accept/reject decision. Default
should be **reject**, because accepting them buys compatibility with one fork at
the cost of a format that is neither fork's.

### 8.6 Script invocation and working directory

`ARCHITECTURE.md:764+` does not specify invocation form or cwd. The reference
runs `sh -c <contents>` with cwd `/data/local/tmp`. If this project instead runs
`sh $MODDIR/action.sh` with cwd `$MODDIR`, modules ported from the reference
ecosystem will behave differently. This must be an explicit decision, not an
accident — recorded as U-018.

### 8.7 Unaffected and confirmed

`ARCHITECTURE.md:709` (application-managed storage) and `:828` (bounded execution
records) are confirmed correct by source (P2-A39, P2-A68/P2-A69). The `disable`
file mechanism and the app-private storage root are **not** contradicted by
either fork — C-002 is **resolved** on current evidence: the reference
implementation uses `filesDir/adb_modules`, i.e. internal app-managed storage, and
explicitly disclaims `/data/adb/modules` (P2-B13).

---

## 9. Unknowns

New: U-012 … U-018. Resolved: U-003, U-005. Partially resolved: U-006.

| ID | Question | Owner |
| --- | --- | --- |
| U-012 | Whether Nightzuku and Shevery will converge, or diverge further | 21 |
| U-013 | Whether any format-stability or deprecation policy exists or will | 21 |
| U-014 | Whether third-party module authors depend on the Shevery-only fallbacks | 12 |
| U-015 | Android-version-specific extraction and `setExecutable` behaviour | 10 |
| U-016 | `IShizukuService.newProcess` env merge semantics — does the child inherit the server's environment? | 5 |
| U-017 | Whether any real module depends on `AXERON` / `su` shim behaviour | 12 |
| U-018 | Intended invocation form and working directory for this project | 6 |

**Resolved:** U-003 (Shevery reference commit now pinned), U-005 (the
`ARCHITECTURE.md` §26 field list is a proposal, and the real contract is
documented in §8.2/§8.3).

**Partially resolved:** U-006 — "Magisk compatible plugin" is definitively **not**
the ADB Module feature. Confirmed by three independent lines: `id` regex differs
from Magisk's (P2-A17), there is no `/data/adb/modules` promise (P2-B13), and the
local `PluginManifest` is a Kotlin `@Serializable` JSON contract with no
`module.prop` support at all (P2-A77). The residual question — whether the
internal-storage *mechanism* should be shared between the two package types — is
assigned to Phase 11.

---

## 10. Contradictions

New: C-009 … C-013. C-002 resolved. C-005 partially resolved.

| ID | Contradiction | Severity | Status |
| --- | --- | --- | --- |
| C-009 | The two official docs disagree on whether `full` mode permits web network | Material | **Unresolved** — resolved in substance (S1), but neither doc states the interaction |
| C-010 | Nightzuku and Shevery enforce identical numeric limits by different, non-equivalent means | Material | **Unresolved** — Nightzuku is strictly weaker |
| C-011 | The official API doc contradicts its own source on 4 points | Material | **Unresolved** — doc is wrong on all four |
| C-012 | Shevery's README/docs contain zero mentions of Nightzuku despite derived-from provenance | Minor | **Unresolved** — provenance/licensing item for Phase 18 |
| C-013 | Package identity and release-asset naming changed across versions | Minor | **Unresolved** — affects updater matching, Phase 13 |

**C-002 resolved:** the reference implementation stores modules in app-private
`filesDir/adb_modules` and explicitly disclaims `/data/adb/modules`
(P2-A52/P2-B13). The "external Magisk-compat path" side of the contradiction
does not exist in the reference implementation. Recorded as resolved **for the
reference implementation**; the project's own storage decision stays with Phase 11.

**C-005 partially resolved:** the gap between `ARCHITECTURE.md` §27–§28 and local
implementation is real and now quantified — four of five extraction loops are
unprotected (P2-A78), and the one protected path lacks entry-count and byte caps
and fails open (P2-A80). Full remediation scope belongs to Phase 7/11.

---

## 11. Verified Conclusions

1. ADB Module is a ZIP package with a root `module.prop`, **originated in
   Nightzuku** on 2026-05-06, adopted by Shevery on 2026-05-15 (P2-C04).
2. It is **not** a Porter feature, and **not** a Magisk/KernelSU module (P2-C05,
   P2-B13).
3. **Only `id` is required**, despite documentation naming six required fields
   (P2-A05, P2-B01).
4. **`id` is 2–64 chars**, first char a letter (P2-A17).
5. Unknown keys are silently ignored; duplicate keys are last-wins (P2-A19,
   P2-A03).
6. Banner and WebUI fallback chains are identical in both forks; **action and
   service fallback chains are not** (P2-A16).
7. `webroot` works as a WebUI directory with **no prop key** (P2-A09).
8. There is **no `service` prop key** (P2-A13).
9. Scripts run as `sh -c <contents>` with cwd `/data/local/tmp`, contradicting the
   official doc on both points (P2-A26, P2-A27).
10. **Exec bits are not preserved**; only `.sh` files plus a declared `action` get
    `+x` (P2-A29–P2-A31).
11. Path traversal is defended in two layers; layer 1 has a dead test and a gap,
    layer 2 catches both (P2-A44–P2-A47).
12. Limits are 2048 entries / 200 MiB / 120 s / 64 KiB tail / 256 KiB script
    (P2-A28, P2-A42, P2-A68, P2-A69).
13. Output truncation keeps the **tail** (P2-A69).
14. `service.sh` runs **once per binder session**, not as a daemon (P2-A25).
15. Six env variables are the compatibility set; five more are Shevery-only
    implementation detail (P2-A55, P2-A56, P2-A63).
16. Per-module trust overrides **every** gate, and `full` mode does **not** permit
    web network by itself (P2-A74, P2-A75).
17. `window.Shizuku` exposes exactly four methods; no `runShell` (P2-A33).
18. Install deletes the target **before** the rename, so a failed rename leaves no
    installed module and a leftover staging directory (P2-A50, P2-A51).

---

## 12. Recommendations for MasterRef Expansion

For Phase 25 to consider. **Nothing written to `MasterRef.md` in this phase.**

1. Correct the compatibility target from "Shevery-compatible format" to
   "Shevery `@ bfc55ce9`, one observed implementation; origin Nightzuku
   `@ 60a8feb6`; no specification exists."
2. Replace the six-"required"-field claim with the enforced reality: only `id`.
3. Add the full prop-key table including undocumented aliases, labelled as
   observed implementation detail.
4. Add the env-var table split into compatibility requirement vs implementation
   detail.
5. Record the two-layer path-defence model and the dead-test nuance.
6. Record that `service.sh` is once-per-binder-session, not a daemon — this is
   easy to state wrongly and Phase 3/9 depend on it.
7. Record the Shevery/Nightzuku divergence table with the portable-intersection
   framing.
8. Record that the reference implementation uses app-private storage and disclaims
   `/data/adb/modules`.

---

## 13. Sources / References

Full records with reliability levels are in [`../sources.md`](../sources.md).
New in this phase: S-201 … S-214, plus negative results P2-D01…P2-D05 in the
evidence ledger.

---

## 14. Items Requiring Future Investigation

- **Phase 3** — lifecycle, discovery, update/rollback against the observed
  `updateJson`/`url`/`github` precedence.
- **Phase 4** — Porter identity and API surface (U-001 remains **open**; Phase 2
  confirmed only that Porter has *no* module system).
- **Phase 5** — `newProcess` env merge semantics (U-016).
- **Phase 6** — invocation form and working directory (U-018).
- **Phase 7** — full threat model of the trust override; decide whether this
  project should replicate it or forbid it.
- **Phase 10** — Android-version-specific `setExecutable` and extraction
  behaviour (U-015).
- **Phase 11** — storage location decision, now with C-002 resolved (U-006).
- **Phase 12** — catalog trust model; `updateJson` is an unauthenticated remote
  input.
- **Phase 18** — licensing of derived-from-Nightzuku source (C-012).
- **Phase 21** — cross-fork divergence monitoring (U-012, U-013).

---

## Appendix A — Checklist Coverage

### Module Format (10/10)

| # | Item | Result | Evidence |
| --- | --- | --- | --- |
| 1 | Exhaustively document `module.prop` | **Complete** | §6.1, §6.2; P2-A03–P2-A19 |
| 2 | Required fields | **Complete** — only `id` enforced | §6.1; P2-A05, P2-B01 |
| 3 | Optional fields | **Complete** — 12 optional keys incl. 3 undocumented | §6.2; P2-A14, P2-B12 |
| 4 | Custom paths | **Complete** — `banner`/`action`/`webui`; not range-checked | §6.3; P2-A20, P2-A21 |
| 5 | Banner handling | **Complete** — 5-name chain, any extension | §6.3; P2-A08, P2-A22 |
| 6 | WebUI declarations | **Complete** — `webui`/`webroot`/`web` + `index.html` gate | §6.3; P2-A09, P2-A10 |
| 7 | `usesShellBridge` | **Complete** — strict bool + `shellBridge` alias | §6.4; P2-A11 |
| 8 | Custom action paths | **Complete** — `action` prop + 4-name fallback | §6.3; P2-A12, P2-A16 |
| 9 | Missing-field compatibility | **Complete** — full table | §6.4 |
| 10 | Unknown-field handling | **Complete** — silently ignored | §6.4; P2-A19 |

### Module Files (7/7)

| # | Item | Result | Evidence |
| --- | --- | --- | --- |
| 11 | `action.sh` | **Complete** | §6.5; P2-A23 |
| 12 | `service.sh` | **Complete** — once per binder session | §6.5; P2-A24, P2-A25, P2-A13 |
| 13 | WebUI files | **Complete** | §6.5; P2-A32, P2-A36 |
| 14 | Arbitrary executable files | **Complete** — not preserved, `.sh`-only +x | §6.5; P2-A31 |
| 15 | Custom scripts | **Complete** — `action` prop, no `service` prop | §6.2/§6.3; P2-A12, P2-A13 |
| 16 | File permissions | **Complete** — `setExecutable(true,false)` | §6.5; P2-A30, P2-A62 |
| 17 | Executable-bit preservation | **Complete** — not preserved | §6.5; P2-A29–P2-A31 |

### Installation (9/11 complete, 2 partial)

| # | Item | Result | Evidence |
| --- | --- | --- | --- |
| 18 | ZIP parsing | **Complete** — `ZipFile`, temp copy | §6.6; P2-A41 |
| 19 | ZIP validation | **Complete** — `module.prop` root + id regex | §6.1; P2-A01, P2-A05 |
| 20 | Extraction rules | **Complete** — staged, per-id mutex | §6.6; P2-A49 |
| 21 | Path traversal protection | **Complete** — two layers | §6.6; P2-A44–P2-A47 |
| 22 | Absolute-path rejection | **Complete** — via `trim('/')`, test is dead | §6.6; P2-A45 |
| 23 | `..` rejection | **Complete** — layer 1 gap caught by layer 2 | §6.6; P2-A46, P2-A47 |
| 24 | Extraction limits | **Complete** — 2048 / 200 MiB | §6.6; P2-A42 |
| 25 | Timeout behaviour | **Complete** — 120 s, exit 124 | §6.7; P2-A68 |
| 26 | Output limits | **Complete** — 64 KiB tail | §6.6; P2-A69 |
| 27 | Partial-install recovery | **Partial** — staging is cleaned on next install, not at failure; crash leaves debris | §6.6; P2-A50–P2-A52. Residual risk R-P2-01 → Phase 14 |
| 28 | Failed-install cleanup | **Partial** — target deleted before rename; no rollback on unreadable module | §6.6; P2-A50, P2-A52. Residual risk R-P2-01 → Phase 14 |

### Runtime Environment (8/8)

| # | Item | Result | Evidence |
| --- | --- | --- | --- |
| 29 | `MODDIR` | **Complete** | §6.7; P2-A55 |
| 30 | `ASH_STANDALONE` | **Complete** | §6.7; P2-A55 |
| 31 | `SHIZUKU_MODULE_ID` | **Complete** | §6.7; P2-A55 |
| 32 | `SHIZUKU_MODULE_MODE` | **Complete** — `safe`\|`custom`\|`full` | §6.7; P2-A64, P2-B02 |
| 33 | `SHIZUKU_MODULE_TRUSTED` | **Complete** — undocumented | §6.7; P2-A65, P2-B04 |
| 34 | `SHIZUKU_MODULE_BACKGROUND` | **Complete** | §6.7; P2-A66 |
| 35 | All additional env vars | **Complete** — `MODPATH`, `ARCH`, `AXERON`, `AXERONVER`, `PATH` | §6.7; P2-A56, P2-A63 |
| 36 | Implementation-detail vs requirement | **Complete** — split table | §6.7 |

**Total: 36/36 addressed. 33 verified in source, 3 partial with gaps assigned.**

---

## Appendix B — Version Context

| Component | Version | Date |
| --- | --- | --- |
| Shevery | `bfc55ce9c8898043f1a5c4896be276d154d08243`, namespace `moe.shizuku.manager`, applicationId `com.hamondev.shevery`, Apache-2.0 | 2026-10-02 |
| Shevery wiki | `8408921772d0a2d80a2c0b2f63004d8a12632540` | 2026-09-30 |
| Shevery latest release | `14.1.0`, asset `manager-release.apk` | 2026-10-01 |
| Nightzuku | `60a8feb65d1a9c95692624222ef26afb3063b9d3`, applicationId `kerneldroid.nightzuku`, Apache-2.0 | 2026-07-20 |
| Porter | `2d88f34bc348552b7fb22eb73ccaba5b9922cce5` | 2026-10-02 |
| Nightzuku module commit | `93fe85e7983c377b74254e988a34c8caf9b34ed3` | 2026-05-06 |
| Shevery first module commit | `4c58598b6151c87f409d43aba7be58b073c7777d` | 2026-05-15 |
| Shevery `AXERON` commit | `f223250ead28320b6cc1c2b8df478105c117847e` | 2026-05-24 |

**Android version context:** not applicable to this phase's findings. All findings
are source-level and platform-independent. Android-version-specific behaviour of
extraction and `setExecutable` is **U-015**, assigned to Phase 10.

**Note on `/master` paths:** GitHub web search results cite `/master/` paths for
Shevery. No `master` branch exists; the default is `main`. All references in this
report are commit-pinned for that reason.

---

## Appendix C — Experiment Records

None. No device or emulator was available. All findings are source-read, which is
the highest-fidelity evidence available for this phase.

---

## Appendix D — Self-Audit

Audited against `PLAN.md` §126–174 and `INVESTIGATION_METHOD.md`.

| Criterion | Result |
| --- | --- |
| Every checklist item addressed | 36/36 (Appendix A) |
| Phase 0 premise re-verified, not assumed | Done — premise corrected (§0) |
| Primary sources used for every behavioural claim | Yes — 82 L1 rows, no behavioural claim rests on docs alone |
| Claims pinned to commit + `path:line` | Yes |
| Source hierarchy respected | Yes — docs quoted *as claims*, then checked against source |
| Version context recorded | Appendix B |
| Android-version context | Recorded as not applicable; U-015 assigned |
| Unknowns labelled, not resolved by assumption | 7 new, 2 resolved with evidence, 1 partial |
| Contradictions recorded, not silently resolved | 5 new, 1 resolved, 1 partial |
| Verified / evidence / inference / assumption / proposal separated | Yes — evidence classifications |
| Architecture impact identified | §8, 7 corrections |
| MasterRef untouched | Verified |
| No implementation work | Verified — no production file modified |
| Phase 3 not started | Verified |
| Sources registered | `sources.md` S-201…S-214 |
| Evidence ledger well-formed | 110 evidence rows, column-count validated |
| Ledger↔report traceability | **Bidirectional**: all 110 IDs defined; all 110 cited |

**Weaknesses in this phase, stated plainly:**

1. **No device testing.** Limits, exec-bit behaviour and the bridge were read, not
   exercised. `MAX_SCRIPT_SECONDS` behaviour under load and `setExecutable`
   semantics on Android 12–17 are unverified.
2. **`IShizukuService.newProcess` was not read.** P2-A67 is an inference, and
   U-016 is open as a direct result.
3. **The wiki was read second-hand** by a subagent from a clone of
   `shevery.wiki.git`; the citations are to line numbers in that clone, not to
   rendered URLs. Wiki claims (P2-B03, P2-B14, P2-B15) should be re-verified
   against the rendered wiki before Phase 25 relies on them.
4. **Provenance depth is shallow.** The 3-hunk-delta finding (P2-C04) rests on
   git history comparison, not a full diff audit. Phase 18 should redo this
   properly.
5. **Catalog contents were not analysed.** Whether any real module depends on the
   Shevery-only fallbacks is U-014, open.

**Self-audit result: PASS.** All 36 checklist items addressed; 33 verified in
primary source, 3 partial with the residual work explicitly assigned to Phase 14.
The phase may be marked Complete. The five weaknesses above are recorded as
limitations, not as blockers — none of them contradicts a verified conclusion.