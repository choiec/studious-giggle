---
name: fleet-implement
description: "Generate a ready-to-run Copilot CLI /fleet plan using this repository's Mode C lane rules"
agent: eng-review
argument-hint: "Paste the approved brief or scoped feature request"
tools: [read, search, execute, todo]
---

Do not implement the feature yet.

Using the current repository context and the approved brief, produce a ready-to-paste Copilot CLI /fleet block.

Repository-specific rules:
- Treat this as Mode C lite: parallel implementation tracks plus serial integration gates.
- Prefer 3 to 5 tracks, including one explicit integration track.
- Split tracks by module boundary under src/main/kotlin/org/cherrypick/picker/{student,draft,submission,knowledge,assistant} when applicable.
- Shared files must belong to the integration track only: README.md, AGENTS.md, .github/copilot-instructions.md, build.gradle.kts, compose.yaml, src/main/resources/application*.yml, and src/main/resources/db/migration/**.
- If the feature adds Flyway migrations or shared runtime properties, assign that work to the integration track even if domain code is parallelized.
- Do not make /review, /qa, /qa-only, /cso, /benchmark, /prepare-release, or /document-release separate coding tracks. Schedule them after the integration merge.
- When a track lacks a focused test target, use ./gradlew test as the fallback validation command.

The output must include:
- Track names and ownership boundaries
- Exact file or folder scope for each track
- Dependency ordering between tracks
- Validation commands per track
- Final merge checklist
- Unresolved assumptions called out explicitly

Approved brief:
${input:brief:Paste the approved brief or scope}