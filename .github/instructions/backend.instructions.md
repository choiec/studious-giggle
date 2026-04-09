---
description: "Use when modifying backend services, APIs, data models, persistence, auth, queues, or server-side integrations. Covers contracts, validation, failure paths, and rollout safety."
name: "Backend Guidance"
---

# Backend Guidance

- Trace request, response, and data model changes end to end before editing code.
- Preserve existing contracts unless the task explicitly includes a versioned or coordinated breaking change.
- Validate inputs early and make failure modes explicit with actionable errors.
- Minimize blast radius by changing the narrowest module boundary that solves the problem.
- When behavior changes, add or update tests for success paths, validation failures, and at least one edge case.