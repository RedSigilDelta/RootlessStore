
**Status: Current Proposed Architecture**

This document describes the current architectural proposal. It is a hypothesis subject to the investigation program. Verified investigation findings may confirm, modify, replace, or invalidate portions of this architecture. It is not a final or immutable architecture.

ADB Modules Project — Architecture

1. Purpose

This document defines the architecture of the unified Android application built from a Rootless Store foundation with first-class ADB Module support.

It defines:

- system structure,
- component boundaries,
- responsibilities,
- data flow,
- execution architecture,
- package architecture,
- privilege boundaries,
- policy boundaries,
- trust boundaries,
- runtime behavior,
- compatibility boundaries,
- architectural invariants.

This document does not define implementation sequencing or agent operating rules.

---

2. Documentation Source of Truth

The project deliberately separates documentation responsibilities.

Document| Authority
"AGENTS.md"| Agent behavior, rules, constraints, and workflow
"ARCHITECTURE.md"| Current proposed architecture
"PLAN.md"| Authoritative investigation scope and phase order
"INVESTIGATION_METHOD.md"| Investigation methodology and evidence standards
"MasterRef.md"| Consolidated project reference
"INVESTIGATION.md"| Original pre-MasterRef investigation and historical research/reference
"README.md"| Project overview and user-facing documentation

Each document is authoritative only within its assigned domain.

Architecture status and scope

"ARCHITECTURE.md" defines the current proposed architecture for:

- system boundaries,
- component responsibilities,
- package relationships,
- backend relationships,
- data flow,
- security boundaries,
- runtime boundaries,
- architectural invariants.

These are proposed architectural statements, not immutable conclusions. Verified investigation findings may change them. The document does not determine investigation scope, evidence standards, or implementation order.

---

3. Architectural Goal

The application should provide one unified package-management and execution experience while preserving the distinct semantics of:

Rootless Plugins
ADB Modules
CodeBricks

The system should reuse appropriate Rootless Store infrastructure while adding a clean ADB Module architecture.

The target execution architecture is:

                    Unified Application
                           │
              ┌────────────┼────────────┐
              │            │            │
              ▼            ▼            ▼
       Rootless Plugins ADB Modules CodeBricks
              │            │            │
              └────────────┼────────────┘
                           │
                           ▼
                     Common Runtime
                           │
                  ┌────────┴────────┐
                  │                 │
                  ▼                 ▼
               Policy             Trust
                  │                 │
                  └────────┬────────┘
                           │
                           ▼
                Execution Abstraction
                    │             │
                    ▼             ▼
                 Porter       Shizuku
                PRIMARY     COMPATIBILITY

---

4. Core Architectural Model

The system separates six concepts:

Package
    What is being executed

Backend
    How execution is provided

Privilege
    What authority execution actually has

Policy
    What the package is allowed to do

Trust
    Whether the user trusts the package

Runtime State
    What is currently happening

These concepts must not be collapsed merely because they appear related.

---

5. Package Architecture

The application contains three primary package families.

Package System
├── Rootless Plugin
├── ADB Module
└── CodeBrick

Rootless Plugin

Represents the existing Rootless Store plugin model.

It retains its own:

- manifest,
- lifecycle,
- execution model,
- persistence,
- WebUI behavior,
- source behavior.

ADB Module

Represents the Shevery-compatible ADB Module package model.

It retains its own:

- "module.prop",
- archive format,
- script lifecycle,
- module environment,
- policy,
- trust,
- WebUI,
- shell bridge,
- storage semantics.

CodeBrick

Represents saved command/automation functionality.

It remains distinct from both Rootless Plugins and ADB Modules.

---

6. Common Package Abstraction

Common package infrastructure may represent genuinely shared concepts such as:

- package identity,
- name,
- version,
- source,
- installation state,
- update state,
- enabled state,
- execution availability.

The common abstraction must not erase package-specific semantics.

Conceptually:

                    Package
                       │
          ┌────────────┼────────────┐
          │            │            │
          ▼            ▼            ▼
     Rootless       ADB Module   CodeBrick
      Plugin

