---
name: browse
description: "Use browser automation to inspect, navigate, capture, or extract information from a live site"
agent: browse-operator
argument-hint: "Enter a URL and the browsing task"
tools: [read, search, playwright/*]
---

Use browser tools to complete the requested browsing task.

Output exactly these sections:
1. Steps taken
2. Observations
3. Artifacts captured
4. Blockers or next action

Task:
${input:task:Enter the URL and what to inspect, capture, or extract}