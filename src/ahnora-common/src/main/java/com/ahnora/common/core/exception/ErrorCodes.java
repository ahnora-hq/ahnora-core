package com.ahnora.common.core.exception;

/**
 * Generic, platform-wide error codes.
 *
 * <p>These are the only codes {@code ahnora-common} defines. Every new code path should
 * default to one of them; the human-readable message and the {@code details} map carry the
 * specifics (e.g. {@code entityType}, {@code identifier}).
 *
 * <p>When a client genuinely needs to branch on a specific condition (e.g.
 * {@code AGENT_VERSION_IMMUTABLE}), the owning service declares its own constant locally
 * and passes it to an exception together with an HTTP status — no change to
 * {@code ahnora-common} is required. Service-specific codes must be recorded in
 * {@code docs/04-contracts/ERROR_CODES.md} before release.
 *
 * <p>Conforms to docs/04-contracts/ERROR_CODES.md.
 */
public final class ErrorCodes {

    /** 400 — request payload or parameters failed validation. */
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    /** 401 — no credentials were presented. */
    public static final String AUTHENTICATION_REQUIRED = "AUTHENTICATION_REQUIRED";

    /** 401 — credentials were presented but are invalid, expired or revoked. */
    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    /** 403 — authenticated principal lacks the required permission. */
    public static final String PERMISSION_DENIED = "PERMISSION_DENIED";

    /** 403 — principal is authenticated but belongs to another tenant. */
    public static final String TENANT_ACCESS_DENIED = "TENANT_ACCESS_DENIED";

    /** 404 — the requested resource does not exist or is not visible to the caller. */
    public static final String NOT_FOUND = "NOT_FOUND";

    /** 409 — the operation conflicts with the current state of the resource. */
    public static final String CONFLICT = "CONFLICT";

    /** 429 — caller exceeded a rate or resource limit. */
    public static final String RATE_LIMITED = "RATE_LIMITED";

    /** 503 — a required upstream dependency is unavailable. */
    public static final String DEPENDENCY_UNAVAILABLE = "DEPENDENCY_UNAVAILABLE";

    /** 500 — unexpected server-side failure. */
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

    private ErrorCodes() {
    }
}
