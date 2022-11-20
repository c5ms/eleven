package com.eleven.core.domain;

public interface DomainErrors {
    String DEFAULT_DOMAIN = "common";

    DomainError ERROR_REQUEST_BODY_FAILED = SimpleDomainError.of("request_body_failed", "Process request body failed");
    DomainError ERROR_VALIDATE_FAILED = SimpleDomainError.of("validate_failed", "Validate failed");
    DomainError ERROR_ACCESS_DENIED = SimpleDomainError.of("access_denied", "Access denied");
}
