---
name: web-benchmark
description: "Benchmark or sanity-check runtime performance for routes and flows using browser and shell tools. Use for benchmark, performance regression, or post-change health checks."
argument-hint: "URL, route, or performance concern"
---

# Web Benchmark

## When to use
- A route feels slower, heavier, or less stable after a change
- You need a quick performance baseline before or after a release
- You want a canary-style health check with runtime signals

## Procedure
1. Define the page, route, or flow to measure.
2. Capture basic runtime signals such as reachability, console errors, failed requests, and visible load behavior.
3. Record the highest-risk assets or interactions affecting user-perceived speed.
4. Translate observations into thresholds and follow-up measurements using the [benchmark checklist](./references/benchmark-checklist.md).

## Output
- Scope measured
- Signals observed
- Likely bottlenecks
- Regression risk
- Suggested thresholds and next measurements