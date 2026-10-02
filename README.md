<img src="./asset/banner/RootlessStore.png"></img>

<h1 align="center">Rootless Store</h1>

<div align="center">
    <a href="/asset/markdown/readme/README_zh-CN.md">本文中文版</a>&nbsp; · &nbsp;<a href="https://resilien-mobile.github.io/RootlessStore_WiKi/">Official Wiki</a>
</div>

<h4 align="center">An open-source, rootless plugin management and runtime platform for the Android ecosystem</h4>
<br>

<p>
    <img src="./asset/picture/HomeScreen.png" width="32%" />
    <img src="./asset/picture/PluginScreen.png" width="32%" />
    <img src="./asset/picture/CodeBrickScreen.png" width="32%" />
    <img src="./asset/picture/SettingScreen.png" width="32%" />
    <img src="./asset/picture/ExecuteScreen.png" width="32%" />
    <img src="./asset/picture/ShellScreen.png" width="32%" />
</p>

## Overview

Rootless Store is not just a tool for installing plugins.  
It aims to fill a long-missing gap in the Android ecosystem:

- making plugins discoverable, manageable, and executable
- making Sources organized, traceable, and maintainable
- allowing more users to access Android and Linux-like capabilities with a lower barrier to entry

It stands on three core principles:

- **Rootless / Low Barrier**
- **Open Source / Maintainable**
- **Decentralized / Extensible**

## Features

- Manage, execute, share, and install plugins from configurable sources
- Build quick automations with CodeBrick, then promote them into full plugins when they grow
- Run both one-shot scripts and daemon-style plugins designed for long-running workflows
- Choose the right execution context: limited app shell, Shizuku / ADB, or Root
- Receive remote notifications for plugin status changes and warning events
- Monitor device status, including Memory, Storage, Kernel, SELinux, Plugin state, and temperature
- Use a GUI-first workflow instead of wrestling with traditional TUI / TTY tooling

## Roadmap

- [x] Complete the core pages and documentation structure
- [x] Establish the basic interfaces for Source and Market
- [x] Build the initial runtime plugin development documentation
- [x] Improve the Market and plugin detail UI
- [x] Support for plugin kill notification
- [x] Third-party Notification Pushing
- [x] More personalized plugins
- [x] Daemon fully supports
- [x] Plugin status transition cleaning
- [x] Support Preference Panel
- [x] Support private sources, invisible sources, and paid sources
- [x] Shell code snippet support
- [ ] Base64 CodeBrick Token support
- [x] Magisk Plugin Compatibility Layer
- [x] Quick launch tile of the Android Control Center
- [x] Host status panel， More Expressive
- [x] Add test matrix (not yet completed)
- [x] More objective error cause
- [ ] Certificate signature tamper-proof verification chain
- [ ] A more intuitive demonstration of plugin execution methods
- [ ] Improve filtering, state feedback, and permission boundaries
- [ ] Publish to F-Droid

## Why I Built Rootless Store

Because I have always believed that the Android ecosystem does not lack capability.  
What it lacks is a proper entry point that can bring people together.

Shizuku, Magisk, KernelSU, ADB, Shell, Root...  
These tools are powerful, but they have long remained trapped in fragmented information, scattered scripts, and high-barrier command-line workflows.  
There are many capable people, but very few ecosystems that are truly approachable.

That is why Rootless Store exists.  
It is meant to reorganize these capabilities.  
Not to turn technology into a black box,  
but to return understanding and access to more people.

## My Belief

I have always believed in one sentence:

> **Truth will eventually tear through lies, and technology should belong to everyone.**

From Animora to Rootless Store,  
what I truly want to build is not just “more features,” but:

- clearer code
- friendlier documentation
- capabilities that do not belong only to a few
- an open-source reality that people can actually participate in

## What Rootless Store Wants to Say

- The Android ecosystem deserves its own plugin infrastructure
- Technology should not remain in the hands of only a few people
- Open source is not only about exposing code, but also about exposing understanding
- Capability should not become a barrier; it should become a bridge

## Final Words

What Rootless Store wants to do is simple:

**to place the final missing piece into the Android ecosystem.**

Not to create new barriers,  
but to reorganize the capabilities that already exist  
in a clearer, more open, and more approachable way.

---

### For those
**who still believe in openness, still dare to explore, and still choose hope**

---

---

RedSigilDelta Research & Development

