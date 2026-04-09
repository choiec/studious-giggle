---
name: docs-sync
description: "Synchronize README, docs, setup steps, and examples with shipped behavior or configuration changes. Use for docs update, docs sync, README refresh, or operator guidance."
argument-hint: "Behavior change or docs scope"
---

# Docs Sync

## When to use
- Public behavior changes and the docs must reflect the shipped result
- Setup commands, flags, environment variables, or examples changed
- A release needs README, docs, or examples refreshed together

## Procedure
1. Identify what changed for users, operators, or contributors.
2. Update the nearest docs entry point first, then supporting docs and examples.
3. Make commands and snippets copy-pasteable whenever possible.
4. Call out any limitations, prerequisites, or still-unverified steps.
5. Use the [docs rules](./references/docs-rules.md) to keep edits consistent.

## Output
- Docs touched
- Why each update was necessary
- Remaining documentation gaps