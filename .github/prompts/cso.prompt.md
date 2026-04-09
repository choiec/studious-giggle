---
name: cso
description: "Run a security review focused on exploit paths, trust boundaries, and missing controls"
agent: security-review
argument-hint: "Paste the feature, diff summary, or subsystem to audit"
tools: [read, search, execute]
---

Review the supplied scope as a security audit.

Output exactly these sections:
1. Critical findings
2. Medium-risk findings
3. Exploit paths or abuse cases
4. Missing tests or controls
5. Recommended minimal fixes

Scope:
${input:scope:Paste the feature, diff summary, or subsystem}