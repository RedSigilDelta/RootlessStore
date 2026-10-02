ADB Modules App — AGENTS.md

Purpose

This repository is a research-first Android project based on Rootless Store.

The immediate objective is to perform a comprehensive, evidence-driven investigation of:

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
- catalogs and sources
- updates and rollback
- runtime recovery
- diagnostics
- UI/UX
- testing
- licensing and provenance
- performance
- reliability
- interoperability
- architectural scalability
- future backends
- future module ecosystem

The investigation program is defined by "PLAN.md".

The investigation methodology is defined by "INVESTIGATION_METHOD.md".

"MasterRef.md" is the project's synthesized reference document.

---

1. Authoritative Project Documents

Before beginning any investigation or project task, inspect the relevant project documentation.

The primary documents are:

AGENTS.md
PLAN.md
INVESTIGATION_METHOD.md
MasterRef.md
ARCHITECTURE.md
INVESTIGATION.md
README.md

Authority hierarchy

Use the documents according to their purpose:

Document | Authority
"AGENTS.md" | Agent behavior and project rules
"PLAN.md" | Investigation scope and phase order
"INVESTIGATION_METHOD.md" | Investigation methodology and evidence standards
"ARCHITECTURE.md" | Current proposed architecture
"INVESTIGATION.md" | Original pre-MasterRef investigation and historical research/reference material
"MasterRef.md" | Consolidated project reference
"README.md" | Project overview and user-facing information

If documents conflict:

1. Follow explicit safety/security rules.
2. Follow "AGENTS.md" for agent behavior.
3. Follow "PLAN.md" for investigation scope.
4. Follow "INVESTIGATION_METHOD.md" for research methodology.
5. Treat "MasterRef.md", "ARCHITECTURE.md", and "INVESTIGATION.md" as knowledge/reference material to be verified when conducting research.
6. Do not silently resolve contradictions.

Document the contradiction when necessary.

---

2. Research-First Workflow

The project follows a strict research-first workflow.

Research
    ↓
Evidence Collection
    ↓
Investigation Report
    ↓
Verification / Audit
    ↓
Verified Findings
    ↓
MasterRef Audit
    ↓
MasterRef Incorporation
    ↓
Architecture Confirmation / Revision
    ↓
Application Development

Do not reverse this order merely for convenience. Architecture Confirmation / Revision occurs after Phase 26 and is not a new investigation phase or Phase 27.

Do not begin application development based on assumptions that could reasonably be resolved through investigation. Treat ARCHITECTURE.md as the current proposed architecture: verified findings may confirm, modify, replace, or invalidate it.

---

3. Phase Authority

"PLAN.md" is the authoritative investigation checklist.

It defines:

- all investigation phases
- phase order
- investigation topics
- required investigation areas
- Phase 25 MasterRef audit
- Phase 26 MasterRef incorporation

Do not:

- remove required investigation topics
- silently skip checklist items
- invent replacement phases
- declare a phase complete without auditing its checklist
- modify the investigation scope without documenting the change

If an investigation item cannot currently be verified, record it as unresolved rather than pretending it is complete.

---

4. Investigation Phases

The investigation program consists of:

Phase 0  — Investigation Infrastructure
Phase 1  — Rootless Store Deep Investigation
Phase 2  — Shevery ADB Module Compatibility
Phase 3  — ADB Module Lifecycle
Phase 4  — Porter
Phase 5  — Shizuku Compatibility
Phase 6  — Execution Abstraction
Phase 7  — Security
Phase 8  — WebUI
Phase 9  — Background Services
Phase 10 — Android Compatibility
Phase 11 — Storage
Phase 12 — Catalog / Sources
Phase 13 — Updates / Rollback
Phase 14 — Runtime Recovery
Phase 15 — Logging / Diagnostics
Phase 16 — UI / UX
Phase 17 — Testing
Phase 18 — Licensing / Provenance
Phase 19 — Performance
Phase 20 — Reliability
Phase 21 — Interoperability
Phase 22 — Architecture Stress Testing
Phase 23 — Future Backends
Phase 24 — Future Module Ecosystem
Phase 25 — MasterRef Expansion Audit
Phase 26 — MasterRef Incorporation

Phases 0–24 are primarily research.

Phase 25 is the MasterRef audit.

Phase 26 is controlled incorporation.

---

5. MasterRef Protection

Phases 0–24

