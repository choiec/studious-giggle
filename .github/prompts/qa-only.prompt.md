---
name: qa-only
description: "Run runtime QA and report only without making code changes"
agent: qa-lead
argument-hint: "Enter the local or staging URL"
tools: [read, search, execute, playwright/*]
---

Use repository context, app runtime behavior, and available QA tools to perform a focused QA pass.

Do not edit code. Report only.

Output:
1. User flows tested
2. Failures found
3. Exact reproduction steps
4. Likely root causes
5. Recommended fixes and regression tests
6. Release risk summary

Target URL:
${input:url:Enter local or staging URL}