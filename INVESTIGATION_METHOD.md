ADB Modules App — Investigation Methodology

«Purpose: Define the methodology, evidence standards, research process, verification requirements, and reporting format used for the ADB Modules App investigation program.

"PLAN.md" defines what must be investigated.

"AGENTS.md" defines how the agent must behave.

This document defines how an investigation must be performed, evaluated, documented, and verified.»

---

1. Investigation Philosophy

The ADB Modules App follows a research-first development process.

The investigation process exists to establish a reliable understanding of:

- existing implementations
- external technologies
- compatibility requirements
- security boundaries
- runtime behavior
- architectural constraints
- Android-version differences
- failure and recovery behavior
- ecosystem compatibility
- future architectural possibilities

The investigation process must distinguish between:

Evidence
    ↓
Analysis
    ↓
Conclusion
    ↓
Architectural implication
    ↓
Future proposal

These are not interchangeable.

A plausible explanation is not automatically a verified fact.

A documented behavior is not automatically the same as implemented behavior.

A conceptual architecture is not automatically an existing architecture.

---

2. Investigation Authority

The investigation program follows the project document hierarchy:

AGENTS.md
    ↓
Agent/project rules

PLAN.md
    ↓
Authoritative investigation scope and phase order

INVESTIGATION_METHOD.md
    ↓
Investigation methodology and evidence standards

ARCHITECTURE.md
    ↓
Current proposed architecture, subject to investigation

MasterRef.md
    ↓
Consolidated project reference

INVESTIGATION.md
    ↓
Original pre-MasterRef investigation and historical research/reference

README.md
    ↓
Project overview and user-facing documentation

"PLAN.md" is the authoritative checklist for investigation scope and phase order. This document is the authoritative methodology and evidence-standard reference.

Do not silently remove, skip, or replace investigation requirements.

If an item cannot be established, record it as unresolved.

---

3. Investigation Lifecycle

Every investigation should follow this general process:

1. Define scope
       ↓
2. Identify questions
       ↓
3. Identify sources
       ↓
4. Inspect existing implementation
       ↓
5. Perform external research
       ↓
6. Collect evidence
       ↓
7. Compare sources
       ↓
8. Identify contradictions
       ↓
9. Identify unknowns
       ↓
10. Analyze compatibility/security implications
       ↓
11. Form verified conclusions
       ↓
12. Identify architectural implications
       ↓
13. Produce investigation report
       ↓
14. Self-audit against PLAN.md
       ↓
15. Mark investigation status

Do not skip directly from research to architectural implementation.

---

4. Investigation Scope

Every investigation must begin by defining its scope.

The scope should identify:

- phase number
- phase name
- specific checklist items
- technologies involved
- relevant versions
- relevant Android versions
- relevant repositories
- relevant source trees
- relevant documentation
- known limitations

Example:

Phase:
4 — Porter Investigation

Scope:
Porter dependency, SDK/API surface, execution behavior,
process lifecycle, permissions, failure modes, Android
compatibility, and integration requirements.

Primary concern:
Establish the verified contract required for the Porter
execution backend.

---

5. Questions to Answer

Before researching, translate checklist items into concrete questions.

Questions should seek observable or verifiable answers.

Good:

How does Porter create a process?
What API returns the process handle?
How are stdout and stderr exposed?
What happens when the Porter service dies?

Weak:

Is Porter good?
Is this architecture probably okay?

The investigation should favor questions that can be answered through evidence.

---

6. Source Hierarchy

Sources should be evaluated according to reliability.

Level 1 — Primary Implementation Evidence

Highest priority.

Examples:

- source code
- exact dependency source
- exact tagged release
- exact commit
- actual Android platform implementation
- controlled device experiment

Level 2 — Official Documentation

Examples:

- official API documentation
- official developer documentation
- official project documentation
- official compatibility documentation
- official release notes

Level 3 — Official Project Discussions

Examples:

