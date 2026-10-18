package com.ahnora.common.web.response;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Standard error response envelope.
 * Conforms to docs/04-contracts/REST_API.md.
 */
public record ErrorResponse(
        String code,
        String message,
        String correlationId,
        Map<String, Object> details
) {

    public ErrorResponse {
        details = (details == null) ? Collections.emptyMap() : Collections.unmodifiableMap(new LinkedHashMap<>(details));
    }

    public static ErrorResponse of(String code, String message, String correlationId, Map<String, Object> details) {
        return new ErrorResponse(code, message, correlationId, details);
    }
}
