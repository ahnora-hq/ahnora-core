package com.ahnora.common.core.exception;

import java.util.Map;

/**
 * Thrown when an authenticated principal is not allowed to perform an operation (HTTP 403).
 *
 * <p>Defaults to {@link ErrorCodes#PERMISSION_DENIED}; pass
 * {@link ErrorCodes#TENANT_ACCESS_DENIED} explicitly for cross-tenant access attempts.
 */
public class PermissionDeniedException extends AhnoraException {

    public PermissionDeniedException(String message) {
        super(ErrorCodes.PERMISSION_DENIED, 403, message);
    }

    public PermissionDeniedException(String message, Map<String, Object> details) {
        super(ErrorCodes.PERMISSION_DENIED, 403, message, details);
    }

    public PermissionDeniedException(String code, String message) {
        super(code, 403, message);
    }

    public PermissionDeniedException(String code, String message, Map<String, Object> details) {
        super(code, 403, message, details);
    }
}
