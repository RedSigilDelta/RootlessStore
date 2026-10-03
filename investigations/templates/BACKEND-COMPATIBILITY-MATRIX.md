# ADB Modules App — Execution Backend Compatibility Matrix

> Template for Porter and Shizuku backend findings. Required by
> `investigations/METHOD.md` §7.2 and §7.3. Copy into
> `investigations/phase-NN/` as needed.
> Remove this blockquote when using the template.

Phase: `<NN>`
Date of assessment: `<YYYY-MM-DD>`

---

## 0. Rules for this matrix

1. **No invented APIs.** A capability is recorded as existing only with an L1
   or L2 source naming the exact artifact and version. Otherwise it is
   `UNKNOWN`.
2. **Name the artifact.** "Porter" and "Shizuku" each may refer to an app, a
   client library, a server, or a protocol. Every row states which.
3. **Backend identity is not privilege.** The privilege column is filled from
   evidence about how the backend was started, never inferred from its name.
4. **Capacities are not promises.** A backend must not be recorded as
   supporting a capability because a different backend, or the same backend on
   a different platform version, supports it.
5. **Compatibility obligations are separate from implementation.** A
   `window.Shizuku` surface is an obligation owed to module authors; it is not
   evidence of the backend's internal design.

---

## 1. Version context

| Backend | Artifact | Version | Commit / build | Date checked | Currency | Source level |
| --- | --- | --- | --- | --- | --- | --- |
| Porter | | | | | | |
| Shizuku (client API) | | | | | | |
| Shizuku (server) | | | | | | |
| Shizuku (fork, if applicable) | | | | | | |

Record `Unknown` where a version cannot be established. Do not infer it.

---

## 2. Capability matrix

| Capability | Porter | Shizuku | Conditions / limitations | Evidence (source + location) | Classification | Confidence |
| --- | --- | --- | --- | --- | --- | --- |
| Shell execution | | | | | | |
| Process creation | | | | | | |
| Detached / background process | | | | | | |
| stdin | | | | | | |
| stdout | | | | | | |
| stderr | | | | | | |
| Exit code | | | | | | |
| PID exposure | | | | | | |
| Process termination | | | | | | |
| Environment variables | | | | | | |
| Working directory control | | | | | | |
| File read | | | | | | |
| File write | | | | | | |
| Archive extraction | | | | | | |
| Permission request | | | | | | |
| Permission state query | | | | | | |
| Binder / IPC availability | | | | | | |
| Service lifecycle (long-running) | | | | | | |
| Reboot / boot-completed reaction | | | | | | |
| WebUI shell bridge | | | | | | |
| Privilege level | | | | | | |

---

## 3. State model

| State | Meaning | Porter evidence | Shizuku evidence | Notes |
| --- | --- | --- | --- | --- |
| `NOT_INSTALLED` | Backend app absent | | | |
| `NOT_RUNNING` | Present but no active session | | | |
| `PERMISSION_DENIED` | Present, running, app not granted | | | |
| `READY` | Granted and usable | | | |
| `UNSUPPORTED` | Present but cannot provide required capability | | | |
| `ERROR` | Unexpected failure | | | |

`ERROR` and `NOT_RUNNING` must remain distinguishable from "module failure".
Backend state is never inferred from module state.

---

## 4. Privilege

| Question | Answer | Evidence | Classification |
| --- | --- | --- | --- |
| Does the backend's privilege depend on how its server was started? | | | |
| What privilege results from an ADB-started server? | | | |
| What privilege results from a root-started server? | | | |
| Can privilege change between sessions without the app changing? | | | |
| Is privilege detectable at runtime by the app? | | | |

A "no" or "unknown" here is a valid and important finding. It constrains
backend fallback design (`ARCHITECTURE.md` Invariant 15).

---

## 5. Compatibility surface

The `window.Shizuku` namespace and related WebUI bridges are obligations owed
to existing module authors. Record them separately from backend internals.

| Surface | Required by | Provided by | Version | Deviations found | Classification |
| --- | --- | --- | --- | --- | --- |
| `window.Shizuku` | Module ecosystem | | | | |
| `exec` | | | | | |
| `execWithOptions` | | | | | |
| Env var read | | | | | |
| Working directory | | | | | |
| stdin | | | | | |
| Timeout | | | | | |
| Output limit | | | | | |
| Exit code delivery | | | | | |
| Module info read | | | | | |

---

## 6. Failure, lifecycle, recovery

| Scenario | Porter behaviour | Shizuku behaviour | Evidence | Classification |
| --- | --- | --- | --- | --- |
| App process death | | | | |
| Backend app killed | | | | |
| Backend restarted | | | | |
| Binder death | | | | |
| Permission revoked | | | | |
| Device reboot | | | | |
| Force-stop of backend | | | | |
| Network loss (wireless start) | | | | |
| Reconnection behaviour | | | | |
| Recovery without user action | | | | |

---

## 7. Security boundaries

| Boundary | Question | Answer | Evidence |
| --- | --- | --- | --- |
| Authentication | How does the backend authorise this app? | | |
| Signature binding | Is approval bound to the app signer? | | |
| Revocation | What happens to running services on revoke/pause? | | |
| Data channel | Which binder/provider surface is exposed? | | |
| Attack surface | What can a hostile app or module reach through this backend? | | |

---

## 8. Android-version interaction

Populate the version rows that matter for this backend only; the full grid
lives in `ANDROID-COMPATIBILITY-MATRIX.md`.

| Behaviour | Android 12 | Android 13 | Android 14 | Android 15 | Android 16 | Android 17 |
| --- | --- | --- | --- | --- | --- | --- |

---

## 9. Unknowns and contradictions raised

| ID | Type (`U-`/`C-`) | Short description | Priority / severity |
| --- | --- | --- | --- |

Cross-reference `investigations/unknowns.md` and
`investigations/contradictions.md`.