- official issue trackers
- official discussions
- official maintainer statements
- official migration notes

Useful but must be interpreted in context.

Level 4 — High-Quality Secondary Sources

Examples:

- reputable technical documentation
- detailed engineering articles
- established technical references

Level 5 — Community Sources

Examples:

- forums
- Reddit
- community discussions
- personal blogs
- user reports

Useful for discovering behavior or edge cases, but should not automatically establish authoritative behavior.

Level 6 — Search Snippets

Search-result snippets may be used as research leads.

They should not normally be treated as evidence by themselves.

---

7. Source Recording

Important sources must be recorded.

For each significant source, capture as much as practical:

Source:
Type:
Project:
Version:
Tag:
Commit:
Date:
URL:
Relevant file/page:
Relevant section:
Why it matters:

For source code, record:

Repository
Branch/tag/commit
File path
Class/function
Relevant behavior

---

8. Version Pinning

Version-sensitive findings must be version-aware.

Record relevant versions for:

- Android
- Porter
- Shizuku
- Shevery
- Rootless Store
- Kotlin
- Android Gradle Plugin
- important dependencies
- WebView where relevant

Do not generalize historical behavior into current behavior.

When exact version information cannot be established:

Version: Unknown

Do not invent a version.

---

9. Current vs Historical Information

Every important finding should be classified when historical context matters.

Use:

Current
Historical
Version-specific
Unknown

Example:

Finding:
Behavior X existed in version 12.x.

Status:
Historical — requires verification against current release.

Historical evidence remains valuable but must not be presented as current without verification.

---

10. Documentation vs Implementation

Always distinguish:

Documented Behavior

Something the official documentation says should happen.

Implemented Behavior

Something verified directly in source code.

Observed Behavior

Something observed during a controlled test.

Inferred Behavior

Something logically inferred from available evidence.

Proposed Behavior

Something the ADB Modules App may choose to implement.

These categories must not be merged.

---

11. Evidence Classification

Use the following evidence classifications:

VERIFIED
DOCUMENTED
OBSERVED
INFERRED
PROPOSED
UNKNOWN

VERIFIED

Confirmed directly through strong evidence.

DOCUMENTED

Explicitly stated by an authoritative source but not independently verified.

OBSERVED

Confirmed through controlled testing.

INFERRED

Reasonably derived from evidence but not directly established.

PROPOSED

A potential architecture or implementation choice.

UNKNOWN

Insufficient evidence to establish the answer.

---

12. Confidence

Where useful, assign confidence:

High
Medium
Low

Confidence should reflect evidence quality, not how strongly the researcher feels about the conclusion.

Example:

Claim:
Porter exposes process termination through API X.

Evidence:
Source code at commit ABC.

Confidence:
High

versus:

Claim:
This behavior probably survives application process death.

Evidence:
Indirect documentation only.

Confidence:
Low

---

13. Conflicting Sources

When sources conflict:

1. Record both claims.
2. Identify each source.
3. Compare versions.
4. Compare dates.
5. Determine whether the conflict is historical.
6. Inspect source code where possible.
7. Perform controlled testing where appropriate.
8. Record the final interpretation.
9. Preserve unresolved disagreement when necessary.

Do not simply choose the newest-looking source without investigation.

Do not silently delete contradictory evidence.

---

14. Contradiction Record

Each significant contradiction should contain:

## Contradiction

### Claim A

Source:
Version:
Evidence:

### Claim B

Source:
Version:
Evidence:

### Analysis

Possible explanation:

### Resolution

Resolved / Partially resolved / Unresolved

### Impact

What this means for the project.

---

15. Unknowns

Unknowns are legitimate investigation results.

An investigation should explicitly document:

- missing source information
- undocumented behavior
- unavailable implementation details
- conflicting evidence
- behavior requiring device testing
- behavior requiring future versions
- behavior that cannot currently be reproduced

Never replace an unknown with an assumption merely to make the report appear complete.

---

16. Experimental Research

