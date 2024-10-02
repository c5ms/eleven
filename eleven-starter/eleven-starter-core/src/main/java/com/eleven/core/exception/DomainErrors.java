package com.eleven.core.exception;

public interface DomainErrors {
    String DEFAULT_DOMAIN = "common";

    DomainError ERROR_REQUEST_BODY_FAILED = createError("request_body_failed", "Process request body failed");
    DomainError ERROR_VALIDATE_FAILED = createError("validate_failed", "Validate failed");
    DomainError ERROR_ACCESS_DENIED = createError("access_denied", "Access denied");

    // create an error
    private static DomainError createError(String error, String message) {
        return SimpleDomainError.of(DEFAULT_DOMAIN, error, message);
    }

}
