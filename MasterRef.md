ADB Modules App

Definitive Personal Knowledge Base

Document status: Personal master reference
Audience: Me only
Purpose: Complete long-term knowledge base for the project
Scope: Investigation, architecture, design decisions, implementation strategy, compatibility, security, testing, development workflow, terminology, history, future direction, and project rationale.

---

# 0. HOW TO USE THIS DOCUMENT

This document exists for one person:

«Me.»

It is not an "AGENTS.md".

It is not a replacement for "PLAN.md".

It is not a public README.

It is not intended to give an AI agent instructions.

Instead, it is the place where I can come back months later and recover the entire mental model of the project without having to reconstruct all of the research and reasoning from scratch.

The other project documents have narrower purposes:

| Document | Purpose |
| --- | --- |
| "AGENTS.md" | Rules for agents working on the project |
| "INVESTIGATION.md" | Research findings and evidence |
| "ARCHITECTURE.md" | Structural architecture |
| "PLAN.md" | Implementation sequence |
| "README.md" | Public project explanation |
| This document | My complete personal knowledge base |

This document may contain information that would be unnecessary or excessive in the other documents.

That is intentional.

# Part I — Project Foundation

## 1. Project Identity

### 1. PROJECT AT A GLANCE

1.1 Project concept

The project is a unified Android application built from Rootless Store and expanded to support:

1. Rootless Plugins
2. ADB Modules
3. CodeBricks

The application will use a unified runtime architecture while preserving the distinct semantics of each package type.

The privileged execution architecture will be:

Porter
  ↓
PRIMARY backend

Shizuku
  ↓
COMPATIBILITY backend

The project is therefore:

«A Rootless-based unified Android package/runtime platform with first-class ADB Module support and a Porter-first privileged execution architecture with Shizuku compatibility.»

### 2. THE ONE-SENTENCE DESCRIPTION

If I need to explain the entire project in one sentence:

«I am extending Rootless Store into a unified package platform that preserves Rootless Plugins and CodeBricks while adding Shevery-compatible ADB Modules, executed through a backend-neutral runtime where Porter is primary and Shizuku is the compatibility path.»

### 3. THE CORE IDEA

The entire project can be reduced to:

Rootless foundation
        +
ADB Module compatibility
        +
Porter-first execution
        +
Shizuku compatibility
        +
security/policy/trust
        +
persistent runtime management
        =
ADB Modules App

## 2. Project History

### 7. PROJECT ORIGIN

The project originated from the idea of taking an existing, useful application architecture and extending it rather than building an entirely new package/runtime system from scratch.

The initial direction was:

«Fork Rootless Store and add full ADB Module support.»

Research then revealed that this was viable, but only if the two package ecosystems were kept conceptually separate.

That led to the current architecture.

## 3. Project Goals

## 4. Project Non-Goals

# Part II — Complete Investigation

## 5. Investigation Methodology

## 6. Rootless Store Investigation

### 8. WHY ROOTLESS STORE

Rootless Store already provides many difficult pieces:

- package management
- package metadata
- execution
- execution contexts
- persistence
- runtime tracking
- runtime recovery
- plugin sources
- market/catalog concepts
- WebUI support
- CodeBricks
- notifications
- device monitoring
- Shizuku execution infrastructure

Rebuilding all of that independently would create unnecessary work and additional failure points.

Therefore:

«Rootless Store is the foundation.»

### 11. ROOTLESS STORE INVESTIGATION

Rootless Store provides a useful architecture for executable packages.

Important conceptual areas include:

- "Plugin"
- "PluginManifest"
- "PluginSource"
- "PluginExecution"
- "PluginRuntime"
- "ExecutionContext"
- "CodeBrick"
- market/source infrastructure
- persistence
- runtime recovery
- execution gateways
- WebUI
- device status

The exact implementation should always be inspected in the current fork rather than relying permanently on remembered source names.

### 13. ROOTLESS EXECUTION MODEL

Rootless already contains the concept of execution contexts and backend-oriented execution.

This provides a useful foundation.

However, the project should refactor where necessary so the architecture can support:

Porter
+
Shizuku

without forcing the entire application to understand backend-specific implementation details.

### 14. ROOTLESS PERSISTENCE

Existing persistence concepts are valuable.

Installed package state and execution state should remain distinct.

That distinction becomes even more important once ADB Modules can have:

- enabled state
- services
- actions
- WebUI
- persistent runtime state
- logs

### 15. ROOTLESS RUNTIME RECOVERY

Runtime recovery is one of the existing ideas worth preserving.

The application should be able to start and ask:

«What did I previously think was running?»

Then:

«What is actually running now?»

Then reconcile the two.

## 7. Shevery Investigation

### 9. WHY NOT BUILD FROM SHEVERY

Shevery was investigated because it contains the desired ADB Module functionality.

However, Shevery is fundamentally a different application with a different architectural center.

The desired application needs:

- Rootless Plugin support
- CodeBricks
- source architecture
- Rootless runtime infrastructure
- ADB Modules
- Porter

Therefore Shevery is better treated as:

«The reference implementation for the ADB Module compatibility contract.»

It is not the application's architectural foundation.

### 17. SHEVERY INVESTIGATION

Shevery was investigated specifically for its ADB Module behavior.

Important features discovered include:

- "module.prop"
- module banners
- "action.sh"
- "service.sh"
- WebUI
- enable/disable
- policy controls
- trust controls
- background execution
- module logs
- module storage
- catalog
- execution limits
- "window.Shizuku"
- shell bridge functionality

The project should implement the relevant compatibility contract without copying unrelated Shevery features.

### 18. SHEVERY IS ACTIVELY EVOLVING

The reference implementation is not static.

Therefore:

«Compatibility should be pinned to a deliberately supported contract/version rather than assuming "whatever Shevery does today forever."»

Whenever a significant Shevery update changes the ADB Module contract:

1. investigate
2. compare behavior
3. determine whether compatibility matters
4. update the compatibility layer if appropriate
5. update tests
6. update documentation

## 8. Porter Investigation

### 39. PORTER INVESTIGATION

Porter is the chosen primary backend.

The exact Porter SDK/API must always be verified from the actual dependency/source.

No guessed API is acceptable.

The architecture should therefore use an adapter:

```
ExecutionBackend
      │
      └── PorterExecutionBackend
```

rather than scattering Porter API calls throughout the application.

## 9. Shizuku Investigation

## 10. Cross-Project Comparison

# Part III — Core Architectural Decisions

## 11. Architectural Philosophy

## 12. Core Architectural Separations

### 5. WHAT EACH CONCEPT MEANS

Package

What is being executed?

Examples:

- Rootless Plugin
- ADB Module
- CodeBrick

---

Backend

How is privileged execution provided?

Examples:

- Porter
- Shizuku

---

Privilege

What authority does the resulting process actually have?

Examples can differ depending on how the backend was started.

---

Policy

What is this package allowed to do?

Examples:

- run actions
- run services
- run in background
- use WebUI bridge
- use networking
- execute commands

---

Trust

Do I trust this package sufficiently to execute it?

Trust is a user/security decision.

---

Runtime State

What is actually happening right now?

Examples:

- installed
- enabled
- starting
- running
- stopped
- failed
- unknown

### 6. WHY THIS SEPARATION MATTERS

If these concepts are merged together, the architecture becomes difficult to reason about.

For example:

"Porter is available"

does not necessarily mean:

"Every package can run."

Likewise:

"Package is trusted"

does not mean:

"Package may bypass every policy."

And:

"Shizuku is running"

does not automatically mean:

"Shizuku provides the same privilege semantics as every possible Porter configuration."

The architecture must preserve these distinctions.

## 13. Final Architectural Model

## 14. Architectural Invariants

### 4. THE MOST IMPORTANT ARCHITECTURAL RULE

The most important mental model in the entire project is:

PACKAGE
   ≠
BACKEND
   ≠
PRIVILEGE
   ≠
POLICY
   ≠
TRUST
   ≠
RUNTIME STATE

These are related concepts.

They are not interchangeable.

# Part IV — Application Architecture

## 15. Application Layer Structure

## 16. UI Architecture

## 17. Application/Use-Case Architecture

## 18. Domain Architecture

### 64. PACKAGE LAYER

The common package layer should provide only genuinely common concepts.

Potential common fields:

id
name
version
description
author
source
installed
update available

But package-specific fields remain in package-specific models.

### 65. ADB MODULE DOMAIN MODEL

Conceptually:

```
AdbModule
├── identity
├── metadata
├── installation
├── enabled state
├── action
├── service
├── WebUI
├── policy
├── trust
├── runtime
└── logs
```

### 75. PACKAGE REMOVAL

Removal should consider:

- active execution
- service state
- logs
- persistent configuration
- WebUI
- cached data

The user should not be allowed to unknowingly leave an active privileged process behind.

## 19. Runtime Architecture

## 20. Data Architecture

# Part V — Execution Backend Architecture

## 21. Execution Abstraction

### 42. BACKEND ABSTRACTION

Conceptually:

```
ExecutionBackend
├── PorterExecutionBackend
└── ShizukuExecutionBackend
```

Potential operations include conceptual equivalents of:

- execute
- terminate
- inspect capability
- inspect state
- create execution handle

Exact interfaces will be determined during implementation.

### 43. EXECUTION REQUEST

An execution request should conceptually contain enough information to answer:

- what package?
- what operation?
- what working directory?
- what environment?
- what policy?
- what limits?
- what output requirements?
- what backend requirements?

Example conceptual model:

```
ExecutionRequest
├── package
├── operation
├── command/script
├── environment
├── workingDirectory
├── timeout
├── outputLimits
└── capabilityRequirements
```

### 44. EXECUTION RESULT

Conceptually:

```
ExecutionResult
├── success
├── exitCode
├── stdout
├── stderr
├── failureReason
├── backend
└── runtime metadata
```

The exact model should be driven by actual requirements.

### 45. EXECUTION HANDLE

Long-running operations need something that represents the active runtime.

Conceptually:

```
ExecutionHandle
├── executionId
├── process identity
├── backend
├── state
├── start time
└── controls
```

### 47. BACKEND STATE

Potential states:

NOT_INSTALLED
NOT_RUNNING
NO_PERMISSION
READY
UNSUPPORTED
ERROR

Again, exact states should be verified against actual backend behavior.

## 22. Porter Backend

### 40. WHY PORTER IS PRIMARY

The project is intentionally being designed against Porter first.

That means Porter should influence:

- capability modeling
- lifecycle design
- runtime integration
- privileged execution abstraction
- testing

But Porter should not become the entire domain model.

## 23. Shizuku Backend

### 41. WHY SHIZUKU REMAINS

Shizuku compatibility provides:

- compatibility with existing environments
- compatibility with modules expecting Shizuku
- fallback where appropriate
- a second backend for validation

It also helps preserve the ecosystem the ADB Module format was designed around.

## 24. Backend Resolver

### 48. BACKEND RESOLUTION

The resolver considers:

```
Execution Request
       ↓
Required capabilities
       ↓
Policy
       ↓
Trust
       ↓
Available backends
       ↓
User preference
       ↓
Resolved backend
```

The resolver should explain why a backend was selected or rejected.

## 25. Capability Model

### 46. BACKEND CAPABILITY

A backend should report capabilities.

Potential conceptual capabilities:

EXECUTE_COMMAND
EXECUTE_SCRIPT
BACKGROUND_SERVICE
PROCESS_CONTROL
WEBUI_BRIDGE
PERSISTENT_RUNTIME

The final list should be based on real requirements.

# Part VI — Privilege, Policy, Trust

## 26. Privilege Model

### 49. NO SILENT PRIVILEGE CHANGES

This is one of the strongest rules in the project.

Suppose:

Porter

provides one privilege level and:

Shizuku

provides another.

The application must not silently substitute one for the other if doing so changes the module's effective authority.

The user should have meaningful control.

### 50. PRIVILEGE MODEL

The architecture should answer three separate questions:

1. Which backend is being used?
2. What privilege does that backend currently provide?
3. Is that privilege sufficient for this operation?

These must not be collapsed into one boolean.

## 27. Policy Model

### 33. POLICY

Reference policy concepts include:

- Safe mode
- Custom access
- Full access
- background action permission
- service permission

The final architecture should represent these as policy rather than embedding them inside the backend.

### 38. EXECUTION PERMISSION

Even a trusted package may be denied execution by policy.

Therefore:

Trusted
+
Policy denied
=
No execution

## 28. Trust Model

### 34. TRUST

A module may have trust state.

Reference behavior includes per-module trust overrides.

However:

