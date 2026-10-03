# ADB Modules App — Android Compatibility Matrix

> Template for per-phase Android-version findings. Required by
> `investigations/METHOD.md` §7.1. Copy into
> `investigations/phase-NN/` as needed.
> Remove this blockquote when using the template.

Phase: `<NN>`
Date of assessment: `<YYYY-MM-DD>`
This project's SDK configuration at time of assessment:

| Field | Value | Source |
| --- | --- | --- |
| `minSdk` | | `app/build.gradle.kts` |
| `targetSdk` | | `app/build.gradle.kts` |
| `compileSdk` | | `app/build.gradle.kts` |
| Application ID | | `app/build.gradle.kts` |
| Android Gradle Plugin | | `gradle/libs.versions.toml` |
| Kotlin | | `gradle/libs.versions.toml` |

`targetSdk` matters independently of platform behaviour: a low `targetSdk`
changes which platform restrictions apply to this app even on a newer Android
release. Any row whose conclusion depends on `targetSdk` must say so.

---

## 1. Support markers

Use exactly these markers:

| Marker | Meaning |
| --- | --- |
| `Supported` | Verified working under the stated conditions |
| `Partially supported` | Works, with recorded limitations |
| `Unsupported` | Verified not to work |
| `Unknown` | Insufficient evidence — **not** an assumption of support |

A row may only be `Supported` with L1 evidence (source) or `OBSERVED`
evidence (device test), not from documentation alone.

---

## 2. Version matrix

| Area | Android 12 | Android 13 | Android 14 | Android 15 | Android 16 | Android 17 |
| --- | --- | --- | --- | --- | --- | --- |
| Shell behaviour | | | | | | |
| Process behaviour | | | | | | |
| Binder behaviour | | | | | | |
| WebView behaviour | | | | | | |
| Background execution | | | | | | |
| Storage behaviour | | | | | | |
| Package visibility | | | | | | |
| Permissions | | | | | | |
| Notifications | | | | | | |
| Foreground services | | | | | | |
| Battery restrictions | | | | | | |
| App standby | | | | | | |
| Process termination | | | | | | |
| Reboot behaviour | | | | | | |
| Porter compatibility | | | | | | |
| Shizuku compatibility | | | | | | |

`PLAN.md` Phase 10 requires all six versions. Other phases may use this matrix
for the rows they touch and mark the rest `Out of scope for this phase`.

---

## 3. Row detail

For each area, record the evidence rather than the conclusion alone.

### <Area name>

| Field | Value |
| --- | --- |
| Behaviour | |
| Affected versions | |
| Classification | `VERIFIED / DOCUMENTED / OBSERVED / INFERRED / UNKNOWN` |
| Confidence | |
| Evidence location | |
| Introduced in API level | |
| Depends on `targetSdk`? | `yes / no` — explain |
| Limitations | |
| Follow-up | |

---

## 4. Device / experiment matrix

One row per observed result. One device is not universal Android behaviour.

| ID | Device | Manufacturer | Android version | API level | App version | Backend + version | Permission state | Trust state | Network | Battery/background | Result | Classification | Limitations |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |

---

## 5. Unverified rows

Rows left `Unknown`, with the specific evidence needed.

| Area / version | Why unknown | What would resolve it | Owning phase |
| --- | --- | --- | --- |

---

## 6. Rules

1. Do not fill a cell by generalizing from an adjacent Android version.
2. Do not mark a row `Supported` from documentation alone.
3. Behaviour discovered on one OEM skin is recorded as `OBSERVED` for that
   device, not as Android behaviour.
4. Where a platform restriction depends on `targetSdk`, record the target-SDK
   condition explicitly.
5. Where the app's own `targetSdk` is below the level that introduced a
   restriction, record that as a compatibility-relevant fact, not as an
   exemption from investigation.