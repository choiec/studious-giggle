---
name: webapp-qa
description: "Perform focused web application QA, smoke tests, browser validation, failure capture, and regression planning. Use for qa, smoke test, browser test, or staging validation."
argument-hint: "Target URL or environment"
---

# Webapp QA

## When to use
- Validate local or staging web flows before review or release
- Capture browser-visible failures with exact reproduction steps
- Recommend regression coverage after manual or MCP-assisted testing

## Procedure
1. Identify the highest-risk user flows from the request, routes, docs, and recent changes.
2. Validate critical paths first:
   - authentication and session state
   - create, edit, and delete flows
   - navigation and routing
   - form validation and error states
3. For each failure, capture:
   - exact failing step
   - expected versus actual behavior
   - likely source module or route
4. Recommend regression coverage across unit, integration, and end-to-end layers.
5. Use the supporting assets when helpful:
   - [QA checklist](./references/qa-checklist.md)
   - [Checklist script](./scripts/test-checklist.sh)

## Output
- Flows tested
- Failures found
- Likely root causes
- Regression tests to add
- Release risk summary