«Trust must not become a universal "ignore security" button.»

Fundamental archive and runtime safety must remain intact.

### 35. FULL TRUST

Reference Full Trust can affect certain module capabilities.

The project may support similar semantics where required for compatibility.

But Full Trust must not mean:

disable every security boundary

Core platform protections should remain.

### 36. SOURCE TRUST

A catalog or source can be considered trustworthy.

That does not mean every package from it automatically receives unrestricted execution trust.

This is a critical security distinction.

### 37. PACKAGE TRUST

Package trust belongs to the package/user relationship.

It may involve:

- explicit approval
- trust override
- revocation
- source information

### 80. SOURCE TRUST VS EXECUTION TRUST

This deserves repetition because it is easy to forget.

Trusted catalog
      ≠
Trusted package
      ≠
Allowed execution

## 29. Security Model

### 78. SECURITY THREAT MODEL

Potential hostile inputs include:

- malicious ZIPs
- malicious "module.prop"
- malicious scripts
- malicious WebUI
- malicious catalog metadata
- compromised package sources
- malicious update packages
- packages attempting privilege abuse
- packages attempting filesystem escape

The application should assume package content may be malicious.

### 79. SECURITY LAYERS

The system should defend through:

```
Source validation
      ↓
Package validation
      ↓
Archive validation
      ↓
Path validation
      ↓
Trust
      ↓
Policy
      ↓
Capability
      ↓
Backend
      ↓
Privilege
      ↓
Execution limits
      ↓
Runtime monitoring
```

### 81. FULL TRUST VS FUNDAMENTAL SECURITY

A future Full Trust mode can relax certain module-specific restrictions.

It should not disable fundamental platform safety.

For example:

«Full Trust does not mean "allow ZIP traversal."»

### 82. RESOURCE EXHAUSTION

Security is not only about malicious commands.

A module can cause denial-of-service behavior through:

- huge archive
- huge number of files
- enormous output
- endless execution
- excessive WebUI activity
- repeated background work

Therefore limits are security features.

### 83. SCRIPT TIMEOUT

A bounded execution timeout protects against accidental or malicious indefinite execution.

The reference target is approximately:

120 seconds

for bounded script actions unless a different capability specifically requires another model.

### 84. OUTPUT LIMITS

Retaining unlimited stdout/stderr could exhaust memory or storage.

The reference model limits retained output to approximately:

64 KB per stream

The final implementation should verify and document the chosen behavior.

# Part VII — ADB Module System

## 30. ADB Module Concept

### 19. ADB MODULES ARE NOT MAGISK MODULES

This distinction is extremely important.

The targeted ADB Module system is not intended to be:

- Magisk
- KernelSU
- APatch
- a systemless module system

Therefore the application should not assume:

/data/adb/modules

or:

- mount overlays
- boot-stage module injection
- systemless replacement
- Magisk lifecycle semantics

ADB Modules are their own package format.

## 31. Module Package Structure

### 20. ADB MODULE FORMAT

A minimal module:

```
my-module/
├── module.prop
├── action.sh
└── webui/
    └── index.html
```

Optional:

banner.png
service.sh

Custom paths may be specified by metadata.

## 32. module.prop

### 21. "module.prop"

Required fields:

id=
name=
version=
versionCode=
author=
description=

Example:

id=my-module
name=My Module
version=1.0
versionCode=1
author=Author
description=Short module description

### 22. CUSTOM MODULE PATHS

The reference format can support fields such as:

banner=assets/banner.webp
webui=webui
usesShellBridge=true
action=scripts/action.sh

All paths must be validated.

## 33. Module Archive Validation

### 23. ARCHIVE ROOT REQUIREMENT

The ZIP must contain "module.prop" at the expected root location.

This must be validated before installation.

The application should not blindly extract arbitrary ZIP contents and search afterward.

### 24. ARCHIVE SECURITY

ZIP archives must be treated as hostile input.

Security checks include:

- entry count
- extracted size
- path validity
- traversal
- absolute paths
- malformed archives
- invalid metadata
- unexpected structure

### 25. REFERENCE SAFETY LIMITS

The investigated ADB Module implementation uses limits around:

ZIP entries:
2048

Extracted size:
200 MB

Script timeout:
120 seconds

Retained stdout:
64 KB

Retained stderr:
64 KB

These should be treated as compatibility/reference values and verified against the supported target version before implementation.

### 26. PATH TRAVERSAL

A package must never be able to escape its installation directory.

Reject paths such as:

../file
../../file

and absolute paths.

The conceptual validation is:

```
Archive path
    ↓
Normalize
    ↓
Resolve inside module root
    ↓
Verify containment
    ↓
Allow or reject
```

## 34. Module Storage

### 27. MODULE STORAGE

Reference storage:

/data/user/0/<package>/files/adb_modules/<id>

The exact final storage architecture belongs to the application implementation.

The important principles are:

- private
- isolated
- deterministic
- secure
- persistent
- recoverable

## 35. Module Installation

### 178. ADB MODULE INSTALLATION PIPELINE

```
Source
  ↓
Download
  ↓
Archive inspection
  ↓
ZIP validation
  ↓
Path validation
  ↓
Size/entry validation
  ↓
module.prop parsing
  ↓
Metadata validation
  ↓
Staging
  ↓
Safe extraction
  ↓
Post-install verification
  ↓
Persistence
  ↓
Registration
```

## 36. Module Environment

### 28. MODULE ENVIRONMENT

Reference variables include:

MODDIR
ASH_STANDALONE
SHIZUKU_MODULE_ID
SHIZUKU_MODULE_MODE
SHIZUKU_MODULE_TRUSTED
SHIZUKU_MODULE_BACKGROUND

These may become part of the compatibility contract.

## 37. Action Runtime

### 29. "action.sh"

"action.sh" represents a user-triggered action.

Typical lifecycle:

```
User taps Action
       ↓
Resolve module
       ↓
Trust evaluation
       ↓
Policy evaluation
       ↓
Backend capability check
       ↓
Backend resolution
       ↓
Execution
       ↓
Capture output
       ↓
Persist result
       ↓
Show result
```

### 179. ADB MODULE ACTION PIPELINE

```
User
  ↓
Select module
  ↓
Action requested
  ↓
Module exists?
  ↓
Trusted?
  ↓
Policy allows?
  ↓
Backend capable?
  ↓
Resolve backend
  ↓
Prepare environment
  ↓
Execute action.sh
  ↓
Capture output
  ↓
Record result
  ↓
Update runtime
  ↓
Show result
```

## 38. Service Runtime

### 30. "service.sh"

"service.sh" represents controlled background behavior.

It must not be interpreted as automatically equivalent to a Rootless daemon plugin.

The reference behavior includes conditions around:

- module enabled state
- access mode
- background action permission
- backend availability
- policy

### 31. SERVICE SESSION MODEL

Reference behavior includes automatically running enabled services once per relevant Shizuku binder session.

The final implementation must reconcile this behavior with the backend-neutral architecture.

That means:

«Do not simply copy "Shizuku session" assumptions into the Porter runtime.»

Instead, determine the equivalent runtime event for each backend.

### 180. ADB MODULE SERVICE PIPELINE

```
Module enabled
  ↓
Service eligible?
  ↓
Background policy allows?
  ↓
Backend available?
  ↓
Service not already running?
  ↓
Prepare environment
  ↓
Start service.sh
  ↓
Track runtime
  ↓
Persist state
  ↓
Monitor
```

## 39. Module Enable/Disable

### 32. ENABLE/DISABLE

Module enabled state is separate from installation state.

A module can be:

Installed
+
Disabled

or:

Installed
+
Enabled

Disabling should affect service/background behavior appropriately without necessarily deleting the module.

# Part VIII — Module WebUI

## 40. WebUI Architecture

### 52. WEBUI ARCHITECTURE

The WebUI is part of the module experience.

It should support:

```
Module
  ↓
WebUI content
  ↓
WebView
  ↓
Controlled bridge
  ↓
Execution request
```

The WebView itself should never become the runtime engine.

### 55. WEBUI ENVIRONMENT

WebUI command execution may provide:

- environment
- working directory
- stdin
- timeout
- output limits

These should be translated into the backend-neutral execution request.

### 181. WEBUI PIPELINE

```
Module
  ↓
WebUI
  ↓
WebView
  ↓
JavaScript
  ↓
window.Shizuku
  ↓
Compatibility bridge
  ↓
Execution request
  ↓
Policy/trust
  ↓
Backend resolver
  ↓
Porter/Shizuku
```

## 41. window.Shizuku Compatibility

### 53. "window.Shizuku"

Compatibility requires preserving the expected JavaScript namespace:

window.Shizuku

This does not mean the entire application must internally use Shizuku.

It means the compatibility layer can emulate/provide the expected contract where appropriate.

### 54. WEBUI COMMAND EXECUTION

Reference APIs include concepts like:

window.Shizuku.exec(...)

and:

window.Shizuku.execWithOptions(...)

The implementation should preserve compatibility with the supported API.

## 42. WebUI Security

### 56. WEBUI SECURITY

The WebUI must be restricted.

Reference protections include:

- file access restrictions
- content access restrictions
- third-party cookie restrictions
- mixed content blocking
- HTTPS/network controls

Additional project policy should govern whether WebUI has command bridge access.

### 57. WEBUI NETWORKING

Network access should not automatically imply privileged command access.

These are different capabilities.

For example:

WebUI network
    ≠
Shell bridge
    ≠
Privileged execution

# Part IX — Execution, Logging, Recovery

## 43. Execution Model

## 44. Process Management

### 58. PROCESS MANAGEMENT

The runtime should track active executions.

Possible lifecycle:

```
REQUESTED
   ↓
STARTING
   ↓
RUNNING
   ↓
STOPPING
   ↓
STOPPED
```

or:

RUNNING
   ↓
COMPLETED

or:

RUNNING
   ↓
FAILED

### 59. PID HANDLING

A PID can be useful, but it should not be treated as eternal identity.

A persisted PID may become:

- stale
- reused
- unavailable
- invalid

Therefore runtime recovery must verify actual state.

### 85. PROCESS TERMINATION

The application should provide controlled termination where the backend permits it.

It must also correctly report when termination fails.

## 45. Execution Logging

### 60. EXECUTION LOGGING

Each execution should have an identifiable record.

Useful fields include:

Execution ID
Package ID
Operation
Backend
Start time
End time
Result
Exit code
stdout
stderr
Error

Output retention should respect configured limits.

## 46. Runtime Persistence

### 61. RUNTIME PERSISTENCE

Persistence exists so application process death does not destroy knowledge of what was happening.

Persistent data can include:

- installed packages
- package metadata
- enabled state
- execution records
- active runtime records
- logs
- trust state
- policy state

## 47. Runtime Recovery

### 62. RUNTIME RECOVERY

On startup:

```
Read persisted state
        ↓
Inspect actual backend state
        ↓
Inspect actual process state
        ↓
Reconcile
        ↓
Mark stale records
        ↓
Recover valid records
        ↓
Update UI
```

### 183. RECOVERY PIPELINE

```
App startup
  ↓
Load persisted state
  ↓
Inspect backend
  ↓
Inspect process
  ↓
Compare expected vs actual
  ↓
Reconcile
  ↓
Mark stale state
  ↓
Recover valid state
  ↓
Update UI
```

# Part X — Rootless Plugins

## 48. Rootless Plugin Preservation

### 12. ROOTLESS PLUGIN MANIFEST

Rootless Plugin metadata should remain compatible with the existing application.

A major rule is:

«Do not contaminate the Rootless Plugin manifest with Shevery-specific ADB Module fields merely to make the two formats look unified.»

Instead:

Rootless Plugin
    ↓
PluginManifest

ADB Module
    ↓
AdbModuleManifest

A common abstraction may sit above them where genuinely appropriate.

### 66. ROOTLESS PLUGIN DOMAIN MODEL

Conceptually:

```
RootlessPlugin
├── manifest
├── run model
├── execution context
├── entry point
├── WebUI
├── installation
└── runtime
```

## 49. Rootless Adaptation

## 50. Rootless vs ADB Module Semantics

### 86. ROOTLESS DAEMON VS MODULE SERVICE

This distinction must remain explicit.

Rootless daemon plugin
        ≠
ADB Module service.sh

Even though both can involve persistent/background execution, their contracts differ.

### 87. WHY NOT UNIFY THEM COMPLETELY

They may share:

- process management
- logging
- persistence
- scheduling infrastructure

But they should not be treated as identical lifecycle contracts.

# Part XI — CodeBricks

## 51. CodeBrick Architecture

