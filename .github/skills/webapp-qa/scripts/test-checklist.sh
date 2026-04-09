#!/usr/bin/env bash
set -euo pipefail

target_url="${1:-}"

echo "Webapp QA checklist"
if [[ -n "$target_url" ]]; then
  echo "Target: $target_url"
  if command -v curl >/dev/null 2>&1; then
    echo "HTTP reachability:"
    if ! curl -fsSIL "$target_url" | sed -n '1p'; then
      echo "Unable to reach target with curl. Continue with manual or browser-based checks."
    fi
  fi
fi

cat <<'EOF'
[ ] Landing or entry route renders without console-breaking errors
[ ] Authentication flow works or fails with a clear message
[ ] Primary create or edit flow succeeds
[ ] Validation errors are visible and actionable
[ ] Navigation between major routes preserves expected state
[ ] Empty, loading, and error states are intentional
[ ] Destructive actions require confirmation when appropriate
[ ] A recovery path exists after a failed request
EOF