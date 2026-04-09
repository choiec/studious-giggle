---
name: plan-devex-review
description: "Review developer-facing UX such as docs, onboarding, CLI, API, SDK, and upgrade path before implementation"
agent: devex-review
argument-hint: "Paste the developer-facing brief, API plan, or onboarding flow"
tools: [read, search, execute]
---

Review the plan from a developer experience perspective.

Output exactly these sections:
1. Target developer personas
2. Time to hello world estimate
3. Friction points
4. Docs, CLI, API, or naming gaps
5. Upgrade and migration risks
6. Minimal improvements before build

Plan or brief:
${input:plan:Paste the developer-facing plan or brief}