### 16. ROOTLESS CODEBRICKS

CodeBricks should remain.

They represent a lightweight automation mechanism and should not be unnecessarily converted into ADB Modules.

The three package families therefore remain:

Rootless Plugin
ADB Module
CodeBrick

### 67. CODEBRICK DOMAIN MODEL

Conceptually:

```
CodeBrick
├── command/content
├── environment
├── metadata
└── execution
```

# Part XII — Sources, Catalogs, Updates

## 52. Source Architecture

### 71. SOURCE ARCHITECTURE

Sources should provide discovery.

Potential source layers:

```
Source
 ↓
Gateway
 ↓
Repository
 ↓
Catalog
 ↓
Package metadata
```

The exact structure can reuse Rootless source architecture where appropriate.

## 53. ADB Module Catalog

### 72. CATALOG ARCHITECTURE

Catalogs can provide:

- module metadata
- versions
- download locations
- descriptions
- screenshots
- authorship
- update information

But catalog data must not be treated as equivalent to execution permission.

## 54. Update Architecture

### 73. PACKAGE UPDATE ARCHITECTURE

An update should be staged.

Conceptually:

```
New package
     ↓
Validate
     ↓
Stage
     ↓
Stop/reconcile affected runtime
     ↓
Replace
     ↓
Verify
     ↓
Restore appropriate state
```

### 74. UPDATE SAFETY

An update should not:

- blindly overwrite a running package
- erase trust state without reason
- bypass validation
- bypass archive security
- assume the new version is compatible

### 182. UPDATE PIPELINE

```
Catalog
  ↓
New version
  ↓
Compare
  ↓
Download
  ↓
Validate
  ↓
Stage
  ↓
Runtime reconciliation
  ↓
Replace
  ↓
Verify
  ↓
Restore appropriate state
```

# Part XIII — UI

## 55. Application Navigation

### 88. USER EXPERIENCE MODEL

The user should be able to understand:

- what package they are looking at
- what it will execute
- what backend will execute it
- what privilege it will receive
- what policy applies
- whether it is trusted
- whether it is currently running

The UI should not hide important security information.

## 56. ADB Module UI

### 89. PACKAGE DETAILS SCREEN

A future package detail experience can include:

Name
Version
Author
Description
Source
Package Type

Backend
Privilege
Trust
Policy

Installed
Enabled
Running

Actions
Service
WebUI
Logs
Updates

Exact UI is not yet final.

## 57. Backend UI

### 51. UI BACKEND SELECTION

The UI may allow backend selection where appropriate.

But the UI should not directly instantiate Porter or Shizuku services.

Instead:

```
UI
 ↓
Use Case
 ↓
Resolver
 ↓
Backend
```

### 90. BACKEND STATUS UI

The user should be able to see whether:

Porter

is:

- unavailable
- not running
- permission denied
- ready

and likewise for:

Shizuku

where applicable.

### 91. ERROR UI

Instead of:

«Failed.»

Prefer something like:

Module could not run.

Backend:
Porter

Reason:
Required capability unavailable.

Available alternative:
Shizuku

Privilege difference:
[shown if relevant]

The exact UX remains to be designed.

## 58. Device/Runtime UI

### 76. DEVICE STATUS

The application should eventually provide useful runtime status such as:

Porter: READY
Shizuku: AVAILABLE
Runtime: HEALTHY

But status should represent actual observed state.

### 77. NOTIFICATIONS

Rootless already contains notification concepts.

These can be preserved for:

- execution completion
- service problems
- updates
- installation
- failures

Notifications should not become the only source of runtime information.

# Part XIV — Development Plan

## 59. Master Implementation Plan

### 103. DEVELOPMENT PHASES

The official implementation plan currently contains 55 major stages, numbered 0–54.

They are grouped conceptually into:

```
Foundation
 ↓
Execution
 ↓
Backend
 ↓
Package model
 ↓
ADB Modules
 ↓
Security
 ↓
Runtime
 ↓
Sources
 ↓
UI
 ↓
Testing
 ↓
Release
 ↓
Audit
```

### 104. PHASE 0 — BASELINE

Before changing code:

- build current project
- run existing tests
- inspect structure
- record baseline
- identify existing functionality

### 105. PHASE 1 — SOURCE/LICENSE AUDIT

Determine:

- what is being reused
- what is being adapted
- what is newly implemented
- licenses
- notices
- attribution requirements

### 106. PHASE 2 — ARCHITECTURE EXTRACTION

Map the current Rootless architecture.

Identify:

- application
- domain
- data
- service
- UI
- execution
- persistence
- source
- runtime

### 107. PHASE 3 — EXECUTION ABSTRACTION

Introduce/define backend-neutral execution semantics.

Do this before deep Porter/Shizuku integration.

### 108. PHASE 4 — PRIVILEGE/CAPABILITY MODEL

Separate:

- backend
- capability
- privilege
- policy

### 109. PHASE 5 — PORTER BACKEND

Implement the primary backend against verified Porter APIs.

### 110. PHASE 6 — PORTER VALIDATION

Test:

- availability
- execution
- errors
- lifecycle
- capabilities
- privilege semantics

### 111. PHASE 7 — SHIZUKU BACKEND

Implement Shizuku behind the same abstraction.

### 112. PHASE 8 — BACKEND RESOLVER

Create the logic that determines which backend can satisfy a request.

### 113. PHASE 9 — ROOTLESS REGRESSION

Before going deeper into ADB Modules, verify Rootless behavior remains intact.

### 114. PHASE 10 — COMMON PACKAGE MODEL

Introduce only genuinely shared package concepts.

### 115. PHASE 11 — ADB MODULE DOMAIN

Create module-specific domain concepts.

### 116. PHASE 12 — MODULE.PROP PARSER

Implement parser and validation.

### 117. PHASE 13 — ARCHIVE VALIDATION

Implement:

- ZIP validation
- traversal protection
- size limits
- entry limits
- root structure validation

### 118. PHASE 14 — STAGED INSTALLATION

Implement safe installation.

### 119. PHASE 15 — STORAGE

Implement persistent module storage.

### 120. PHASE 16 — MODULE ENVIRONMENT

Implement compatibility environment variables.

### 121. PHASE 17 — ACTION RUNTIME

Implement "action.sh".

### 122. PHASE 18 — SERVICE RUNTIME

Implement "service.sh".

### 123. PHASE 19 — ENABLE/DISABLE

Implement module lifecycle state.

### 124. PHASE 20 — EXECUTION LOGGING

Persist execution information.

### 125. PHASE 21 — POLICY

Implement policy evaluation.

### 126. PHASE 22 — TRUST

Implement package trust.

### 127. PHASE 23 — WEBUI

Implement local WebUI.

### 128. PHASE 24 — WINDOW.SHIZUKU

Implement the compatibility bridge.

### 129. PHASE 25 — WEBUI SECURITY

Harden:

- networking
- file access
- content access
- bridge
- WebView restrictions

### 130. PHASE 26 — PROCESS MANAGEMENT

Implement process tracking and control.

### 131. PHASE 27 — RUNTIME PERSISTENCE

Persist active execution state.

### 132. PHASE 28 — RUNTIME RECOVERY

Reconcile persisted state with actual state.

### 133. PHASE 29 — CODEBRICKS

Verify CodeBrick preservation/integration.

### 134. PHASE 30 — SOURCE ARCHITECTURE

Extend Rootless source infrastructure where necessary.

### 135. PHASE 31 — CATALOG

Implement ADB Module discovery/catalog support.

### 136. PHASE 32 — UPDATES

Implement package update architecture.

### 137. PHASE 33 — PACKAGE UI

Create unified package presentation.

### 138. PHASE 34 — BACKEND UI

Display backend state and capability.

### 139. PHASE 35 — DEVICE/RUNTIME UI

Expose runtime health and state.

### 140. PHASE 36 — PACKAGE DETAILS

Provide complete package information.

### 141. PHASE 37 — ERROR/RECOVERY UI

Make failures understandable and actionable.

### 142. PHASE 38 — CORE UNIT TESTING

Expand automated test coverage.

### 143. PHASE 39 — ADB MODULE COMPATIBILITY

Test real module behavior.

### 144. PHASE 40 — REFERENCE MODULES

Build the reference fixture collection.

### 145. PHASE 41 — PORTER INTEGRATION TESTING

Test the primary backend.

### 146. PHASE 42 — SHIZUKU TESTING

Test compatibility backend.

### 147. PHASE 43 — BACKEND SWITCHING

Verify correct resolution and no silent privilege changes.

### 148. PHASE 44 — SECURITY VALIDATION

Perform focused security review/testing.

### 149. PHASE 45 — PERSISTENCE/RECOVERY

Test runtime durability.

### 150. PHASE 46 — PERFORMANCE

Test resource usage.

### 151. PHASE 47 — DEVICE COMPATIBILITY

Test across supported Android/device environments.

### 152. PHASE 48 — UI/UX

Polish the final experience after functionality is proven.

### 153. PHASE 49 — DOCUMENTATION

Synchronize:

- README
- architecture
- investigation
- plan
- agents

### 154. PHASE 50 — RELEASE PREPARATION

Prepare:

- builds
- versioning
- notices
- release documentation
- compatibility notes

### 155. PHASE 51 — FINAL COMPATIBILITY AUDIT

Review:

- Rootless compatibility
- ADB Module compatibility
- Porter compatibility
- Shizuku compatibility
- Android compatibility

### 156. PHASE 52 — ARCHITECTURE AUDIT

Verify the final implementation still follows:

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
Runtime

### 157. PHASE 53 — ADAPTIVE EXPANSION

Determine whether legitimate gaps remain.

Only add work when justified.

### 158. PHASE 54 — FINAL DEFINITION OF DONE

The project is complete only after:

- functionality
- compatibility
- security
- testing
- documentation
- architecture

are all validated.

### 214. WHAT SHOULD BE BUILT FIRST

The most important foundation is:

```
Execution abstraction
        ↓
Porter
        ↓
Shizuku
        ↓
Resolver
```

before deeply integrating ADB Module behavior.

Why?

Because the module runtime needs to know how execution actually works.

### 215. WHY BACKEND FIRST

If ADB Modules are implemented directly against Shizuku first, the architecture may accidentally become Shizuku-centric.

Because Porter is the intended primary backend, the backend abstraction needs to exist before module execution is deeply implemented.

### 217. WHY UI LATE

The UI should follow the architecture.

Building the UI first risks embedding assumptions about:

- backend
- package model
- privilege
- runtime

before those systems are actually settled.

### 219. PROJECT MATURITY MODEL

The project can be thought of as:

Level 1
Rootless foundation

Level 2
Backend abstraction

Level 3
Porter

Level 4
Shizuku compatibility

Level 5
ADB Module package system

Level 6
Security/policy/trust

Level 7
Runtime persistence/recovery

Level 8
Sources/catalogs/updates

Level 9
Unified UI

Level 10
Compatibility/release maturity

### 220. WHAT "DONE" REALLY MEANS

"Done" does not mean:

APK installs.

It means:

The system is coherent.
The package formats work.
The backends work.
The security model works.
The runtime works.
The old Rootless functionality still works.
ADB Modules are compatible.
The application survives failures.
The documentation matches reality.

# Part XV — Testing

## 60. Testing Philosophy

### 92. TESTING PHILOSOPHY

The project must not rely on "it builds."

Testing must validate:

- behavior
- compatibility
- security
- runtime state
- backend semantics
- persistence
- recovery

### 93. UNIT TEST MATRIX

Important unit-test groups:

Metadata

- valid manifest
- missing field
- malformed field
- duplicate field
- unsupported field

Archive

- valid archive
- traversal
- absolute path
- oversized archive
- too many entries
- missing module.prop

Backend

- available
- unavailable
- permission denied
- unsupported capability

Policy

- allowed
- denied
- trust override
- background restriction

Runtime

- start
- stop
- completion
- failure
- stale state

### 98. PERSISTENCE TEST MATRIX

Test:

- clean startup
- app restart
- process death
- device reboot
- backend restart
- process disappearance
- stale PID
- stale service
- incomplete installation
- interrupted update

### 218. WHY TESTING THROUGHOUT

Security and runtime bugs become much more expensive to fix after the entire application is built.

Tests should accompany the relevant phases.

## 61. Backend Testing

### 95. BACKEND TEST MATRIX

For every supported execution operation:

| Situation | Porter | Shizuku |
| --- | --- | --- |
| Installed/available | Test | Test |
| Not running | Test | Test |
| Permission denied | Test | Test |
| Capability unsupported | Test | Test |
| Process starts | Test | Test |
| Process exits | Test | Test |
| Process fails | Test | Test |
| Backend disappears | Test | Test |
| Recovery | Test | Test |

