---
name: prd-to-pr
description: "Run a one-line PRD through brief creation, planning, implementation, validation, draft PR creation, and PR review follow-up. Use for prd-to-pr, one-line PRD delivery, draft PR automation, or review follow-up setup."
argument-hint: "One-line PRD or scoped change request"
---

# PRD To PR

## When to use
- A user gives a one-line PRD and expects a reviewed implementation path instead of a chat-only answer
- You want to move from request to code, tests, and a draft pull request with minimal human handoff
- You need to combine local Copilot work with GitHub Copilot coding agent or GitHub MCP pull-request creation

## Procedure
1. Turn the request into a compact brief with pain, wedge, scope, non-goals, success metric, and major risks.
2. Produce a reviewed plan before implementation when the request is not trivially small.
3. Save durable brief and plan artifacts under plans/ when the scope is medium or large.
4. Ensure GitHub cloud-agent prerequisites exist on the default branch:
   - .github/workflows/copilot-setup-steps.yml
   - repository instructions and any required custom agent
   - CI workflows for lint, tests, and security checks
5. Choose an execution path:
   - VS Code or MCP path: use the prd-to-pr prompt and prefer remote pull-request creation with Copilot
   - GitHub Actions path: use the PRD To PR workflow to create an issue assigned to Copilot coding agent
6. Before closing a remote-backed handoff, inspect git state and reconcile it:
   - report the checked-out branch, local HEAD, remote base ref, PR branch, and worktree status
   - do not leave local `main` with uncommitted feature edits that were pushed to another branch
   - if remote tooling created the PR branch outside the local checkout, either switch or create the matching local branch, clean or stash equivalent edits, or stop before remote PR creation and hand off clearly
   - treat `git:` tabs opened from `refs/remotes/origin/*` as snapshots and compare against the workspace file before declaring state
7. Validate changes with repository gates:
   - ./gradlew ktlintCheck
   - ./gradlew test
8. Keep merge human-owned. Use the review follow-up workflow or batched @copilot review comments to iterate on the same pull request.

## Output
- Brief
- Reviewed plan
- Execution path chosen
- Validation status
- Git state handoff summary
- Draft pull request or blocker summary
