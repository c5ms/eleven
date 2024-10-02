package com.eleven.core.exception;

import lombok.Data;

@Data
public class SimpleDomainError implements DomainError {

    public final String domain;
    public final String error;
    public final String message;

    public SimpleDomainError(String domain, String error, String message) {
        this.domain = domain;
        this.error = error;
        this.message = message;
    }

    public static SimpleDomainError of(String domain, String error, String message) {
        return new SimpleDomainError(domain, error, message);
    }

}