### 96. BACKEND SWITCHING

Critical scenarios:

Porter only

Porter ready
Shizuku unavailable

→ execute through Porter

Shizuku only

Porter unavailable
Shizuku ready

→ execute through Shizuku

Neither

Porter unavailable
Shizuku unavailable

→ clear failure

Both

Porter ready
Shizuku ready

→ resolver follows configured policy/preference

## 62. ADB Module Testing

### 94. REFERENCE MODULE TEST SUITE

Create a set of known modules:

01-minimal
02-action-only
03-service-only
04-action-service
05-webui
06-webui-shell-bridge
07-custom-paths
08-policy-restricted
09-invalid-manifest
10-traversal
11-large-archive
12-output-limit
13-timeout

These become long-term compatibility fixtures.

## 63. Security Testing

### 97. SECURITY TEST MATRIX

Test:

- ZIP traversal
- absolute paths
- malformed ZIP
- excessive entries
- excessive extraction
- malicious filenames
- invalid manifest
- unauthorized service
- unauthorized background action
- unauthorized WebUI bridge
- trust bypass
- policy bypass
- backend privilege mismatch

### 216. WHY SECURITY EARLY

Archive validation and policy cannot safely be left until the end.

The architecture should establish the security boundaries before large amounts of execution functionality are added.

## 64. Device Testing

### 99. DEVICE TESTING

Eventually test on real devices.

Important variables:

- Android version
- OEM
- WebView version
- background restrictions
- process behavior
- Porter availability
- Shizuku availability

### 100. ANDROID 16/17 CONSIDERATIONS

The investigation of Shevery showed active work around newer Android versions and hidden API changes affecting Shizuku-style implementations.

This means Android compatibility cannot be assumed.

The project must test the actual target Android versions.

### 101. WHY REAL DEVICE TESTING MATTERS

Privileged execution is one of the areas where emulator-only testing is insufficient.

Real devices can differ in:

- process behavior
- vendor restrictions
- background execution
- permission handling
- shell behavior
- WebView
- service persistence

### 102. PERFORMANCE

Potential performance concerns:

- large catalogs
- large modules
- archive extraction
- logs
- WebUI
- background services
- process monitoring
- database growth

Performance work should happen after correct semantics exist.

# Part XVI — Licenses, Dependencies, Legal

## 65. Rootless Store License

### 202. ROOTLESS REFERENCE CONCEPTS

Important concepts from Rootless include:

Plugin
PluginManifest
PluginSource
PluginExecution
PluginRuntime
ExecutionContext
CodeBrick
Market

Exact current source names should be reverified before coding.

## 66. Shevery License

### 198. SHEVERY REFERENCE POINTS

The important Shevery documentation areas include:

- ADB Module guide
- ADB Module API
- Android compatibility notes
- module catalog behavior
- module policy behavior
- WebUI bridge behavior

### 199. SHEVERY MODULE ENVIRONMENT REFERENCE

MODDIR
ASH_STANDALONE
SHIZUKU_MODULE_ID
SHIZUKU_MODULE_MODE
SHIZUKU_MODULE_TRUSTED
SHIZUKU_MODULE_BACKGROUND

### 200. SHEVERY MODULE POLICY REFERENCE

Conceptual modes:

Safe
Custom
Full

with additional controls around:

- action
- service
- background
- WebUI bridge

Exact semantics should always be verified against the targeted compatibility version.

### 201. SHEVERY WEBUI REFERENCE

Important compatibility concepts:

window.Shizuku
exec()
execWithOptions()

with controls around:

- environment
- working directory
- stdin
- timeout
- output limits

## 67. Porter Licensing

### 203. PORTER REFERENCE RULE

Never write:

«"Porter has API X."»

unless API X has been verified.

Use:

«"The current Porter dependency/source exposes X."»

after checking the actual source.

## 68. Shizuku Licensing

### 204. SHIZUKU REFERENCE RULE

The same principle applies to Shizuku.

Do not rely on memory for:

- APIs
- permissions
- binder behavior
- UserService behavior
- hidden API compatibility

Verify.

## 69. Dependency Audit

# Part XVII — Reference Material

## 70. Technical Glossary

### 239. PROJECT TERMINOLOGY

Rootless Plugin

The original Rootless executable package type.

ADB Module

The Shevery-compatible module package format being added.

CodeBrick

A lightweight saved command/automation concept.

Porter

Primary privileged execution backend.

Shizuku

Compatibility privileged execution backend.

Backend

Mechanism providing execution.

Privilege

Authority available to execution.

Policy

Rules governing what a package may do.

Trust

Whether the package is trusted for execution.

Runtime

Active execution lifecycle.

WebUI

Module-provided local web interface.

Source

Package discovery origin.

Catalog

Structured package discovery information.

## 71. File and Directory Reference

## 72. Data Model Reference

## 73. State Machine Reference

## 74. Error Reference

### 63. ERROR BOUNDARIES

Errors should remain close to the subsystem that understands them.

Examples:

Archive parser
    ↓
Archive validation error

Backend
    ↓
Backend execution error

Runtime
    ↓
Runtime lifecycle error

Persistence
    ↓
Persistence error

The application layer can translate these into user-facing results.

### 190. TROUBLESHOOTING MENTAL MODEL

When something fails, ask in this order:

1. Is the package valid?
2. Is the package installed?
3. Is it trusted?
4. Is policy allowing the operation?
5. Is the backend available?
6. Does the backend have the required capability?
7. Does the backend provide sufficient privilege?
8. Did the process start?
9. Did the process fail?
10. Is the persisted state accurate?

This prevents random debugging.

### 191. IF AN ACTION FAILS

Check:

```
Module installed?
      ↓
module.prop valid?
      ↓
action path valid?
      ↓
policy allows action?
      ↓
trust allows execution?
      ↓
backend ready?
      ↓
backend capable?
      ↓
script executable?
      ↓
environment correct?
      ↓
runtime started?
      ↓
exit code/output?
```

### 192. IF A SERVICE FAILS

Check:

```
Module enabled?
      ↓
Service exists?
      ↓
Background policy?
      ↓
Service policy?
      ↓
Backend available?
      ↓
Backend session/runtime valid?
      ↓
Already running?
      ↓
Process started?
      ↓
Process exited?
```

### 193. IF WEBUI FAILS

Check:

```
webui path?
      ↓
index exists?
      ↓
WebView loads?
      ↓
JavaScript enabled?
      ↓
window.Shizuku available?
      ↓
bridge enabled?
      ↓
policy allows bridge?
      ↓
command accepted?
      ↓
backend available?
```

### 194. IF PORTER FAILS

Check:

```
Porter installed?
      ↓
Porter initialized?
      ↓
Permission/state?
      ↓
Capability?
      ↓
Execution request valid?
      ↓
Process started?
      ↓
Runtime still connected?
```

Use actual Porter diagnostics/API rather than assumptions.

### 195. IF SHIZUKU FAILS

Check:

```
Shizuku installed?
      ↓
Shizuku running?
      ↓
Permission granted?
      ↓
Binder available?
      ↓
User service available?
      ↓
Execution request valid?
      ↓
Process started?
```

### 196. IF STATE IS WRONG

Ask:

What does persistence say?
        +
What does the backend say?
        +
What does the OS say?

Actual state should win over stale assumptions.

# Part XVIII — Implementation Reference

## 75. Proposed Domain Components

### 177. PROPOSED COMPONENT MAP

```
Application
│
├── UI
│
├── Application / Use Cases
│
├── Domain
│   ├── Package
│   ├── ADB Module
│   ├── Rootless Plugin
│   ├── CodeBrick
│   ├── Execution
│   ├── Policy
│   └── Trust
│
├── Runtime
│   ├── Process management
│   ├── Persistence
│   ├── Recovery
│   └── Logging
│
├── Data
│   ├── Package repositories
│   ├── Module storage
│   ├── Execution persistence
│   └── Sources
│
└── Backend
    ├── Porter
    └── Shizuku
```

### 207. PROPOSED CLASS NAMES

Names such as:

AdbModuleInstaller
AdbModuleParser
PorterExecutionBackend
ShizukuExecutionBackend
ExecutionBackendResolver

are architectural proposals until confirmed against the codebase.

They are not commitments to exact names.

## 76. Dependency Direction

### 68. PACKAGE DEPENDENCY RULE

An ADB Module must not require the Rootless Plugin model to exist.

A Rootless Plugin must not require ADB Module semantics.

Shared infrastructure is allowed.

Semantic dependency is not.

### 69. BACKEND DEPENDENCY RULE

Package/domain code should depend on:

Execution Abstraction

rather than:

Porter API

or:

Shizuku API

directly.

## 77. API Boundary Reference

### 70. EXTERNAL API BOUNDARY

External APIs should be isolated.

Examples:

Porter SDK
Shizuku SDK
WebView
Android framework
Catalog APIs

This makes compatibility changes easier.

# Part XIX — Development Rules / Agent Knowledge

## 78. AGENTS.md Reference

### 163. AGENTS VS PLAN

"AGENTS.md" tells OpenCode how to work.

"PLAN.md" tells OpenCode what work exists.

### 164. PERSONAL KNOWLEDGE BASE VS ALL OF THEM

This document can contain everything.

It does not need to be concise.

It exists so I do not lose the project's reasoning.

## 79. Rules That Protect the Architecture

### 159. DOCUMENT SOURCE OF TRUTH

There is not one universal "highest priority" document.

Each document owns a different domain.

AGENTS.md
    ↓
Agent behavior

ARCHITECTURE.md
    ↓
System structure

PLAN.md
    ↓
Implementation sequence

INVESTIGATION.md
    ↓
Research/evidence

README.md
    ↓
Public explanation

PERSONAL KNOWLEDGE BASE
    ↓
My complete understanding

### 160. WHAT TO DO WHEN DOCUMENTS CONFLICT

When a contradiction appears:

1. Identify what kind of information conflicts.
2. Determine which document owns that information.
3. Update the owning document.
4. Synchronize affected documents.
5. Continue only after the documentation is coherent.

### 161. INVESTIGATION VS ARCHITECTURE

Example:

If research discovers:

«Shevery changed its module format.»

That belongs first in:

INVESTIGATION.md

Then, if the change affects our architecture:

ARCHITECTURE.md

Then, if implementation work changes:

PLAN.md

Then, if agent behavior needs a new rule:

AGENTS.md

### 162. ARCHITECTURE VS PLAN

Architecture says:

«How the system works.»

Plan says:

«What we are going to build and when.»

The plan should not become an architectural dump.

### 167. REUSE BEFORE REWRITE

Before writing something new:

1. Search Rootless.
2. Determine whether it already solves the problem.
3. Determine whether its semantics match.
4. Reuse if appropriate.
5. Adapt if necessary.
6. Replace only when justified.

### 168. AVOID PREMATURE ABSTRACTION

Do not create ten generic interfaces before understanding the actual behavior.

The abstraction should emerge from actual requirements.

### 169. AVOID UNDER-ABSTRACTION

At the same time, do not scatter:

Porter API calls
Shizuku API calls
module parsing
policy checks

throughout the UI.

The system needs meaningful boundaries.

### 170. SECURITY OVER CONVENIENCE

When convenience and security conflict:

«Security wins.»

For example, it may be less convenient to require explicit trust.

That is preferable to silently executing untrusted privileged code.

### 171. COMPATIBILITY OVER COSMETIC SIMILARITY

The application does not need to look exactly like Shevery.

It needs to correctly support the module contract.

Likewise, it does not need to preserve every internal Rootless implementation detail if a carefully justified architectural refactor improves the result.

### 172. USER CHOICE

Where multiple execution paths are legitimate, the user should have meaningful choice.

The application should explain relevant differences rather than silently deciding everything.

### 173. NO SILENT SEMANTIC CONVERSION

Do not silently convert:

ADB Module

into:

Rootless Plugin

or:

service.sh

into:

daemon plugin

or:

Porter

into:

Shizuku

if the conversion changes behavior.

### 175. SECURITY DECISION HIERARCHY

When evaluating a new feature:

```
Does it preserve fundamental platform security?
        ↓
Does it preserve package semantics?
        ↓
Does it preserve backend boundaries?
        ↓
Does it preserve privilege semantics?
        ↓
Does it preserve policy/trust separation?
        ↓
Does it improve the project?
```

If the answer is no at an earlier level, reconsider the feature.

### 176. ARCHITECTURAL INVARIANTS

The following should remain true:

