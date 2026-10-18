package com.ahnora.common.core.exception;

import java.util.Map;

/**
 * Thrown when an operation conflicts with the current entity state (HTTP 409) — duplicate
 * resources, invalid state transitions, immutable published versions.
 *
 * <p>Defaults to {@link ErrorCodes#CONFLICT}; use a service-specific code only when a client
 * must branch on the exact conflict reason.
 */
public class ConflictException extends AhnoraException {

    public ConflictException(String message) {
        super(ErrorCodes.CONFLICT, 409, message);
    }

    public ConflictException(String message, Map<String, Object> details) {
        super(ErrorCodes.CONFLICT, 409, message, details);
    }

    public ConflictException(String code, String message) {
        super(code, 409, message);
    }

    public ConflictException(String code, String message, Map<String, Object> details) {
        super(code, 409, message, details);
    }
}
