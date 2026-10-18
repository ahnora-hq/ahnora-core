package com.ahnora.identity.application.dto.auth;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SwitchTenantRequest(
        @NotNull(message = "targetTenantId must be not null")
        UUID targetTenantId
) {
}