Invariant 1

ADB Modules are a first-class package type.

Invariant 2

Rootless Plugins remain supported.

Invariant 3

CodeBricks remain supported.

Invariant 4

Porter is the primary backend.

Invariant 5

Shizuku is the compatibility backend.

Invariant 6

Backend is separate from privilege.

Invariant 7

Privilege is separate from policy.

Invariant 8

Policy is separate from trust.

Invariant 9

Source trust is separate from execution trust.

Invariant 10

UI does not own privileged execution.

Invariant 11

Unvalidated archives never reach extraction.

Invariant 12

Backend switching cannot silently alter privilege semantics.

Invariant 13

ADB Modules are not Magisk/KSU modules.

Invariant 14

"service.sh" is not automatically a Rootless daemon.

Invariant 15

Runtime state is reconciled with actual state.

### 208. IMPLEMENTATION RULE

Before creating a new class:

1. search existing code
2. determine whether equivalent exists
3. inspect responsibilities
4. decide whether reuse/adaptation is better
5. only then create the new abstraction

### 209. CODE REVIEW QUESTIONS

When reviewing a change, ask:

Package

Does it preserve package semantics?

Backend

Does it remain backend-neutral?

Privilege

Could this change alter privilege?

Policy

Does it bypass policy?

Trust

Does it bypass trust?

Runtime

Does it correctly persist/reconcile state?

Security

Could hostile package content exploit it?

Compatibility

Could existing modules/plugins break?

### 210. ARCHITECTURE REVIEW QUESTIONS

Before accepting a major architecture change:

1. Does it simplify the system?
2. Does it preserve package boundaries?
3. Does it preserve backend boundaries?
4. Does it improve testability?
5. Does it preserve security?
6. Does it preserve Rootless functionality?
7. Does it preserve ADB Module compatibility?
8. Does it avoid unnecessary Shevery coupling?
9. Does it avoid unnecessary Porter coupling?
10. Does it reduce or increase long-term maintenance?

### 221. LONG-TERM MAINTAINABILITY

The architecture should make future maintenance straightforward.

If Porter changes:

Porter adapter changes.

If Shizuku changes:

Shizuku adapter changes.

If ADB Module format changes:

Module compatibility layer changes.

If UI changes:

UI changes.

The entire application should not need to be rewritten.

### 222. IDEAL CHANGE ISOLATION

A good architecture allows:

Porter update
     ↓
Porter backend

without requiring:

ADB Module domain rewrite
Rootless plugin rewrite
CodeBrick rewrite
UI rewrite

### 223. PACKAGE COMPATIBILITY ISOLATION

Likewise:

ADB Module format update

should primarily affect:

ADB Module parser/runtime/compatibility

rather than every package type.

### 224. WEBUI COMPATIBILITY ISOLATION

If the "window.Shizuku" API changes:

WebUI compatibility bridge

should absorb as much of the change as possible.

### 231. THE "BOUNDARY" TEST

For every new dependency:

«Which boundary does this belong behind?»

Examples:

```
Porter → backend boundary
Shizuku → backend boundary
WebView → WebUI boundary
ZIP → archive boundary
Catalog → source boundary
```

### 232. THE "PRIVILEGE" TEST

Before any execution-related feature:

«Can this change what authority a package receives?»

If yes, it deserves explicit architectural review.

### 233. THE "TRUST" TEST

Before allowing an external package to execute:

«What tells us this package should be trusted?»

Source information alone is not necessarily sufficient.

### 234. THE "RECOVERY" TEST

For any persistent runtime:

«What happens if the app dies right now?»

The system should have an answer.

### 235. THE "UPDATE" TEST

For any update mechanism:

«What happens if the update fails halfway through?»

The installation architecture should be designed to recover safely.

### 236. THE "ANDROID" TEST

For any background feature:

«What happens when Android kills the application process?»

This must be explicitly understood.

### 237. THE "BACKEND DISAPPEARS" TEST

For any active execution:

«What happens if the backend disappears?»

The runtime must detect and reconcile the failure.

## 80. Development Workflow

# Part XX — Decision Log

## 81. Major Decisions

## 82. Rejected Approaches

### 10. WHY NOT SIMPLY COPY BOTH APPLICATIONS

Because that would produce a Frankenstein architecture.

A naive merge could create:

Rootless architecture
        +
Shevery architecture
        +
duplicate runtime systems
        +
duplicate package models
        +
duplicate security models
        +
duplicate execution systems

That would be difficult to maintain.

Instead:

Rootless foundation
        +
carefully integrated ADB Module subsystem

is the intended model.

### 165. IMPORTANT REJECTED APPROACHES

Rejected: Make ADB Modules into Rootless Plugins

Reason:

They have different metadata and lifecycle semantics.

---

Rejected: Make Rootless Plugins into ADB Modules

Reason:

It would break Rootless compatibility.

---

Rejected: Copy Shevery wholesale

Reason:

The project would inherit unrelated architecture and become difficult to maintain.

---

Rejected: Make Shizuku the central abstraction

Reason:

Porter is the primary backend.

---

Rejected: Put Porter everywhere

Reason:

That would make adding/changing other backends unnecessarily difficult.

---

Rejected: Treat Full Trust as "disable security"

Reason:

Fundamental platform protections must remain.

---

Rejected: Automatically treat "service.sh" as a Rootless daemon

Reason:

The lifecycle semantics differ.

---

Rejected: Use "/data/adb/modules"

Reason:

These are not Magisk/KSU modules.

---

Rejected: Trust everything from a catalog

Reason:

Source trust and execution trust are different.

---

Rejected: Let UI directly execute privileged commands

Reason:

Security and architectural boundaries would collapse.

### 212. FEATURE COPY RULE

Do not implement a feature merely because:

«"Shevery has it."»

or:

«"Rootless has it."»

Instead ask:

«Does the feature belong to the unified application?»

## 83. Decision Rationale

### 166. WHY THE ARCHITECTURE SHOULD BE BORING

A good architecture here should be relatively boring.

That is a compliment.

The system should be:

```
Package
 ↓
Use Case
 ↓
Policy
 ↓
Runtime
 ↓
Execution abstraction
 ↓
Backend
```

rather than:

UI
 ↔
Plugin
 ↔
Shizuku
 ↔
Porter
 ↔
random shell
 ↔
module
 ↔
database

Predictability matters more than cleverness.

### 211. FEATURE EVALUATION

For a proposed feature:

```
Is it required?
    ↓
Is it architecturally justified?
    ↓
Does it improve compatibility/security/usability?
    ↓
Does it introduce unnecessary complexity?
    ↓
Does it belong to this project?
```

### 229. THE "FRANKENSTEIN TEST"

Whenever code from another project is considered, ask:

«If I remove the source project's branding and comments, does this still look like one coherent architecture?»

If not:

«Stop and redesign.»

### 230. THE "WHY" TEST

For every major abstraction:

«Why does this exist?»

If the answer is:

«"Because another project has one."»

that is not sufficient.

### 245. THE FINAL RULE

When making a decision, ask:

«Does this make the application more coherent, more compatible, more secure, or more maintainable without violating the fundamental separation between package, backend, privilege, policy, trust, and runtime state?»

If yes:

«Investigate it.»

If no:

«Do not add it merely because it is technically possible.»

# Part XXI — Open Questions / Future Investigation

## 84. Known Unknowns

## 85. Research Queue

### 197. SOURCE RESEARCH REFERENCE

Primary research targets include:

- Rootless Store source
- Shevery source
- Shevery ADB Module guide
- Shevery ADB Module API documentation
- Porter SDK/source
- Shizuku documentation/source
- Android platform documentation

Whenever a technical detail is uncertain:

«Go back to the actual source.»

### 205. VERSION DRIFT

External projects change.

Therefore this knowledge base contains two categories of information:

Stable architectural decisions

Examples:

Porter primary
Shizuku compatibility
Package/backend separation

Version-sensitive facts

Examples:

specific API names
specific safety limits
specific catalog behavior
specific Android compatibility

Version-sensitive facts must be revalidated before implementation.

### 206. RESEARCH CONFIDENCE

When documenting external behavior, distinguish:

Verified
Observed
Inferred
Proposed
Unknown

Do not turn an inference into a fact.

## 86. Adaptive Expansion

### 174. ADAPTIVE ARCHITECTURE

The architecture is allowed to evolve.

But architectural evolution should be deliberate.

A legitimate expansion may be required if:

- a backend exposes an unexpected limitation
- Android introduces a new restriction
- module compatibility requires a new abstraction
- Rootless functionality exposes an architectural conflict
- security testing identifies a missing boundary

The project should not freeze prematurely.

### 184. FUTURE PACKAGE TYPES

The architecture may eventually support additional package types.

If that happens, the same question must be asked:

«Does this genuinely have a different package contract?»

If yes:

NewPackageType

should be distinct.

If no, reuse an existing type.

### 185. FUTURE BACKENDS

Porter and Shizuku are the current backends.

Future backends are theoretically possible.

The architecture should make that possible without designing five speculative backends today.

### 186. FUTURE SECURITY FEATURES

Possible future additions:

- package signatures
- cryptographic verification
- source signing
- trust inheritance rules
- permission visualization
- stronger sandboxing
- dependency security
- rollback
- package quarantine

These are future possibilities, not automatically current scope.

### 187. FUTURE PACKAGE MANAGEMENT

Potential features:

- backup
- restore
- export
- import
- package sharing
- package dependencies
- version rollback
- package snapshots

Again, only implement when justified.

### 188. FUTURE DISCOVERY

Possible discovery improvements:

- searchable catalog
- categories
- screenshots
- package metadata
- compatibility badges
- backend requirements
- Android version requirements
- update notifications

### 189. FUTURE DIAGNOSTICS

A mature version could provide:

Backend diagnostics
Module diagnostics
Execution diagnostics
Archive diagnostics
WebUI diagnostics
Runtime diagnostics

This could eventually make debugging much easier.

# Part XXII — Personal Project Knowledge

## 87. Why I Am Building This

### 225. WHY THIS PROJECT COULD BECOME LARGE

There are actually several systems inside the application:

Package manager
+
Plugin manager
+
ADB Module manager
+
Automation engine
+
Execution runtime
+
Backend manager
+
Security system
+
WebUI runtime
+
Catalog system
+
Update system
+
Persistence system
+
Recovery system

That is why a large personal knowledge base is justified.

### 226. THE PROJECT IS NOT "JUST AN APP"

Conceptually it is closer to:

«A package/runtime platform embedded in an Android application.»

That explains why architecture matters so much.

## 88. Personal Design Preferences

### 228. PERSONAL DESIGN PRINCIPLE

The project should feel:

- unified
- predictable
- secure
- extensible
- maintainable
- compatible

without becoming:

- bloated
- over-abstracted
- backend-dependent
- security-hostile
- semantically confused

### 238. THE "USER UNDERSTANDING" TEST

For any potentially dangerous operation:

«Can the user understand what authority is being used and what is about to happen?»

The UI should expose important information.

## 89. Personal Development Strategy

# Part XXIII — Complete Project Reference

## 90. One-Page Project Summary

### 240. PERSONAL CHEAT SHEET

When I forget what this project is:

«Rootless Store + ADB Modules + Porter-first runtime + Shizuku compatibility.»

When I forget the biggest rule:

«Package ≠ Backend ≠ Privilege ≠ Policy ≠ Trust ≠ Runtime.»

When I wonder whether to copy Shevery:

«No. Implement its ADB Module contract.»

When I wonder whether Shizuku should be the center:

«No. Porter is primary.»

When I wonder whether ADB Modules should become plugins:

«No. Keep package types distinct.»

When I wonder whether a trusted module can do anything:

«No. Trust and policy are separate.»

When I wonder whether a catalog is enough to trust a package:

«No. Source trust and execution trust are separate.»

When I wonder whether "service.sh" is a daemon plugin:

«No. Different semantics.»

When I wonder whether ADB Modules are Magisk modules:

«No.»

### 243. FINAL PERSONAL SUMMARY

This project started as an idea to add ADB Modules to Rootless Store.

It has grown into something more substantial:

«A unified Android package and runtime platform.»

Rootless Store provides the foundation.

ADB Modules provide a second first-class package ecosystem.

CodeBricks remain the lightweight automation layer.

Porter provides the primary privileged execution mechanism.

Shizuku provides compatibility.

The runtime manages execution.

Policy determines what is allowed.

Trust determines whether a package is trusted.

Privilege determines what authority execution actually has.

Sources provide discovery.

Catalogs provide package information.

WebUI provides package interfaces.

Persistence keeps the system aware of what happened.

