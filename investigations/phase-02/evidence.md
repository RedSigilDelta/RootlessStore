# ADB Modules App — Phase 02 — Evidence Ledger

Phase: `02 — Shevery ADB Module Compatibility Investigation`
Maintained by: investigation session
Last updated: 2026-10-02

The authority for a claim is this ledger. The central `investigations/sources.md`
register is the authority for the *source*, not for the claim.

**Field definitions**

- **Claim** — the specific assertion, written so it could be falsified.
- **Level** — reliability of the source, `METHOD.md` §2.
- **Version / Commit** — the exact version or commit the evidence came from.
- **Evidence location** — `path:line`, commit sha, doc page/section, or
  experiment identifier.
- **Evidence type** — `Source Code`, `Official Documentation`,
  `Official Release`, `Official Issue`, `Official Discussion`, `Experiment`,
  `Device Test`, `Secondary Source`, `Inference`, `Conceptual Proposal`.
- **Classification** — `VERIFIED`, `DOCUMENTED`, `OBSERVED`, `INFERRED`,
  `PROPOSED`, `LEAD`, `UNKNOWN` (`METHOD.md` §3).
- **Currency** — `CURRENT`, `HISTORICAL`, `VERSION-SPECIFIC`,
  `UNKNOWN-CURRENCY` (`METHOD.md` §6.2).
- **Confidence** — `High`, `Medium`, `Low`, `None` (`METHOD.md` §4).

**Pinned revisions used throughout this ledger** (all cloned and read locally on
2026-10-02):

| Short name | Repository | Commit | Date |
| --- | --- | --- | --- |
| SHEVERY | `HmnDev-Tech/shevery` | `bfc55ce9c8898043f1a5c4896be276d154d08243` | 2026-10-02 |
| NIGHTZUKU | `kerneldroid/Nightzuku` | `60a8feb65d1a9c95692624222ef26afb3063b9d3` | 2026-07-20 |
| LOCAL | this repository | `main` | pre-Phase 2 |

---

## A. Claims supported by primary implementation evidence (L1)

### A.1 Package container and manifest location

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A01 | A module package is a ZIP; `module.prop` must exist at ZIP root or install aborts with `"module.prop is missing."` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:73-77` | Source Code | VERIFIED | CURRENT | High | Root match is `it.name.trim('/') == "module.prop"`, so `sub/dir/module.prop` does **not** satisfy it |
| P2-A02 | `module.prop` is read from the ZIP entry directly at install, and re-read from disk on every later read | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:77`, `:296-298` | Source Code | VERIFIED | CURRENT | High | Parsed twice; install-time parse gates, `readModule` re-derives |
| P2-A03 | The `module.prop` parser splits on the **first** `=`, trims both halves, skips blank lines and `#` comments, and requires `=` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:350-358` | Source Code | VERIFIED | CURRENT | High | No quoting, escaping, sections, or continuation. `associate` ⇒ **last duplicate key wins** |
| P2-A04 | `module.prop` must be UTF-8-decodable only when read from disk; the install-time read uses the platform default charset | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:77` vs `:298` | Source Code | VERIFIED | CURRENT | Medium | Asymmetry is a real but low-impact inconsistency; module.prop is ASCII in practice |

