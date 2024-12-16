package com.eleven.framework.web;

import com.eleven.framework.error.DomainError;
import com.eleven.framework.error.SimpleDomainError;

public interface RestErrors {
    DomainError ERROR_REQUEST_BODY_FAILED = SimpleDomainError.of("request_body_failed", "Process request body failed");
    DomainError ERROR_VALIDATE_FAILED = SimpleDomainError.of("validate_failed", "Validate failed");
    DomainError ERROR_ACCESS_DENIED = SimpleDomainError.of("access_denied", "Access denied");
}
