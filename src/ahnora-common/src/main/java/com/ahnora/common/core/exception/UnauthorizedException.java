package com.ahnora.common.core.exception;

import java.util.Map;

/**
 * Thrown when the caller is not successfully authenticated (HTTP 401) — missing credentials,
 * invalid/expired tokens, or invalid API keys.
 *
 * <p>Defaults to {@link ErrorCodes#AUTHENTICATION_REQUIRED}; pass
 * {@link ErrorCodes#UNAUTHORIZED} explicitly when credentials were presented and rejected.
 */
public class UnauthorizedException extends AhnoraException {

    public UnauthorizedException(String message) {
        super(ErrorCodes.AUTHENTICATION_REQUIRED, 401, message);
    }

    public UnauthorizedException(String code, String message) {
        super(code, 401, message);
    }

    public UnauthorizedException(String code, String message, Map<String, Object> details) {
        super(code, 401, message, details);
    }

    public UnauthorizedException(String code, String message, Throwable cause) {
        super(code, 401, message, cause);
    }

    public UnauthorizedException(String code, String message, Map<String, Object> details, Throwable cause) {
        super(code, 401, message, details, cause);
    }
}
