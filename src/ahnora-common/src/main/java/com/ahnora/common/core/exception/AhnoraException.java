package com.ahnora.common.core.exception;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Base runtime exception for all Ahnora domain and platform errors.
 *
 * <p>The error code is an open {@link String}: common code paths use the generic constants
 * from {@link ErrorCodes}, while a service may pass its own code together with the matching
 * HTTP status. The wire shape ({@code code}, {@code message}, {@code correlationId},
 * {@code details}) is unchanged — see docs/04-contracts/REST_API.md.
 */
public class AhnoraException extends RuntimeException {

    private final String code;
    private final int httpStatus;
    private final Map<String, Object> details;
    private String correlationId;

    public AhnoraException(String code, int httpStatus, String message) {
        this(code, httpStatus, message, Collections.emptyMap(), null);
    }

    public AhnoraException(String code, int httpStatus, String message, Throwable cause) {
        this(code, httpStatus, message, Collections.emptyMap(), cause);
    }

    public AhnoraException(String code, int httpStatus, String message, Map<String, Object> details) {
        this(code, httpStatus, message, details, null);
    }

    public AhnoraException(String code, int httpStatus, String message, Map<String, Object> details, Throwable cause) {
        super(message, cause);
        this.code = Objects.requireNonNull(code, "code must not be null");
        this.httpStatus = httpStatus;
        this.details = details != null ? Collections.unmodifiableMap(new LinkedHashMap<>(details)) : Collections.emptyMap();
    }

    /** Machine-readable error code as sent to clients (see {@link ErrorCodes}). */
    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public AhnoraException withCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }
}