### A.2 module.prop keys

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A05 | `id` is the **only** key whose absence makes a module invalid; `readModule` returns `null` and install aborts on an id that fails the regex | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:78-79`, `:299` | Source Code | VERIFIED | CURRENT | High | Directly contradicts the "required fields" list in the official doc (see P2-B01) |
| P2-A06 | `name` is optional; it falls back to the value of `id` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:300` | Source Code | VERIFIED | CURRENT | High | |
| P2-A07 | `version`, `versionCode`, `author`, `description` are all optional and default to `null`; `versionCode` is parsed with `toLongOrNull()` so a non-numeric value silently becomes `null` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:304-307` | Source Code | VERIFIED | CURRENT | High | A malformed `versionCode` is indistinguishable from an absent one |
| P2-A08 | `banner` selects a banner file; if absent, `banner.png`, `banner.jpg`, `banner.jpeg`, `banner.webp` are tried in that order | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:309-316` | Source Code | VERIFIED | CURRENT | High | First match wins (`findFirstExisting`, `:451-458`) |
| P2-A09 | `webui` selects the WebUI root directory; if absent, `webroot`, `webui`, `web` are tried; the result must be a directory | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:317-323` | Source Code | VERIFIED | CURRENT | High | `webroot` is a **different string** from `webui`; a package with a `webroot/` dir works without any prop key |
| P2-A10 | WebUI is only offered when `<webRoot>/index.html` exists as a file | L1 | SHEVERY `bfc55ce9` | `AdbModule.kt:35-36`, `ModuleWebViewActivity.kt:36-40` | Source Code | VERIFIED | CURRENT | High | `hasWebUi` and the activity's early `finish()` |
| P2-A11 | `usesShellBridge` is read as a **strict** boolean (`toBooleanStrictOrNull`), falling back to the undocumented alias `shellBridge`, then `false` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:324-326` | Source Code | VERIFIED | CURRENT | High | Strict ⇒ `usesShellBridge=TRUE`, `yes`, `1` are all **`false`**. `shellBridge` is undocumented in the official doc |
| P2-A12 | `action` selects a custom action script path; if absent, `action.sh`, `run.sh`, `main.sh`, `exec.sh` are tried | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:327-334` | Source Code | VERIFIED | CURRENT | High | `run.sh`/`main.sh`/`exec.sh` are **undocumented in the official doc** |
| P2-A13 | There is **no prop key** for the service script; it is always `service.sh`, then `late_start.sh` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:335-339` | Source Code | VERIFIED | CURRENT | High | Asymmetric with `action`. `late_start.sh` is undocumented |
| P2-A14 | `url`, falling back to the undocumented `github`, supplies the update source; `updateJson` supplies an explicit update endpoint | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:345-346` | Source Code | VERIFIED | CURRENT | High | All three optional |
| P2-A15 | NIGHTZUKU reads 12 keys; SHEVERY reads the same 12 **plus** `url`, `github`, `repo`. SHEVERY is a superset | L1 | NIGHTZUKU `60a8feb6` vs SHEVERY `bfc55ce9` | `nightzuku/.../AdbModuleManager.kt:210-249` vs `.../AdbModuleManager.kt:295-348` | Source Code | VERIFIED | VERSION-SPECIFIC | High | `repo` read in `UpdateChecker.kt:52`. Nightzuku's release-update fallback is broken (`UpdateChecker.kt:117` derives owner from the literal dir name `adb_modules`) |
| P2-A16 | NIGHTZUKU's action fallback is `action` → `action.sh` only, and its service fallback is `service.sh` only | L1 | NIGHTZUKU `60a8feb6` | `AdbModuleManager.kt:242`, `:243` | Source Code | VERIFIED | CURRENT | High | **Divergent from SHEVERY.** Directly verified by local read |
| P2-A17 | Both forks use the identical id regex `[A-Za-z][A-Za-z0-9._-]{1,63}` (2–64 chars, first char a letter) | L1 | both | SHEVERY `AdbModuleManager.kt:32`; NIGHTZUKU `:29` | Source Code | VERIFIED | CURRENT | High | Mirrored in `discovery/ModuleValidator.kt:201` (byte-identical file in both) |
| P2-A18 | No format/version/schema key exists in either fork: `formatVersion`, `format_version`, `moduleFormatVersion`, `schemaVersion`, `specVersion` are all absent | L1 | both | repository-wide search of both module trees | Source Code | VERIFIED (negative) | CURRENT | High | Basis for U-013 |
| P2-A19 | Unknown `module.prop` keys are silently ignored — the parser builds an untyped `Map<String,String>` and no consumer rejects unknown keys | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:350-358`, `:295-348` | Source Code | VERIFIED | CURRENT | High | Forward-compatible by accident; a typo like `useshellbridge=` is silently inert |

