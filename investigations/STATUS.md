# ADB Modules App — Investigation Status

Current position of the investigation program.

**Standard:** `investigations/METHOD.md`
**Last updated:** 2026-10-02 (Phase 4)

---

## 1. Current position

| Field | Value |
| --- | --- |
| Completed phases | Phase 0, Phase 1, Phase 2, Phase 4 |
| Audited, not complete | Phase 3 — see the note below |
| Active phase | None |
| Next phase | **Phase 5 — Shizuku Compatibility Investigation** |
| Total phases | 27 (0–26) |
| Phases remaining | 22 |
| MasterRef | Protected, unmodified |
| Implementation work | None — prohibited during the investigation program |

**Note on Phase 3.** Phase 3 is recorded `Audited`, not `Complete`. All 17 of its
checklist items are addressed with evidence, but items 14–16 (crash recovery,
stale-runtime recovery, reboot/session) are source-read only: they describe runtime
behaviour and no device or emulator is available
(`INVESTIGATION_METHOD.md` §10). Phase 4 resolves U-001 and with it U-019, so
those three items can be revisited then. `AGENTS.md` §31 forbids marking a phase
Complete merely because research activity stopped.

---

## 2. Phase status table

| Phase | Name | Status | Notes |
| --- | --- | --- | --- |
| 0 | Investigation Infrastructure | Complete | All 13 checklist items addressed; self-audited |
| 1 | Rootless Store Deep Investigation | Complete | 40/40 items addressed; 33 mapped, 7 partial with gaps assigned; self-audited |
| 2 | Shevery ADB Module Compatibility | Complete | 36/36 items addressed; 33 source-verified, 3 partial with gaps assigned to Phase 14; self-audited. **Corrected the Phase 0 premise: origin is Nightzuku, not Porter** |
| 3 | ADB Module Lifecycle | Audited | 17/17 items addressed; 14 complete, 3 partial (source-read only) with the residual assigned to Phase 14 as U-019/U-020; self-audited. **U-009 and U-011 resolved.** Linear state model found insufficient |
| 4 | Porter Investigation | Complete | 34/34 items addressed; 32 complete, 2 partial (Android-version rows, both assigned to Phase 10). **U-001 resolved**; U-019 factual half resolved. Established the decisive finding: user services do not cross the Porter bridge |
| 5 | Shizuku Compatibility | Not Started | Next. U-001 no longer blocks it; owns U-004, U-027, U-028 |
| 6 | Execution Abstraction | Not Started | Depends on 4, 5. **Owns C-019** — the `KEEP`/`ADAPT` reclassification |
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
| 2026-10-02 | 3 | Not Started | In Progress | Phase 3 initiated; Shevery and Nightzuku re-cloned and pinned to the Phase 2 commits; local repo re-pinned to `6df93ae` |
| 2026-10-02 | 3 | In Progress | Research Complete | Report and evidence ledger produced (137 rows, 13 negative results); seams recorded for items owned by Phases 9, 12, 13, 14 |
| 2026-10-02 | 3 | Research Complete | Audited | Self-audit against `PLAN.md` Phase 3 (16 items + state model) and `INVESTIGATION_METHOD.md` §42 completed. U-009 and U-011 resolved with evidence; U-019…U-024 opened; C-014…C-018 raised |
| 2026-10-02 | 3 | Audited | Audited (held) | `Complete` withheld: items 14–16 require a device or a Porter dependency. Not a transition — a recorded decision, per `INVESTIGATION_METHOD.md` §43 |
| 2026-10-02 | 4 | Not Started | In Progress | Phase 4 initiated; `d4rken-org/porter` and `d4rken-org/porter-api` cloned and pinned; local repo re-pinned to `6df93ae` |
| 2026-10-02 | 4 | In Progress | Research Complete | Report and evidence ledger produced (144 rows, 8 negative results); U-001 resolved; U-025…U-030 opened; C-019…C-021 raised |
| 2026-10-02 | 4 | Research Complete | Audited | Self-audit against `PLAN.md` Phase 4 (24 + 10 items) and `INVESTIGATION_METHOD.md` §42 completed |
| 2026-10-02 | 4 | Audited | Complete | All Phase 4 completion criteria satisfied. Distinct from Phase 3: Phase 4's checklist is analytical, and its two partials are both the Android matrix Phase 10 owns |

---

## 4. Open unknowns

Central register: `investigations/unknowns.md`.