A common interface represents shared management concerns.

Specialized models represent package-specific behavior.

---

7. Rootless Store Foundation

Rootless Store provides the foundation for:

- package management,
- plugin management,
- execution persistence,
- runtime recovery,
- CodeBricks,
- sources,
- catalogs,
- notifications,
- device/runtime status,
- existing WebUI behavior.

The architecture should reuse those systems when their semantics are compatible.

Reuse does not mean that every Rootless subsystem must be shared with ADB Modules.

---

8. Application Layers

The application should maintain clear separation between:

UI
│
Application / Use Cases
│
Domain
│
Runtime / Execution
│
Data / Persistence
│
Backend Integrations

The exact package/module names are implementation details.

The architectural responsibilities are more important than specific filenames.

---

9. UI Layer

The UI is responsible for:

- displaying package state,
- accepting user actions,
- displaying backend status,
- displaying policy/trust state,
- displaying execution state,
- displaying errors,
- presenting WebUI where appropriate.

The UI must not directly implement:

- shell execution,
- privileged process creation,
- archive extraction,
- backend internals,
- policy enforcement,
- persistence mechanics.

---

10. Application Layer

The application/use-case layer coordinates user-facing operations.

Examples include:

- install package,
- uninstall package,
- execute package,
- enable module,
- disable module,
- update package,
- resolve backend,
- change policy,
- change trust,
- recover runtime state.

This layer coordinates domain and infrastructure without owning backend-specific implementation.

---

11. Domain Layer

The domain layer represents the application's core concepts.

Important domain concepts include:

- package,
- package type,
- ADB Module,
- module manifest,
- execution request,
- execution result,
- execution state,
- backend,
- capability,
- privilege,
- policy,
- trust,
- runtime state,
- source,
- update.

Domain logic should not directly depend on Porter or Shizuku implementation details.

---

12. Runtime Layer

The runtime layer coordinates execution behavior.

It is responsible for:

- execution requests,
- backend resolution,
- process tracking,
- execution state,
- output,
- errors,
- timeouts,
- cancellation,
- runtime persistence,
- recovery.

The runtime layer communicates with backend adapters rather than directly implementing each backend.

---

13. Data Layer

The data layer is responsible for persistence and external data.

It may contain:

- package repositories,
- execution repositories,
- runtime-state repositories,
- source repositories,
- update metadata,
- module metadata,
- policy persistence,
- trust persistence.

Persistence must not become the only source of truth for active process state.

Runtime state must be reconciled with actual backend/process state.

---

14. Backend Integration Layer

Backend integrations implement actual privileged execution.

The target architecture is:

Execution Abstraction
        │
   ┌────┴────┐
   │         │
   ▼         ▼
 Porter    Shizuku
PRIMARY   COMPATIBILITY

Backend-specific APIs remain inside their adapters.

---

15. Execution Abstraction

The execution abstraction represents operations that the application can request from a privileged backend.

Conceptual objects may include:

- "ExecutionBackend"
- "ExecutionRequest"
- "ExecutionResult"
- "ExecutionHandle"
- "BackendCapability"
- backend state
- execution state

These names are architectural concepts and may be implemented differently in source code.

The abstraction must support only behavior actually available from the underlying backend.

---

16. Porter Backend

Porter is the primary backend.

Conceptually:

Application
     │
     ▼
Execution Abstraction
     │
     ▼
Porter Adapter
     │
     ▼
Porter
     │
     ▼
Privileged Execution

The architecture must not assume a specific Porter API until verified.

Porter-specific implementation remains behind the adapter boundary.

---

17. Shizuku Backend

Shizuku provides compatibility execution.

Conceptually:

Application
     │
     ▼
Execution Abstraction
     │
     ▼
Shizuku Adapter
     │
     ▼
Shizuku
     │
     ▼
Privileged Execution

Shizuku is not the architectural center of the application.

---

18. Capability Model

Backends may expose different capabilities.

The architecture therefore represents capability independently of backend identity.

Conceptual capabilities include:

Shell Execution
Process Management
Persistent Processes
Environment
Working Directory
File Operations
Installation
Background Execution
WebUI Shell Bridge

