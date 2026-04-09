---
name: autoplan
description: "Run a combined review pipeline that merges strategy, design, engineering, and developer experience guidance into one reviewed plan. Use for autoplan, run all reviews, or reviewed plan generation."
argument-hint: "Approved brief or draft plan"
---

# Autoplan

## When to use
- A feature or plan needs more than one review lens before implementation
- You want one reviewed plan instead of several disconnected review outputs
- The user asks for an autoplan, combined review, or full planning gauntlet

## Procedure
1. Confirm whether the request already has a clear problem statement or needs office-hours style reframing.
2. Decide which tracks apply:
   - CEO review for scope, ambition, wedge, or strategic tradeoffs
   - design review for user-facing UI or interaction work
   - engineering review for architecture, failure modes, and tests
   - developer experience review for APIs, CLIs, SDKs, docs, or onboarding
3. Run the applicable tracks and preserve disagreements instead of flattening them away.
4. Consolidate the outcomes into one implementation-ready plan.
5. Save or update a plan artifact using the [review principles](./references/review-principles.md) and the repo templates under plans/templates/.

## Output
- Applicable review tracks
- Consolidated findings by track
- Conflicts or taste decisions
- Recommended reviewed plan
- Validation gates
- Remaining open questions