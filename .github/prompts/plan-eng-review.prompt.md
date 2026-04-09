---
name: plan-eng-review
description: "Produce an engineering plan with architecture, tests, failure modes, and rollback notes"
agent: eng-review
argument-hint: "Paste the approved brief or technical request"
tools: [read, search, execute, todo]
---

Use the current repository context and the provided brief to produce:

- Architecture summary
- Files or modules to touch
- Implementation sequence
- Edge cases and failure paths
- Tests to add or update
- Exact validation commands
- Rollback notes

Brief:
${input:brief:Paste the approved product or technical brief}