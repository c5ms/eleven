package com.eleven.core.error;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException implements DomainError {

    public final String error;

    public DomainException(DomainError error) {
        super(error.getMessage());
        this.error = error.getError();
    }

    public DomainException(DomainError error, String message) {
        super(message);
        this.error = error.getError();
    }

    public static DomainException of(DomainError error) {
        return new DomainException(error);
    }

    public static DomainException of(DomainError error, String message) {
        return new DomainException(error, message);
    }

}
