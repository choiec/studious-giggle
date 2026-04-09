# QA Checklist

## Access and session
- Confirm the entry route loads without blocking errors.
- Verify login, logout, session expiry, and permission-denied states if authentication exists.

## Core flows
- Test the highest-value create, edit, delete, and search flows.
- Check that success feedback is visible and that data refreshes correctly.

## Forms and validation
- Submit with valid data, invalid data, empty required fields, and oversized input where relevant.
- Check that validation is attached to the right field and uses actionable copy.

## Navigation and resilience
- Refresh on a deep link and verify the expected state.
- Confirm back and forward navigation do not corrupt the flow.
- Trigger a failed request and note the user recovery path.

## Regression notes
- Record exact reproduction steps.
- Map each issue to a likely route, component, or API boundary.
- Recommend the smallest stable automated test that would catch the issue again.