package com.eleven.core.web;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface WebErrors {
    DomainError ERROR_REQUEST_BODY_FAILED = SimpleDomainError.of("request_body_failed", "Process request body failed");
    DomainError ERROR_VALIDATE_FAILED = SimpleDomainError.of("validate_failed", "Validate failed");
    DomainError ERROR_ACCESS_DENIED = SimpleDomainError.of("access_denied", "Access denied");
}
