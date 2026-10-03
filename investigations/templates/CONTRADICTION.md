# ADB Modules App — Contradiction Record

> Entry format for `investigations/contradictions.md`. See
> `investigations/METHOD.md` §8.
> Append entries to the central register; do not overwrite earlier entries.

## Contradiction <C-NNN> — <short title>

| Field | Value |
| --- | --- |
| Phase raised | `<NN>` |
| Raised on | `<YYYY-MM-DD>` |
| Severity | `Blocking \| Material \| Minor` |
| Current status | `Unresolved \| Partially resolved \| Resolved` |
| Last updated | `<YYYY-MM-DD>` |
| Owning phase | `<NN>` |
| Blocks | `<architecture decisions / phase gates / none>` |

### Claim A

<The first claim, stated precisely enough to be checked.>

| Field | Value |
| --- | --- |
| Source | |
| Reliability level | `L1 … L6` |
| Version | |
| Date | |
| Evidence location | |
| Currency | `CURRENT / HISTORICAL / VERSION-SPECIFIC / UNKNOWN-CURRENCY` |

### Claim B

<The conflicting claim.>

| Field | Value |
| --- | --- |
| Source | |
| Reliability level | `L1 … L6` |
| Version | |
| Date | |
| Evidence location | |
| Currency | `CURRENT / HISTORICAL / VERSION-SPECIFIC / UNKNOWN-CURRENCY` |

### Version difference

<What version/commit/date separates the claims? If none, state that
explicitly.>

### Possible explanation

<Historical change, different environment, different artifact, documentation
drift, or "no explanation established".>

### Investigation performed

- [ ] Source code inspected
- [ ] Version history compared
- [ ] Issues/discussions reviewed
- [ ] Controlled experiment performed
- [ ] Cross-source comparison

<Describe what was actually done.>

### Resolution

`Resolved | Partially resolved | Unresolved`

<If resolved, explain what evidence settled it. If partially resolved, state
exactly what remains contested.>

### Interpretation adopted

<The reading this project will use in the meantime, and why. Adopting an
interpretation for working purposes is not the same as resolving the
contradiction.>

### Residual uncertainty

<What would still falsify the adopted interpretation.>

### Impact

- Architecture: <which `ARCHITECTURE.md` sections or invariants are affected>
- Compatibility: <which compatibility claims are affected>
- Security: <which security conclusions are affected>
- Testing: <what must be tested to settle it>

### Next action

| Action | Owning phase | Target |
| --- | --- | --- |

---

## Notes for maintainers

1. Never resolve a contradiction by deleting the losing claim.
2. Never choose the newest-looking source without investigation.
3. A contradiction becomes `Resolved` only with recorded evidence, not with a
   decision to stop discussing it.
4. An unresolved `Blocking` contradiction must be visible in
   `investigations/STATUS.md`.
5. Phase 25 must treat every `Unresolved` contradiction as a MasterRef audit
   item; Phase 26 must not incorporate either side as settled fact.