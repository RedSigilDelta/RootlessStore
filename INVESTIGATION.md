Investigation

1. Purpose

This document records the research and findings that inform the ADB Modules project.

It answers:

- What exists in the projects being studied?
- How do those systems currently work?
- What behaviors and formats need to be compatible?
- What constraints or risks were discovered?
- What evidence supports the project's architectural decisions?

This document is a research record, not the architecture specification or implementation plan.

---

2. Documentation Source of Truth

The project uses separate documents for separate responsibilities.

Document| Authority
"AGENTS.md"| Agent behavior, rules, constraints, and workflow
"ARCHITECTURE.md"| System structure, boundaries, responsibilities, and architectural invariants
"PLAN.md"| Implementation scope, sequencing, milestones, and completion criteria
"INVESTIGATION.md"| Research findings, external-project behavior, evidence, and compatibility observations
"README.md"| Public-facing project explanation

Each document is authoritative only within its own domain.

A finding recorded here does not automatically change the architecture or implementation plan.

When investigation reveals that the existing architecture or plan is incorrect:

1. Record the finding here.
2. Determine whether the finding changes the architecture.
3. If architecture changes, update "ARCHITECTURE.md".
4. If implementation scope or ordering changes, update "PLAN.md".
5. If agent behavior or guardrails need to change, update "AGENTS.md".
6. Keep this document as the research record.

Cross-document contradictions must never be silently resolved by choosing whichever statement is convenient.

---

3. Project Under Investigation

The project is a unified Android application based on a fork of Rootless Store.

The application is intended to support three distinct executable package families:

Unified Application
├── Rootless Plugins
├── ADB Modules
└── CodeBricks

The project is not intended to become a copy of either Rootless Store or Shevery.

The investigation therefore focuses on:

- preserving useful Rootless Store functionality,
- understanding Shevery's ADB Module compatibility requirements,
- determining how Porter can provide the primary privileged execution path,
- maintaining Shizuku compatibility,
- identifying security and lifecycle requirements,
- avoiding incompatible assumptions between the systems.

---

4. Rootless Store Investigation

4.1 Project Role

Rootless Store provides the foundation for the application.

The project already contains concepts for:

- plugin management,
- plugin installation,
- plugin execution,
- execution contexts,
- runtime persistence,
- execution history,
- CodeBricks,
- configurable sources,
- market/catalog functionality,
- WebUI-capable plugins,
- device/runtime status,
- Shizuku-backed execution.

This makes it a useful starting point rather than requiring an entirely new package-management application.

Official repository:

"Rootless Store repository" (https://reference-url-citation.invalid/0)

---

5. Rootless Store Architectural Findings

The investigation identified several useful concepts in Rootless Store.

These include concepts corresponding to:

- "Plugin"
- "PluginManifest"
- "PluginSource"
- "PluginExecution"
- "PluginRuntime"
- "ExecutionContext"
- "CodeBrick"
- market/source abstractions
- execution gateways
- plugin repositories
- execution persistence
- runtime recovery

Exact source names and paths should be verified against the current fork before implementation.

Where this document uses a class name that has not been freshly verified, it represents a research reference, not a mandate to preserve that exact API.

---

6. Rootless Plugin Findings

Rootless Plugins have their own package format and lifecycle.

Important characteristics include:

- plugin metadata,
- configured entry points,
- executable files,
- one-shot execution,
- daemon-style execution,
- environment configuration,
- execution contexts,
- installation and extraction,
- execution persistence,
- process management,
- optional WebUI behavior.

Rootless Plugin semantics must remain distinct from ADB Module semantics.

The ADB Module compatibility layer should therefore not redefine Rootless Plugin metadata to accommodate Shevery.

---

7. Rootless Execution Findings

Rootless Store contains execution mechanisms supporting different privilege environments.

The investigated execution concepts include:

Application / Local Shell
        │
        ├── restricted execution
        │
        ├── Shizuku / ADB execution
        │
        └── Root execution

The exact current implementation must be verified before reuse.

A key finding is that Rootless Store already separates executable package management from some of the mechanics used to execute packages.

This provides useful precedent for a separate execution abstraction.

---

8. Rootless Shizuku Findings

Rootless Store uses Shizuku-related services for privileged operations.

The investigation identified concepts involving:

- Shizuku UserService,
- AIDL communication,
- shell execution,
- installation/extraction,
- process IDs,
- process termination,
- execution callbacks,
- execution persistence.

A researched execution pattern is approximately:

Application
    ↓
Shizuku service
    ↓
shell command
    ↓
package entry point
    ↓
stdout / stderr
    ↓
execution result

The exact implementation must be verified against the current Rootless Store source before reuse.

---

9. Rootless Installation Findings

The investigated Rootless installation flow includes privileged installation for packages requiring elevated execution environments.

The broad behavior is:

Package archive
    ↓
validation / preparation
    ↓
application-managed staging
    ↓
privileged installation/extraction
    ↓
installed package directory

The precise storage paths and extraction implementation are implementation details that must be verified from the current source.

The ADB Module system should not automatically reuse Rootless's package storage semantics where those semantics conflict with Shevery compatibility.

---

10. Rootless Runtime Persistence

Rootless Store contains persistence concepts for executions and runtime state.

The investigation indicates support for:

- storing execution information,
- tracking process IDs,
- recovering runtime state,
- terminating executions,
- maintaining execution history.

This is valuable because ADB Modules also require runtime state that survives ordinary UI lifecycle changes.

However, Rootless daemon semantics and Shevery "service.sh" semantics are not equivalent.

---

11. CodeBrick Findings

CodeBricks are a distinct Rootless Store concept representing saved commands/automations.

They may be promoted into executable plugin forms.

CodeBricks therefore represent a third package family rather than merely another ADB Module format.

The investigation supports preserving CodeBrick functionality while avoiding unnecessary coupling to the ADB Module system.

---

12. Rootless Market and Source Findings

Rootless Store includes source/catalog concepts for discovering and managing packages.

The investigated concepts include:

- sources,
- manifests,
- paging,
- APIs/gateways,
- market/catalog behavior,
- package discovery.

These concepts are potentially reusable for ADB Module catalogs.

However, source discovery and package execution trust must remain separate concerns.

A package being discoverable from a source does not inherently mean that the package is trusted to execute.

---

13. Rootless WebUI Findings

Rootless Store has WebUI-related execution concepts.

This provides useful precedent for exposing package-specific interfaces.

However, Shevery's ADB Module WebUI behavior introduces additional compatibility and security requirements, particularly around:

- "window.Shizuku",
- command execution,
- environment access,
- network restrictions,
- WebView security,
- module-specific policies.

Therefore Rootless WebUI functionality cannot simply be assumed to be equivalent to Shevery WebUI functionality.

---

14. Shevery Investigation

14.1 Project Role

Shevery is the primary compatibility reference for the ADB Module package format and behavior.

Official repository:

"Shevery repository" (https://reference-url-citation.invalid/1)

Shevery is a modernized Shizuku-based Android application that includes an ADB Modules system.

The project currently changes relatively quickly, so compatibility assumptions should be tied to a specific reference version and regression-tested.

---

15. Shevery ADB Module Findings

ADB Modules are ZIP-based packages containing a "module.prop" file at the root.

A minimal module can resemble:

my-module/
├── module.prop
├── action.sh
└── webui/
    └── index.html

The module system is not a Magisk/KSU systemless module framework.

ADB Modules should therefore not be assumed to support:

- "/data/adb/modules",
- Magisk mount behavior,
- KernelSU module hooks,
- systemless overlay semantics.

---

16. "module.prop" Findings

The investigated required metadata includes:

id=
name=
version=
versionCode=
author=
description=

Optional/custom metadata can include concepts such as:

banner=
webui=
usesShellBridge=true
action=

Paths are relative to the module package.

The API documentation indicates that unsafe path forms such as absolute paths and traversal using ".." are rejected.

Official guide:

"Shevery ADB Modules Guide" (https://reference-url-citation.invalid/2)

Official API reference:

"Shevery ADB Modules API" (https://reference-url-citation.invalid/3)

---

17. ADB Module Storage Findings

The investigated Shevery storage location is conceptually:

/data/user/0/<package>/files/adb_modules/<module-id>

The exact path should be treated as a compatibility requirement only where required by the supported module contract.

The important observed behavior is that modules are stored inside application-private storage rather than "/data/adb/modules".

---

18. ADB Module Scripts

The investigated module lifecycle recognizes several script types.

"action.sh"

"action.sh" represents a user-triggered module action.

It is not automatically equivalent to a daemon.

"service.sh"

"service.sh" represents controlled background/service behavior.

Its execution is subject to module policy and runtime state.

This is fundamentally different from simply launching a Rootless daemon plugin.

---

19. "service.sh" Findings

The investigated conditions for automatic service execution include concepts such as:

- module enabled state,
- appropriate access mode,
- background-action permission,
- active Shizuku binder/session,
- module policy.

The manager can execute enabled services once during an applicable Shizuku binder session.

This means "service.sh" is a policy-controlled lifecycle feature rather than an unrestricted background process.

---

20. ADB Module Environment

The investigated environment includes variables such as:

MODDIR=/data/user/0/<package>/files/adb_modules/<id>
ASH_STANDALONE=1
SHIZUKU_MODULE_ID=<id>
SHIZUKU_MODULE_MODE=safe|custom|full
SHIZUKU_MODULE_TRUSTED=0|1
SHIZUKU_MODULE_BACKGROUND=0|1

These variables form part of the compatibility surface and should be preserved where required.

The final implementation should verify exact values and semantics against the targeted Shevery reference version.

---

21. ADB Module WebUI Findings

ADB Modules can contain a local WebUI.

A typical structure is:

module/
├── module.prop
├── action.sh
└── webui/
    └── index.html

The WebUI can optionally interact with a shell bridge.

The investigated bridge preserves:

window.Shizuku

This namespace should not be casually renamed because existing modules may depend on it.

---

22. WebUI Security Findings

The investigated WebUI security controls include restrictions around:

- HTTPS/network access,
- file/content access,
- third-party cookies,
- mixed content,
- shell bridge exposure,
- command execution,
- output limits,
- timeout limits.

This demonstrates that the WebUI is an execution surface rather than merely a visual interface.

Security therefore needs to be treated as part of ADB Module compatibility.

---

23. WebUI Shell Bridge Findings

The investigated API includes concepts similar to:

window.Shizuku.exec(...)

and:

window.Shizuku.execWithOptions(...)

The API supports concepts including:

- environment variables,
- working directories,
- stdin,
- timeouts,
- output limits.

Exact API behavior must be verified from the targeted compatibility reference before implementation.

---

24. ADB Module Policy Findings

The investigated Shevery system contains policy concepts including:

- Safe mode,
- Custom access,
- Full access,
- background-action permission,
- WebUI bridge permissions,
- network/download restrictions,
- WebView restrictions,
- command/re-command behavior.

The system also provides per-module trust behavior.

This demonstrates that execution privilege, module policy, and trust are separate concepts.

---

25. Full Trust Findings

The investigated Full Trust behavior can bypass several normal module restrictions.

The documented areas include concepts such as:

- Action restrictions,
- Service restrictions,
- background restrictions,
- WebUI bridge restrictions,
- WebView restrictions,
- internet/download restrictions,
- command restrictions.

Full Trust should therefore be treated as an explicit user decision rather than an automatic property of a package.

---

26. ADB Module Resource Limits

The investigated API specifies safety limits including approximately:

- maximum ZIP entries: "2048"
- maximum extracted size: "200 MB"
- script timeout: "120 seconds"
- retained output per stream: "64 KB"

These limits are part of the compatibility/security research and should be verified against the exact target version before being treated as immutable constants.

---

27. ADB Module Installation Findings

The investigated installation behavior is broadly:

ZIP
 ↓
archive validation
 ↓
module.prop validation
 ↓
metadata parsing
 ↓
safe extraction
 ↓
module storage
 ↓
module registration

The important security properties include:

- "module.prop" at the expected location,
- relative paths,
- traversal protection,
- archive-entry limits,
- extracted-size limits,
- controlled storage.

---

28. Shizuku Findings

The ADB Module implementation investigated in Shevery uses the active Shizuku server as its privileged execution mechanism.

The resulting privilege depends on how Shizuku itself was started.

Conceptually:

Shizuku started through ADB
        ↓
ADB shell-level execution

Shizuku started with root
        ↓
root-level execution

Therefore Shizuku is a mechanism for obtaining a privileged execution context, while the actual privilege level depends on the active Shizuku server.

---

29. Porter Investigation

Porter is intended to become the primary privileged execution mechanism for this project.

However, Porter APIs must not be inferred from:

- Shizuku APIs,
- Rootless APIs,
- Shevery APIs,
- memory of another project,
- similarly named classes.

The actual Porter dependency/source must be inspected before implementation.

The investigation therefore establishes the requirement for Porter support without inventing its API surface.

---

30. Porter and Shizuku Relationship

The intended relationship is:

                    Execution Request
                           │
                           ▼
                 Execution Abstraction
                    /             \
                   /               \
                  ▼                 ▼
        Porter Execution     Shizuku Compatibility
             PRIMARY                SECONDARY

The investigation does not establish that Porter and Shizuku have identical capabilities.

Their capabilities must be discovered and represented accurately.

---

31. Capability Findings

A backend may support some operations while another does not.

Relevant capability categories may include:

- shell execution,
- process management,
- persistent processes,
- environment configuration,
- file operations,
- privileged installation,
- background execution,
- WebUI shell bridging,
- module services.

The exact capability set should be derived from verified backend behavior.

A backend must not claim capabilities it cannot actually provide.

---

32. Privilege Findings

The investigation distinguishes:

Backend
    ↓
How execution is provided

Privilege
    ↓
What authority the process actually has

Policy
    ↓
What the package is permitted to do

Trust
    ↓
Whether the user explicitly trusts the package

These concepts should not be treated as interchangeable.

---

33. Backend Fallback Findings

The investigation identified a significant compatibility concern:

A backend change can potentially change the privilege level of a package.

For example:

Porter unavailable
      ↓
automatic Shizuku fallback
      ↓
different privilege environment

This cannot be treated as a harmless implementation detail.

Any fallback behavior must preserve the user's explicit execution expectations and must never silently escalate or otherwise change privilege semantics.

---

34. Package-Type Findings

The investigation supports three distinct package families:

Rootless Plugin
ADB Module
CodeBrick

They share some management concerns, such as:

- installation,
- metadata,
- execution,
- persistence,
- updates,
- user interface.

However, their formats and lifecycle semantics differ.

Therefore common infrastructure can be shared where semantics genuinely overlap, while package-specific behavior remains separate.

---

35. Package Identity Findings

The research indicates that package identity should not be conflated with execution backend.

Conceptually:

Package
  = what is being executed

Backend
  = how it is executed

Privilege
  = authority available

Policy
  = permitted behavior

Trust
  = user's trust decision

Runtime State
  = current execution condition

This separation is one of the most important findings from comparing Rootless Store and Shevery.

---

36. Source Trust Findings

A package source can provide:

- metadata,
- package discovery,
- version information,
- download location,
- update information.

That does not automatically establish execution trust.

The research therefore identifies two separate concepts:

Source Trust
     ≠
Execution Trust

A catalog should not silently grant a package permission to execute.

---

37. Update Findings

Both package management and catalog functionality introduce update concerns.

The investigation identifies the need to distinguish:

- discovering an update,
- downloading an update,
- validating an update,
- installing an update,
- replacing an installed version,
- preserving user/runtime state,
- deciding whether execution is trusted.

An update should not be treated as trusted merely because the package was previously installed.

---

38. Runtime Recovery Findings

Rootless Store's runtime persistence/recovery concepts provide useful precedent.

ADB Modules introduce additional recovery cases involving:

- active scripts,
- service processes,
- Shizuku binder sessions,
- enabled modules,
- interrupted installations,
- incomplete extraction,
- stale process state.

These behaviors need explicit compatibility handling rather than assuming Rootless runtime recovery automatically applies.

---

39. Android Compatibility Findings

Shevery's current development includes Android 16/17 compatibility work.

The investigation found documentation concerning hidden API compatibility issues affecting the original Shizuku release under Android 17.

The important conclusion is not that a specific Android version is permanently supported by either project, but that:

- Android version compatibility is an active concern,
- Shizuku compatibility may differ by Android version,
- backend compatibility must be tested independently,
- the targeted Shevery compatibility reference must be version-pinned.

---

40. Shevery Compatibility Boundary

The project should target ADB Module compatibility, not complete Shevery application compatibility.

Compatibility should therefore focus on the documented module contract:

module.prop
scripts
environment
storage behavior
policy behavior
WebUI behavior
window.Shizuku bridge
execution limits
lifecycle semantics

Shevery-specific application features that are unrelated to the ADB Module contract are not automatically required.

---

41. Features Not Automatically Imported From Shevery

The investigation does not establish a requirement to copy every Shevery feature.

Examples include:

- Gemini explanation,
- Commandium,
- AI command generation,
- Shevery-specific UI,
- Shevery-specific Shizuku management,
- experimental Dhizuku behavior,
- unrelated command tools.

These features may be investigated separately if they become relevant to the project's goals.

---

42. Features Not Automatically Imported From Rootless Store

Likewise, Rootless Store behavior should not automatically be imposed on ADB Modules.

Examples include:

- treating every ADB Module as a Rootless daemon,
- forcing Rootless Plugin metadata onto modules,
- using Rootless-specific storage paths where incompatible,
- assuming Rootless execution contexts exactly match module policy,
- automatically converting "service.sh" into a generic daemon.

---

43. License and Attribution Findings

The investigated projects have their own licensing requirements.

Rootless Store is currently licensed under AGPL-3.0.

Shevery's README states that its code files are Apache 2.0.

License compatibility, notices, attribution, and file-level provenance must be audited before copying or adapting code.

The project should prefer behavioral compatibility and clean-room integration over indiscriminate source merging.

---

44. Current Research Conclusions

The investigation establishes the following major findings:

1. Rootless Store provides a useful foundation for package management, execution, persistence, sources, and CodeBricks.
2. Shevery provides the primary reference for ADB Module compatibility.
3. ADB Modules are distinct from Rootless Plugins.
4. ADB Modules are not Magisk/KSU systemless modules.
5. "module.prop" is a central compatibility surface.
6. "action.sh" and "service.sh" have different lifecycle semantics.
7. ADB Module WebUI is an execution surface and requires security controls.
8. "window.Shizuku" is an important compatibility surface.
9. Shizuku privilege depends on how the Shizuku server was started.
10. Porter is intended to be the primary execution backend.
11. Shizuku is intended as compatibility support rather than the project's core execution abstraction.
12. Backend, privilege, policy, trust, and runtime state are separate concepts.
13. Automatic backend fallback can alter execution semantics and therefore cannot be treated casually.
14. Source trust and execution trust are separate.
15. Rootless Plugin, ADB Module, and CodeBrick formats should remain distinct.
16. Runtime persistence and recovery are important across package types but cannot be assumed to have identical semantics.
17. Android-version compatibility must be verified against current backend/reference versions.
18. Shevery application features outside the ADB Module contract are not automatically project requirements.
19. Rootless Store features outside the common package/runtime foundation are not automatically applicable to ADB Modules.
20. License and provenance must be verified before reusing source code.

---

45. Research-to-Architecture Boundary

The investigation produces evidence.

The architecture determines how that evidence is represented in the application.

For example:

Investigation finding:
Shevery requires module.prop at ZIP root.
                    ↓
Architecture decision:
ADB Modules have a dedicated manifest/parser boundary.
                    ↓
Plan:
Implement archive validation and module.prop parsing.

Similarly:

Investigation finding:
Porter and Shizuku may provide different capabilities.
                    ↓
Architecture decision:
Execution backends expose explicit capabilities.
                    ↓
Plan:
Implement capability discovery and backend resolution.

The investigation itself does not prescribe the class names, module structure, or implementation sequence.

---

46. Research Maintenance

This document should be updated when:

- Rootless Store behavior changes,
- Shevery changes its ADB Module contract,
- Porter behavior/API changes,
- Shizuku compatibility changes,
- Android platform behavior changes,
- new security constraints are discovered,
- compatibility testing reveals previously unknown behavior,
- licensing or provenance information changes.

When a new finding changes the architecture, the architecture document must be updated separately.

When a new finding changes implementation scope or order, the plan must be updated separately.

When a new finding changes agent behavior or safety requirements, the agent rules must be updated separately.

The research record should remain focused on what was discovered and what evidence supports it.

---

47. Final Research Model

The project should continue to reason about the system using this model:

                    RESEARCH
                       │
                       ▼
              INVESTIGATION.md
                       │
             verified findings
                       │
                       ▼
               ARCHITECTURE.md
                       │
              system structure
                       │
                       ▼
                  PLAN.md
                       │
             implementation order
                       │
                       ▼
                IMPLEMENTATION

Agent behavior surrounds the entire process:

                         AGENTS.md
                    ┌───────────────┐
                    │ Rules / Safety│
                    │ / Workflow    │
                    └───────┬───────┘
                            │
                            ▼
 INVESTIGATION → ARCHITECTURE → PLAN → IMPLEMENTATION

"README.md" remains the public-facing explanation of the resulting project and is not an implementation source of truth.