| ID | Short description | Priority | Status | Owning phase |
| --- | --- | --- | --- | --- |
| U-001 | Exact Porter artifact, version, and API surface | Blocking (for backend design) | **Resolved** (Phase 4) | — |
| U-002 | Upstream Rootless Store repository state and license | Medium | Open | 18 |
| U-003 | Reference Shevery commit for the ADB Module compatibility contract | High | **Resolved** (Phase 2) | — |
| U-004 | Shizuku server implementation/version available to users | High | Open | 5 |
| U-005 | Whether `ARCHITECTURE.md` §26 manifest fields match the real `module.prop` contract | High | **Resolved** (Phase 2) | — |
| U-006 | Whether the existing "Magisk compatible plugin" workflow is or is not the ADB Module feature | High | **Resolved** (Phase 2); residual design question → 11 | 11 |
| U-007 | Real-world impact of the database version reset 5 → 1 in commit `efab664` | High | Open | 14 |
| U-008 | Source authentication, integrity, and update-discovery controls beyond the unauthenticated GET | High | Open | 12 |
| U-009 | Whether the UI prevents executing a plugin whose `isEnabled` is false | Medium | **Resolved** (Phase 3) | — |
| U-010 | What `illusioncube` is and what its presence in `data`/`application` implies | Medium | Open | 6 |
| U-011 | Whether any non-default `PluginState` value is ever produced by any writer | High | **Resolved** (Phase 3) | — |
| U-012 | Whether Nightzuku and Shevery will converge or diverge further | Medium | Open | 21 |
| U-013 | Whether any format-stability or deprecation policy exists or will be published | Medium | Open | 21 |
| U-014 | Whether third-party module authors depend on the Shevery-only fallbacks (`run.sh`, `main.sh`, `exec.sh`, `late_start.sh`) | Medium | Open | 12 |
| U-015 | Android-version-specific extraction and `setExecutable` behaviour | Medium | Open | 10 |
| U-016 | `IShizukuService.newProcess` env-merge semantics — does the child inherit the server's environment? | Medium | Open | 5 |
| U-017 | Whether any real module depends on `AXERON`/`AXERONVER` or the `su` shim behaviour | Low | Open | 12 |
| U-018 | Intended script invocation form and working directory for this project | High | Open | 6 |

Phase 3 additions:

| U-019 | Backend-neutral equivalent of the reference's "binder session" service-start trigger | High | **Resolved** (factual half, Phase 4); decision → 6, 9 | 6, 9 |
| U-020 | Runtime behaviour of local recovery under PID reuse and Shizuku unavailability | High | Open | 14 |
| U-021 | Whether real modules depend on the 120 s service bound or on directory replacement by update | Medium | Open | 12 |
| U-022 | Whether GitHub release assets in practice carry a consumable digest | Medium | Open | 12 |
| U-023 | Whether an interrupted `disable`-marker write can read as "enabled" | Low | Open | 11 |
| U-024 | Whether the declared-but-uninvoked lifecycle APIs were a regressed feature or scaffolding | Low | Open | 6 |
| U-025 | Runtime confirmation that `bindUserService` through Porter's bridge throws `UnsupportedOperationException` with no fallback | High | Open | 5 |
| U-026 | Android 12–17 behaviour of Porter itself | Medium | Open | 10 |
| U-027 | Whether `startProcess`/`exec` behave identically on the `PorterBackend.SHIZUKU` wire | Medium | Open | 5 |
| U-028 | Whether the Porter Compatibility companion (`moe.shizuku.privileged.api`) interacts with this project's `dev.rikka.shizuku:provider` differently than documented | Medium | Open | 5 |
| U-029 | Whether Porter SDK `0.x` API instability will affect this project, and at what migration cost | Medium | Open | 21 |
| U-030 | Whether the SDK's internal shell-service `tag`/`version` strings are stable or implementation detail | Low | Open | 6 |

**Open: 24.** U-001, U-003, U-005, U-006, U-009, U-011 are marked **Resolved** and
are retained here only so the mirror stays row-identical to the register; their
resolution blocks remain in `unknowns.md` (`INVESTIGATION_METHOD.md` §45).
Phase 3 resolved U-009 and U-011 and added U-019…U-024; Phase 4 resolved **U-001**
and added U-025…U-030.

