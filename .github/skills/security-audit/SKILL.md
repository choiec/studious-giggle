---
name: security-audit
description: "Audit exploit paths, trust boundaries, validation, and auth risks. Use for security review, threat modeling, or hardening changes."
argument-hint: "Feature, diff, or subsystem to audit"
---

# Security Audit

## When to use
- A feature changes trust boundaries, auth, permissions, uploads, or external integrations
- A review must focus on exploitability rather than general correctness
- You need a targeted threat model before shipping

## Procedure
1. Identify identities, trust boundaries, entry points, and sensitive data.
2. Review auth, authorization, input validation, output encoding, and secret handling.
3. Check likely abuse cases such as privilege escalation, injection, SSRF, CSRF, replay, and data leakage.
4. Rank the findings using exploitability and impact, guided by the [OWASP and STRIDE checklist](./references/owasp-stride-checklist.md).

## Output
- Critical findings
- Medium-risk findings
- Exploit paths or abuse cases
- Missing tests or controls
- Recommended minimal fixes