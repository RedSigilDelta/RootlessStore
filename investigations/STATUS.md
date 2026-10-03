# ADB Modules App — Investigation Status

Current position of the investigation program.

**Standard:** `investigations/METHOD.md`
**Last updated:** 2026-10-02 (Phase 2)

---

## 1. Current position

| Field | Value |
| --- | --- |
| Completed phases | Phase 0, Phase 1, Phase 2 |
| Active phase | None |
| Next phase | **Phase 3 — ADB Module Lifecycle** |
| Total phases | 27 (0–26) |
| Phases remaining | 24 |
| MasterRef | Protected, unmodified |
| Implementation work | None — prohibited during the investigation program |

---

## 2. Phase status table

| Phase | Name | Status | Notes |
| --- | --- | --- | --- |
| 0 | Investigation Infrastructure | Complete | All 13 checklist items addressed; self-audited |
| 1 | Rootless Store Deep Investigation | Complete | 40/40 items addressed; 33 mapped, 7 partial with gaps assigned; self-audited |
| 2 | Shevery ADB Module Compatibility | Complete | 36/36 items addressed; 33 source-verified, 3 partial with gaps assigned to Phase 14; self-audited. **Corrected the Phase 0 premise: origin is Nightzuku, not Porter** |
| 3 | ADB Module Lifecycle | Not Started | Next. Depends on 2 |
| 4 | Porter Investigation | Not Started | Marked highest-priority architectural investigation |
| 5 | Shizuku Compatibility | Not Started | |
| 6 | Execution Abstraction | Not Started | Depends on 4, 5 |
| 7 | Security | Not Started | |
| 8 | WebUI | Not Started | Depends on 7 |
| 9 | Background Services | Not Started | |
| 10 | Android Compatibility | Not Started | Requires Android 12–17 matrix |
| 11 | Storage | Not Started | |
| 12 | Catalog / Source | Not Started | |
| 13 | Updates and Rollback | Not Started | |
| 14 | Runtime Recovery | Not Started | Depends on 2, 3, 4, 5 |
| 15 | Logging / Diagnostics | Not Started | |
| 16 | UI / UX | Not Started | Depends on 7 |
| 17 | Testing | Not Started | |
| 18 | Licensing / Provenance | Not Started | Resolves licensing fields left `Unknown` in the source register |
| 19 | Performance | Not Started | |
| 20 | Reliability | Not Started | |
| 21 | Interoperability | Not Started | |
| 22 | Architecture Stress Testing | Not Started | |
| 23 | Future Backend | Not Started | Future-facing only |
| 24 | Future Module Ecosystem | Not Started | Future-facing only |
| 25 | MasterRef Expansion Audit | Not Started | First phase permitted to propose MasterRef changes concretely |
| 26 | MasterRef Incorporation | Not Started | Only phase permitted to edit `MasterRef.md` |

---

## 3. Recent status transitions

| Date | Phase | From | To | Reason |
| --- | --- | --- | --- | --- |
| 2026-10-02 | 0 | Not Started | In Progress | Phase 0 initiated |
| 2026-10-02 | 0 | In Progress | Research Complete | Report, evidence ledger, registers, and templates produced |
| 2026-10-02 | 0 | Research Complete | Audited | Self-audit against `PLAN.md` Phase 0 and `INVESTIGATION_METHOD.md` §42 completed |
| 2026-10-02 | 0 | Audited | Complete | All Phase 0 completion criteria satisfied |
| 2026-10-02 | 1 | Not Started | In Progress | Phase 1 initiated against `main` @ `4b35c36` |
| 2026-10-02 | 1 | In Progress | Research Complete | Report, evidence ledger (42 verified findings), registers updated |
| 2026-10-02 | 1 | Research Complete | Audited | Self-audit against `PLAN.md` Phase 1 (40 items) and `INVESTIGATION_METHOD.md` §16 completed |
| 2026-10-02 | 1 | Audited | Complete | All Phase 1 completion criteria satisfied |
| 2026-10-02 | 2 | Not Started | In Progress | Phase 2 initiated; Shevery and Nightzuku cloned and pinned |
| 2026-10-02 | 2 | In Progress | Research Complete | Report, evidence ledger (111 rows), sources, unknowns, contradictions updated; U-003/U-005/U-006 resolved |
| 2026-10-02 | 2 | Research Complete | Audited | Self-audit against `PLAN.md` Phase 2 (36 items) and `INVESTIGATION_METHOD.md` §16 completed |
| 2026-10-02 | 2 | Audited | Complete | All Phase 2 completion criteria satisfied |

