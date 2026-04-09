# Project-wide Copilot Instructions

This repository uses a role-based Copilot workflow inspired by gstack.

## Operating mode
- For medium or large tasks, produce a short plan before making edits.
- Prefer prompt files for command-like entry points, custom agents for role specialization, and skills for repeatable procedures.
- Use /fleet when the work can be split into independent tracks with clear ownership.
- Default to Mode C lite for medium and large implementation work: parallel module tracks plus one integration track.
- Keep shared files owned by the integration track only: build.gradle.kts, compose.yaml, src/main/resources/application*.yml, src/main/resources/db/migration/**, README.md, AGENTS.md, and .github/copilot-instructions.md.
- Run /review, /qa or /qa-only, /cso, /benchmark, /prepare-release, and /document-release after integration rather than as separate coding tracks.
- If a task adds Flyway migrations or shared runtime properties, keep that work in the integration track even when domain code is split across /fleet lanes.

## Workflow routing
- Route new ideas, brainstorming, or worth-building questions to /office-hours.
- Route one-line PRD to draft-PR delivery requests to /prd-to-pr.
- Route scope, ambition, or product strategy questions to /plan-ceo-review.
- Route architecture, failure-mode, and testing plans to /plan-eng-review.
- Route UI and UX plan critiques to /plan-design-review or /design-review.
- Route developer-facing onboarding, CLI, API, SDK, or docs ergonomics to /plan-devex-review.
- Route full multi-review planning to /autoplan.
- Route debugging and root-cause work to /investigate before proposing risky fixes.
- Route code review requests to /review.
- Route runtime verification to /qa or /qa-only.
- Route docs synchronization after behavior changes to /document-release.
- Route security questions to /cso and performance regression checks to /benchmark or /canary.
- Route shipping and PR preparation to /ship or /prepare-release.
- Treat merge or deploy requests as handoff workflows unless CI or human approval steps are explicitly available. Merge remains human-owned even when PR creation and review follow-up are automated.

## Artifact conventions
- Keep reusable planning outputs under plans/ when work produces a brief or reviewed plan.
- Keep durable command reference and workflow docs under docs/.
- When a planning prompt produces implementation guidance, prefer updating a plan artifact rather than burying decisions in chat.

## Delivery standard
- Restate the requested outcome before implementation when the task is ambiguous.
- Prefer minimal diffs over rewrites.
- Preserve existing project patterns unless a change is explicitly requested.
- Update docs when public behavior or setup changes.
- Never claim validation that was not actually run.

## Shell execution hygiene
- For exploratory shell probes where failure is expected or informative, prefer `set +e`, capture the exit status, and report it instead of surfacing a terminal failure.
- Reserve hard non-zero shell exits for true validation or gate failures where the failing status is the intended result.

## Git state hygiene
- When remote PR tooling creates or updates a branch outside the current local checkout, reconcile local git state before closing the task.
- Do not leave implementation changes as uncommitted edits on local `main` when those same changes were pushed to a separate remote branch.
- For remote-backed handoffs, report the checked-out branch, local HEAD, remote base ref, PR branch, and whether the worktree is clean.
- Treat `git:` editor tabs for `refs/remotes/origin/*` as repository snapshots, not as proof of current workspace file state.

## Review standard
- Separate results into done, not done, assumed, and unverified when closing substantial work.
- Call out correctness risks, test gaps, rollback concerns, and dependency impact.
- When relevant, propose validation commands and failure-path checks.

## Safety
- Do not expose secrets or tokens.
- Highlight auth, permissions, data handling, or injection risks when they appear.