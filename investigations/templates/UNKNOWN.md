# ADB Modules App — Unknown Record

> Entry format for `investigations/unknowns.md`. See
> `investigations/METHOD.md` §13.
> Append entries to the central register; do not overwrite earlier entries.

## Unknown <U-NNN> — <short question>

| Field | Value |
| --- | --- |
| Phase raised | `<NN>` |
| Raised on | `<YYYY-MM-DD>` |
| Priority | `Blocking \| High \| Medium \| Low` |
| Status | `Open \| Investigating \| Resolved \| Accepted-as-unknown` |
| Owning phase | `<NN>` |
| Blocks | `<architecture decisions / phase gates / none>` |
| Last updated | `<YYYY-MM-DD>` |

### Unknown

<The question, stated precisely. "Is X correct?" is not a good unknown. "Does
the Porter client library expose a process handle for a detached process, and
at which version?" is.>

### Why unknown

<What kind of evidence is missing: no source exists, source is inaccessible,
behaviour is device-dependent, version not established, requires an experiment
that has not been run, blocked on a decision, etc.>

### Evidence already checked

| Source | Level | Version | What was looked for | Outcome |
| --- | --- | --- | --- | --- |

Explicitly record what was searched *and not found*. This is what prevents the
same ground being covered twice.

### What would resolve it

- The exact source, version, and file to read; or
- The exact experiment to run, with its device matrix; or
- The exact decision or dependency required.

### Current best hypothesis (optional, clearly labelled)

<Optional. If recorded, this is an `INFERRED` statement capped at `Medium`
confidence and must never be cited as a finding. Leave blank if none.>

### Cross-phase dependencies

<Which later phases depend on this unknown, and what they must not assume while
it is open.>

### Resolution

<Filled when resolved.>

| Field | Value |
| --- | --- |
| Resolved on | `<YYYY-MM-DD>` |
| Resolved by | `<evidence ID / source / experiment>` |
| Resolution | `<verified answer>` |
| Affected phases | |
| MasterRef impact | `<audit item for Phase 25, or none>` |

---

## Notes for maintainers

1. An unknown is a legitimate investigation result. Never replace one with an
   assumption to make a report look complete.
2. Unknowns survive phase completion. A phase may finish with open unknowns.
3. Closing an unknown records who closed it, with which evidence, and on what
   date.
4. `Accepted-as-unknown` means the question is deliberately out of scope and
   must be labelled as an unknown wherever it is referenced.
5. `Blocking` unknowns must be visible in `investigations/STATUS.md` and must
   not be silently worked around in later phases.