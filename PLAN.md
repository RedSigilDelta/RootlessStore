ADB Modules App — Investigation Plan & Phase Checklist

«Purpose: Define and track the complete research and investigation program for the ADB Modules App. Findings from these investigations may later be incorporated into "MasterRef.md" after verification and audit.

Status: Investigation Program

Important: This document is the authoritative investigation checklist and roadmap. It defines what must be investigated and in what order. It is not an implementation plan.

Investigation findings must be verified before being incorporated into "MasterRef.md".»

---

Investigation Program Structure

The investigation program consists of 27 phases (0–26).

Research Phases

Phases 0–24 investigate the project's architecture, compatibility, security, runtime behavior, testing, reliability, interoperability, and future possibilities.

During these phases:

- Research findings must be documented.
- Evidence must be collected and evaluated.
- Facts must be separated from assumptions.
- Verified implementation behavior must be distinguished from conceptual architecture.
- "MasterRef.md" must not be modified as part of normal Phase 0–24 research.
- Unknowns and contradictions must be recorded rather than silently resolved.

Audit Phase

Phase 25 compares the completed investigation findings against "MasterRef.md".

It identifies:

- Missing information
- Outdated information
- Incorrect assumptions
- Unsupported claims
- Contradictions
- Missing compatibility/security/runtime/testing information
- Areas requiring stronger evidence or source attribution

Incorporation Phase

Phase 26 incorporates verified investigation findings into "MasterRef.md".

Only findings that have passed the investigation and verification process should be incorporated.

---

Phase 0 — Investigation Infrastructure

- [ ] Define the standard investigation-document format
- [ ] Define source reliability levels
- [ ] Define how conflicting sources are handled
- [ ] Define how implementation evidence is distinguished from documentation
- [ ] Define how conceptual architecture is distinguished from verified architecture
- [ ] Define how outdated information is marked
- [ ] Define how Android-version-specific findings are recorded
- [ ] Define how Porter-version-specific findings are recorded
- [ ] Define how Shizuku compatibility findings are recorded
- [ ] Define investigation completion criteria
- [ ] Define MasterRef incorporation criteria
- [ ] Create investigation index
- [ ] Create investigation status tracking

---

Phase 1 — Rootless Store Deep Investigation

Repository Architecture

- [ ] Exhaustively inspect Rootless Store architecture
- [ ] Map every major package/module
- [ ] Map domain/data/UI layers
- [ ] Map dependency boundaries
- [ ] Map persistence architecture
- [ ] Map execution architecture
- [ ] Map plugin lifecycle
- [ ] Map source/catalog lifecycle
- [ ] Map notification architecture
- [ ] Map runtime recovery architecture
- [ ] Map WebUI architecture

Plugin System

- [ ] Investigate "Plugin"
- [ ] Investigate "PluginManifest"
- [ ] Investigate "PluginSource"
- [ ] Investigate "PluginExecution"
- [ ] Investigate "PluginRuntime"
- [ ] Investigate "ExecutionContext"
- [ ] Investigate plugin installation
- [ ] Investigate plugin extraction
- [ ] Investigate plugin storage
- [ ] Investigate plugin execution
- [ ] Investigate plugin termination
- [ ] Investigate plugin uninstall
- [ ] Investigate plugin persistence
- [ ] Investigate plugin state recovery

CodeBricks

- [ ] Investigate CodeBrick architecture
- [ ] Investigate CodeBrick persistence
- [ ] Investigate CodeBrick execution
- [ ] Investigate CodeBrick environment handling
- [ ] Investigate CodeBrick promotion into plugins
- [ ] Determine boundaries between CodeBricks and ADB Modules

Market / Sources

- [ ] Investigate Market architecture
- [ ] Investigate source manifests
- [ ] Investigate source discovery
- [ ] Investigate paging
- [ ] Investigate source trust
- [ ] Investigate source authentication
- [ ] Investigate source integrity
- [ ] Investigate update discovery
- [ ] Investigate package metadata handling

---

Phase 2 — Shevery ADB Module Compatibility Investigation

Module Format

