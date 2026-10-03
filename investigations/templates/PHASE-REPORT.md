# ADB Modules App — Phase <NN> — Investigation Report

> Copy this template into `investigations/phase-NN/REPORT.md` and fill it in.
> Required sections (1–14) may be extended but not removed. See
> `investigations/METHOD.md` §11.
> Remove this blockquote when using the template.

| Field | Value |
| --- | --- |
| Phase | `<NN>` |
| Phase name | `<name exactly as in PLAN.md>` |
| Status | `Not Started \| In Progress \| Research Complete \| Awaiting Verification \| Audited \| Complete \| Blocked` |
| Started | `<YYYY-MM-DD>` |
| Last updated | `<YYYY-MM-DD>` |
| Depends on | `<phases>` |
| Feeds | `<phases>` |
| Leads to next phase | `<yes/no + why>` |

---

## 1. Scope

- Phase number and name (verbatim from `PLAN.md`).
- Checklist items in scope (verbatim from `PLAN.md`).
- Technologies involved.
- Relevant versions and repositories.
- Relevant Android versions.
- Known limitations of this scope.

## 2. Questions to Answer

Translate each checklist item into a concrete, evidence-answerable question.
Good: "Which code path creates the process, and what handle does it return?"
Weak: "Is the execution design sound?"

| # | From checklist item | Question | Answered? |
| --- | --- | --- | --- |

## 3. Primary Sources

Level 1 and Level 2 sources actually inspected for this phase, with the
version/commit/file that was read.

| Source | Level | Version / commit | Location inspected | Used for |
| --- | --- | --- | --- | --- |

## 4. Secondary Sources

Level 3–6 sources consulted. Level 5/6 entries are leads, not evidence.

| Source | Level | Version / date | Used for | Lead or evidence |
| --- | --- | --- | --- | --- |

## 5. Existing Implementation Evidence

Directly verified behaviour from source. Every statement here should be
`VERIFIED` from an L1 source, or explicitly marked otherwise.

| ID | Finding | Evidence location (`path:line`) | Classification | Confidence |
| --- | --- | --- | --- | --- |

## 6. Compatibility Findings

State compatibility as *"compatible with X under conditions A, B, C; behaviour D
unverified"* — never as bare "supports X".

| ID | Subject | Compatible with | Conditions | Not compatible / unverified | Version | Classification |
| --- | --- | --- | --- | --- | --- | --- |

## 7. Security Findings

Cover both intended behaviour and abuse cases. For each boundary ask: what is
trusted, what is untrusted, who controls the input, what privilege results,
can the input cross a boundary, can the operation be redirected, can state be
forged, what happens after failure.

| ID | Boundary | Risk | Evidence | Classification | Phase that owns follow-up |
| --- | --- | --- | --- | --- | --- |

## 8. Architecture Implications

Use the architecture labels from `investigations/METHOD.md` §3.2:
`EXISTING`, `DOCUMENTED-ARCH`, `CONCEPTUAL`, `PROPOSED`, `CONTRADICTED`.

| ID | Implication | Label | Evidence | Affected `ARCHITECTURE.md` section | Recommendation |
| --- | --- | --- | --- | --- | --- |

Keep Package / Backend / Privilege / Policy / Trust / Runtime State separate.

## 9. Unknowns

Every item here must also exist in `investigations/unknowns.md` with an ID.

| ID | Unknown | Why unresolved | What would resolve it | Priority |
| --- | --- | --- | --- | --- |

## 10. Contradictions

Every item here must also exist in `investigations/contradictions.md` with an ID.

| ID | Contradiction | Severity | Status | Interpretation adopted |
| --- | --- | --- | --- | --- |

## 11. Verified Conclusions

Only statements whose classification supports the wording. A `VERIFIED` claim
needs L1 evidence or two agreeing L2 sources.

| ID | Conclusion | Classification | Confidence | Version context |
| --- | --- | --- | --- | --- |

## 12. Recommendations for MasterRef Expansion

**Recommendations only. Do not edit `MasterRef.md` during Phases 0–24.**

| ID | Current MasterRef section | Recommended change | Reason | Intended classification in MasterRef |
| --- | --- | --- | --- | --- |

## 13. Sources / References

Full source records in the format of `investigations/sources.md`, including
repository, branch/tag/commit, file path, class/function, version, date, and
URL.

## 14. Items Requiring Future Investigation

| ID | Item | Why deferred | Owning phase |
| --- | --- | --- | --- |

---

## Appendix A — Checklist Coverage

Every `PLAN.md` checklist item for this phase, mapped to the section that
addresses it and the resulting outcome. An item is complete only when it has a
verified answer, documented answer, verified limitation, verified
incompatibility, verified historical behaviour, or an explicit unresolved
question.

| # | `PLAN.md` checklist item (verbatim) | Addressed in | Outcome | Classification |
| --- | --- | --- | --- | --- |

## Appendix B — Version Context

| Item | Version / commit | Date checked | Currency |
| --- | --- | --- | --- |
| This project (`Rootless Store`) | | | |
| Upstream Rootless Store | | | |
| Android (per version) | | | |
| Porter | | | |
| Shizuku (client API / server) | | | |
| Shevery | | | |
| Other dependencies | | | |

## Appendix C — Experiment Records

Complete only if controlled tests were performed; otherwise state
"No experiments performed in this phase" and why.

See `investigations/METHOD.md` §10 for required fields.

## Appendix D — Self-Audit

Performed before the phase is marked `Audited` or `Complete`.

- [ ] Every `PLAN.md` checklist item for this phase is addressed.
- [ ] Appendix A maps every checklist item to a section and an outcome.
- [ ] Primary sources reviewed and recorded.
- [ ] Implementation evidence inspected where it exists.
- [ ] Important claims carry version context.
- [ ] Android-, Porter- and Shizuku-version-specific findings follow
      `investigations/METHOD.md` §7.
- [ ] Documentation vs implementation distinguished.
- [ ] Conceptual vs verified architecture distinguished.
- [ ] Verified behaviour separated from assumptions.
- [ ] Contradictions documented in the central register.
- [ ] Unknowns documented in the central register.
- [ ] Compatibility findings documented with conditions.
- [ ] Security implications documented, including abuse cases.
- [ ] Architecture implications documented and conflicts marked `CONTRADICTED`.
- [ ] `MasterRef.md` not modified.
- [ ] No implementation work performed.
- [ ] Phase 0–26 structure unchanged.
- [ ] Remaining limitations explicitly recorded.

### Self-audit result

| Item | Finding |
| --- | --- |
| Gaps found | |
| Items still unresolved | |
| Justification if not `Complete` | |