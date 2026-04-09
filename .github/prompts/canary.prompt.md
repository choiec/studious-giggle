---
name: canary
description: "Run a post-change health check for a local, staging, or production URL"
agent: performance-review
argument-hint: "Enter the deployed URL and what changed"
tools: [read, search, execute, playwright/*]
---

Perform a focused post-change health check.

Output exactly these sections:
1. Health checks run
2. Console, network, or runtime issues found
3. Suspected regressions
4. Rollback or mitigation recommendation
5. Follow-up monitoring checks

Target and change summary:
${input:target:Paste the URL and a short summary of what changed}