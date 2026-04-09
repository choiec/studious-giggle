---
name: qa
description: "Run runtime QA, smoke tests, and regression planning against a local or staging URL"
agent: qa-lead
argument-hint: "Enter the local or staging URL"
tools: [read, search, execute, playwright/*]
---

Use repository context, app runtime behavior, and available QA tools to perform a focused QA pass.

Output:
1. User flows tested
2. Failures found
3. Likely root causes
4. Regression tests to add
5. Release risk summary

Target URL:
${input:url:Enter local or staging URL}