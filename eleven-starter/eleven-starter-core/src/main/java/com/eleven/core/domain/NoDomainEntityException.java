package com.eleven.core.domain;

public class NoDomainEntityException extends RuntimeException {
    public NoDomainEntityException() {
    }

    public NoDomainEntityException(String message) {
        super(message);
    }

    public NoDomainEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDomainEntityException(Throwable cause) {
        super(cause);
    }

    public NoDomainEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
