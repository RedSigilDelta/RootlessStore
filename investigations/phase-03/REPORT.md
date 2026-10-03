# ADB Modules App — Phase 03 — Investigation Report

Phase: `03 — ADB Module Lifecycle`
Status: **In Progress**
Started: 2026-10-02
Checklist: in progress (`PLAN.md` §176–201, 16 items + the state-model requirement)
Evidence ledger: [`evidence.md`](evidence.md) — *pending*

---

## 0. Scope decisions (recorded before research)

Three ambiguities were identified before any Phase 3 research began, because
`PLAN.md` is a checklist rather than a specification and Phase 2 established that
this repository has **no** ADB Module subsystem (P2-A77). These decisions are
recorded here, up front, so that later readers can see what was chosen and why
rather than inferring it from the findings.

### D1 — Investigate both layers, split by layer

`PLAN.md` titles this phase *ADB Module Lifecycle*, yet the ADB Module subsystem
does not exist in this repository. Two subjects were therefore in play:

| Layer | Subject | Available material |
| --- | --- | --- |
| **Reference** | Shevery / Nightzuku module lifecycle | Pinned clones at `bfc55ce9` / `60a8feb6`; Phase 2 read them |
| **Local** | This repository's Rootless Store plugin and CodeBrick lifecycle | Real source: `PluginState`, `PluginProcessMonitor`, `UninstallPluginUseCase`, 31 `isEnabled` references |

**Decision: investigate both, kept explicitly separated.** They are not merged and
not presented as equivalent. The reference layer defines the compatibility
contract this project would have to match; the local layer describes what exists
today.

Grounds:

- The local layer is not optional. `PLAN.md` assigns Phase 3 ownership of U-009
  (`isEnabled` enforcement) and U-011 (`PluginState` writers), both purely local
  questions that no amount of reference reading can answer.
- The reference layer is not optional either. Lifecycle is meaningless without the
  contract it must satisfy, and Phase 2 explicitly deferred lifecycle to this
  phase.
- `AGENTS.md` §14 requires these distinctions to be preserved rather than
  collapsed. Presenting a local plugin lifecycle as though it were a
  reference-contract lifecycle would be exactly that failure.

**Consequence:** every finding is labelled `REFERENCE` or `LOCAL`. No artifact may
state "the ADB Module lifecycle" without naming which layer it means.

### D2 — Six seam items investigated at the boundary, depth deferred

Six of the sixteen checklist items fall squarely inside phases owned by other
investigations:

| Item | Owning phase | Phase 3 treatment |
| --- | --- | --- |
| discovery / download | 12 — Catalog / Sources | Seam only |
| verification | 12 — Catalog / Sources (U-008) | Seam only |
| module updates | 13 — Updates / Rollback | Seam only |
| module rollback | 13 — Updates / Rollback | Seam only |
| background execution | 9 — Background Services | Seam only |
| crash / stale-runtime / reboot-session | 14 — Runtime Recovery | Seam only |

**Decision: investigate the seam, defer the depth.** For each, Phase 3 documents
what the lifecycle boundary *requires* — which data must exist at the handoff,
what state must be durable across it, what failure must be survivable — and
labels the remainder as owned by the later phase. No mechanism belonging to 9, 12,
13 or 14 is investigated in depth here.

Grounds: `AGENTS.md` §30 permits parallel and adjacent research but requires
clear boundaries; `PLAN.md` phase order is canonical and may not be rearranged.
Duplicating another phase's investigation would produce two competing
descriptions of the same mechanism.

**Consequence:** each seam item gets a *seam record* naming the owning phase, the
required data contract, and the residual risk. No seam item may be marked
`Complete` on the strength of another phase's mechanism.

### D3 — Recovery items are source-read only

Checklist items 14–16 (crash recovery, stale-runtime recovery, reboot/session
behaviour) are inherently experimental: they describe what happens after a process
dies or a device reboots. No device or emulator is available.

**Decision: source-read only, each finding labelled `Needs device testing`.**
What the code statically guarantees will be recorded. What it does not guarantee
will be recorded as an unknown, not as a finding.

Grounds: `INVESTIGATION_METHOD.md` §21 requires device, Android version, app
version, and procedure for an experimental finding, and `AGENTS.md` §21 forbids
generalising from a single experiment. §16 forbids converting "I think" into "the
system does". A static read of recovery code cannot establish runtime behaviour,
and presenting it as though it could would be the specific error these rules
exist to prevent. Phase 2 recorded the same limitation.

**Consequence:** items 14–16 will read `Partial (source-read only)` with an
explicit unknown, not `Complete`.

### D4 — The state model is PROPOSED, not decided

`PLAN.md` requires determining whether the linear model

```
Discovered → Downloaded → Verified → Installed → Enabled
           → Running → Stopped → Disabled → Uninstalled
```

is sufficient, or whether lifecycle state must be represented across multiple
independent dimensions.

**Decision: Phase 3 records the answer as `PROPOSED`, with evidence.** Phase 3 can
determine what the reference and local implementations actually do. It cannot
decide what this project should do: `AGENTS.md` §24 forbids using one backend's
semantics to define the architecture, and `INVESTIGATION_METHOD.md` §40 requires an
explicit architecture decision rather than a silent edit. Architecture confirmation
follows Phase 26 and is not Phase 27.

---

*Sections 1–14 follow as research proceeds.*