### A.3 Custom paths and file resolution

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A20 | Every path-valued prop is trimmed of surrounding `/` before resolution, so `action=/x.sh`, `action=./x.sh` and `action=x.sh` behave differently | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:451-458` | Source Code | VERIFIED | CURRENT | High | Only `trim('/')`, no `..` normalisation — `action=../x.sh` resolves **outside** the module dir. Only reachable by a locally-modified `module.prop`; not ZIP-supplied at install |
| P2-A21 | A `banner`/`action`/`webui` path pointing outside the module directory is **not** range-checked | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:451-458` vs `:369-375` | Source Code | VERIFIED | CURRENT | High | `ensureInside` exists but is used only for extraction and JS-bridge cwd/download, not for prop resolution |
| P2-A22 | `banner` accepts any extension, not only `.png/.jpg/.jpeg/.webp`; the extension list applies only to the *fallback* filenames | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:309-316` | Source Code | VERIFIED | CURRENT | Medium | Doc wording ("`banner` can point to `.png`, …") is narrower than the code |

### A.4 Module files

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A23 | `action.sh` is a manual, user-triggered entry point, gated by `canRunAction(module)` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:138-148` | Source Code | VERIFIED | CURRENT | High | |
| P2-A24 | `service.sh` is gated by both `canRunService(module)` **and** `canRunBackground(module)` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:150-155` | Source Code | VERIFIED | CURRENT | High | |
| P2-A25 | Service scripts auto-run **once per binder session**, gated by a `@Volatile` boolean, and only when `Shizuku.pingBinder()` succeeds | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:31`, `:157-170` | Source Code | VERIFIED | CURRENT | High | **Not a daemon.** Confirms the doc's own "no long-running service supervision" note over the wiki's "long-running service" wording |
| P2-A26 | Scripts are executed as `sh -c <script file contents>`, not `sh <path>` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:183-187`, `:214-218` | Source Code | VERIFIED | CURRENT | High | The whole file is read into memory and passed as one argv element. Doc's `sh /path/to/action.sh` (P2-B05) is wrong |
| P2-A27 | The process working directory is the literal `/data/local/tmp`, **not** the module directory | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:217` | Source Code | VERIFIED | CURRENT | High | Doc's "Working directory is the module directory" (P2-B06) is wrong |
| P2-A28 | A script larger than 256 KiB is rejected before execution with `exitCode = -1` and a `Script too large` stderr message | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:30`, `:179-181` | Source Code | VERIFIED | CURRENT | High | Undocumented limit (P2-B07) |
| P2-A29 | On install, **every** `.sh` file in the package is made executable, plus the declared custom `action` path | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:114`, `:414-423` | Source Code | VERIFIED | CURRENT | High | `walkTopDown`, filter on `extension == "sh"` OR exact relative match to the declared action |
| P2-A30 | Unix executable bits in the ZIP are **not** preserved: extraction writes through `File.outputStream()`, then `setExecutable(true, false)` is applied by extension | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:99-110`, `:414-423` | Source Code | VERIFIED (inference from absence) | CURRENT | High | `java.util.zip.ZipFile` exposes no permission metadata; `setExecutable(true,false)` is owner-independent (all users) |
| P2-A31 | A non-`.sh` executable shipped in a package (e.g. a prebuilt binary `tools/run`) is **not** made executable unless it is also the declared `action` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:414-423` | Source Code | VERIFIED | CURRENT | High | Practical compatibility trap: the module installs cleanly, then fails at run time |
| P2-A32 | WebUI assets live under the resolved `webRoot` and are loaded from `file://` URLs | L1 | SHEVERY `bfc55ce9` | `ModuleWebViewActivity.kt:36`, `:83` | Source Code | VERIFIED | CURRENT | High | `index.toURI().toString()` |
| P2-A33 | `window.Shizuku` is exposed as the JS interface name, with exactly four methods: `getModuleInfo()`, `exec(String)`, `execWithOptions(String,String)`, `download(String,String)` | L1 | SHEVERY `bfc55ce9` | `ModuleJsBridge.kt:49-50`, `:80-81`, `:86-87`, `:96-97`, `:80` (name) | Source Code | VERIFIED | CURRENT | High | There is **no** `runShell` method (P2-B03) |
| P2-A34 | Bridge exposure requires: module enabled **and** `canExposeWebBridge(module)` **and** (`declaresShellBridge` **or** module trusted) **and** (web network off **or** module trusted) | L1 | SHEVERY `bfc55ce9` | `ModuleWebViewActivity.kt:45-48`, `:69-82` | Source Code | VERIFIED | CURRENT | High | |
| P2-A35 | Every bridge call re-validates that the WebView's current URL is a `file:` URL inside `webRoot` (`isOriginValid()`), with a 500 ms latch timeout that **fails closed** | L1 | SHEVERY `bfc55ce9` | `ModuleJsBridge.kt:21-47`, `:51`, `:82`, `:88`, `:98` | Source Code | VERIFIED | CURRENT | High | **SHEVERY-only**; absent in NIGHTZUKU — a materially stronger boundary |
| P2-A36 | WebView settings: JS on, DOM storage on, `allowFileAccess=true`, `allowContentAccess=false`, file/file-URLs access and universal file access **both equal to `trusted`**, `blockNetworkLoads = !webNetworkAllowed`, `LOAD_DEFAULT`, mixed content never allowed, third-party cookies off | L1 | SHEVERY `bfc55ce9` | `ModuleWebViewActivity.kt:58-67` | Source Code | VERIFIED | CURRENT | High | Doc line 159 "Universal file access from file URLs: disabled" holds only for untrusted modules (P2-B08) |
| P2-A37 | `http`/`https` navigations are handed to an external `ACTION_VIEW` intent and return `true` from `shouldOverrideUrlLoading`; `file:` navigations outside `webRoot` are blocked | L1 | SHEVERY `bfc55ce9` | `ModuleWebViewActivity.kt:146-173` | Source Code | VERIFIED | CURRENT | High | |
| P2-A38 | `download(url, path)` requires HTTPS, follows at most 5 redirects re-validating HTTPS each hop, caps at 20 MiB, 15 s connect/read timeouts, and blocks overwriting `index.html` unless the module is trusted | L1 | SHEVERY `bfc55ce9` | `ModuleJsBridge.kt:343-402`, `:315-327`, `:431-434` | Source Code | VERIFIED | CURRENT | High | Redirect re-validation closes HTTPS→HTTP downgrade |
| P2-A39 | A file named exactly `disable` inside the module directory marks the module disabled; it is created with the content `disabled\n` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:25`, `:125-132`, `:344` | Source Code | VERIFIED | CURRENT | High | Same mechanism name as Magisk, different storage location |
| P2-A40 | Logs are written to `<moduleDir>/logs/action-last.log` and `<moduleDir>/logs/service-last.log`, **overwriting** the previous run each time | L1 | SHEVERY `bfc55ce9` | `AdbModule.kt:51-55`, `AdbModuleManager.kt:425-449` | Source Code | VERIFIED | CURRENT | High | No history, no rotation. Relevant to Phase 15 |

### A.5 Installation and archive safety

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A41 | The ZIP is copied to a cache temp file, then parsed with `java.util.zip.ZipFile` (central-directory based) | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:66-73` | Source Code | VERIFIED | CURRENT | High | |
| P2-A42 | Limits: `MAX_ENTRY_COUNT = 2048`, `MAX_EXTRACTED_BYTES = 200 MiB` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:26-27`, `:91`, `:106` | Source Code | VERIFIED | CURRENT | High | Byte total enforced **inside** the copy loop |
| P2-A43 | NIGHTZUKU enforces the same numeric limits but checks extracted size only **after** the file is written (`outFile.length()`), making its zip-bomb defence strictly weaker | L1 | NIGHTZUKU `60a8feb6` | `AdbModuleManager.kt:24`, `:91` | Source Code | VERIFIED | VERSION-SPECIFIC | Medium | Divergence recorded as C-010 |
| P2-A44 | `cleanZipName` replaces `\` with `/`, trims leading/trailing `/`, rejects blank, rejects `startsWith("/")`, rejects `contains("../")`, rejects exactly `".."` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:360-367` | Source Code | VERIFIED | CURRENT | High | Byte-identical to NIGHTZUKU `:262-269` |
| P2-A45 | The `!clean.startsWith("/")` test in `cleanZipName` is **dead code**: `trim('/')` on the preceding line has already removed every leading `/` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:361-363` | Source Code | VERIFIED | CURRENT | High | Absolute-path rejection is nonetheless achieved, by the trim itself |
| P2-A46 | The `contains("../")` test does not catch a trailing `..` segment (e.g. `a/b/..`), which `cleanZipName` accepts | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:363` | Source Code | VERIFIED | CURRENT | High | Accepted by layer 1, **caught by layer 2** |
| P2-A47 | A second, independent layer `ensureInside()` canonicalises both paths and requires the child to start with the staging root | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:94`, `:369-375` | Source Code | VERIFIED | CURRENT | High | Canonicalisation resolves `..` **before** the comparison, so layer 2 catches everything layer 1 misses. Uses `Path.startsWith`, i.e. component-wise, avoiding the `/dir` vs `/dir-evil` prefix bug |
| P2-A48 | NIGHTZUKU's `ensureInside` compares `String` canonical paths with `startsWith("$rootPath/")`; SHEVERY's uses `java.nio.Path` | L1 | NIGHTZUKU `60a8feb6` vs SHEVERY | `nightzuku/.../AdbModuleManager.kt:271-277` vs `.../AdbModuleManager.kt:369-375` | Source Code | VERIFIED | VERSION-SPECIFIC | High | Equivalent effect; different implementation. SHEVERY dropped Nightzuku's `childPath == rootPath` equality case, which is safe because a child is never the root |
| P2-A49 | Extraction happens into a per-module staging directory `.<id>.installing`, guarded by a per-`id` `Mutex` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:33`, `:81-85` | Source Code | VERIFIED | CURRENT | High | |
| P2-A50 | On success the target directory is `deleteRecursively()`-ed **before** `renameTo(staging → target)`; if the rename fails the module is already gone and the staging directory is left behind | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:115-116` | Source Code | VERIFIED | CURRENT | High | **Partial-install window.** Recorded as risk R-P2-01 |
| P2-A51 | Stale staging directories are removed at the *start* of the next install attempt, not at the point of failure | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:35-39`, `:65` | Source Code | VERIFIED | CURRENT | High | A crash mid-install therefore leaves `.id.installing` on disk until the next install |
| P2-A52 | If `readModule(target)` fails after the rename, install throws `"Installed module is unreadable."` — but the directory is already installed | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:117` | Source Code | VERIFIED | CURRENT | High | Rollback does not occur |
| P2-A53 | The cache temp ZIP is deleted in a `finally` block | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:120-122` | Source Code | VERIFIED | CURRENT | High | |
| P2-A54 | Install failures surface to the catalog path as `Result.failure`; the exception message is the only user-visible signal | L1 | SHEVERY `bfc55ce9` | `ModuleInstaller.kt:40-63` | Source Code | VERIFIED | CURRENT | Medium | Error strings double as the module author's only diagnostic |

