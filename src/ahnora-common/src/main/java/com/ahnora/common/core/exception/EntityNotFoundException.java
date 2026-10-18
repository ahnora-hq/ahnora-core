package com.ahnora.common.core.exception;

import java.util.Map;

/**
 * Thrown when a requested domain entity cannot be found or is not visible to the caller
 * (HTTP 404).
 *
 * <p>Defaults to {@link ErrorCodes#NOT_FOUND}; the entity type and identifier belong in the
 * {@code details} map, not in the code.
 */
public class EntityNotFoundException extends AhnoraException {

    public EntityNotFoundException(String message) {
        super(ErrorCodes.NOT_FOUND, 404, message);
    }

    public EntityNotFoundException(String code, String message) {
        super(code, 404, message);
    }

    public EntityNotFoundException(String code, String message, Map<String, Object> details) {
        super(code, 404, message, details);
    }

    /** Convenience factory keeping the entity type and identifier out of the error code. */
    public static EntityNotFoundException forEntity(String entityType, String identifier) {
        return new EntityNotFoundException(
                ErrorCodes.NOT_FOUND,
                String.format("%s was not found with identifier: %s", entityType, identifier),
                Map.of("entityType", entityType, "identifier", identifier)
        );
    }
}