- [ ] Exhaustively document "module.prop"
- [ ] Investigate required fields
- [ ] Investigate optional fields
- [ ] Investigate custom paths
- [ ] Investigate banner handling
- [ ] Investigate WebUI declarations
- [ ] Investigate "usesShellBridge"
- [ ] Investigate custom action paths
- [ ] Investigate compatibility behavior for missing fields
- [ ] Investigate unknown-field handling

Module Files

- [ ] Investigate "action.sh"
- [ ] Investigate "service.sh"
- [ ] Investigate WebUI files
- [ ] Investigate arbitrary executable files
- [ ] Investigate custom scripts
- [ ] Investigate file permissions
- [ ] Investigate executable-bit preservation

Installation

- [ ] Investigate ZIP parsing
- [ ] Investigate ZIP validation
- [ ] Investigate extraction rules
- [ ] Investigate path traversal protection
- [ ] Investigate absolute-path rejection
- [ ] Investigate ".." rejection
- [ ] Investigate extraction limits
- [ ] Investigate timeout behavior
- [ ] Investigate output limits
- [ ] Investigate partial-install recovery
- [ ] Investigate failed-install cleanup

Runtime Environment

- [ ] Investigate "MODDIR"
- [ ] Investigate "ASH_STANDALONE"
- [ ] Investigate "SHIZUKU_MODULE_ID"
- [ ] Investigate "SHIZUKU_MODULE_MODE"
- [ ] Investigate "SHIZUKU_MODULE_TRUSTED"
- [ ] Investigate "SHIZUKU_MODULE_BACKGROUND"
- [ ] Identify all additional environment variables
- [ ] Identify environment variables that are implementation details rather than compatibility requirements

---

Phase 3 — ADB Module Lifecycle

- [ ] Investigate discovery
- [ ] Investigate download
- [ ] Investigate verification
- [ ] Investigate installation
- [ ] Investigate enable/disable
- [ ] Investigate action execution
- [ ] Investigate service execution
- [ ] Investigate background execution
- [ ] Investigate WebUI availability
- [ ] Investigate module updates
- [ ] Investigate module rollback
- [ ] Investigate uninstall
- [ ] Investigate data cleanup
- [ ] Investigate crash recovery
- [ ] Investigate stale-runtime recovery
- [ ] Investigate reboot/session behavior

Lifecycle State Machine

Create a complete state model for:

Discovered
    ↓
Downloaded
    ↓
Verified
    ↓
Installed
    ↓
Enabled
    ↓
Running
    ↓
Stopped
    ↓
Disabled
    ↓
Uninstalled

The investigation must determine whether this linear model is sufficient or whether lifecycle state must be represented across multiple independent dimensions.

---

Phase 4 — Porter Investigation

«Highest-priority architectural investigation.»

- [ ] Identify exact Porter dependency/version
- [ ] Verify official Porter documentation
- [ ] Inspect Porter source
- [ ] Inspect Porter SDK/API surface
- [ ] Identify supported Android versions
- [ ] Identify supported execution modes
- [ ] Identify process creation behavior
- [ ] Identify command execution behavior
- [ ] Identify stdin/stdout/stderr behavior
- [ ] Identify exit-code behavior
- [ ] Identify PID/process handling
- [ ] Identify process termination
- [ ] Identify service/background behavior
- [ ] Identify lifecycle behavior
- [ ] Identify permission requirements
- [ ] Identify failure modes
- [ ] Identify unavailable states
- [ ] Identify initialization requirements
- [ ] Identify runtime state detection
- [ ] Identify version compatibility
- [ ] Identify security boundaries
- [ ] Identify known limitations
- [ ] Identify Android-version-specific differences
- [ ] Determine which Porter behavior is verified versus assumed

Porter Backend Architecture

- [ ] Define verified Porter backend contract
- [ ] Define execution request mapping
- [ ] Define execution result mapping
- [ ] Define execution handle mapping
- [ ] Define capability detection
- [ ] Define state detection
- [ ] Define error mapping
- [ ] Define lifecycle mapping
- [ ] Investigate recovery after Porter process death
- [ ] Investigate recovery after app process death

---

Phase 5 — Shizuku Compatibility Investigation