### A.6 Runtime environment

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A55 | Scripts receive `MODDIR=<absolute module dir>`, `ASH_STANDALONE=1`, `SHIZUKU_MODULE_ID`, `SHIZUKU_MODULE_MODE`, `SHIZUKU_MODULE_TRUSTED`, `SHIZUKU_MODULE_BACKGROUND` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:201-207` | Source Code | VERIFIED | CURRENT | High | |
| P2-A56 | SHEVERY additionally passes `AXERON=true`, `AXERONVER=1.0.0`, `MODPATH=<same as MODDIR>`, `ARCH=<SUPPORTED_ABIS[0] or arm64-v8a>`, and a fixed `PATH` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:208-212` | Source Code | VERIFIED | CURRENT | High | **None of these five appear in the official doc**, and none exist in NIGHTZUKU |
| P2-A57 | `AXERON`/`AXERONVER` were added to chase a third ecosystem ("AxManager plugins"), in a single commit, with no spec and no compatibility note | L1 | SHEVERY | commit `f223250ead28320b6cc1c2b8df478105c117847e`, 2026-05-24, "feat(modules): support AxManager plugins" | Source Code | VERIFIED | HISTORICAL | Medium | `git log --all -S` finds these strings nowhere in Nightzuku's history |
| P2-A58 | The JS-bridge path builds the identical 11-variable environment, then merges caller-supplied `extraEnv` | L1 | SHEVERY `bfc55ce9` | `ModuleJsBridge.kt:220-240` | Source Code | VERIFIED | CURRENT | High | |
| P2-A59 | `extraEnv` keys must match `[A-Za-z_][A-Za-z0-9_]*`, at most 32 keys, values at most 4096 chars, and can **override** any built-in variable | L1 | SHEVERY `bfc55ce9` | `ModuleJsBridge.kt:252-264`, `:238` | Source Code | VERIFIED | CURRENT | High | A page can set `MODDIR`/`PATH` for its own child processes |
| P2-A60 | `MODDIR`, `MODPATH`, `ASH_STANDALONE`, `SHIZUKU_MODULE_*` are all host-generated; none is read from the archive | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:201-213`, `ModuleJsBridge.kt:225-237` | Source Code | VERIFIED | CURRENT | High | Matches `ARCHITECTURE.md` §31 "generated by the runtime rather than trusted from the module archive" |
| P2-A61 | `PATH` is prefixed with a host-written `su` shim (`<filesDir>/bin/su`) that forwards to a real `su` binary if one exists, else exits 127 | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:212`, `:377-412` | Source Code | VERIFIED | CURRENT | High | **SHEVERY-only.** Probes `/system/bin/su`, `/system/xbin/su`, `/sbin/su`, `/vendor/bin/su`, `/data/adb/ksu/bin/su`, `/data/adb/apatch/su`. If root is present, module scripts inherit it |
| P2-A62 | The `su` shim is created with `setExecutable(true, false)` — owner-independent | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:409` | Source Code | VERIFIED | CURRENT | High | |
| P2-A63 | NIGHTZUKU passes only the 6 core variables and sets no `PATH`; `AXERON`, `AXERONVER`, `MODPATH`, `ARCH`, `PATH` are absent from its entire module tree | L1 | NIGHTZUKU `60a8feb6` | `nightzuku/.../AdbModuleManager.kt:163-170`; negative grep over `nightzuku/.../module/` | Source Code | VERIFIED (negative) | CURRENT | High | Directly verified by local read |
| P2-A64 | `SHIZUKU_MODULE_MODE` is one of `safe`, `custom`, `full` | L1 | SHEVERY `bfc55ce9` | `ModuleSettings.kt:37-63`, `AdbModuleManager.kt:205` | Source Code | VERIFIED | CURRENT | High | `docs/adb-modules-api.md:96` says `safe\|full` and omits `custom` (P2-B02) |
| P2-A65 | `SHIZUKU_MODULE_TRUSTED` is `1` when the module is in the trusted set | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:206`, `ModuleSettings.kt:176-181` | Source Code | VERIFIED | CURRENT | High | Absent from the official doc's env list (P2-B09) |
| P2-A66 | `SHIZUKU_MODULE_BACKGROUND` is `1` when `canRunBackground(module)` holds | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:207`, `ModuleSettings.kt:172-174` | Source Code | VERIFIED | CURRENT | High | |
| P2-A67 | Env values are passed as a plain `String[]` of `KEY=VALUE` to `IShizukuService.newProcess`; there is no `env -i` isolation, so the child also inherits whatever the Shizuku server process provides | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:214-218` | Source Code | INFERRED | CURRENT | Medium | Depends on server-side merge semantics in `IShizukuService`, not read in this phase — see U-016 |