---

## 4. Open unknowns

Central register: `investigations/unknowns.md`.

| ID | Short description | Priority | Status | Owning phase |
| --- | --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Blocking (for backend design) | Open | 4 |
| U-002 | Upstream Rootless Store repository state and license | Medium | Open | 18 |
| U-003 | Reference Shevery commit for the ADB Module compatibility contract | High | **Resolved** (Phase 2) | — |
| U-004 | Shizuku server implementation/version available to users | High | Open | 5 |
| U-005 | Whether `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract | High | **Resolved** (Phase 2) | — |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is or is not the ADB Module feature | High | **Resolved** (Phase 2); residual design question → 11 | 11 |
| U-007 | Real-world impact of the database version reset 5 → 1 in commit `efab664` | High | Open | 14 |
| U-008 | Source authentication, integrity, and update-discovery controls beyond the unauthenticated GET | High | Open | 12 |
| U-009 | Whether the UI prevents executing a plugin whose `isEnabled` is false | Medium | Open | 3 |
| U-010 | What `illusioncube` is and what its presence in `data`/`application` implies | Medium | Open | 6 |
| U-011 | Whether any non-default `PluginState` value is ever produced by any writer | High | Open | 3 |
| U-012 | Whether Nightzuku and Shevery will converge or diverge further | Medium | Open | 21 |
| U-013 | Whether any format-stability or deprecation policy exists or will be published | Medium | Open | 21 |
| U-014 | Whether third-party module authors depend on the Shevery-only fallbacks (`run.sh`, `main.sh`, `exec.sh`, `late_start.sh`) | Medium | Open | 12 |
| U-015 | Android-version-specific extraction and `setExecutable` behaviour | Medium | Open | 10 |
| U-016 | `IShizukuService.newProcess` env-merge semantics — does the child inherit the server's environment? | Medium | Open | 5 |
| U-017 | Whether any real module depends on `AXERON`/`AXERONVER` or the `su` shim behaviour | Low | Open | 12 |
| U-018 | Intended script invocation form and working directory for this project | High | Open | 6 |

**Open: 15.** U-003, U-005 and U-006 are marked **Resolved** and are retained
here only so the mirror stays row-identical to the register; their resolution
blocks remain in `unknowns.md` (`INVESTIGATION_METHOD.md` §45).

**No open unknown blocks Phase 3.** U-018 (`High`, Phase 6) is the nearest
constraint on Phase 3's design work: Phase 3 must describe lifecycle behaviour
without presupposing an invocation contract.

`Blocking` in the Priority column is a **priority value, not a program gate**
(`METHOD.md` §13). U-001 is the only open unknown carrying that priority: it blocks
Porter-dependent architecture decisions in Phases 6, 10, 22 and 23, and nothing
else. See §7.

---

## 5. Open contradictions

Central register: `investigations/contradictions.md`.

| ID | Short description | Severity | Status | Owning phase |
| --- | --- | --- | --- | --- |
| C-001 | Alleged 26 vs 27 phase-count conflict | Minor | **Retracted** — claim not substantiated | — |
| C-002 | ADB Module storage: internal app-managed vs external Magisk-compat paths | Material | **Resolved** (Phase 2) | — (resolved; storage contract → 11) |
| C-003 | README states Phase 0 is not complete; Phase 0 is complete | Minor | Resolved — README corrected in Phase 0 | — |
| C-004 | Alleged `module.prop` format conflation in `ARCHITECTURE.md` | Minor | **Retracted** — misattributed section, scope is correct | — |
| C-005 | `ARCHITECTURE.md` assumes manifest/archive validation stages the implementation lacks | Material | **Unresolved** (quantified in Phase 2) | 2, 7 |
| C-006 | `PluginManifest.kt` documents field requirements that no code enforces | Minor | **Unresolved** | 3, 7 |
| C-007 | Alleged missing `CodeBrickEntity` migration causing a crash | Material | **Refuted** — post-reset baseline already contained the entity | — |
| C-008 | Subagents disagreed on extraction Zip-Slip coverage (4/5 vs 2/10) | Minor | **Corrected** — 1 of 9 protected; 1 reachable, 4 dead | — |
| C-009 | The two official docs disagree on whether `full` mode permits web network | Material | **Unresolved** | 7 |
| C-010 | Nightzuku and Shevery enforce identical limits by different, non-equivalent means | Material | **Unresolved** | 21 |
| C-011 | The official API doc contradicts its own source on four points | Material | **Unresolved** | — (informational) |
| C-012 | Derived-from-Nightzuku provenance is unacknowledged on-platform | Minor | **Unresolved** | 18 |
| C-013 | Package identity and release-asset naming changed across versions | Minor | **Unresolved** | 13 |

C-002 is **resolved** by Phase 2: the reference implementation uses app-private
`filesDir/adb_modules` and disclaims `/data/adb/modules`, so the two documents were
describing different features. The storage contract itself remains Phase 11's.

No open contradiction blocks Phase 3. Blocking contradictions: **0** throughout the
program.

Carried forward:

- **C-005** is now quantified rather than vague (Phase 2 §C-005 contribution) and
  is owned by Phase 7. Its most serious element is that the local protected
  extraction path **fails open**.
- **C-009** is the direct input to Phase 7's trust-model decision.
- **C-011** is permanent-for-a-third-party: the Shevery doc contradicts its own
  source and this project will not edit it. Phase 12 must treat "claims Shevery
  compatibility" as insufficient evidence of format correctness.

---

## 6. Verified baseline facts

Established by Phase 0 for reuse by later phases. Full records in
`phase-00/baseline.md`.

| Fact | Value | Evidence |
| --- | --- | --- |
| Project identity | Fork of Rootless Store; AGPL-3.0 | `README.md`, `LICENSE` |
| Application ID | `com.baidaidai.rootless_store` | `app/build.gradle.kts` |
| Version | `2.3.1` (code 2) | `app/build.gradle.kts` |
| SDK configuration | `minSdk 26`, `targetSdk 28`, `compileSdk 37` | `app/build.gradle.kts` |
| Gradle modules | `app`, `application`, `core`, `data`, `domain`, `illusioncube`, `service`, `ui` | `settings.gradle.kts` |
| Shizuku client API | `dev.rikka.shizuku:api` / `:provider` **13.1.5** | `gradle/libs.versions.toml` |
| Root library | `com.github.topjohnwu.libsu:core` **6.0.0** | `gradle/libs.versions.toml` |
| Porter dependency | **None declared** | `gradle/libs.versions.toml` + repository-wide search |
| Privilege mechanism in use today | Shizuku `UserService` + AIDL, and libsu root shell | `data/src/main/aidl/`, `ShizukuEndpointTemplate.kt` |
| ADB Module subsystem present? | **No** — no `module.prop` parser, no `AdbModule` type, no ADB Module storage root | `phase-02/evidence.md` P2-A77 |
| ADB Module format origin | Nightzuku, 2026-05-06 (commit `93fe85e7`); **not** Porter, **not** Shevery-original | P2-C04, P2-C05 |
| Authoritative Shevery reference | `HmnDev-Tech/shevery` @ `bfc55ce9c8898043f1a5c4896be276d154d08243` (2026-10-02), Apache-2.0 | `sources.md` S-201, S-006 |

The `targetSdk 28` value is recorded here because it materially affects several
Android-compatibility questions in Phases 9, 10, 11 and 16. Phase 0 makes no
claim about Android behaviour based on it.

---

## 7. Blocking items

**No open unknown blocks Phase 3 (next).**

One open unknown carries `Blocking` **priority**, recorded here because
`unknowns.md` maintenance rule 5 requires a `Blocking` unknown to appear in both
§4 and §7:

| ID | Status | Scope of "blocking" | Blocks | Does not block |
| --- | --- | --- | --- | --- |
| U-001 | Open | Porter-dependent architecture decisions | Phases 6, 10, 22, 23 | Phases 1, 2, 3, and every other phase |

`Blocking` means priority, not a halt (`METHOD.md` §13). U-001 is resolved by
Phase 4 — Porter Investigation — and is not a precondition for starting Phase 3.

Phase 2 confirmed that Porter has **no** ADB Module subsystem (P2-C05), so U-001's
scope is narrower than Phase 0 assumed: it cannot affect the module package format.
Phase 0 had suspected Porter was the format's origin; that hypothesis is
**disproved**, not merely unresolved.

---

## 8. Program rules currently in force

1. `MasterRef.md` is read-only until Phase 25 audits it and Phase 26
   incorporates verified findings.
2. `ARCHITECTURE.md` is a proposal, subject to revision by verified findings.
3. No implementation during Phases 0–26.
4. No invented sources, versions, APIs, or findings.
5. No silently resolved contradictions or closed unknowns.
6. Phase 0–26 structure is canonical and unchangeable.
7. One phase at a time; a phase does not start another phase.