Do not modify "MasterRef.md" as part of ordinary investigation work.

Investigation findings belong in investigation reports first.

Do not:

- rewrite MasterRef based on preliminary findings
- silently correct MasterRef
- replace conceptual sections with unverified claims
- remove existing material merely because new information was discovered
- treat a research hypothesis as established fact

Instead:

Finding
→ Investigation Report
→ Evidence
→ Verification
→ Phase 25 Audit
→ Phase 26 Incorporation

Phase 25

Audit "MasterRef.md" against the completed investigations.

Identify:

- missing information
- outdated information
- incorrect assumptions
- unsupported claims
- contradictions
- duplicate information
- missing source attribution
- missing compatibility information
- missing security information
- missing runtime information
- missing testing information

Phase 26

Only verified findings may be incorporated into "MasterRef.md".

Preserve clear distinctions between:

- verified facts
- implementation evidence
- documented behavior
- conceptual architecture
- proposed architecture
- assumptions
- unresolved questions
- future possibilities

---

6. Source-Code Investigation

When investigating project or dependency behavior, inspect the actual source code whenever available.

Prefer:

1. exact source implementation
2. official documentation
3. official release information
4. official issue/discussion information
5. authoritative technical documentation
6. reputable secondary sources
7. community discussions
8. search-result summaries only as leads

Do not treat a search-result snippet as authoritative evidence.

When possible, record:

- repository
- branch/tag
- commit
- file path
- relevant class/function
- version
- date
- source URL

---

7. External Research

Use available research tools when investigating external technologies.

External research may include:

- official documentation
- source repositories
- release notes
- API documentation
- Android documentation
- Porter documentation/source
- Shizuku documentation/source
- Shevery documentation/source
- Rootless Store source
- relevant Android platform documentation
- relevant security documentation

Prefer primary sources.

When sources disagree, investigate the disagreement rather than choosing whichever source is convenient.

---

8. GitHub Research

When GitHub access is available, use it directly for repository investigation.

Inspect:

- source files
- history
- releases
- tags
- branches
- pull requests
- issues
- documentation
- dependency declarations

Pin important findings to specific versions or commits whenever practical.

Do not assume the default branch represents every historical or current release.

---

9. Version Awareness

Version-sensitive claims must include version context whenever relevant.

At minimum, distinguish:

- Android version
- Porter version
- Shizuku version
- Shevery version
- Rootless Store revision
- dependency version

Do not state behavior discovered in an old version as though it were current behavior.

Historical behavior may still be useful, but label it as historical.

---

10. Android Compatibility

Android behavior must be treated as version-dependent.

When relevant, explicitly investigate:

Android 12
Android 13
Android 14
Android 15
Android 16
Android 17

Do not generalize behavior across Android versions without evidence.

Record:

- behavior
- affected versions
- evidence
- limitations
- confidence

---

11. Porter Rules

Porter is the primary intended privileged execution backend.

Do not invent Porter APIs.

Do not assume Porter behaves like:

- Shizuku
- root
- Magisk
- KernelSU
- an ordinary shell
- another privileged execution framework

Every important Porter capability must be classified as one of:

Verified
Documented but not source-verified
Inferred
Conceptual
Unknown

The exact Porter dependency/version must be established during Phase 4.

---

12. Shizuku Rules

Shizuku is intended as a compatibility backend.

Do not allow Shizuku-specific assumptions to define the entire execution architecture.

Investigate:

- service lifecycle
- Binder behavior
- UserService
- permissions
- process execution
- termination
- reconnection
- WebUI compatibility
- API-level differences

Preserve compatibility with the expected "window.Shizuku" interface where required by the module ecosystem.

Do not invent compatibility behavior.

---

13. ADB Module Compatibility

The project should investigate compatibility with the Shevery ADB Module ecosystem.

Do not assume that:

ADB Module = Rootless Plugin

Do not silently transform Shevery-specific semantics into Rootless Store plugin semantics.

Investigate the actual:

- package format
- metadata
- files
- installation behavior
- runtime environment
- WebUI behavior
- action behavior
- service behavior
- trust model
- execution semantics

Compatibility claims must be evidence-backed.

---

14. Architectural Boundaries

Maintain these conceptual distinctions:

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

Also maintain:

Rootless Plugin
    ≠
ADB Module
    ≠
CodeBrick

Do not collapse these concepts merely to simplify implementation.

---

15. Security