### A.7 Execution limits

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A68 | Script execution is bounded at 120 s via `waitForTimeout`; on timeout the process is destroyed and `exitCode = 124` | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:28`, `:260-268` | Source Code | VERIFIED | CURRENT | High | `124` mirrors coreutils `timeout` |
| P2-A69 | stdout/stderr are truncated to the **last** 64 KiB (`takeLast`), not the first | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:284-285`, `:460-478` | Source Code | VERIFIED | CURRENT | High | For a long-running script the *beginning* of the output is lost |
| P2-A70 | Streams are drained continuously by two reader threads, then joined with a 1 s timeout | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:226-270` | Source Code | VERIFIED | CURRENT | High | Avoids the classic pipe-buffer deadlock |
| P2-A71 | The script's stdin is closed immediately for the action/service path; the JS bridge may instead supply up to 64 KiB of stdin | L1 | SHEVERY `bfc55ce9` | `AdbModuleManager.kt:220`; `ModuleJsBridge.kt:189-194`, `:428` | Source Code | VERIFIED | CURRENT | High | |

### A.8 Trust and policy

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | ``` | --- | --- |
| P2-A72 | Three global modes exist: `safe`, `custom`, `full`; default is `safe` | L1 | SHEVERY `bfc55ce9` | `ModuleSettings.kt:37-63`, `:73-77` | Source Code | VERIFIED | CURRENT | High | |
| P2-A73 | In `safe` mode **no** action, service, or WebUI bridge is permitted | L1 | SHEVERY `bfc55ce9` | `ModuleSettings.kt:112-146` | Source Code | VERIFIED | CURRENT | High | |
| P2-A74 | `canUseWebNetwork()` returns **`false` in `full` mode** and only `true` in `custom` with the `webNetwork` permission | L1 | SHEVERY `bfc55ce9` | `ModuleSettings.kt:148-154` | Source Code | VERIFIED | CURRENT | High | Not what the doc implies (P2-B10) |
| P2-A75 | Every per-module gate is `isModuleTrusted(module.id)` OR-ed with the `<global mode gate>` — per-module trust overrides **all six** gates | L1 | SHEVERY `bfc55ce9` | `ModuleSettings.kt:120-174` | Source Code | VERIFIED | CURRENT | High | Including web network in `full` mode and ReCommand confirmation |
| P2-A76 | ReCommand confirmation is skipped when the module is trusted | L1 | SHEVERY `bfc55ce9` | `ModuleJsBridge.kt:144-155`; NIGHTZUKU retains `!trusted &&` | Source Code | VERIFIED | VERSION-SPECIFIC | High | SHEVERY is stricter here; Nightzuku's version still prompts trusted modules |

