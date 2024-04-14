package com.eleven.core.domain;

public interface DomainConstants {
    String DOMAIN_COMMON = "common";

    DomainError ERROR_JSON_PARSING = createError("json_parsing_error", "Problems parsing JSON");
    DomainError ERROR_VALIDATE_FAILED = createError("validate_failed", "Validation Failed");

    // create an error
    static DomainError createError(String error, String message) {
        return SimpleDomainError.of(DOMAIN_COMMON, error, message);
    }

}