Security findings take priority over convenience.

Investigate security implications for:

- package authenticity
- package integrity
- ZIP extraction
- executable files
- shell execution
- argument handling
- environment variables
- working directories
- WebView
- JavaScript bridges
- trust levels
- backend selection
- privilege boundaries
- source trust
- update mechanisms
- rollback
- persistent state

Do not weaken security merely to make a feature easier to implement.

---

16. Unknowns

Unknown information must remain explicitly unknown.

Use labels such as:

Verified
Documented
Likely
Inferred
Proposed
Unknown
Needs testing
Needs source verification

Never convert:

"I think"

into:

"The system does"

without evidence.

---

17. Contradictions

When credible sources disagree:

1. Record both claims.
2. Identify the sources.
3. Determine their versions/dates.
4. Inspect source code where possible.
5. Determine whether the disagreement is historical or current.
6. Record the unresolved issue if it cannot be conclusively resolved.

Never silently discard contradictory evidence.

---

18. Investigation Reports

Each major phase should produce a comprehensive investigation document.

The standard report should contain, where applicable:

1. Scope
2. Questions to answer
3. Primary sources
4. Secondary sources
5. Existing implementation evidence
6. Compatibility findings
7. Security findings
8. Architecture implications
9. Unknowns
10. Contradictions
11. Verified conclusions
12. Recommendations for MasterRef expansion
13. Sources/references
14. Items requiring future investigation

Reports should also include relevant:

- version information
- dates
- evidence locations
- confidence classifications
- implementation-vs-documentation distinctions
- Android-version-specific findings
- Porter-version-specific findings
- Shizuku-version-specific findings

---

19. Investigation Directory

Use the existing project structure when available.

Do not create duplicate investigation systems merely because a preferred directory name is absent.

If an investigation directory does not already exist, a suitable structure is:

investigations/
├── README.md
├── INDEX.md
├── STATUS.md
├── phase-00/
├── phase-01/
├── phase-02/
├── ...
├── phase-24/
├── contradictions.md
├── unknowns.md
└── sources.md

Adapt to the existing project structure rather than blindly creating this layout.

---

20. Evidence Ledger

Important claims should have traceable evidence.

Where practical, record:

Claim
Source
Version / Commit
Evidence Location
Evidence Type
Confidence
Notes

Example evidence types:

Source Code
Official Documentation
Official Release
Official Issue
Official Discussion
Experiment
Device Test
Secondary Source
Inference
Conceptual Proposal

---

21. Experimental Verification

When source/documentation research cannot establish behavior, a controlled experiment may be appropriate.

Experimental findings must identify:

- device
- Android version
- app version
- dependency/backend version
- configuration
- exact procedure
- observed result
- limitations

A single successful experiment does not automatically establish universal behavior.

---

22. Testing vs Requirements

Do not confuse:

Required behavior

with:

Behavior successfully tested

Similarly:

Conceptual architecture

must not be presented as:

Implemented architecture

unless implementation evidence exists.

---

23. Implementation Restrictions During Investigation

During Phases 0–24:

Do not modify production source code merely to make an investigation easier.

Do not implement speculative features solely because the investigation suggests they might be useful.

If experimental code is necessary:

- keep it isolated
- clearly identify it as experimental
- do not present it as production architecture
- document what was tested
- preserve the original project state where practical

Implementation work should follow the research program unless explicitly authorized otherwise.

---

24. Do Not Frankenstein the Architecture

Do not merge unrelated systems wholesale.

Specifically, do not:

- merge the entire Shevery application into Rootless Store
- make ADB Modules pretend to be Rootless Plugins
- expand "PluginManifest" with arbitrary Shevery-specific fields
- make "service.sh" automatically behave like a traditional root daemon
- make Shizuku the core abstraction
- rename or break "window.Shizuku"
- use "/data/adb/modules" as the ADB Module storage model
- bypass runtime safety because a module has Full Trust
- turn the project into a Shizuku Manager clone
- copy code without a provenance/license audit
- create a Frankenstein merge of architectures

The goal is compatibility through appropriate boundaries, not wholesale duplication.

---

25. Backend Isolation

The execution architecture must allow backend-specific behavior to remain isolated.

Conceptually:

ADB Module
    ↓
Execution / Runtime Policy
    ↓
Backend Abstraction
    ├── Porter
    └── Shizuku

Do not allow:

ADB Module
    ↓
