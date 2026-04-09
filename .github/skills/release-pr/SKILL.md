---
name: release-pr
description: "Prepare a release-ready PR summary with validation status, docs sync, breaking-change checks, and follow-up tasks. Use for ship, release, merge readiness, or PR prep."
argument-hint: "Branch goal or release scope"
---

# Release PR

## When to use
- Prepare a branch for pull request creation or final merge review
- Summarize validation, risk, and user-visible changes in a reusable format

## Procedure
1. Collect the exact validation commands that ran and their outcomes.
2. Summarize user-visible behavior, setup, or config changes.
3. Check for breaking changes, migration needs, and docs updates.
4. Draft the PR title, body, and follow-up tasks.
5. Use the [PR template](./references/pr-template.md) to keep output consistent.

## Required output
- Validation commands run
- Results
- User-visible change summary
- Breaking-change statement
- Docs updated status
- PR title
- PR body
- Follow-up issues or tasks