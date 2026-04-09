---
name: benchmark
description: "Measure and analyze runtime performance or likely regressions for a target route or URL"
agent: performance-review
argument-hint: "Enter the URL, route, or performance concern"
tools: [read, search, execute, playwright/*]
---

Assess the performance profile of the target scope.

Output exactly these sections:
1. Scope measured
2. Signals observed
3. Likely bottlenecks
4. Regression risk
5. Suggested thresholds and next measurements

Target:
${input:target:Enter the URL, route, or performance concern}