Recovery reconciles what the application remembers with what the device is actually doing.

Security protects the boundary between downloaded code and privileged execution.

And the architecture keeps all of those concerns separate enough that the project can continue evolving.

The project should not become Rootless Store + Shevery mashed together.

It should become its own coherent system.

### 244. THE PROJECT'S NORTH STAR

If I ever get lost in the implementation, come back here:

```
                         NORTH STAR
                             │
                             ▼
                 One coherent application
                             │
             ┌───────────────┼───────────────┐
             │               │               │
             ▼               ▼               ▼
        Rootless          ADB Modules     CodeBricks
        preserved         first-class      preserved
             │               │               │
             └───────────────┼───────────────┘
                             │
                             ▼
                     Unified Runtime
                             │
                             ▼
                   Porter-first execution
                             │
                    Shizuku compatibility
                             │
                             ▼
                  Strong security boundaries
                             │
                             ▼
                   Long-term maintainability
```

### 246. END STATE

The ideal finished application is not:

«"A Rootless Store fork with some ADB Module buttons."»

It is:

«A unified, secure, extensible Android package/runtime platform where Rootless Plugins, ADB Modules, and CodeBricks coexist as distinct package systems; where Porter provides the primary privileged execution path; where Shizuku provides compatibility; and where execution, privilege, policy, trust, persistence, and recovery are deliberately modeled as separate concerns.»

That is the project.

---

END OF DEFINITIVE PERSONAL KNOWLEDGE BASE

## 91. Architecture at a Glance

### 213. MINIMUM VIABLE ARCHITECTURE

The minimum successful architecture is:

Rootless
+
ADB Module package type
+
Execution abstraction
+
Porter backend
+
Shizuku backend
+
Policy
+
Trust
+
Safe installation
+
Runtime

Everything else builds on this.

### 227. THE PLATFORM MODEL

The long-term model is:

```
                    Package Platform
                           │
       ┌───────────────────┼───────────────────┐
       │                   │                   │
    Plugins             Modules            CodeBricks
       │                   │                   │
       └───────────────────┼───────────────────┘
                           │
                     Runtime System
                           │
                   Execution Abstraction
                           │
              ┌────────────┴────────────┐
              │                         │
           Porter                   Shizuku
```

### 241. FINAL ARCHITECTURE DIAGRAM

```
                           UNIFIED APPLICATION
                                   │
             ┌─────────────────────┼─────────────────────┐
             │                     │                     │
             ▼                     ▼                     ▼
       ROOTLESS PLUGINS        ADB MODULES           CODEBRICKS
             │                     │                     │
             └─────────────────────┼─────────────────────┘
                                   │
                                   ▼
                         COMMON PACKAGE LAYER
                                   │
                                   ▼
                         APPLICATION / USE CASES
                                   │
                                   ▼
                              DOMAIN LAYER
                                   │
                                   ▼
                             RUNTIME LAYER
                                   │
                    ┌──────────────┴──────────────┐
                    │                             │
                    ▼                             ▼
                 POLICY                         TRUST
                    │                             │
                    └──────────────┬──────────────┘
                                   │
                                   ▼
                       EXECUTION ABSTRACTION
                                   │
                    ┌──────────────┴──────────────┐
                    │                             │
                    ▼                             ▼
                 PORTER                        SHIZUKU
                PRIMARY                    COMPATIBILITY
                    │                             │
                    └──────────────┬──────────────┘
                                   │
                                   ▼
                         PRIVILEGED EXECUTION
```

## 92. Execution Model at a Glance

### 242. FINAL EXECUTION MODEL

```
                 PACKAGE
                    │
                    ▼
            What am I running?
                    │
        ┌───────────┼───────────┐
        ▼           ▼           ▼
      Plugin      Module     CodeBrick
        │           │           │
        └───────────┼───────────┘
                    │
                    ▼
                 POLICY
                    │
                    ▼
                 TRUST
                    │
                    ▼
             CAPABILITIES
                    │
                    ▼
               BACKEND
                    │
           ┌────────┴────────┐
           ▼                 ▼
        PORTER           SHIZUKU
        PRIMARY        COMPATIBILITY
           │                 │
           └────────┬────────┘
                    │
                    ▼
                PRIVILEGE
                    │
                    ▼
                RUNTIME
                    │
           ┌────────┼────────┐
           ▼        ▼        ▼
        Process    Logs    Persistence
                    │
                    ▼
                 Recovery
```

## 93. Security Model at a Glance

## 94. ADB Module Model at a Glance

## 95. Backend Model at a Glance

## 96. Package Model at a Glance

## 97. Development Plan at a Glance

## 98. Testing Strategy at a Glance

## 99. Current Project Status

## 100. Current Phase

## 101. Completed Work

## 102. Work Remaining

## 103. Known Risks

## 104. Known Unknowns

## 105. Immediate Next Steps

# Appendix A — Terminology & Glossary

This appendix is a compact lookup layer for terminology already defined elsewhere in the knowledge base. The detailed definitions and architectural reasoning remain in the main chapters.

## A.1 Package and Project Terms

| Term | Reference meaning | Primary source |
| --- | --- | --- |
| Rootless Plugin | The original Rootless executable package type. | §239 |
| ADB Module | The Shevery-compatible module package format being added. | §239 |
| CodeBrick | A lightweight saved command/automation concept. | §239 |
| Package | The thing being executed; it can be a Rootless Plugin, ADB Module, or CodeBrick. | §5 |
| Source | A package discovery origin. | §239 |
| Catalog | Structured package discovery information. | §239 |
| WebUI | A module-provided local web interface. | §239 |

## A.2 Execution and Runtime Terms

| Term | Reference meaning | Primary source |
| --- | --- | --- |
| Backend | The mechanism providing privileged execution. | §239 |
| Privilege | The authority available to execution. | §239 |
| Policy | Rules governing what a package may do. | §§33, 239 |
| Trust | Whether a package is trusted for execution. | §§34, 239 |
| Runtime | The active execution lifecycle. | §239 |
| Execution Request | Conceptual request containing package, operation, command/script, environment, working directory, timeout, output limits, and capability requirements. | §43 |
| Execution Result | Conceptual result containing success, exit code, stdout, stderr, failure reason, backend, and runtime metadata. | §44 |
| Execution Handle | Representation of an active long-running runtime, including execution identity, process identity, backend, state, start time, and controls. | §45 |
| Backend Capability | A capability reported by an execution backend. | §46 |
| Backend State | A representation of backend availability/readiness such as `READY`, `UNSUPPORTED`, or `ERROR`; exact states remain subject to verification. | §47 |

## A.3 Security and Compatibility Terms

| Term | Reference meaning | Primary source |
| --- | --- | --- |
| Source Trust | Trust associated with a package source; it does not automatically grant unrestricted execution trust to every package from that source. | §36 |
| Package Trust | Trust belonging to the package/user relationship, including approval, override, revocation, and source information. | §37 |
| Execution Permission | The result of policy evaluation after trust is considered; a trusted package can still be denied. | §38 |
| Full Trust | A compatibility concept that may relax certain module-specific restrictions but does not disable fundamental platform safety. | §35 |
| Compatibility Layer | The boundary that preserves compatibility behavior such as the Shizuku path without making it the central architecture. | §§41–42 |
| Runtime Recovery | Reconciliation of persisted expectations with actual backend/process state. | §62 |

## A.4 Non-Equivalences That Must Remain Explicit

- Package ≠ Backend ≠ Privilege ≠ Policy ≠ Trust ≠ Runtime State.
- Rootless Plugin ≠ ADB Module ≠ CodeBrick.
- Porter is the primary execution backend; Shizuku is the compatibility backend.
- ADB Modules are not Magisk/KSU modules.
- `service.sh` is not automatically a Rootless daemon.

These are architectural distinctions, not interchangeable labels. See §§100, 176, 239, and 240.

# Appendix B — Acronyms & Abbreviations

This appendix records abbreviations actually used in the project knowledge base. Where the source does not explicitly spell out an abbreviation, this appendix does not invent an expansion.

## B.1 Execution, Android, and Development Terms

| Abbreviation / term | Usage in the knowledge base |
| --- | --- |
| ADB | Used in “ADB Modules” and throughout the project name/architecture. |
| API | Used for external/API compatibility boundaries and the ADB Module API reference. |
| SDK | Used for Porter SDK and Shizuku SDK references. |
| UI | User-interface terminology throughout the application/UI chapters. |
| UX | User-experience terminology in the UI/development material. |
| PID | Process identifier terminology in runtime/process management. |
| ZIP | Archive format used for ADB Module packages and archive validation. |
| WebUI | Module-provided web interface and compatibility bridge terminology. |
| OEM | Device-manufacturer category used in device-testing variables. |
| KSU | Used only in the explicit distinction that ADB Modules are not Magisk/KSU modules. |

## B.2 Names That Are Project/Technology Identifiers Rather Than Generic Expansions

- Porter
- Shizuku
- Rootless
- Shevery
- CodeBrick
- Magisk

The knowledge base intentionally treats these as named systems/projects rather than inventing expansions or alternate meanings.

# Appendix C — File, Directory & Storage Reference

## C.1 ADB Module Package Layout

The reference minimal module is:

```text
my-module/
├── module.prop
├── action.sh
└── webui/
    └── index.html
```

Optional package content identified by the knowledge base includes `banner.png` and `service.sh`. Custom paths may also be specified by metadata. See §20.

## C.2 Module Metadata Files and Paths

| Item | Role / reference |
| --- | --- |
| `module.prop` | Required module metadata file. |
| `action.sh` | User-triggered action entry point. |
| `service.sh` | Controlled background/service behavior. |
| `webui/` | Reference WebUI directory. |
| `banner.png` | Optional banner identified by the reference format. |
| `banner=assets/banner.webp` | Example custom banner path. |
| `webui=webui` | Example WebUI path metadata. |
| `action=scripts/action.sh` | Example custom action path metadata. |
| `MODDIR` | Module environment variable. |

## C.3 Reference `module.prop` Fields

Required fields:

```text
id=
name=
version=
versionCode=
author=
description=
```

Example values are documented in §21. The exact field semantics remain tied to the targeted compatibility contract.

## C.4 Reference Module Storage

The reference storage location is:

```text
/data/user/0/<package>/files/adb_modules/<id>
```

The exact final storage architecture belongs to application implementation. The stated principles are: private, isolated, deterministic, secure, persistent, and recoverable. See §27.

## C.5 Storage and Runtime Records

Persistence can include:

- installed packages
- package metadata
- enabled state
- execution records
- active runtime records
- logs
- trust state
- policy state

See §61.

# Appendix D — Architecture Diagrams

This appendix collects the canonical diagrams already present in the knowledge base for quick architectural orientation. The detailed discussion remains in the main chapters.

## D.1 Final Architecture

```text
                           UNIFIED APPLICATION
                                   │
             ┌─────────────────────┼─────────────────────┐
             │                     │                     │
             ▼                     ▼                     ▼
       ROOTLESS PLUGINS        ADB MODULES           CODEBRICKS
             │                     │                     │
             └─────────────────────┼─────────────────────┘
                                   │
                                   ▼
                         COMMON PACKAGE LAYER
                                   │
                                   ▼
                         APPLICATION / USE CASES
                                   │
                                   ▼
                              DOMAIN LAYER
                                   │
                                   ▼
                             RUNTIME LAYER
                                   │
                    ┌──────────────┴──────────────┐
                    │                             │
                    ▼                             ▼
                 POLICY                         TRUST
                    │                             │
                    └──────────────┬──────────────┘
                                   │
                                   ▼
                       EXECUTION ABSTRACTION
                                   │
                    ┌──────────────┴──────────────┐
                    │                             │
                    ▼                             ▼
                 PORTER                        SHIZUKU
                PRIMARY                    COMPATIBILITY
                    │                             │
                    └──────────────┬──────────────┘
                                   │
                                   ▼
                         PRIVILEGED EXECUTION
```

Primary source: §241.

## D.2 Final Execution Model

```text
                 PACKAGE
                    │
                    ▼
            What am I running?
                    │
        ┌───────────┼───────────┐
        ▼           ▼           ▼
      Plugin      Module     CodeBrick
        │           │           │
        └───────────┼───────────┘
                    │
                    ▼
                 POLICY
                    │
                    ▼
                 TRUST
                    │
                    ▼
             CAPABILITIES
                    │
                    ▼
               BACKEND
                    │
           ┌────────┴────────┐
           ▼                 ▼
        PORTER           SHIZUKU
        PRIMARY        COMPATIBILITY
           │                 │
           └────────┬────────┘
                    │
                    ▼
                PRIVILEGE
                    │
                    ▼
                RUNTIME
                    │
           ┌────────┼────────┐
           ▼        ▼        ▼
        Process    Logs    Persistence
                    │
                    ▼
                 Recovery
```

