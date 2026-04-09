---
name: document-release
description: "Update README, docs, examples, and operator guidance to match shipped behavior"
agent: docs-engineer
argument-hint: "Paste the change summary, branch goal, or release scope"
tools: [read, search, edit]
---

Synchronize docs with the shipped or planned behavior.

Must include:
- Docs that need updates
- Exact copy or section changes needed
- Setup, config, or command changes
- Remaining documentation gaps

Change summary:
${input:scope:Paste the shipped change or release scope}