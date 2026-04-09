---
description: "Use when adding or updating tests, regression coverage, smoke checks, QA automation, or validation commands. Covers durable assertions, coverage priorities, and honest reporting."
name: "Testing Guidance"
---

# Testing Guidance

- Prefer tests that reflect user-visible or contract-visible behavior over internal implementation details.
- Add a regression test for any bug fix when a stable reproduction path exists.
- Keep test setup minimal and readable; avoid brittle timing assumptions and snapshot sprawl.
- Report exact commands run and whether they passed, failed, or could not run.
- If coverage is missing because the environment is incomplete, say so clearly and list the highest-value next test.