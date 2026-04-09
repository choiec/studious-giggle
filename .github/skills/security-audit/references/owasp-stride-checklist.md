# OWASP And STRIDE Checklist

## Auth and authorization
- Broken object-level authorization
- Missing role or tenant scoping
- Session fixation, replay, or weak logout semantics

## Input and output handling
- Injection risk in queries, templates, commands, or URLs
- SSRF via remote fetch or callback endpoints
- XSS, content injection, or unsafe rich text rendering

## State and data
- Secrets or tokens in logs, client bundles, or error payloads
- Unsafe file upload or download paths
- Sensitive data exposure through caches, debug endpoints, or analytics

## Operational abuse
- Missing rate limits or anti-automation controls
- Weak audit logs for privileged actions
- Unclear ownership of security-critical configuration