- [ ] Identify exact Shizuku compatibility requirements
- [ ] Investigate Shizuku service lifecycle
- [ ] Investigate UserService behavior
- [ ] Investigate Binder lifecycle
- [ ] Investigate permission state
- [ ] Investigate process execution
- [ ] Investigate stdin/stdout/stderr
- [ ] Investigate process termination
- [ ] Investigate service death
- [ ] Investigate reconnection
- [ ] Investigate API-level differences
- [ ] Investigate compatibility with current Shizuku forks
- [ ] Investigate compatibility with Shevery's Shizuku implementation
- [ ] Investigate "window.Shizuku" compatibility requirements
- [ ] Investigate WebUI execution semantics
- [ ] Determine what should be implemented through a compatibility bridge rather than exposing Shizuku directly

Backend Isolation

- [ ] Verify Porter and Shizuku can coexist
- [ ] Investigate backend selection
- [ ] Investigate backend fallback
- [ ] Determine when fallback is safe
- [ ] Determine when fallback must be prohibited
- [ ] Investigate backend-specific capabilities
- [ ] Investigate backend-specific errors
- [ ] Investigate backend-specific lifecycle behavior

---

Phase 6 — Execution Abstraction Investigation

- [ ] Investigate "ExecutionBackend" conceptual model
- [ ] Investigate "ExecutionRequest"
- [ ] Investigate "ExecutionResult"
- [ ] Investigate "ExecutionHandle"
- [ ] Investigate "BackendCapability"
- [ ] Investigate backend resolver requirements
- [ ] Investigate backend state model
- [ ] Investigate execution cancellation
- [ ] Investigate timeout handling
- [ ] Investigate process tracking
- [ ] Investigate concurrent executions
- [ ] Investigate execution queues
- [ ] Investigate resource limits
- [ ] Investigate output streaming
- [ ] Investigate persistent execution history

---

Phase 7 — Security Investigation

Package Security

- [ ] Investigate module authenticity
- [ ] Investigate module integrity
- [ ] Investigate signature verification
- [ ] Investigate hashes
- [ ] Investigate certificate verification
- [ ] Investigate tamper detection
- [ ] Investigate malicious module scenarios

ZIP Security

- [ ] Investigate path traversal
- [ ] Investigate ZIP bombs
- [ ] Investigate oversized archives
- [ ] Investigate excessive file counts
- [ ] Investigate symlink handling
- [ ] Investigate special-file handling
- [ ] Investigate executable permissions
- [ ] Investigate extraction race conditions

Execution Security

- [ ] Investigate shell injection
- [ ] Investigate command construction
- [ ] Investigate argument escaping
- [ ] Investigate environment-variable injection
- [ ] Investigate working-directory attacks
- [ ] Investigate process isolation
- [ ] Investigate privilege boundary enforcement
- [ ] Investigate backend substitution attacks
- [ ] Investigate UI-to-runtime trust boundaries

Trust Model

- [ ] Investigate source trust
- [ ] Investigate package trust
- [ ] Investigate signature trust
- [ ] Investigate user trust
- [ ] Investigate runtime trust
- [ ] Investigate backend trust
- [ ] Investigate Full Trust semantics
- [ ] Investigate Custom Trust semantics
- [ ] Investigate Limited Trust semantics
- [ ] Investigate trust persistence
- [ ] Investigate trust revocation
- [ ] Investigate trust changes after updates

---

Phase 8 — WebUI Investigation

- [ ] Investigate WebUI loading
- [ ] Investigate WebView isolation
- [ ] Investigate local assets
- [ ] Investigate JavaScript bridge
- [ ] Investigate "window.Shizuku"
- [ ] Investigate compatibility bridge design
- [ ] Investigate "exec"
- [ ] Investigate "execWithOptions"
- [ ] Investigate output handling
- [ ] Investigate asynchronous execution
- [ ] Investigate callbacks/events
- [ ] Investigate WebUI permissions
- [ ] Investigate internet access
- [ ] Investigate downloads
- [ ] Investigate WebView storage
- [ ] Investigate cookies
- [ ] Investigate navigation restrictions
- [ ] Investigate JavaScript injection risks
- [ ] Investigate origin/security boundaries

---

Phase 9 — Background Services Investigation

