package com.ahnora.common.web.handler;

import com.ahnora.common.core.exception.AhnoraException;
import com.ahnora.common.core.exception.EntityNotFoundException;
import com.ahnora.common.core.exception.ErrorCodes;
import com.ahnora.common.core.exception.PermissionDeniedException;
import com.ahnora.common.web.filter.CorrelationIdFilter;
import com.ahnora.common.web.response.ErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @AfterEach
    void cleanup() {
        MDC.clear();
    }

    @Test
    @DisplayName("should map EntityNotFoundException to 404 with the generic NOT_FOUND code")
    void shouldHandleEntityNotFoundException() {
        MDC.put(CorrelationIdFilter.MDC_CORRELATION_ID_KEY, "test-corr-id");
        EntityNotFoundException ex = new EntityNotFoundException("Agent not found");

        ResponseEntity<ErrorResponse> response = handler.handleAhnoraException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCodes.NOT_FOUND);
        assertThat(response.getBody().message()).isEqualTo("Agent not found");
        assertThat(response.getBody().correlationId()).isEqualTo("test-corr-id");
    }

    @Test
    @DisplayName("should map PermissionDeniedException with TENANT_ACCESS_DENIED to 403")
    void shouldHandleTenantAccessDenied() {
        PermissionDeniedException ex =
                new PermissionDeniedException(ErrorCodes.TENANT_ACCESS_DENIED, "Access denied to tenant");

        ResponseEntity<ErrorResponse> response = handler.handleAhnoraException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCodes.TENANT_ACCESS_DENIED);
    }

    @Test
    @DisplayName("should pass through a service-defined code and its status unchanged")
    void shouldPassThroughServiceSpecificCode() {
        AhnoraException ex = new AhnoraException("AGENT_VERSION_IMMUTABLE", 409,
                "Published agent version is immutable and cannot be modified.");

        ResponseEntity<ErrorResponse> response = handler.handleAhnoraException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("AGENT_VERSION_IMMUTABLE");
        assertThat(response.getBody().message()).contains("immutable");
    }

    @Test
    @DisplayName("should map unhandled Exception to 500 without leaking stack trace")
    void shouldHandleUnhandledExceptionSafely() {
        Exception ex = new RuntimeException("Database connection timeout or internal detail");

        ResponseEntity<ErrorResponse> response = handler.handleUnhandledException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(ErrorCodes.INTERNAL_ERROR);
        assertThat(response.getBody().message()).doesNotContain("Database connection");
        assertThat(response.getBody().details()).isEmpty();
    }
}
