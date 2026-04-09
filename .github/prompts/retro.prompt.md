---
name: retro
description: "Run a lightweight retrospective after implementation, QA, or release"
agent: reviewer
argument-hint: "Paste the work summary, issue, or release context"
---

Review the completed work as a short retrospective.

Output exactly these sections:
1. What went well
2. What slipped
3. What was assumed
4. What to automate next time
5. Follow-up owners or tasks

Context:
${input:context:Paste the project, issue, or release context}