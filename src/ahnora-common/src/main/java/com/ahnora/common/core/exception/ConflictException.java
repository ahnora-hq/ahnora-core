package com.ahnora.common.core.exception;

import java.util.Map;

/**
 * Thrown when an operation conflicts with current entity state (e.g., version immutability, duplicate state).
 */
public class ConflictException extends AhnoraException {

    public ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ConflictException(ErrorCode errorCode, String message, Map<String, Object> details) {
        super(errorCode, message, details);
    }
}
