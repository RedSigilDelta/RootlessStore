# ADB Modules App — Phase <NN> — Evidence Ledger

> Copy into `investigations/phase-NN/evidence.md`. See
> `investigations/METHOD.md` §12.
> Remove this blockquote when using the template.

Phase: `<NN> — <name>`
Maintained by: `<agent / session>`
Last updated: `<YYYY-MM-DD>`

The authority for a claim is this ledger. The central `investigations/sources.md`
register is the authority for the *source*, not for the claim.

**Field definitions**

- **Claim** — the specific assertion, written so it could be falsified.
- **Level** — reliability of the source, `METHOD.md` §2.
- **Version / Commit** — the exact version or commit the evidence came from.
- **Evidence location** — `path:line`, commit sha, doc page/section, or
  experiment identifier.
- **Evidence type** — `Source Code`, `Official Documentation`,
  `Official Release`, `Official Issue`, `Official Discussion`, `Experiment`,
  `Device Test`, `Secondary Source`, `Inference`, `Conceptual Proposal`.
- **Classification** — `VERIFIED`, `DOCUMENTED`, `OBSERVED`, `INFERRED`,
  `PROPOSED`, `LEAD`, `UNKNOWN` (`METHOD.md` §3).
- **Currency** — `CURRENT`, `HISTORICAL`, `VERSION-SPECIFIC`,
  `UNKNOWN-CURRENCY` (`METHOD.md` §6.2).
- **Confidence** — `High`, `Medium`, `Low`, `None` (`METHOD.md` §4).

---

## A. Claims supported by primary implementation evidence (L1)

| ID | Claim | Level | Version / Commit | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |

## B. Claims supported by official documentation (L2)

| ID | Claim | Level | Version | Evidence location | Evidence type | Classification | Currency | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |

## C. Claims from official project discussion (L3)

| ID | Claim | Level | Version / date | Evidence location | Evidence type | Classification | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |

## D. Secondary sources (L4)

| ID | Claim | Level | Version / date | Evidence location | Classification | Confidence | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |

## E. Community sources and search results (L5/L6) — leads only

| ID | Lead | Level | Source | Date seen | Promoted to? | Notes |
| --- | --- | --- | --- | --- | --- | --- |

## F. Inferences

| ID | Inference | Derived from (evidence IDs) | Classification | Confidence | What would confirm or refute it |
| --- | --- | --- | --- | --- | --- | --- |

## G. Proposals (not facts)

| ID | Proposal | Rationale | Classification | Decision owner |
| --- | --- | --- | --- | --- |

## H. Unknowns raised

Cross-reference `investigations/unknowns.md`.

| Unknown ID | Short description | Priority | Owning phase |
| --- | --- | --- | --- |

## I. Contradictions raised

Cross-reference `investigations/contradictions.md`.

| Contradiction ID | Short description | Severity | Status |
| --- | --- | --- | --- |

## J. Experiments performed

| ID | Experiment | Device / Android | Backend + version | Result | Limitations |
| --- | --- | --- | --- | --- | --- |

Complete fields per `investigations/METHOD.md` §10.

## K. Retired or superseded entries

Never delete an entry. Move it here with the superseding evidence.

| Original ID | Original claim | Superseded by | New classification | Date | Reason |
| --- | --- | --- | --- | --- | --- |