The final capability list must be based on verified backend behavior.

---

19. Backend State

Backend state should distinguish at least:

NOT_INSTALLED
NOT_AVAILABLE
NOT_RUNNING
READY
UNSUPPORTED
ERROR

Exact state names are implementation details.

The architectural requirement is that the application distinguish:

- backend exists,
- backend is usable,
- backend supports the requested capability,
- backend is currently unavailable.

---

20. Backend Resolution

Backend resolution combines:

- package requirements,
- requested operation,
- backend availability,
- backend capability,
- user preference,
- runtime state,
- safety constraints.

Conceptually:

Execution Request
       │
       ▼
Required Capabilities
       │
       ▼
Available Backends
       │
       ▼
Policy / Trust
       │
       ▼
User Preference
       │
       ▼
Backend Resolver
       │
   ┌───┴────┐
   ▼        ▼
Porter   Shizuku

Backend selection must not silently change privilege semantics.

---

21. Privilege Model

Privilege is represented independently from backend.

For example, Shizuku execution may operate under different authority depending on how the Shizuku server was started.

Therefore:

Backend ≠ Privilege

The architecture must not hard-code assumptions such as:

Shizuku = root

or:

Porter = fixed privilege

without verified evidence.

---

22. Policy Model

Policy controls what a package is allowed to do.

Examples include:

- action execution,
- service execution,
- background execution,
- WebUI,
- shell bridge,
- network access,
- downloads,
- command execution.

Policy is independent from backend privilege.

---

23. Trust Model

Trust represents the user's trust decision regarding a package.

Trust may affect:

- whether execution is allowed,
- whether additional restrictions are bypassed,
- whether WebUI bridge behavior is permitted,
- whether background behavior is permitted.

The exact policy is defined by the supported ADB Module contract.

The architectural boundary remains:

Trust ≠ Policy ≠ Privilege

---

24. Source Trust

Package discovery sources and execution trust remain separate.

Source
  ↓
Discovery
  ↓
Package
  ↓
Validation
  ↓
Trust / Policy
  ↓
Execution

A package appearing in a trusted catalog does not automatically receive unrestricted execution authority.

---

25. ADB Module Architecture

ADB Modules receive a dedicated package subsystem.

Conceptually:

ADB Module
│
├── Manifest
├── Archive
├── Storage
├── Installation
├── Environment
├── Action Runtime
├── Service Runtime
├── Policy
├── Trust
├── Execution
├── Logging
└── WebUI

This subsystem integrates with common runtime infrastructure without becoming a Rootless Plugin.

---

26. ADB Module Manifest

The module manifest is represented by "module.prop".

Required information includes concepts such as:

id
name
version
versionCode
author
description

Optional/custom properties may define:

banner
webui
usesShellBridge
action

The parser is responsible for translating the supported format into the ADB Module domain model.

---

27. ADB Module Archive

The archive subsystem must validate the module before installation.

Conceptual flow:

ZIP
 ↓
Archive Validation
 ↓
Manifest Parsing
 ↓
Path Validation
 ↓
Resource Validation
 ↓
Staging
 ↓
Extraction
 ↓
Verification
 ↓
Registration

Unsafe content must not reach privileged extraction.

---

28. Archive Security Boundary

The archive boundary protects the application from malicious or malformed packages.

Validation must address:

- archive structure,
- entry count,
- extraction size,
- path traversal,
- absolute paths,
- invalid metadata,
- unsupported paths.

The final limits are determined by the supported compatibility contract and verified reference version.

---

29. Module Storage

ADB Modules use isolated application-managed storage.

The investigated compatibility model corresponds to:

/data/user/0/<package>/files/adb_modules/<module-id>

The exact implementation should preserve the supported contract.

The architecture must not use Magisk/KSU module storage semantics.

---

30. Module Installation

Installation is staged.

Package
   ↓
Validate
   ↓
Parse
   ↓
Stage
   ↓
Extract
   ↓
Verify
   ↓
Persist
   ↓
Register

A failed installation must not leave a partially installed module that appears valid.