Primary source: §242.

## D.3 Security Layer Diagram

```text
Source validation
      ↓
Package validation
      ↓
Archive validation
      ↓
Path validation
      ↓
Trust
      ↓
Policy
      ↓
Capability
      ↓
Backend
      ↓
Privilege
      ↓
Execution limits
      ↓
Runtime monitoring
```

Primary source: §79.

# Appendix E — Data & Metadata Schemas

These are the conceptual schemas already documented in the project. They are references, not commitments to exact implementation class names or APIs.

## E.1 Execution Request

```text
ExecutionRequest
├── package
├── operation
├── command/script
├── environment
├── workingDirectory
├── timeout
├── outputLimits
└── capabilityRequirements
```

Source: §43.

## E.2 Execution Result

```text
ExecutionResult
├── success
├── exitCode
├── stdout
├── stderr
├── failureReason
├── backend
└── runtime metadata
```

Source: §44.

## E.3 Execution Handle

```text
ExecutionHandle
├── executionId
├── process identity
├── backend
├── state
├── start time
└── controls
```

Source: §45.

## E.4 ADB Module Domain Model

```text
AdbModule
├── identity
├── metadata
├── installation
├── enabled state
├── action
├── service
├── WebUI
├── policy
├── trust
├── runtime
└── logs
```

Source: §65.

## E.5 Rootless Plugin Domain Model

```text
RootlessPlugin
├── manifest
├── run model
├── execution context
├── entry point
├── WebUI
├── installation
└── runtime
```

Source: §66.

## E.6 CodeBrick Domain Model

```text
CodeBrick
├── command/content
├── environment
├── metadata
└── execution
```

Source: §67.

## E.7 Module Environment Reference

The compatibility/reference environment variables identified in the knowledge base are:

```text
MODDIR
ASH_STANDALONE
SHIZUKU_MODULE_ID
SHIZUKU_MODULE_MODE
SHIZUKU_MODULE_TRUSTED
SHIZUKU_MODULE_BACKGROUND
```

Source: §§28 and 199.

# Appendix F — Execution Flow Reference

## F.1 Module Installation

```text
Source
  ↓
Download
  ↓
Archive inspection
  ↓
ZIP validation
  ↓
Path validation
  ↓
Size/entry validation
  ↓
module.prop parsing
  ↓
Metadata validation
  ↓
Staging
  ↓
Safe extraction
  ↓
Post-install verification
  ↓
Persistence
  ↓
Registration
```

Source: §178.

## F.2 Module Action

```text
User
  ↓
Select module
  ↓
Action requested
  ↓
Module exists?
  ↓
Trusted?
  ↓
Policy allows?
  ↓
Backend capable?
  ↓
Resolve backend
  ↓
Prepare environment
  ↓
Execute action.sh
  ↓
Capture output
  ↓
Record result
  ↓
Update runtime
  ↓
Show result
```

Source: §179.

## F.3 Module Service

```text
Module enabled
  ↓
Service eligible?
  ↓
Background policy allows?
  ↓
Backend available?
  ↓
Service not already running?
  ↓
Prepare environment
  ↓
Start service.sh
  ↓
Track runtime
  ↓
Persist state
  ↓
Monitor
```

Source: §180.

## F.4 WebUI Execution

```text
Module
  ↓
WebUI
  ↓
WebView
  ↓
JavaScript
  ↓
window.Shizuku
  ↓
Compatibility bridge
  ↓
Execution request
  ↓
Policy/trust
  ↓
Backend resolver
  ↓
Porter/Shizuku
```

Source: §181.

## F.5 Update

```text
Catalog
  ↓
New version
  ↓
Compare
  ↓
Download
  ↓
Validate
  ↓
Stage
  ↓
Runtime reconciliation
  ↓
Replace
  ↓
Verify
  ↓
Restore appropriate state
```

Source: §182.

## F.6 Recovery

```text
App startup
  ↓
Load persisted state
  ↓
Inspect backend
  ↓
Inspect process
  ↓
Compare expected vs actual
  ↓
Reconcile
  ↓
Mark stale state
  ↓
Recover valid state
  ↓
Update UI
```

Source: §183.

## F.7 Backend Resolution

```text
Execution Request
       ↓
Required capabilities
       ↓
Policy
       ↓
Trust
       ↓
Available backends
       ↓
User preference
       ↓
Resolved backend
```

The resolver should explain why a backend was selected or rejected. Source: §48.

# Appendix G — Execution Backend Capability Matrix

The knowledge base deliberately does not assert unverified Porter or Shizuku API capabilities. This matrix therefore records architectural roles and the test obligations already defined, rather than inventing support claims.

## G.1 Backend Role

| Dimension | Porter | Shizuku |
| --- | --- | --- |
| Architectural role | Primary execution backend | Compatibility execution backend |
| Used through execution abstraction | Yes | Yes |
| Exact API surface | Must be verified from dependency/source | Must be verified from documentation/source |
| Backend-specific behavior | Must be isolated behind the backend boundary | Must be isolated behind the backend boundary |
| Silent privilege substitution | Not permitted | Not permitted |

Sources: §§40–42, 203–204.

## G.2 Conceptual Capability Vocabulary

The backend abstraction identifies these as potential capabilities:

| Capability | Status in the knowledge base |
| --- | --- |
| `EXECUTE_COMMAND` | Conceptual capability; final backend support must be verified. |
| `EXECUTE_SCRIPT` | Conceptual capability; final backend support must be verified. |
| `BACKGROUND_SERVICE` | Conceptual capability; final backend support must be verified. |
| `PROCESS_CONTROL` | Conceptual capability; final backend support must be verified. |
| `WEBUI_BRIDGE` | Conceptual capability; final backend support must be verified. |
| `PERSISTENT_RUNTIME` | Conceptual capability; final backend support must be verified. |

Source: §46.

## G.3 Required Backend Test Situations

| Situation | Porter | Shizuku |
| --- | --- | --- |
| Installed/available | Test | Test |
| Not running | Test | Test |
| Permission denied | Test | Test |
| Capability unsupported | Test | Test |
| Process starts | Test | Test |
| Process exits | Test | Test |
| Process fails | Test | Test |
| Backend disappears | Test | Test |
| Recovery | Test | Test |

Source: §95. “Test” records the documented test requirement; it is not a claim that the test has already passed.

## G.4 Privilege Separation

The architecture keeps three questions separate:

1. Which backend is being used?
2. What privilege does that backend currently provide?
3. Is that privilege sufficient for this operation?

Source: §50.

# Appendix H — Policy & Trust Matrix

## H.1 Policy Concepts

| Policy concept | Meaning in the knowledge base |
| --- | --- |
| Safe mode | Reference policy concept. |
| Custom access | Reference policy concept. |
| Full access | Reference policy concept. |
| Background action permission | Controls whether background action is permitted. |
| Service permission | Controls service behavior. |

Source: §33.

## H.2 Trust Concepts

| Trust dimension | Reference meaning |
| --- | --- |
| Source trust | A source can be trusted without every package from it receiving unrestricted execution trust. |
| Package trust | Trust belongs to the package/user relationship and may involve approval, override, revocation, and source information. |
| Full Trust | May relax certain module-specific restrictions; does not disable fundamental platform safety. |
| Execution permission | Still subject to policy even when a package is trusted. |

Sources: §§34–38.

## H.3 Decision Relationship

```text
Source trust
     │
     ▼
Package trust
     │
     ▼
Policy evaluation
     │
     ▼
Execution permission
```

A trusted package can still be denied:

```text
Trusted
+
Policy denied
=
No execution
```

Source: §38.

## H.4 Security/Policy Boundary

Fundamental archive and runtime safety remain in force even where Full Trust relaxes module-specific restrictions. The knowledge base explicitly rejects treating Full Trust as “disable security.” See §§35, 81, and 176.

# Appendix I — ADB Module Compatibility Reference

## I.1 Package Contract

The reference minimal package is:

```text
my-module/
├── module.prop
├── action.sh
└── webui/
    └── index.html
```

Optional elements identified by the knowledge base include `banner.png` and `service.sh`; custom paths may be specified by metadata. Source: §20.

## I.2 Required Metadata

```text
id=
name=
version=
versionCode=
author=
description=
```

Source: §21.

## I.3 Compatibility Metadata Examples

```text
banner=assets/banner.webp
webui=webui
usesShellBridge=true
action=scripts/action.sh
```

All paths must be validated. Source: §22.

## I.4 Environment Reference

```text
MODDIR
ASH_STANDALONE
SHIZUKU_MODULE_ID
SHIZUKU_MODULE_MODE
SHIZUKU_MODULE_TRUSTED
SHIZUKU_MODULE_BACKGROUND
```

Source: §§28 and 199.

## I.5 Archive Compatibility/Safety Values

| Value | Reference value | Qualification |
| --- | --- | --- |
| ZIP entry limit | 2048 | Compatibility/reference value; verify against target version. |
| Extracted size | 200 MB | Compatibility/reference value; verify against target version. |
| Script timeout | 120 seconds | Reference target for bounded script actions; verify final behavior. |
| Retained stdout | 64 KB | Reference value; verify final behavior. |
| Retained stderr | 64 KB | Reference value; verify final behavior. |

Source: §25.

## I.6 Explicit Non-Compatibility

ADB Modules are not Magisk/KSU modules. The knowledge base explicitly rejects use of `/data/adb/modules` as the module storage model. Sources: §§19, 165, and 176.

# Appendix J — Rootless Compatibility Reference

## J.1 Rootless Concepts Retained

Important Rootless concepts identified by the knowledge base:

- Plugin
- PluginManifest
- PluginSource
- PluginExecution
- PluginRuntime
- ExecutionContext
- CodeBrick
- Market

Exact current source names should be reverified before coding. Source: §202.

## J.2 Rootless Manifest Boundary

Rootless Plugin metadata remains compatible with the existing application. ADB Module-specific fields should not be added merely to make the formats look unified.

```text
Rootless Plugin
    ↓
PluginManifest

ADB Module
    ↓
AdbModuleManifest
```

A common abstraction may exist above them where genuinely appropriate. Source: §12.

## J.3 Rootless Execution and Persistence

The investigation covers Rootless execution, persistence, runtime recovery, and CodeBricks in §§13–16. Those concepts may be reused where their semantics match, rather than copied indiscriminately.

## J.4 Rootless Regression Boundary

The implementation plan includes a dedicated Rootless regression phase after the Shizuku/backend work and before the common package/domain expansion. See §113 and the corresponding development phase.

## J.5 Daemon/Service Distinction

```text
Rootless daemon plugin
        ≠
ADB Module service.sh
```

They may share infrastructure such as process management, logging, persistence, and scheduling, but they do not have identical lifecycle contracts. Sources: §§86–87.

# Appendix K — Security Reference

## K.1 Threat Categories

The threat model includes:

- malicious ZIPs
- malicious `module.prop`
- malicious scripts
- malicious WebUI
- malicious catalog metadata
- compromised package sources
- malicious update packages
- packages attempting privilege abuse
- packages attempting filesystem escape

Source: §78.

## K.2 Security Layers

```text
Source validation
      ↓
Package validation
      ↓
Archive validation
      ↓
Path validation
      ↓
Trust
      ↓
Policy
      ↓
Capability
      ↓
Backend
      ↓
Privilege
      ↓
Execution limits
      ↓
Runtime monitoring
```

Source: §79.

## K.3 Archive and Path Rules

ZIP archives are hostile input. Checks include entry count, extracted size, path validity, traversal, absolute paths, malformed archives, invalid metadata, and unexpected structure. Source: §24.

The path-validation model is:

```text
Archive path
    ↓
Normalize
    ↓
Resolve inside module root
    ↓
Verify containment
    ↓
Allow or reject
```

Source: §26.

## K.4 Reference Safety Limits

| Limit | Reference value |
| --- | ---: |
| ZIP entries | 2048 |
| Extracted size | 200 MB |
| Script timeout | 120 seconds |
| Retained stdout | 64 KB |
| Retained stderr | 64 KB |

These are compatibility/reference values and must be verified against the supported target version before implementation. Source: §25.

## K.5 Runtime Security

The knowledge base treats resource exhaustion as a security issue. Relevant abuse patterns include huge archives, huge file counts, enormous output, endless execution, excessive WebUI activity, and repeated background work. Sources: §§82–85.