When documentation and source inspection are insufficient, controlled experiments may be performed.

Record:

Device:
Manufacturer/model:
Android version:
App version:
Dependency versions:
Backend:
Configuration:
Permissions:
Trust state:
Network state:
Battery state:
Procedure:
Expected result:
Observed result:
Logs:
Limitations:

Experiments must be reproducible where practical.

---

17. Device Matrix

When testing Android-specific behavior, record at minimum:

Android version
Device
App version
Backend
Permission state
Trust state
Network state
Battery/background state

Do not treat one device result as universal Android behavior.

---

18. Android-Version Analysis

When an investigation involves Android behavior, explicitly identify affected versions.

Use the project's compatibility range:

Android 12
Android 13
Android 14
Android 15
Android 16
Android 17

For each version, record:

Supported
Partially supported
Unsupported
Unknown

when sufficient evidence exists.

---

19. Porter Investigation Method

Porter requires especially rigorous investigation because it is the primary execution backend.

Establish:

Exact dependency version
Source revision
Official documentation
API surface
Initialization
Permission model
Execution model
Process model
Output model
Termination
Lifecycle
Failure states
Recovery
Android compatibility
Security boundaries

For every important Porter capability, identify:

API
Input
Output
Handle
Error behavior
Lifecycle
Failure behavior
Recovery behavior

Do not invent a Porter contract.

If the contract cannot yet be established:

Status: Unknown / Requires verification

---

20. Shizuku Investigation Method

Investigate Shizuku as a compatibility backend rather than assuming it defines the entire architecture.

Research:

- service lifecycle
- Binder lifecycle
- UserService
- permission state
- process execution
- output handling
- termination
- service death
- reconnection
- API-level behavior
- WebUI compatibility
- "window.Shizuku"
- compatibility requirements

Clearly distinguish:

Shizuku behavior

from:

Shevery behavior

and:

ADB Modules App behavior

---

21. Rootless Store Investigation Method

The Rootless Store investigation must be based on actual implementation evidence wherever possible.

Map:

Package structure
Dependencies
Domain
Data
UI
Persistence
Execution
Runtime
Market
Sources
Notifications
WebUI
Recovery
Testing

For each major component determine:

Purpose
Inputs
Outputs
Dependencies
Persistence
Lifecycle
Failure behavior
Consumers
Extension points

Do not infer architecture solely from package names.

---

22. Shevery Compatibility Investigation Method

Shevery compatibility must be investigated from the actual implementation and current ecosystem.

For each compatibility feature, determine:

Format
Required behavior
Optional behavior
Runtime behavior
Security implications
Version
Source evidence
Compatibility requirement
Implementation detail

Particular attention should be given to:

- "module.prop"
- "action.sh"
- "service.sh"
- WebUI
- custom paths
- environment variables
- permissions
- executable bits
- ZIP handling
- trust modes
- shell bridges

Do not assume every Shevery implementation detail is an ecosystem requirement.

---

23. Security Investigation Method

Security investigations must consider both intended behavior and abuse cases.

For each security boundary ask:

What is trusted?
What is untrusted?
Who controls the input?
What privilege does the operation receive?
Can the input cross a trust boundary?
Can the operation be redirected?
Can state be forged?
Can a malicious package exploit this?
What happens after failure?

Security analysis must cover:

- package security
- archive security
- execution security
- WebView security
- environment security
- backend security
- update security
- trust persistence
- source trust

---

24. Architecture Analysis

Architecture analysis should distinguish:

Existing Architecture

What the source code currently implements.

Verified Target Architecture

An architecture supported by research evidence and explicit project decisions.

Conceptual Architecture

A model used to reason about the system.

Proposed Architecture

A future implementation design.

Never describe a proposal as though it already exists.

---

25. Compatibility Analysis

For each compatibility issue determine:

What is compatible?
Why is it compatible?
What evidence proves it?
What version does it apply to?
What limitations exist?
What is not compatible?