---

31. Module Environment

ADB Module scripts receive the environment required by the compatibility contract.

Conceptual variables include:

MODDIR
ASH_STANDALONE
SHIZUKU_MODULE_ID
SHIZUKU_MODULE_MODE
SHIZUKU_MODULE_TRUSTED
SHIZUKU_MODULE_BACKGROUND

The environment is generated by the runtime rather than trusted from the module archive.

---

32. Action Runtime

"action.sh" is a user-triggered execution entry point.

Conceptual flow:

User
 ↓
Action Request
 ↓
Policy / Trust
 ↓
Backend Resolution
 ↓
Execution
 ↓
Result
 ↓
Log

It is not automatically a persistent service.

---

33. Service Runtime

"service.sh" is a separate lifecycle path.

Conceptually:

Module Enabled
      │
      ▼
Service Policy
      │
      ▼
Background Permission
      │
      ▼
Backend Availability
      │
      ▼
Service Execution

The service runtime must preserve ADB Module semantics.

It must not simply reuse Rootless daemon semantics.

---

34. Module Enable State

Module enabled state is persistent package state.

It affects service behavior but does not itself grant execution privilege.

Enabled
   ≠
Trusted
   ≠
Privileged

---

35. Execution Logging

ADB Module executions produce bounded execution records.

Conceptual record:

Execution
├── Module
├── Start Time
├── End Time
├── State
├── Exit Status
├── Output
├── Error
└── Backend

Logs must remain bounded.

---

36. Process Management

The runtime maintains process state for executions that produce processes.

Conceptually:

Execution
   ↓
Process Handle / Identity
   ↓
Runtime State
   ↓
Persistence

The application must distinguish persisted process information from verified active process state.

---

37. Runtime Persistence

Runtime persistence stores enough information to recover from normal application lifecycle changes.

Potential state includes:

- execution ID,
- package ID,
- process identity,
- backend,
- start time,
- execution state,
- service state,
- recovery metadata.

Persistence must not blindly claim that a process remains active.

---

38. Runtime Recovery

Recovery reconciles:

Persisted State
      +
Actual Runtime State
      ↓
Recovered State

Recovery must handle:

- application restart,
- process death,
- backend restart,
- binder/session changes,
- interrupted installation,
- interrupted updates,
- stale execution records.

---

39. WebUI Architecture

ADB Module WebUI is a package-specific presentation and execution surface.

Conceptually:

ADB Module
   │
   └── webui/
          │
          ▼
       WebView
          │
          ▼
   Security Boundary
          │
          ▼
   Shell Bridge / API
          │
          ▼
   Execution Abstraction

The WebUI must not bypass the runtime architecture.

---

40. "window.Shizuku" Compatibility

The compatibility layer exposes:

window.Shizuku

where required.

Conceptually:

Module WebUI
      │
      ▼
window.Shizuku
      │
      ▼
WebUI Bridge
      │
      ▼
Policy / Trust
      │
      ▼
Execution Abstraction
      │
      ▼
Selected Backend

The public compatibility namespace remains separate from the internal backend implementation.

---

41. WebUI Security

The WebUI security boundary controls:

- shell bridge access,
- file access,
- content access,
- network access,
- HTTPS,
- mixed content,
- cookies,
- command execution,
- output,
- timeouts.

The WebUI cannot bypass policy or trust simply because it runs inside the application.

---

42. Execution Limits

Execution limits apply to potentially unbounded resources.

Examples include:

- archive entry count,
- extracted archive size,
- script execution time,
- output size,
- WebUI command execution.

Limits must be enforced at the appropriate layer rather than relying solely on UI validation.

---

43. Common Execution Flow

A general execution flow is:

Package
   ↓
Execution Request
   ↓
Package Requirements
   ↓
Policy
   ↓
Trust
   ↓
Capability Resolution
   ↓
Backend Resolver
   ↓
Porter / Shizuku
   ↓
Process
   ↓
Execution Result
   ↓
Persistence / Logging
   ↓
UI

---

44. Installation Flow

General package installation:

Source / Local Package
        ↓