- [ ] Investigate "service.sh"
- [ ] Investigate service lifecycle
- [ ] Investigate startup conditions
- [ ] Investigate stop conditions
- [ ] Investigate reboot behavior
- [ ] Investigate binder-session behavior
- [ ] Investigate Android process restrictions
- [ ] Investigate Android 14 behavior
- [ ] Investigate Android 15 behavior
- [ ] Investigate Android 16 behavior
- [ ] Investigate Android 17 behavior
- [ ] Investigate battery restrictions
- [ ] Investigate background execution restrictions
- [ ] Investigate process death
- [ ] Investigate automatic recovery
- [ ] Investigate duplicate-service prevention

---

Phase 10 — Android Compatibility Investigation

Create a dedicated compatibility matrix for:

- [ ] Android 12
- [ ] Android 13
- [ ] Android 14
- [ ] Android 15
- [ ] Android 16
- [ ] Android 17

For each Android version investigate:

- [ ] Shell behavior
- [ ] Process behavior
- [ ] Binder behavior
- [ ] WebView behavior
- [ ] Background execution
- [ ] Storage behavior
- [ ] Package visibility
- [ ] Permissions
- [ ] Notifications
- [ ] Foreground services
- [ ] Battery restrictions
- [ ] App standby
- [ ] Process termination
- [ ] Reboot behavior
- [ ] Porter compatibility
- [ ] Shizuku compatibility

---

Phase 11 — Storage Investigation

- [ ] Module storage layout
- [ ] Persistent data layout
- [ ] Temporary extraction storage
- [ ] Cache storage
- [ ] WebUI storage
- [ ] Logs
- [ ] Execution history
- [ ] Preferences
- [ ] Trust state
- [ ] Backend state
- [ ] Cleanup rules
- [ ] Migration strategy
- [ ] Backup/restore
- [ ] Corruption recovery

---

Phase 12 — Catalog / Source Investigation

- [ ] Shevery module catalog
- [ ] GitHub topic-based discovery
- [ ] Repository metadata
- [ ] Release metadata
- [ ] Version discovery
- [ ] Update discovery
- [ ] Package verification
- [ ] Source trust
- [ ] Malicious source scenarios
- [ ] Offline behavior
- [ ] Source caching
- [ ] Source failure recovery
- [ ] Multiple sources
- [ ] Source priority
- [ ] Duplicate module IDs
- [ ] Version conflicts

---

Phase 13 — Updates and Rollback

- [ ] Update detection
- [ ] Version comparison
- [ ] Update download
- [ ] Update verification
- [ ] Update installation
- [ ] Runtime preservation
- [ ] Configuration preservation
- [ ] Data migration
- [ ] Failed update recovery
- [ ] Rollback
- [ ] Version pinning
- [ ] Downgrades
- [ ] Incompatible updates
- [ ] Backend compatibility during updates

---

Phase 14 — Runtime Recovery Investigation

- [ ] App process death
- [ ] Porter process death
- [ ] Shizuku service death
- [ ] Binder death
- [ ] Module process death
- [ ] Device reboot
- [ ] Force-stop
- [ ] Backend restart
- [ ] Partial installation
- [ ] Partial uninstall
- [ ] Corrupt module state
- [ ] Stale execution records
- [ ] Stale PID records
- [ ] Recovery after interrupted execution

---

Phase 15 — Logging / Diagnostics

- [ ] Execution logs
- [ ] Installation logs
- [ ] Update logs
- [ ] Backend logs
- [ ] Porter diagnostics
- [ ] Shizuku diagnostics
- [ ] WebUI diagnostics
- [ ] Runtime state diagnostics
- [ ] Error classification
- [ ] User-facing errors
- [ ] Developer diagnostics
- [ ] Log retention
- [ ] Log privacy
- [ ] Sensitive-data filtering
- [ ] Export diagnostics

---

Phase 16 — UI / UX Investigation

- [ ] Module list
- [ ] Module detail
- [ ] Installation UI
- [ ] Update UI
- [ ] Runtime status
- [ ] Backend status
- [ ] Permission status
- [ ] Trust controls
- [ ] WebUI entry
- [ ] Action execution
- [ ] Service controls
- [ ] Logs
- [ ] Error presentation
- [ ] Recovery UI
- [ ] Settings
- [ ] Accessibility
- [ ] Large-screen behavior
- [ ] Android 16/17 UI behavior

