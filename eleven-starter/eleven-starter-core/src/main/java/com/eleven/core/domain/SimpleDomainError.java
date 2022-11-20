package com.eleven.core.domain;

import lombok.Data;

@Data
public class SimpleDomainError implements DomainError {

    public final String error;
    public final String message;

    public SimpleDomainError( String error, String message) {
        this.error = error;
        this.message = message;
    }

    public static SimpleDomainError of( String error, String message) {
        return new SimpleDomainError( error, message);
    }

}