Package Identification
        ↓
Package-Type Detection
        ↓
Package Validation
        ↓
Package-Specific Installation
        ↓
Persistence
        ↓
Registration
        ↓
Installed Package

ADB Modules use their dedicated archive validation and installation path.

---

45. Update Flow

Updates follow:

Source
  ↓
Update Discovery
  ↓
Version Comparison
  ↓
Download
  ↓
Validation
  ↓
Trust / Policy Checks
  ↓
Staging
  ↓
Installation
  ↓
State Reconciliation

An update is treated as new package content and must be validated accordingly.

---

46. Source Architecture

Sources provide package discovery information.

Conceptually:

Source
  ↓
Catalog / Manifest
  ↓
Package Metadata
  ↓
Package Location
  ↓
Download
  ↓
Validation

Sources do not directly control privileged execution.

---

47. CodeBrick Architecture

CodeBricks remain a separate subsystem.

CodeBrick
   │
   ├── Saved Command
   ├── Environment
   ├── Execution
   └── Promotion

They may use common execution infrastructure where appropriate.

They are not ADB Modules.

---

48. Runtime State Model

The system distinguishes package state from execution state.

Package state

Not Installed
Installed
Enabled
Disabled
Update Available

Execution state

Queued
Starting
Running
Completed
Failed
Cancelled
Timed Out
Stale
Recovering

Exact enum names are implementation details.

---

49. Error Boundaries

Errors should remain associated with the layer that caused them.

UI Error
   ↑
Application Error
   ↑
Domain Error
   ↑
Runtime Error
   ↑
Backend Error
   ↑
OS / Process Error

Errors should not be flattened unnecessarily.

The UI should translate technical errors into understandable user-facing messages without destroying diagnostic information.

---

50. Rootless Integration Boundary

Rootless functionality should be classified into:

KEEP
ADAPT
EXTEND
REFACTOR
REPLACE

KEEP

Components whose semantics remain correct.

Examples may include:

- CodeBricks,
- package discovery,
- persistence,
- notifications.

ADAPT

Components whose concepts remain useful but need backend/package changes.

Examples may include:

- execution contexts,
- WebUI,
- privileged execution,
- plugin installation.

EXTEND

Components that need additional package support.

Examples may include:

- sources,
- package repositories,
- catalog UI.

REFACTOR

Components whose current implementation is too tightly coupled to one execution mechanism.

Examples may include:

- backend-specific execution gateways,
- Shizuku-specific privileged paths.

REPLACE

Only components whose semantics fundamentally conflict with the target architecture should be replaced.

---

51. Proposed Domain Components

The architecture may require concepts corresponding to:

ExecutablePackage
AdbModule
AdbModuleManifest
AdbModuleParser
AdbModuleInstaller
AdbModuleRepository
AdbModuleStorage
AdbModulePolicy
AdbModuleRuntime

ExecutionBackend
PorterExecutionBackend
ShizukuExecutionBackend
ExecutionBackendResolver

ExecutionRequest
ExecutionResult
ExecutionHandle
BackendCapability

ShizukuCompatibilityBridge

ModuleWebUiRuntime
ModuleCatalogSource

These are proposed architectural responsibilities, not claims about exact existing source filenames or APIs.

Actual implementation names should follow the repository's established conventions where practical.

---

52. Dependency Direction

The preferred dependency direction is:

UI
 ↓
Application
 ↓
Domain
 ↓
Runtime / Interfaces
 ↓
Infrastructure / Backends

Backend-specific infrastructure must not leak upward into domain concepts unnecessarily.

---

53. Backend Dependency Rule

Domain logic must not directly require:

Porter

or:

Shizuku

The runtime communicates through backend abstractions.

Backend adapters implement the actual integrations.

---

54. Package Dependency Rule

An ADB Module subsystem may use common runtime infrastructure.

It must not depend on Rootless Plugin semantics merely because Rootless Plugins existed first.

Likewise, Rootless Plugins must not become dependent on ADB Module semantics.

---

55. Security Dependency Direction

Security decisions should occur before privileged execution.

Conceptually:

Package Request
      ↓
Validation
      ↓
Policy
      ↓
Trust
      ↓
Capability
      ↓
Backend
      ↓
Privilege
      ↓
Execution

The backend must not be responsible for deciding whether a package should be trusted.

The UI must not be the sole enforcement point.

---

56. No Magisk/KSU Architecture

ADB Modules are not designed as systemless modules.

The architecture does not include:

- "/data/adb/modules",
- mount overlays,
- Magisk module hooks,
- KernelSU module hooks.

Adding those capabilities would constitute an architectural expansion requiring explicit investigation and architectural review.

---

57. No Whole-Shevery Architecture

The project does not adopt Shevery's entire application architecture.

Only relevant ADB Module behavior is incorporated into the compatibility boundary.

Unrelated Shevery functionality remains outside the architecture unless explicitly justified.

---

58. No Whole-Rootless Duplication

The project does not duplicate Rootless Store functionality unnecessarily.

Existing compatible infrastructure should remain the foundation.

Where semantics conflict, adapters or dedicated subsystems are preferred over forcing incompatible systems together.

---

59. Compatibility Boundary

The ADB Module compatibility boundary includes:

Package Format
    ↓
module.prop
    ↓
Scripts
    ↓
Environment
    ↓
Storage
    ↓
Lifecycle
    ↓
Policy
    ↓
Trust
    ↓
WebUI
    ↓
window.Shizuku
    ↓
Execution Limits

The compatibility boundary does not automatically include all features of Shevery.

---

60. External API Boundary

External APIs must remain behind appropriate integration boundaries.

Examples:

Porter
   ↓
Porter Adapter

Shizuku
   ↓
Shizuku Adapter

WebView
   ↓
WebUI Runtime

External Catalog
   ↓
Source Adapter

This reduces external dependency leakage into the domain.

---

61. Data Flow — ADB Module Installation

ZIP
 │
 ▼
Archive Validator
 │
 ▼
Manifest Parser
 │
 ▼
ADB Module Model
 │
 ▼
Staging
 │
 ▼
Safe Extraction
 │
 ▼
Storage
 │
 ▼
Repository
 │
 ▼
Installed Module

---

62. Data Flow — ADB Module Action

User
 │
 ▼
Action Request
 │
 ▼
Module State
 │
 ▼
Policy / Trust
 │
 ▼
Execution Requirements
 │
 ▼
Backend Resolver
 │
 ├──── Porter
 │
 └──── Shizuku
 │
 ▼
Process
 │
 ▼
Result / Output
 │
 ▼
Execution Persistence
 │
 ▼
UI

---

63. Data Flow — ADB Module Service

Module Enabled
      │
      ▼
Service Eligibility
      │
      ▼
Policy
      │
      ▼
Background Permission
      │
      ▼
Backend Capability
      │
      ▼
Backend Resolution
      │
      ▼
service.sh
      │
      ▼
Runtime Tracking
      │
      ▼
Recovery / Persistence

---

64. Data Flow — WebUI Command

Module WebUI
      │
      ▼
window.Shizuku
      │
      ▼
WebUI Bridge
      │
      ▼
Policy / Trust
      │
      ▼
Execution Request
      │
      ▼
Backend Resolver
      │
      ▼
Porter / Shizuku
      │
      ▼
Command Result
      │
      ▼
WebUI

---

65. Data Flow — Update

Source
  │
  ▼
Catalog
  │
  ▼
Update Available
  │
  ▼
Download
  │
  ▼
Validation
  │
  ▼
Trust / Policy
  │
  ▼
Staging
  │
  ▼
Installation
  │
  ▼
Runtime State Reconciliation

---

66. Lifecycle Boundary

The architecture distinguishes:

Package Lifecycle
       │
       ├── Discovery
       ├── Installation
       ├── Configuration
       ├── Enable / Disable
       ├── Update
       └── Removal

Execution Lifecycle
       │
       ├── Request
       ├── Start
       ├── Running
       ├── Complete
       ├── Fail
       ├── Cancel
       └── Recover

Do not merge package lifecycle and process lifecycle into one state machine.

