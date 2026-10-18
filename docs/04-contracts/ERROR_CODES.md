---
document: error-codes
version: 2.0
status: active
authority: contract
depends_on:
  - REST_API
---

# Error Codes

## Platform codes

Every error response uses one of these generic codes unless a service-specific code is
declared below. Declare the specifics in the message and `details`, not in the code.

| Code | HTTP | When |
|------|------|------|
| `VALIDATION_ERROR` | 400 | Request payload, headers or parameters failed validation |
| `AUTHENTICATION_REQUIRED` | 401 | No credentials were presented |
| `UNAUTHORIZED` | 401 | Credentials were presented but are invalid, expired or revoked |
| `PERMISSION_DENIED` | 403 | Authenticated principal lacks the required permission |
| `TENANT_ACCESS_DENIED` | 403 | Principal belongs to another tenant |
| `NOT_FOUND` | 404 | Resource does not exist or is not visible to the caller |
| `CONFLICT` | 409 | Operation conflicts with current resource state |
| `RATE_LIMITED` | 429 | Rate or resource limit exceeded |
| `DEPENDENCY_UNAVAILABLE` | 503 | Required upstream dependency is unavailable |
| `INTERNAL_ERROR` | 500 | Unexpected server-side failure |

## Service-specific codes

Add a service-specific code only when a client must branch on that exact condition and the
generic code plus `details` is not enough (for example `AGENT_VERSION_IMMUTABLE`).

Rules:

1. The owning service declares the constant locally; `ahnora-common` only defines the
   platform codes above.
2. The code is `SCREAMING_SNAKE_CASE` and its HTTP status follows the category of the
   generic code it refines (`AGENT_VERSION_IMMUTABLE` refines `CONFLICT`, so `409`).
3. Register it in this file before release. Published codes are stable and must never be
   renamed or silently reused.

## Versioning

Version 2.0 replaced the domain-specific code list (Agent, Execution, Tool, Knowledge,
Identity entity codes) with the generic platform codes; none of the removed codes were
emitted by a released service, and clients now receive `NOT_FOUND`, `CONFLICT`,
`UNAUTHORIZED` etc. instead.

Error codes are stable machine-readable identifiers. Human-readable messages may change.
