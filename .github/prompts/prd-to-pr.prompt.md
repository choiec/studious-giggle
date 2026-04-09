---
name: prd-to-pr
description: "Turn a one-line PRD into a reviewed plan, code changes, tests, and a draft pull request when GitHub-backed automation is available"
agent: delivery-orchestrator
argument-hint: "Paste the one-line PRD or scoped delivery request"
---

Take the supplied PRD and move it as far through this repository's delivery flow as the available tooling allows.

Rules:
- Start from a compact brief, then continue into planning and execution.
- Prefer a remote-backed pull request using GitHub Copilot coding agent or the GitHub MCP pull-request flow when available.
- If remote PR creation is unavailable, still implement locally, validate, and stop at a PR-ready handoff.
- If remote PR creation is used, keep local git state coherent with the remote handoff: do not leave equivalent implementation changes as uncommitted edits on local `main`, and call out any unavoidable divergence explicitly.
- Keep the final approval and merge human-owned.

Must include:
- brief
- reviewed plan
- implementation summary
- validation commands and outcomes
- git state handoff summary
- PR link or exact blocker

PRD:
${input:prd:Paste the one-line PRD}
