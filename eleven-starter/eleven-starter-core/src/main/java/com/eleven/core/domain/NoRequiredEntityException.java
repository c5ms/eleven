package com.eleven.core.domain;

public class NoRequiredEntityException extends RuntimeException {
    public NoRequiredEntityException() {
    }

    public NoRequiredEntityException(String message) {
        super(message);
    }

    public NoRequiredEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredEntityException(Throwable cause) {
        super(cause);
    }

    public NoRequiredEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