Compatibility should not be expressed merely as:

"Supports X."

Prefer:

Compatible with X under conditions A, B, and C.
Behavior D remains unverified.

---

26. Security and Compatibility Interaction

Compatibility must never be evaluated independently of security.

A compatibility mechanism that:

- changes privilege
- weakens validation
- bypasses trust
- exposes additional APIs
- expands WebView access
- changes execution semantics

must be evaluated for its security consequences.

---

27. Lifecycle Analysis

Do not assume a single lifecycle state is sufficient.

Investigate independent dimensions such as:

Package state
Enablement state
Execution state
Backend state
Permission state
Trust state
WebUI state
Service state

Important distinctions include:

Installed ≠ Enabled ≠ Running

Backend failure ≠ Module failure

Service availability ≠ Service process health

PID ≠ Durable execution identity

These distinctions should be validated rather than merely assumed.

---

28. Failure and Recovery Analysis

For every important subsystem ask:

What can fail?
How is failure detected?
What state remains?
What becomes stale?
Can the system recover?
Does recovery require user action?
Can recovery be automatic?
What data must survive?
What data must be discarded?

Investigate:

- app process death
- backend death
- Binder death
- module process death
- reboot
- force-stop
- interrupted installation
- interrupted update
- partial uninstall
- corrupted state
- stale execution records
- stale PID records

---

29. Update Analysis

Treat updates as state transitions rather than simple file replacement.

Investigate:

Detection
↓
Download
↓
Verification
↓
Staging
↓
Installation
↓
Migration
↓
Runtime recovery

Determine:

- what state is preserved
- what state is replaced
- what data is migrated
- what happens after failure
- whether rollback is possible
- whether downgrade is possible
- whether backend compatibility changes

---

30. Testing Analysis

Testing investigations must distinguish:

Requirement
Test case
Test execution
Observed result
Pass/fail
Unverified behavior

Do not claim:

"Supported"

merely because:

"Tests exist."

A test must actually execute successfully before being considered passed.

---

31. Performance Analysis

Performance research should identify:

Operation
Metric
Measurement method
Environment
Result
Baseline
Limitation

Where relevant measure:

- installation time
- extraction time
- startup time
- execution overhead
- WebUI load time
- memory
- storage
- battery
- concurrent execution
- large archives
- large output

Avoid unsupported performance claims.

---

32. Reliability Analysis

Reliability investigations should test repeated and abnormal conditions.

Examples:

Start → Stop → Start
Backend death → Recovery
App death → Recovery
Reboot → Recovery
Permission revoked → Recovery
Network failure → Recovery
Low storage → Recovery
Low memory → Recovery
Force-stop → Recovery

Document both successful recovery and unrecoverable conditions.

---

33. Interoperability Analysis

Interoperability testing should include:

- ordinary modules
- unusual modules
- modules with custom metadata
- modules with WebUI
- modules with "service.sh"
- modules using shell bridges
- modules using custom paths
- modules depending on environment variables
- modules depending on specific shell behavior

Do not test only ideal/minimal modules.

---

34. Evidence Ledger

For significant claims, maintain an evidence record:

Claim:
Source:
Version:
Commit:
Evidence location:
Evidence type:
Confidence:
Status:
Notes:

Example:

Claim:
service.sh executes once per binder session.

Source:
Shevery source.

Version:
X.Y.Z

Evidence location:
path/to/file

Evidence type:
Source code

Confidence:
High

Status:
Verified

---

35. Investigation Report Format

Every completed phase should produce a comprehensive report.

Use this structure:

# ADB Modules App — Phase X — Investigation

## 1. Scope

## 2. Questions to Answer

## 3. Primary Sources

## 4. Secondary Sources

## 5. Existing Implementation Evidence

## 6. Compatibility Findings

## 7. Security Findings

## 8. Architecture Implications

## 9. Unknowns

## 10. Contradictions

## 11. Verified Conclusions