### A.9 Local implementation evidence (this repository)

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-A77 | This repository has **no** ADB Module subsystem: no `module.prop` parser, no `AdbModule` type, no ADB Module storage root | L1 | LOCAL `main` | repository-wide search for `module.prop`/`AdbModule` across `.kt`/`.kts` | Source Code | VERIFIED (negative) | CURRENT | High | Confirms `ARCHITECTURE.md` §612+ is entirely proposal |
| P2-A78 | Four ZIP extraction loops construct `File(dir, entry.name)` with **no** path validation, entry-count limit, or byte cap | L1 | LOCAL `main` | `AndroidFileSystemCapabilityGatewayImpl.kt:183`, `:232`, `:265`, `:297` | Source Code | VERIFIED | CURRENT | High | This is the path used by the shell-plugin install flow |
| P2-A79 | Exactly one extraction path is hardened: `AndroidFileSystemUnzipOperatorGatewayImpl.unzipFromFileToDirectory` canonicalises and prefix-checks each entry | L1 | LOCAL `main` | `AndroidFileSystemUnzipOperatorGatewayImpl.kt:44-74` | Source Code | VERIFIED | CURRENT | High | Its only caller is `InstallMagiskPluginUseCase.kt:125` |
| P2-A80 | The hardened path has no entry-count or extracted-size limit either, and silently `continue`s past unsafe entries rather than failing the install | L1 | LOCAL `main` | `AndroidFileSystemUnzipOperatorGatewayImpl.kt:53-57` | Source Code | VERIFIED | CURRENT | High | Silent partial extraction |
| P2-A81 | `PluginManifest` already declares `webUiEntryPoint` and `executableFiles`, and documents the KernelSU `webroot` convention | L1 | LOCAL `main` | `domain/plugin/manifest/PluginManifest.kt` | Source Code | VERIFIED | CURRENT | High | The exec-bit concept already exists locally, which narrows the gap for P2-A31 |
| P2-A82 | `AndroidFileSystemChmodOperatorGatewayImpl` chmods only `entryPoint`, never `executableFiles` | L1 | LOCAL `main` | `AndroidFileSystemChmodOperatorGatewayImpl.kt:27-39` | Source Code | VERIFIED | CURRENT | High | `executableFiles` is declared but never consumed — recorded as U-016 |