**No unknown now carries `Blocking` priority.** U-001 held it and is resolved, so the
program currently has no priority-gated entry. Phase 4's residual risks are
registered separately rather than folded into U-001 (maintenance rule 1).

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
| C-014 | `ARCHITECTURE.md`/`MasterRef.md` imply tracked module service processes; the reference tracks none | Material | **Unresolved** | 6, 9 |
| C-015 | Documents require persisted process state to be distinguished from verified state; local code verifies nothing before killing | Material | **Unresolved** | 14, 7 |
| C-016 | The forks enforce the same lifecycle by non-equivalent means and diverge on update tiers | Material | **Unresolved** (extends C-010) | 13, 21 |
| C-017 | The reference's update path cannot fail safely, yet is presented as an ordinary operation | Minor | **Unresolved** | 13 |
| C-018 | Update is destructive in the reference and silently merging locally; neither is safe | Minor | **Unresolved** | 13 |
| C-019 | `ARCHITECTURE.md` §7/§50 classify the existing execution machinery as `KEEP`/`ADAPT` for Porter; Porter's bridge cannot run it | Material | **Unresolved** | 6 |
| C-020 | Porter's dependency set coexists with upstream `provider`, but `shizuku-compat` would crash the app — and `shizuku-bridge` is offered for exactly this app's shape | Minor | **Unresolved** | 6 |
| C-021 | Porter's docs describe `exec` as a plain command runner; it is implemented on a bound user service with per-connection caching | Minor | **Unresolved** | 6 |

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
- **C-014** and **C-015** are Phase 3 additions and are contradictions between this
  project's own documents and its own code. Neither document was edited:
  `ARCHITECTURE.md` is a proposal and Phase 3 only records that its §33/§36/§38 are
  contradicted by source; `MasterRef.md` is protected until Phase 25/26.
- **C-016** extends C-010 rather than replacing it — C-010 covers extraction-limit
  enforcement, C-016 covers lifecycle and update-tier divergence. Both stay open.
- **C-018** records that update is destructive in the reference and silently
  merging locally. Neither is safe, so Phase 13 must design a third behaviour rather
  than pick a fork.
- **C-019** is the most consequential contradiction raised so far. Porter's bridge
  throws `UnsupportedOperationException` for `bindUserService`, and this project
  executes privileged work exclusively through Shizuku user services at 14 call
  sites. `ARCHITECTURE.md` §50 lists that machinery under `ADAPT`; for Porter the
  honest classification is `REPLACE`. **Phase 6 owns it.** Both documents were left
  unedited — `ARCHITECTURE.md` is the proposal, `MasterRef.md` is protected.

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

**No open unknown blocks Phase 5 (next).**

**No unknown currently carries `Blocking` priority.** U-001 held it and was resolved
by Phase 4, so `unknowns.md` maintenance rule 5 has nothing to require in §7. The
row is retained below as the record of what that priority was, and of what it did
**not** block:

| ID | Former status | Scope of "blocking" | Was expected to block | Did not block |
| --- | --- | --- | --- | --- |
| U-001 | **Resolved** (Phase 4) | Porter-dependent architecture decisions | Phases 6, 10, 22, 23 | Phases 1, 2, 3, 4 |

`Blocking` was a priority value, not a program gate (`METHOD.md` §13). Phase 4
discharged it by resolving U-001 in full: Porter is `d4rken-org/porter` at
`2d88f34b`, its SDK is `d4rken-org/porter-api` at tag `0.9.0`, and the API surface
was established from source across 30 files. Phases 6, 10, 22 and 23 no longer need
an `UNKNOWN` placeholder for Porter.

What U-001's resolution did **not** do, and what later phases must not assume:

- It did **not** make Porter an ADB Module authority. Phase 2 confirmed Porter has
  no module subsystem (P2-C05) and Phase 4 re-verified that at the *same commit*
  (P4-D01, P4-D02). Phase 0's hypothesis that Porter originated the format is
  **disproved**, not merely unresolved.
- It did **not** clear this project's execution code. U-001 was about Porter's
  identity; **C-019** is about this project's code, and C-019 is `Material` and
  open. Phase 6 owns it.
- It did **not** establish Android 12–17 behaviour. That is **U-026**, assigned to
  Phase 10, and Phase 4 deliberately made no per-version claim.

The nearest live constraints on Phase 5 are **U-027** (whether `exec`/`startProcess`
behave the same on the `PorterBackend.SHIZUKU` wire — Phase 5 owns it) and **U-004**
(Shizuku server implementation/version, Phase 5, `High`).

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
