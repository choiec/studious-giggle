---
name: investigate
description: "Perform systematic root-cause debugging before code fixes. Use for broken behavior, regressions, flaky tests, and unexplained errors."
argument-hint: "Bug report, logs, or failing path"
---

# Investigate

## When to use
- The cause of a bug is unknown
- The same issue has already resisted one or more speculative fixes
- You need a disciplined debugging write-up before changing code

## Procedure
1. Restate the symptom, affected boundary, and expected behavior.
2. Reproduce or validate the signal using the narrowest possible path.
3. Trace the data flow and isolate likely failure boundaries.
4. Test hypotheses one by one and record what each result rules in or out.
5. Stop when the likely root cause is supported by evidence and write the safest next step using the [debug log template](./references/debug-log-template.md).

## Output
- Symptom summary
- Reproduction or observed signal
- Hypotheses tested
- Evidence collected
- Likely root cause
- Recommended next step