Shizuku-specific implementation
    ↓
Everything else

unless research proves that such coupling is unavoidable.

---

26. Fallback Safety

Never silently change privilege or execution semantics.

If backend fallback is investigated, determine:

- whether fallback is permitted
- whether the user must approve it
- whether trust changes
- whether capabilities change
- whether security properties change
- whether module behavior changes

A backend becoming unavailable must not automatically imply:

"Use whatever backend happens to work."

---

27. No Invented APIs

Never invent:

- Porter APIs
- Shizuku APIs
- Shevery APIs
- Rootless Store APIs
- Android APIs
- module-format fields
- environment variables
- compatibility guarantees

If an API or behavior cannot be verified, mark it as unknown.

---

28. Research Quality Standard

A high-quality investigation should answer:

What is it?
How does it work?
Where is it implemented?
What version does this apply to?
What does the official documentation say?
What does the source code actually do?
What happens on different Android versions?
What are the security implications?
What are the failure modes?
What happens when something dies or disappears?
What is compatible?
What is not compatible?
What remains unknown?
What does this mean for our architecture?
What belongs in MasterRef?

Do not stop at surface-level documentation.

---

29. Subagents

Subagents may be used when they materially improve research quality.

Possible roles include:

- source-code researcher
- documentation researcher
- compatibility analyst
- security analyst
- Android-version analyst
- Porter specialist
- Shizuku specialist
- lifecycle/recovery analyst
- architecture analyst
- licensing/provenance analyst
- contradiction auditor
- evidence auditor

Subagents must follow the same project rules.

Their findings must be reviewed and synthesized by the primary investigation process.

Do not assume a subagent's conclusion is automatically verified.

---

30. Parallel Research

Large phases may be divided into independent research areas.

Parallel research is acceptable when:

- the areas have clear boundaries
- findings can be independently verified
- the final report can reconcile contradictions
- duplicated work is minimized

Do not parallelize tightly coupled questions merely for speed if doing so increases the chance of contradictory conclusions.

---

31. Phase Completion

A phase may only be marked complete when:

- every applicable checklist item in "PLAN.md" has been addressed
- evidence has been collected
- important claims have been verified
- contradictions are documented
- unknowns are documented
- the investigation report is complete
- the report has been self-audited
- unresolved items are explicitly identified

"Investigated" does not mean "everything is known."

A phase may legitimately conclude with unresolved questions.

---

32. Status Tracking

Track investigation progress explicitly.

Recommended status values:

Not Started
In Progress
Research Complete
Awaiting Verification
Audited
Complete
Blocked

Do not mark a phase "Complete" simply because research activity has stopped.

---

33. MasterRef Standards

"MasterRef.md" should ultimately distinguish between:

Verified Facts

Supported by reliable evidence.

Implementation Evidence

Confirmed directly from source code or controlled testing.

Documented Behavior

Explicitly stated by authoritative documentation.

Conceptual Architecture

An architectural model being considered rather than something currently implemented.

Proposed Design

A project decision or future implementation direction.

Unknown

Not yet established.

Historical Behavior

Accurate for an earlier version but not necessarily current.

Future Possibility

A potential future direction that has not been adopted.

Never blur these categories.

---

34. Future Features

Do not implement or recommend a feature merely because:

- another project has it
- it is technically possible
- it sounds useful
- it would make the architecture more impressive

Future features must be investigated for:

- compatibility
- security
- maintenance
- architectural fit
- user value
- ecosystem impact

The investigation program explicitly separates current requirements from future possibilities.

---

35. Project Goal

The ultimate goal is not simply to reproduce another application.

The goal is to develop a well-understood architecture that can support:

Rootless Store
    +
Rootless Plugins
    +
ADB Modules
    +
CodeBricks
    ↓
Common Runtime Architecture
    ↓
Policy / Security / Trust
    ↓
Execution Abstraction
    ├── Porter — Primary
    └── Shizuku — Compatibility

while preserving clean boundaries between package formats, execution backends, privilege mechanisms, policy, trust, and runtime state.

---

36. Final Rule

When uncertain:

«Research before assuming. Verify before claiming. Document before incorporating.»

Prefer an explicitly documented unknown over an invented answer.

Prefer primary evidence over convenient assumptions.

Prefer clean architectural boundaries over shortcuts.

And preserve the distinction between:

What we know
What the sources say
What the code does
What we observed
What we propose
What we still do not know