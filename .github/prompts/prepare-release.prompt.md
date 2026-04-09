---
name: prepare-release
description: "Prepare merge-ready release handoff without self-merging or deploying"
agent: release-manager
argument-hint: "Paste the branch goal, release scope, or deploy handoff context"
tools: [read, search, execute, github/*]
---

Prepare this work for handoff to human review and CI or CD.

Rules:
- Do not assume self-merge or self-deploy.
- Treat this as a merge-readiness and deploy-readiness handoff.

Must include:
- Tests, lint, and typecheck status
- User-visible changes
- Breaking-change and migration check
- Docs update status
- Merge blockers
- Deployment prerequisites
- PR title
- PR summary
- Human or CI handoff steps

Scope:
${input:scope:Paste the branch goal or release scope}