## K.6 Security Invariants

The architecture records these security-relevant invariants:

- Unvalidated archives never reach extraction.
- UI does not own privileged execution.
- Backend switching cannot silently alter privilege semantics.
- Source trust is separate from execution trust.
- Full Trust does not disable fundamental platform safety.
- Runtime state is reconciled with actual state.

Source: §176.

# Appendix L — Testing Reference

## L.1 Unit-Test Groups

| Area | Existing test cases |
| --- | --- |
| Metadata | valid manifest; missing field; malformed field; duplicate field; unsupported field |
| Archive | valid archive; traversal; absolute path; oversized archive; too many entries; missing `module.prop` |
| Backend | available; unavailable; permission denied; unsupported capability |
| Policy | allowed; denied; trust override; background restriction |
| Runtime | start; stop; completion; failure; stale state |

Source: §93.

## L.2 Reference Module Fixtures

The long-term compatibility fixture set is:

```text
01-minimal
02-action-only
03-service-only
04-action-service
05-webui
06-webui-shell-bridge
07-custom-paths
08-policy-restricted
09-invalid-manifest
10-traversal
11-large-archive
12-output-limit
13-timeout
```

Source: §94.

## L.3 Backend Test Matrix

| Situation | Porter | Shizuku |
| --- | --- | --- |
| Installed/available | Test | Test |
| Not running | Test | Test |
| Permission denied | Test | Test |
| Capability unsupported | Test | Test |
| Process starts | Test | Test |
| Process exits | Test | Test |
| Process fails | Test | Test |
| Backend disappears | Test | Test |
| Recovery | Test | Test |

Source: §95.

## L.4 Security Test Matrix

The documented security tests cover:

- ZIP traversal
- absolute paths
- malformed ZIP
- excessive entries
- excessive extraction
- malicious filenames
- invalid manifest
- unauthorized service
- unauthorized background action
- unauthorized WebUI bridge
- trust bypass
- policy bypass
- backend privilege mismatch

Source: §97.

## L.5 Persistence and Recovery Tests

The documented persistence tests cover:

- clean startup
- app restart
- process death
- device reboot
- backend restart
- process disappearance
- stale PID
- stale service
- incomplete installation
- interrupted update

Source: §98.

## L.6 Device Testing Variables

Eventually test on real devices across variables including:

- Android version
- OEM
- WebView version
- background restrictions
- process behavior
- Porter availability
- Shizuku availability

Android compatibility cannot be assumed; the knowledge base specifically calls for testing actual target Android versions. Sources: §§99–101.

# Appendix M — Implementation Cross-Reference

This appendix maps the existing implementation phases to their documented scope. It does not add a new implementation plan.

## M.1 Phase Reference

| Phase | Existing phase scope |
| --- | --- |
| 0 | Baseline |
| 1 | Source/License Audit |
| 2 | Architecture Extraction |
| 3 | Execution Abstraction |
| 4 | Privilege/Capability Model |
| 5 | Porter Backend |
| 6 | Porter Validation |
| 7 | Shizuku Backend |
| 8 | Backend Resolver |
| 9 | Rootless Regression |
| 10 | Common Package Model |
| 11 | ADB Module Domain |
| 12 | `module.prop` Parser |
| 13 | Archive Validation |
| 14 | Staged Installation |
| 15 | Storage |
| 16 | Module Environment |
| 17 | Action Runtime |
| 18 | Service Runtime |
| 19 | Enable/Disable |
| 20 | Execution Logging |
| 21 | Policy |
| 22 | Trust |
| 23 | WebUI |
| 24 | `window.Shizuku` |
| 25 | WebUI Security |
| 26 | Process Management |
| 27 | Runtime Persistence |
| 28 | Runtime Recovery |
| 29 | CodeBricks |
| 30 | Source Architecture |
| 31 | Catalog |
| 32 | Updates |
| 33 | Package UI |
| 34 | Backend UI |
| 35 | Device/Runtime UI |
| 36 | Package Details |
| 37 | Error/Recovery UI |
| 38 | Core Unit Testing |
| 39 | ADB Module Compatibility |
| 40 | Reference Modules |
| 41 | Porter Integration Testing |
| 42 | Shizuku Testing |
| 43 | Backend Switching |
| 44 | Security Validation |
| 45 | Persistence/Recovery |
| 46 | Performance |
| 47 | Device Compatibility |
| 48 | UI/UX |
| 49 | Documentation |
| 50 | Release Preparation |
| 51 | Final Compatibility Audit |
| 52 | Architecture Audit |
| 53 | Adaptive Expansion |
| 54 | Final Definition of Done |

The detailed phase text remains in §§104–158.

## M.2 Proposed Class Names Are Not Commitments

The knowledge base lists names such as:

- `AdbModuleInstaller`
- `AdbModuleParser`
- `PorterExecutionBackend`
- `ShizukuExecutionBackend`
- `ExecutionBackendResolver`

These are architectural proposals until confirmed against the codebase. Source: §207.

## M.3 Implementation Rule

Before creating a new abstraction:

1. search existing code
2. determine whether equivalent exists
3. inspect responsibilities
4. decide whether reuse/adaptation is better
5. only then create the new abstraction

Source: §208.

# Appendix N — Decision & Rejection Index

## N.1 Core Architectural Decisions

| Decision | Existing reference |
| --- | --- |
| ADB Modules are first-class | §176, Invariant 1 |
| Rootless Plugins remain supported | §176, Invariant 2 |
| CodeBricks remain supported | §176, Invariant 3 |
| Porter is primary | §176, Invariant 4 |
| Shizuku is compatibility backend | §176, Invariant 5 |
| Backend is separate from privilege | §176, Invariant 6 |
| Privilege is separate from policy | §176, Invariant 7 |
| Policy is separate from trust | §176, Invariant 8 |
| Source trust is separate from execution trust | §176, Invariant 9 |
| UI does not own privileged execution | §176, Invariant 10 |
| Unvalidated archives never reach extraction | §176, Invariant 11 |
| Backend switching cannot silently alter privilege semantics | §176, Invariant 12 |
| ADB Modules are not Magisk/KSU modules | §176, Invariant 13 |
| `service.sh` is not automatically a Rootless daemon | §176, Invariant 14 |
| Runtime state is reconciled with actual state | §176, Invariant 15 |

## N.2 Rejected Approaches

| Rejected approach | Documented reason |
| --- | --- |
| Make ADB Modules into Rootless Plugins | Different metadata and lifecycle semantics. |
| Make Rootless Plugins into ADB Modules | Would break Rootless compatibility. |
| Copy Shevery wholesale | Would inherit unrelated architecture and become difficult to maintain. |
| Make Shizuku the central abstraction | Porter is the primary backend. |
| Put Porter everywhere | Would make adding/changing other backends unnecessarily difficult. |
| Treat Full Trust as “disable security” | Fundamental platform protections must remain. |
| Automatically treat `service.sh` as a Rootless daemon | Lifecycle semantics differ. |
| Use `/data/adb/modules` | These are not Magisk/KSU modules. |
| Trust everything from a catalog | Source trust and execution trust are different. |
| Let UI directly execute privileged commands | Security and architectural boundaries would collapse. |

Source: §165.

## N.3 Decision Principles

The knowledge base also records these principles:

- Reuse before rewrite.
- Avoid premature abstraction.
- Avoid under-abstraction.
- Security over convenience.
- Compatibility over cosmetic similarity.
- Preserve meaningful user choice.
- No silent semantic conversion.
- Allow deliberate adaptive architecture.

Sources: §§167–174.

# Appendix O — Open Questions & Future Investigation

## O.1 Version-Sensitive Unknowns

External projects change. The knowledge base distinguishes stable architectural decisions from version-sensitive facts. Version-sensitive facts include specific API names, safety limits, catalog behavior, and Android compatibility. These must be revalidated before implementation.

Source: §205.

## O.2 Research Confidence Labels

External behavior should be distinguished as:

- Verified
- Observed
- Inferred
- Proposed
- Unknown

An inference must not be turned into a fact. Source: §206.

## O.3 Backend Investigation

The knowledge base requires verification of:

- actual Porter SDK/source behavior
- exact Porter API surface
- Shizuku APIs
- Shizuku permissions and binder behavior
- UserService behavior
- hidden API compatibility
- actual backend capabilities

Sources: §§40–42, 203–204.

## O.4 Compatibility Investigation

Open compatibility work includes actual target Android versions, newer Android behavior, WebView behavior, background restrictions, Porter availability, Shizuku availability, and changes in the investigated external projects. Sources: §§99–100 and 205.

## O.5 Future Architecture Areas

Documented future possibilities include:

- additional package types where a genuinely different package contract exists
- future execution backends
- package signatures and cryptographic verification
- source signing
- trust inheritance rules
- permission visualization
- stronger sandboxing
- dependency security
- rollback
- package quarantine
- backup/restore/export/import
- package dependencies and snapshots
- searchable catalog/discovery improvements
- backend/module/execution/archive/WebUI/runtime diagnostics

Sources: §§184–189.

These are future possibilities, not current implementation commitments.

## O.6 Questions to Revisit Before Implementation

When a technical detail is uncertain, the documented rule is:

«Go back to the actual source.»

For proposed class names and abstractions, inspect the codebase before treating them as commitments. Sources: §§197, 202, 207–208.

# Appendix P — Historical & Research Notes

## P.1 Project Origin and Motivation

The project originated from the decision to extend Rootless Store rather than build an unrelated application. The knowledge base records the reasons for choosing Rootless Store, the reasons not to build directly from Shevery, and the reasons not to simply copy both applications. Sources: §§7–10.

## P.2 Investigation Sequence

The recorded investigation areas include:

1. Rootless Store
2. Shevery
3. Porter
4. Shizuku
5. cross-project comparison
6. extraction of architectural boundaries
7. definition of the ADB Module contract
8. definition of the backend-neutral execution model

The detailed evidence and reasoning remain in §§11–49 and the later reference sections.

## P.3 Shevery Research Notes

The knowledge base records that Shevery is actively evolving. Therefore compatibility work follows the documented pattern:

1. investigate
2. compare behavior
3. determine whether compatibility matters
4. update the compatibility layer if appropriate
5. update tests
6. update documentation

Source: §18.

Important Shevery reference areas include the ADB Module guide, ADB Module API, Android compatibility notes, module catalog behavior, module policy behavior, and WebUI bridge behavior. Source: §198.

## P.4 Rootless Research Notes

The Rootless investigation covers its plugin manifest, execution model, persistence, runtime recovery, and CodeBricks. The project intends to preserve Rootless functionality while adapting only where the new architecture requires it. Sources: §§11–16 and 202.

## P.5 Porter and Shizuku Research Notes

The documented architectural conclusion is:

```text
Porter
  ↓
PRIMARY backend

Shizuku
  ↓
COMPATIBILITY backend
```

The research rule is not to invent Porter or Shizuku APIs. Exact API names, permissions, binder behavior, UserService behavior, SDK behavior, and compatibility details must be checked against the actual source/documentation before implementation. Sources: §§39–42, 203–204.

## P.6 Historical Decision Context

The knowledge base deliberately preserves rejected approaches because they explain the shape of the current architecture. Major examples include rejecting a wholesale Shevery copy, rejecting a Shizuku-centered architecture, keeping ADB Modules distinct from Rootless Plugins, keeping `service.sh` distinct from Rootless daemon semantics, and refusing to treat catalog/source trust as automatic execution trust. Source: §165.

## P.7 Research and Documentation Maintenance

The personal knowledge base is intended to preserve not only what the project is, but why it was designed this way, what was investigated, what was rejected, what was learned, what remains unknown, how it should be implemented and tested, how it should be secured, and how it should evolve.

The knowledge base also explicitly distinguishes its role from `AGENTS.md`, `ARCHITECTURE.md`, `PLAN.md`, `INVESTIGATION.md`, and `README.md`. The personal knowledge base is the comprehensive memory/reference layer. Source: `0. HOW TO USE THIS DOCUMENT` and the project document-source-of-truth material.

## P.8 No Dated Timeline Is Invented

The supplied project knowledge contains a development-phase sequence but does not establish a complete dated historical timeline. Accordingly, this appendix records the documented research sequence and decision context rather than inventing dates or release milestones.

## P.9 Final Personal Reference

The project's personal shorthand remains:

«Rootless Store + ADB Modules + Porter-first runtime + Shizuku compatibility.»

And the central separation remains:

«Package ≠ Backend ≠ Privilege ≠ Policy ≠ Trust ≠ Runtime.»

Source: §240.