## 12. Recommendations for MasterRef Expansion

## 13. Sources / References

## 14. Items Requiring Future Investigation

Add additional sections when required by the phase.

---

36. Investigation Report Quality

A report is not complete merely because it is long.

A good report must be:

- evidence-driven
- traceable
- version-aware
- technically specific
- internally consistent
- explicit about uncertainty
- explicit about contradictions
- clear about implementation vs proposal
- useful for later architecture decisions

Avoid padding.

---

37. MasterRef Incorporation

Investigation reports are intermediate research artifacts.

They are not automatically authoritative.

Before incorporating findings into "MasterRef.md":

1. Review the source.
2. Verify the claim.
3. Check version context.
4. Check for contradictory evidence.
5. Determine whether the claim is current.
6. Determine whether it is implementation evidence or documentation.
7. Determine whether it is a project proposal.
8. Identify affected MasterRef sections.
9. Preserve uncertainty where necessary.

---

38. Phase 25 Audit Method

Phase 25 must compare:

Investigation Findings
        ↕
MasterRef.md

Look for:

Missing
Outdated
Incorrect
Unsupported
Duplicated
Contradictory
Overly conceptual
Insufficiently sourced

The audit should produce a concrete list of MasterRef changes.

Do not immediately make those changes merely because they were identified.

---

39. Phase 26 Incorporation Method

Phase 26 converts verified investigation findings into MasterRef updates.

For each proposed change record:

Investigation:
Finding:
Evidence:
Current MasterRef section:
Required change:
Reason:
Classification:

Classify the resulting content as appropriate:

Verified fact
Implementation evidence
Documented behavior
Conceptual architecture
Proposed design
Unknown
Historical behavior
Future possibility

---

40. No Silent Corrections

Do not silently alter the project's conceptual model because new evidence was discovered.

When a finding conflicts with existing architecture:

1. Identify the conflict.
2. Record the evidence.
3. Explain the difference.
4. Determine whether the existing architecture is outdated.
5. Update the appropriate investigation/audit document.
6. Incorporate the change only through the proper phase.

---

41. Research Completeness

A checklist item is complete when the investigation has established one of:

Verified answer
Documented answer
Verified limitation
Verified incompatibility
Verified historical behavior
Explicit unresolved question

A checkbox should not be marked complete merely because someone searched for the topic.

---

42. Phase Completion Criteria

Before a phase is considered complete:

- [ ] Every applicable checklist item has been addressed.
- [ ] Primary sources have been reviewed.
- [ ] Relevant implementation evidence has been inspected.
- [ ] Relevant external documentation has been reviewed.
- [ ] Important claims have version context.
- [ ] Contradictions are documented.
- [ ] Unknowns are documented.
- [ ] Compatibility findings are documented.
- [ ] Security implications are documented.
- [ ] Architecture implications are documented.
- [ ] The investigation report is complete.
- [ ] The report has been checked against "PLAN.md".
- [ ] Remaining limitations are explicitly recorded.

---

43. Research Status

Use these statuses:

Not Started
In Progress
Research Complete
Awaiting Verification
Audited
Complete
Blocked

Not Started

No meaningful research has begun.

In Progress

Research is actively occurring.

Research Complete

Research activity for the current scope has finished, but verification/audit remains.

Awaiting Verification

Important findings require additional evidence or testing.

Audited

The investigation has been reviewed against the checklist.

Complete

The phase satisfies its completion criteria.

Blocked

Progress requires information, access, tooling, or a decision that is currently unavailable.

---

44. Investigation Index

Maintain an index of completed and active investigations.

Recommended information:

Phase
Name
Status
Report
Start date
Completion date
Primary sources
Major unknowns
Major contradictions
MasterRef impact

---

45. Unknowns Register

Maintain a central list of unresolved questions when practical.

Each entry should include:

Unknown:
Phase:
Why unknown:
Evidence already checked:
What would resolve it:
Priority:

Do not allow unresolved questions to disappear merely because a phase is marked complete.

---

46. Contradictions Register

Maintain a central record of significant contradictions.

Each entry should include:

Issue:
Phase:
Source A:
Source B:
Version difference:
Possible explanation:
Current resolution:
Remaining uncertainty:
Impact:

---

47. Source Register

Maintain a source register for major external technologies.

At minimum, track:

Project
Source
Version
Repository
Commit/tag
Documentation
Relevant files
Date checked
Investigation phases

This is especially important for:

- Rootless Store
- Shevery
- Porter
- Shizuku
- Android platform behavior

---

48. Research Efficiency

Thoroughness does not require researching the same fact repeatedly.

When a fact has already been established:

- reuse the evidence
- reference the original investigation
- verify that the evidence remains current
- investigate only new context required by the current phase

Do not duplicate entire investigations unnecessarily.

However, do not reuse old evidence blindly when the current phase depends on newer versions or changed behavior.

---

49. Research Depth

Research should proceed through multiple layers when necessary:

Surface documentation
        ↓
Official documentation
        ↓
Source code
        ↓
Version history
        ↓
Issues/discussions
        ↓
Controlled testing
        ↓
Cross-source verification

Not every question requires every layer.

High-risk architectural or security questions should generally receive deeper verification.

---

50. Search Strategy

When researching an unfamiliar topic:

1. Identify the official project.
2. Find official documentation.
3. Identify source repository.
4. Determine current version.
5. Inspect relevant implementation.
6. Search release history.
7. Search issues/discussions for known edge cases.
8. Compare findings.
9. Test when necessary.

Use search engines primarily to discover sources.

Use the source itself as evidence whenever possible.

---

51. Avoiding Research Drift

Stay within the active phase.

If a new topic appears:

- determine whether it belongs to the current phase
- record it as a follow-up if necessary
- avoid abandoning the current investigation
- avoid silently expanding the phase beyond "PLAN.md"

Cross-phase dependencies should be documented.

---

52. Cross-Phase Dependencies

Some investigations depend on earlier findings.

Examples:

Phase 4 Porter
    ↓
Phase 6 Execution Abstraction

Phase 2 Module Compatibility
    ↓
Phase 3 Lifecycle
    ↓
Phase 14 Runtime Recovery

Phase 7 Security
    ↓
Phase 8 WebUI
    ↓
Phase 16 UI/UX

When a later phase depends on unresolved earlier findings:

- reference the earlier investigation
- identify the unresolved dependency
- do not invent an answer solely to continue

---

53. Architectural Decision Discipline

Investigations may produce architectural implications.

However:

Research finding
    ≠
Automatic implementation decision

A finding may show that:

- an architecture is possible
- an architecture is impossible
- an abstraction is too broad
- an abstraction is too narrow
- a compatibility layer is required
- a security boundary must change

The final project decision should remain explicit.

---

54. Future-Facing Research

Future possibilities must remain clearly separated from current requirements.

Use:

Current
Planned
Proposed
Future
Speculative

Do not turn speculative capabilities into project requirements without an explicit decision.

---

55. Final Investigation Standard

A successful investigation should allow a future developer to answer:

What is this?
How does it work?
Where is it implemented?
What version does this apply to?
What does the documentation say?
What does the source code say?
What have we actually tested?
What Android versions are affected?
What are the security implications?
What are the failure modes?
What happens when something dies?
What is compatible?
What is incompatible?
What remains unknown?
What architectural consequences follow?
What should be incorporated into MasterRef?

If the investigation cannot answer a question, explicitly document why.

---

56. Final Rule

«Research before assuming.

Verify before claiming.

Document before incorporating.

Preserve uncertainty rather than inventing certainty.

Keep implementation evidence, documentation, observation, inference, and proposals clearly separated.»

The goal is not to make every question appear answered.

The goal is to create a traceable, evidence-backed technical understanding that can safely support the architecture and future implementation of the ADB Modules App.