---

## B. Claims supported by official documentation (L2)

| ID | Claim | Level | Version | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-B01 | The official API doc lists `id, name, version, versionCode, author, description` as **required** fields | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:25-34` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted by P2-A05**; only `id` is enforced |
| P2-B02 | The official API doc states `SHIZUKU_MODULE_MODE=safe\|full` | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:96` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted by P2-A64**; `custom` is omitted |
| P2-B03 | The project wiki names a bridge method `runShell` | L2 | SHEVERY wiki | `wiki/ADB-Modules.md:33` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted by P2-A33**; no such method exists |
| P2-B04 | The official API doc's env list omits `SHIZUKU_MODULE_TRUSTED`, `MODPATH`, `ARCH`, `AXERON`, `AXERONVER`, `PATH` | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:90-98` | Official Documentation | DOCUMENTED | CURRENT | — | **Incomplete** vs P2-A55/P2-A56 |
| P2-B05 | The official API doc shows scripts invoked as `sh /path/to/module/action.sh` | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:76-86` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted by P2-A26** |
| P2-B06 | The official API doc states "Working directory is the module directory" | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:88` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted by P2-A27**; cwd is `/data/local/tmp` |
| P2-B07 | The official API doc's safety-limit list omits the 256 KiB script-size cap | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:67-72` | Official Documentation | DOCUMENTED | CURRENT | — | **Incomplete** vs P2-A28 |
| P2-B08 | The official API doc states "Universal file access from file URLs: disabled" | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:159` | Official Documentation | DOCUMENTED | CURRENT | — | Holds only for untrusted modules (P2-A36) |
| P2-B09 | The official API doc documents `usesShellBridge=true` as the requirement for shell access, with Full Trust as the documented exception | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:177-184`, `:237-249` | Official Documentation | DOCUMENTED | CURRENT | High | **Accurately matches P2-A34/P2-A75** — the most reliable doc section found |
| P2-B10 | The official API doc states "Full Trust modules can use WebView internet and `window.Shizuku` together" | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:164` | Official Documentation | DOCUMENTED | CURRENT | — | True per P2-A75, but only because trust overrides the `full`-mode network denial — the interaction is not explained |
| P2-B11 | The official API doc states the repository includes `test-modules/adb-test-module.zip` | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:305-321` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted**: no `test-modules/` directory exists at `bfc55ce9` |
| P2-B12 | The official API doc lists `action`, `banner`, `webui` as the optional/custom keys and `action` defaults to `action.sh` | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:36-42`, `:51` | Official Documentation | DOCUMENTED | CURRENT | — | **Incomplete**: omits `usesShellBridge`, `updateJson`, `url`, `github`, `shellBridge`, and the `run.sh`/`main.sh`/`exec.sh`/`late_start.sh` fallbacks |
| P2-B13 | The official doc explicitly disclaims systemless overlays, Magisk/KSU mount semantics, and long-running service supervision | L2 | SHEVERY `bfc55ce9` | `docs/adb-modules-api.md:342-348` | Official Documentation | VERIFIED | CURRENT | High | Confirmed by P2-A25; this disclaimer is accurate and important |
| P2-B14 | The wiki calls `service.sh` a "long-running service" | L2 | SHEVERY wiki | `wiki/ADB-Modules.md:29` | Official Documentation | DOCUMENTED | CURRENT | — | **Contradicted by P2-A25/P2-B13**; conflicts with the repo's own doc |
| P2-B15 | The wiki states web network is never allowed in FULL | L2 | SHEVERY wiki | `wiki/Module-policy.md` | Official Documentation | DOCUMENTED | CURRENT | — | True of the *mode* (P2-A74) but not of *trusted modules* (P2-A75) — the two docs use "Full" to mean different things |
| P2-B16 | The README declares Shevery a Shizuku fork with upstream `RikkaApps/Shizuku`, and states that `com.hamondev.shevery` conflicts with an installed official Shizuku Manager | L2 | SHEVERY `bfc55ce9` | `README.md:12-16` | Official Documentation | DOCUMENTED | CURRENT | High | Package id changed from `moe.shizuku...`; users must uninstall official Shizuku first |
| P2-B17 | The README describes ADB Modules as "a ZIP modules" screen with `module.prop`, banner, `action.sh`, policy-gated `service.sh`, local WebUI, path checks, size limits, output limits and last-run logs | L2 | SHEVERY `bfc55ce9` | `README.md:28-30` | Official Documentation | DOCUMENTED | CURRENT | High | Matches source on every point checked |

---

## C. Claims from official project discussion (L3)

| ID | Claim | Level | Version / date | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| P2-C01 | No standalone "ADB Module Specification" repository, RFC, or schema registry exists | L3 | 2026-10-02 | `gh search repos`: `adb module specification`, `adb-module-spec`, `adbmodule spec` — all 0 results | Official Discussion | VERIFIED (negative) | CURRENT | High | Searches a platform index, so scoped to what is publicly indexed |
| P2-C02 | A GitHub topic `shevery-modules` exists with 13 repositories; third-party module READMEs claim compatibility with both Shevery and Nightzuku | L3 | 2026-10-02 snapshot | GitHub API `search/repositories?q=topic:shevery-modules` | Official Discussion | VERIFIED | CURRENT | Medium | Snapshot; catalog is third-party, not authoritative for the format |
| P2-C03 | Shevery is **not** a GitHub-tracked fork of Nightzuku or Shizuku: `fork: false`, `parent: null`, and Shevery's README/docs contain zero mentions of "Nightzuku" | L3 | 2026-10-02 | GitHub API repo metadata for both repos | Official Discussion | VERIFIED | CURRENT | High | Provenance is unacknowledged on-platform — a provenance/licensing item for Phase 18 |
| P2-C04 | NIGHTZUKU is the origin of the module subsystem (commit `93fe85e7`, 2026-05-06); Shevery's first module commit is `4c58598b` (2026-05-15), a 3-hunk delta from Nightzuku `d961535` | L3 | 2026-05 | git history of both clones | Official Discussion | VERIFIED | HISTORICAL | Medium | Both license Apache-2.0; see Phase 18 |
| P2-C05 | `d4rken-org/porter` contains **no** module system: repository-wide case-insensitive search for `module.prop` / `AdbModule` / `adb_module` returns 0 matches and no `*module*` directory exists | L3 | PORTER `2d88f34b`, 2026-10-02 | full-tree grep of `d4rken-org/porter` and its pinned `porter-api` submodule | Official Discussion | VERIFIED (negative) | CURRENT | High | Confirms the Phase 0 hazard H-001 concern. **U-001 remains open**: Porter's identity, version and API surface are still unestablished — that is Phase 4 |
| P2-C06 | Shevery is published under two different owners and package identities over time (`moe.shizuku.*` → `com.hamondev.shevery`, with a revert in between), and release-asset filename prefixes changed `shizuku-*` → `shevery-*` → `manager-*` | L3 | 2026-07-03 onward | release tags `v13.8.0-r26` (commit `0eceefce`) and `79c92c29` | Official Discussion | VERIFIED | VERSION-SPECIFIC | Medium | Any updater keying on package identity or asset prefix must tolerate all three |

---

## D. Negative results (searched, not found)

| ID | Searched for | Method | Result | Classification |
| --- | --- | --- | --- | --- |
| P2-D01 | A formal "ADB Module" format/version spec | repo + code search across both forks | Not found | VERIFIED (negative) |
| P2-D01b | `formatVersion` / `schemaVersion` / `specVersion` keys | grep both module trees | Not found | VERIFIED (negative) |
| P2-D02 | Shevery `test-modules/` reference fixture | `ls test-modules/` at `bfc55ce9` | Absent (doc still advertises it) | VERIFIED |
| P2-D03 | A `service` prop key allowing a custom service script path | read `readModule` in both forks | Does not exist | VERIFIED (negative) |
| P2-D04 | Executable-bit preservation from ZIP metadata | read extraction + `markScriptsExecutable` | No permission handling exists | VERIFIED (negative) |
| P2-D05 | Unix symlink handling during extraction | `java.util.zip.ZipFile` usage + canonical guards | No symlink API used; canonicalisation would resolve any symlink that did exist | VERIFIED (negative) |

---

## E. Classification summary

- **VERIFIED (read in source):** P2-A01…P2-A82 (all of section A), P2-B13, P2-C01…P2-C06, P2-D01…P2-D05.
- **DOCUMENTED (doc claim, contradicted or incomplete):** P2-B01…P2-B12, P2-B14, P2-B15, P2-B16, P2-B17.
- **INFERRED:** P2-A04 (charset asymmetry), P2-A30 (permission non-preservation), P2-A67 (env inheritance).
- **UNKNOWN:** env merge semantics inside `IShizukuService.newProcess` (U-016); whether any real module depends on the SHEVERY-only fallbacks (U-017); format stability policy across app versions (U-013).