---

67. Architectural Invariants

The following are structural invariants.

Invariant 1

ADB Modules are a distinct package type.

Invariant 2

Rootless Plugins remain supported.

Invariant 3

CodeBricks remain distinct.

Invariant 4

Porter is the primary execution backend.

Invariant 5

Shizuku is a compatibility backend.

Invariant 6

Backend identity is separate from privilege.

Invariant 7

Privilege is separate from policy.

Invariant 8

Policy is separate from trust.

Invariant 9

Source trust is separate from execution trust.

Invariant 10

"window.Shizuku" compatibility is preserved where required.

Invariant 11

ADB Modules do not become Magisk/KSU modules.

Invariant 12

"service.sh" does not automatically become a Rootless daemon.

Invariant 13

Privileged execution does not originate directly from UI code.

Invariant 14

Unvalidated package archives never reach privileged extraction.

Invariant 15

Backend switching cannot silently change execution semantics.

Invariant 16

Backend-specific APIs remain behind backend boundaries.

Invariant 17

Runtime state must be reconciled with actual process/backend state.

---

68. Non-Goals

The architecture does not automatically include:

- becoming a Shizuku Manager replacement,
- becoming a Magisk/KSU manager,
- supporting "/data/adb/modules",
- importing the entire Shevery application,
- importing every Rootless Store feature,
- reproducing another application's UI,
- making every package type share identical lifecycle semantics,
- making every backend expose identical capabilities,
- automatically adding AI functionality,
- unrestricted privileged execution.

These may only become goals through explicit architectural expansion.

---

69. Adaptive Architecture Expansion

The architecture may evolve when legitimate requirements are discovered.

A legitimate architectural gap is one that requires a structural change to:

- preserve compatibility,
- maintain security,
- support a required backend capability,
- support a required lifecycle,
- address a platform constraint,
- preserve package boundaries.

When a gap is discovered:

Discover
   ↓
Investigate
   ↓
Record finding
   ↓
Evaluate architectural impact
   ↓
Update architecture
   ↓
Update plan if implementation changes
   ↓
Implement
   ↓
Validate

Architecture must not be expanded merely because another project has an interesting feature.

---

70. Architecture Maintenance

Update this document when:

- a system boundary changes,
- component responsibility changes,
- a new architectural subsystem is introduced,
- backend relationships change,
- security boundaries change,
- package relationships change,
- lifecycle semantics change,
- compatibility boundaries change,
- a structural assumption is proven incorrect.

Do not update this document merely because a phase was completed.

Implementation progress belongs in "PLAN.md".

Research findings belong in "INVESTIGATION.md".

Agent behavior belongs in "AGENTS.md".

---

71. Current Proposed Architecture

The current proposed structure is subject to investigation and verification. It may be confirmed, modified, replaced, or invalidated by verified investigation findings. The current proposal is:

                         Unified Application
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        │                         │                         │
        ▼                         ▼                         ▼
 Rootless Plugin             ADB Module                CodeBrick
     System                    System                    System
        │                         │                         │
        └─────────────────────────┼─────────────────────────┘
                                  │
                                  ▼
                         Common Package Layer
                                  │
                                  ▼
                           Application Layer
                                  │
                                  ▼
                            Domain Layer
                                  │
                                  ▼
                           Runtime Layer
                                  │
                 ┌────────────────┴────────────────┐
                 │                                 │
                 ▼                                 ▼
              Policy                            Trust
                 │                                 │
                 └────────────────┬────────────────┘
                                  │
                                  ▼
                        Execution Abstraction
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
                    ▼                           ▼
                 Porter                    Shizuku
                PRIMARY                  COMPATIBILITY
                    │                           │
                    └─────────────┬─────────────┘
                                  │
                                  ▼
                         Privileged Execution

The architecture exists to keep the unified application coherent while preserving the different semantics of its package types and execution backends.

The central architectural separation remains:

Package
   ≠
Backend
   ≠
Privilege
   ≠
Policy
   ≠
Trust
   ≠
Runtime State

That separation is the foundation of the project's maintainability, compatibility, and security.
