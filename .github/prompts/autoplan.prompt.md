---
name: autoplan
description: "Run a combined review pipeline and produce one reviewed implementation plan"
agent: plan-pipeline
argument-hint: "Paste the brief, feature request, or draft plan"
tools: [read, search, agent, todo]
---

Use the current repository context and the supplied brief to produce a fully reviewed plan.

Rules:
- Do not implement code.
- If the brief is still vague, say so and include an office-hours-style reframing section before the reviews.
- Run only the review tracks that actually apply.

Output exactly these sections:
1. Applicable review tracks
2. Consolidated findings by track
3. Conflicts or taste decisions
4. Recommended reviewed plan
5. Validation gates
6. Remaining open questions

Brief or draft plan:
${input:brief:Paste the approved brief or current plan draft}