This repository is a fork of "Resilien-Mobile/RootlessStore" (https://github.com/Resilien-Mobile/RootlessStore).

The upstream Rootless Store project remains the foundation of this repository. This fork is also being used as a research and development environment for investigating ADB-based Android modules, execution backends, compatibility, security, and the architecture required to support a broader Android module ecosystem.

Investigation-First Approach

This project follows an investigation-first, implementation-second approach.

The investigation program is being completed before development of the new application begins. The goal is to establish a thorough, evidence-based understanding of the existing ecosystem and determine the architecture and requirements needed for the application.

The investigation covers:

- Rootless Store architecture and implementation
- ADB module compatibility and lifecycle
- Porter
- Shizuku
- Execution backend abstraction
- Security and trust
- WebUI
- Background services
- Android compatibility
- Storage and persistence
- Module catalogs and sources
- Updates and rollback
- Runtime recovery
- Logging and diagnostics
- UI/UX
- Testing
- Licensing and provenance
- Performance and reliability
- Interoperability
- Architecture stress testing
- Future execution backends
- Future module ecosystem capabilities

Investigation → Application

The project is intentionally divided into two major stages:

┌─────────────────────────────┐
│       INVESTIGATION         │
├─────────────────────────────┤
│ Research                    │
│ Evidence                    │
│ Verification               │
│ Architecture Analysis      │
│ Investigation Reports       │
│ MasterRef Audit             │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│     APPLICATION DEVELOPMENT │
├─────────────────────────────┤
│ Architecture & Design       │
│ Implementation              │
│ Testing                     │
│ Device Validation           │
│ Release                     │
└─────────────────────────────┘

The application will be built after the investigation program has been completed and the resulting findings have been reviewed and incorporated into the project's verified reference material.

This approach is intended to prevent major architectural decisions from being made prematurely and to ensure implementation is based on verified knowledge rather than assumptions.

Investigation Program

The project uses a structured 27-phase investigation program.

Phase| Investigation
0| Investigation Infrastructure
1| Rootless Store Deep Investigation
2| Shevery ADB Module Compatibility Investigation
3| ADB Module Lifecycle
4| Porter Investigation
5| Shizuku Compatibility Investigation
6| Execution Abstraction Investigation
7| Security Investigation
8| WebUI Investigation
9| Background Services Investigation
10| Android Compatibility Investigation
11| Storage Investigation
12| Catalog / Source Investigation
13| Updates and Rollback
14| Runtime Recovery Investigation
15| Logging / Diagnostics
16| UI / UX Investigation
17| Testing Investigation
18| Licensing / Provenance Investigation
19| Performance Investigation
20| Reliability Investigation
21| Interoperability Investigation
22| Architecture Stress Testing
23| Future Backend Investigation
24| Future Module Ecosystem Investigation
25| MasterRef Expansion Audit
26| MasterRef Incorporation

Phases 0–24 focus on research and investigation.

Phase 25 audits the completed findings against "MasterRef.md".

Phase 26 incorporates verified findings into "MasterRef.md".

Application development begins after the investigation program is complete.

Investigation Method

The investigation follows this general workflow:

Research
    ↓
Evidence Collection
    ↓
Investigation Report
    ↓
Verification & Audit
    ↓
Verified Findings
    ↓
MasterRef Incorporation
    ↓
Application Architecture
    ↓
Implementation

The investigation distinguishes between:

- Verified implementation behavior
- Primary-source documentation
- Secondary-source information
- Implementation evidence
- Conceptual architecture
- Inference
- Assumptions
- Proposals
- Unknowns
- Contradictions

Important implementation claims are verified against source code where possible.

Version-specific differences are recorded when relevant, including Android, Porter, Shizuku, Rootless Store, and module ecosystem versions.

Project Documentation

Document| Purpose
""AGENTS.md"" (AGENTS.md)| Agent behavior and project rules
""PLAN.md"" (PLAN.md)| Authoritative 27-phase investigation scope and order
""INVESTIGATION_METHOD.md"" (INVESTIGATION_METHOD.md)| Investigation methodology and evidence standards
""INVESTIGATION.md"" (INVESTIGATION.md)| Original pre-MasterRef investigation and historical research
""ARCHITECTURE.md"" (ARCHITECTURE.md)| Current architectural direction
""MasterRef.md"" (MasterRef.md)| Synthesized project knowledge and reference
""README.md"" (README.md)| Project overview and user-facing information

Investigation artifacts are maintained in the ""investigations/"" (investigations/) directory.

MasterRef

"MasterRef.md" is the project's consolidated technical reference.

During the normal investigation phases, it is treated as protected reference material rather than a working scratch document.

The intended information flow is:

Investigation
    ↓
Evidence
    ↓
Verification
    ↓
Audit
    ↓
Verified Finding
    ↓
MasterRef
    ↓
Application Architecture
    ↓
Implementation

This helps prevent assumptions or unverified claims from being silently incorporated into the project's primary reference.

Current Status

Phase 0 — Investigation Infrastructure: Complete

Phase 0 establishes the infrastructure required for the remaining investigation program.

The next phase is:

Phase 1 — Rootless Store Deep Investigation

Relationship to Upstream

This repository remains a fork of Rootless Store and preserves the upstream project's work and identity.

The research and development material described above is specific to this fork and does not represent the upstream project's plans, decisions, or implementation.

Upstream: "Resilien-Mobile/RootlessStore" (https://github.com/Resilien-Mobile/RootlessStore)

Fork: "RedSigilDelta/RootlessStore" (https://github.com/RedSigilDelta/RootlessStore)

The purpose of this fork-specific work is to investigate possibilities, document findings, experiment with architecture, and ultimately use the resulting verified knowledge to build the application.

Future Application

After the investigation program and MasterRef incorporation are complete, the project will transition from research into application development.

The resulting findings will be used to define the application's:

- Architecture
- Execution model
- Module compatibility layer
- Backend abstraction
- Security model
- Storage model
- Lifecycle management
- WebUI support
- Update and rollback system
- Recovery mechanisms
- Testing strategy
- Android compatibility strategy
- Future extensibility

The exact architecture and feature set will be determined by the verified findings rather than being prematurely fixed during the research stage.

Development Philosophy

The research and eventual development process prioritizes:

- Evidence over assumptions
- Verification over speculation
- Clear separation of facts and proposals
- Compatibility over premature implementation
- Explicit security boundaries
- Recoverable runtime behavior
- Backend independence
- Interoperability
- Maintainability
- Extensibility
- Thorough documentation

Potential functionality is investigated before being treated as an implementation requirement.

---
### License

This project follows the applicable upstream and third-party licensing requirements.

See [`LICENSE`](LICENSE) for the repository's license information. Licensing and provenance are also covered by **Phase 18 — Licensing / Provenance Investigation**.