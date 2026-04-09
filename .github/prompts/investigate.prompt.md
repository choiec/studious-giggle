---
name: investigate
description: "Perform structured root-cause debugging before attempting a fix"
agent: investigate
argument-hint: "Paste the bug, error, stack trace, or failing behavior"
tools: [read, search, execute, todo]
---

Investigate the issue systematically.

Rules:
- Do not patch code yet.
- Prefer evidence, traces, and narrowed hypotheses over broad guesses.

Output exactly these sections:
1. Symptom summary
2. Reproduction or observed signal
3. Hypotheses tested
4. Evidence collected
5. Likely root cause
6. Recommended next step

Issue:
${input:issue:Paste the failing behavior, logs, or bug report}