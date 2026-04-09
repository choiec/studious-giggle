---
name: ship
description: "Prepare a branch for PR or release with validation, docs, and follow-up notes"
agent: release-manager
argument-hint: "Paste the branch goal or release scope"
tools: [read, search, execute, github/*]
---

Prepare this work for shipping.

Must include:
- Tests, lint, and typecheck status
- User-visible changes
- Breaking-change check
- Docs update check
- PR title
- PR summary
- Follow-up tasks

Scope:
${input:scope:Paste the branch goal or release scope}