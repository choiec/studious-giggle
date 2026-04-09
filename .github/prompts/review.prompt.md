---
name: review
description: "Review the current branch or workspace changes for correctness, completeness, and risk"
agent: reviewer
argument-hint: "Optional review scope or diff summary"
tools: [read, search, execute]
---

Review the current workspace changes.

Output exactly these sections:
1. High-risk bugs
2. Medium-risk issues
3. Completeness gaps
4. Missing tests
5. Suggested minimal fixes
6. Safe-to-merge verdict: yes / no / conditional

Optional scope:
${input:scope:Paste a diff summary, branch goal, or leave blank}