---

Phase 17 — Testing Investigation

Unit Testing

- [ ] Manifest parser tests
- [ ] ZIP validation tests
- [ ] Installer tests
- [ ] Policy tests
- [ ] Trust tests
- [ ] Backend resolver tests
- [ ] Porter adapter tests
- [ ] Shizuku adapter tests
- [ ] Runtime tests
- [ ] Recovery tests

Integration Testing

- [ ] Full installation
- [ ] Action execution
- [ ] Service execution
- [ ] WebUI execution
- [ ] Update
- [ ] Rollback
- [ ] Uninstall
- [ ] Backend switching
- [ ] Runtime recovery

Device Testing

- [ ] Android-version matrix
- [ ] Porter state matrix
- [ ] Shizuku state matrix
- [ ] Permission matrix
- [ ] Trust matrix
- [ ] Network matrix
- [ ] Battery/background matrix

---

Phase 18 — Licensing / Provenance Investigation

- [ ] Rootless Store license
- [ ] Shevery license
- [ ] Porter license
- [ ] Shizuku license
- [ ] Third-party dependency licenses
- [ ] Source attribution requirements
- [ ] Notice requirements
- [ ] Modified-source requirements
- [ ] Asset licenses
- [ ] Icon/banner licenses
- [ ] WebUI asset licenses
- [ ] Code provenance audit
- [ ] Copied-code audit
- [ ] Generated-code provenance policy

---

Phase 19 — Performance Investigation

- [ ] Module installation performance
- [ ] ZIP extraction performance
- [ ] Module startup performance
- [ ] Action execution overhead
- [ ] Service overhead
- [ ] WebUI performance
- [ ] Logging overhead
- [ ] Memory usage
- [ ] Storage usage
- [ ] Battery impact
- [ ] Concurrent execution
- [ ] Large-module behavior
- [ ] Large-output behavior

---

Phase 20 — Reliability Investigation

- [ ] Repeated execution
- [ ] Long-running services
- [ ] Rapid start/stop
- [ ] Backend restart
- [ ] App restart
- [ ] Device reboot
- [ ] Network interruption
- [ ] Storage pressure
- [ ] Low-memory conditions
- [ ] Battery saver
- [ ] Doze
- [ ] App force-stop
- [ ] Module corruption
- [ ] Backend unavailable
- [ ] Permission revoked

---

Phase 21 — Interoperability Investigation

- [ ] Shevery module compatibility
- [ ] Existing Shevery modules
- [ ] Existing ADB Module repositories
- [ ] Shizuku WebUI modules
- [ ] Modules using "service.sh"
- [ ] Modules using WebUI
- [ ] Modules using shell bridges
- [ ] Modules with custom layouts
- [ ] Modules with unusual metadata
- [ ] Modules depending on specific shell behavior
- [ ] Modules depending on specific environment variables

---

Phase 22 — Architecture Stress Testing

Investigate whether the proposed architecture remains sound under:

- [ ] Multiple execution backends
- [ ] Multiple module formats
- [ ] Multiple module sources
- [ ] Multiple simultaneous modules
- [ ] Multiple simultaneous executions
- [ ] Backend failure
- [ ] Backend switching
- [ ] Trust changes
- [ ] Runtime recovery
- [ ] Android-version differences
- [ ] Future backend additions
- [ ] Future module formats
- [ ] Future WebUI APIs

Architecture Questions

- [ ] Is the abstraction too broad?
- [ ] Is the abstraction too narrow?
- [ ] Are backend-specific details leaking?
- [ ] Are module-format details leaking?
- [ ] Are UI concerns leaking into runtime?
- [ ] Are security decisions centralized appropriately?
- [ ] Can new backends be added without redesigning the module system?
- [ ] Can the module format evolve without breaking the execution layer?

---

Phase 23 — Future Backend Investigation

«Do not implement these merely because they are possible.»

Investigate only:

- [ ] Additional privileged execution mechanisms
- [ ] Additional Binder-based mechanisms
- [ ] Future Porter capabilities
- [ ] Future Shizuku compatibility changes
- [ ] Android-native privileged APIs that may become available
- [ ] Potential future execution abstractions

For every possible backend:

- [ ] Capability analysis
- [ ] Security analysis
- [ ] Permission analysis
- [ ] Compatibility analysis
- [ ] Maintenance cost
- [ ] Architectural impact
- [ ] Whether it belongs in the project at all

---

Phase 24 — Future Module Ecosystem Investigation

- [ ] Module discovery ecosystem
- [ ] Module publishing
- [ ] Module versioning
- [ ] Module compatibility metadata
- [ ] Module dependency metadata
- [ ] Module permissions
- [ ] Module trust levels
- [ ] Module ratings/reviews
- [ ] Module update channels
- [ ] Module compatibility declarations
- [ ] Module documentation
- [ ] Module testing standards
- [ ] Module security scanning

---

Phase 25 — MasterRef Expansion Audit

After investigations are completed:

- [ ] Compare findings against "MasterRef.md"
- [ ] Identify outdated MasterRef sections
- [ ] Identify missing concepts
- [ ] Identify incorrect assumptions
- [ ] Identify overly conceptual sections that now have verified implementation evidence
- [ ] Identify sections that need stronger source attribution
- [ ] Identify duplicated material
- [ ] Identify contradictions
- [ ] Identify missing compatibility matrices
- [ ] Identify missing security analysis
- [ ] Identify missing runtime analysis
- [ ] Identify missing testing information
- [ ] Identify missing architecture diagrams
- [ ] Identify missing examples
- [ ] Identify missing glossary terms
- [ ] Identify sections that should remain future-facing

---

Phase 26 — MasterRef Incorporation

For each completed investigation:

- [ ] Create investigation report
- [ ] Review sources
- [ ] Separate facts from assumptions
- [ ] Mark verified implementation details
- [ ] Mark conceptual proposals
- [ ] Mark unresolved questions
- [ ] Identify affected MasterRef chapters
- [ ] Expand affected chapters
- [ ] Add cross-references
- [ ] Add compatibility information
- [ ] Add security implications
- [ ] Add testing implications
- [ ] Update terminology

---

Investigation Completion Rule

A phase is not complete merely because every checkbox has been researched once.

Before marking a phase complete, the investigation must:

- [ ] Address every checklist item
- [ ] Identify and document primary sources
- [ ] Record relevant secondary sources
- [ ] Verify important implementation claims against source code where possible
- [ ] Distinguish documentation from implementation evidence
- [ ] Distinguish verified behavior from assumptions
- [ ] Record version/date information where relevant
- [ ] Record Android-version-specific differences where relevant
- [ ] Record Porter-version-specific differences where relevant
- [ ] Record Shizuku-version-specific differences where relevant
- [ ] Document contradictions
- [ ] Document unresolved questions
- [ ] Produce a comprehensive investigation report
- [ ] Perform a final self-audit against the phase checklist

Only then should the phase be marked Investigated / Audited.

---

MasterRef Protection Rule

During Phases 0–24, investigation work must not silently rewrite or reshape "MasterRef.md".

Research findings should first be recorded in the appropriate investigation report.

"MasterRef.md" is evaluated during Phase 25 and updated through the controlled process defined in Phase 26.

This preserves a clear distinction between:

Research
    ↓
Evidence
    ↓
Investigation Report
    ↓
Audit
    ↓
Verified Finding
    ↓
MasterRef Incorporation

---

Final Investigation Program Goal

The objective is to produce a thoroughly researched, evidence-backed understanding of:

- Rootless Store
- ADB Modules
- Shevery compatibility
- Porter
- Shizuku
- execution architecture
- security and trust
- WebUI
- background execution
- Android compatibility
- storage
- sources/catalogs
- updates/rollback
- runtime recovery
- diagnostics
- UI/UX
- testing
- licensing/provenance
- performance
- reliability
- interoperability
- architectural scalability
- future backends
- future module ecosystem

The final "MasterRef.md" should contain verified and clearly classified knowledge, while unresolved questions, assumptions, proposals, and future possibilities remain explicitly identified as such.