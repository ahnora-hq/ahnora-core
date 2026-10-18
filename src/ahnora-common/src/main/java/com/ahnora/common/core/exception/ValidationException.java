package com.ahnora.common.core.exception;

import java.util.Map;

/**
 * Thrown when domain or input validation rules fail (HTTP 400).
 */
public class ValidationException extends AhnoraException {

    public ValidationException(String message) {
        super(ErrorCodes.VALIDATION_ERROR, 400, message);
    }

    public ValidationException(String message, Map<String, Object> details) {
        super(ErrorCodes.VALIDATION_ERROR, 400, message, details);
    }

    /** Use only when a client must branch on a service-specific validation code. */
    public ValidationException(String code, String message, Map<String, Object> details) {
        super(code, 400, message, details);
    }
}
