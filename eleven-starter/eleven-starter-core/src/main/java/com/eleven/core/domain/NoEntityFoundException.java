package com.eleven.core.domain;

/**
 * no such required entity during the runtime
 */
public class NoEntityFoundException extends RuntimeException {
    public NoEntityFoundException() {
    }

    public NoEntityFoundException(String message) {
        super(message);
    }

    public NoEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEntityFoundException(Throwable cause) {
        super(cause);
    }

    public NoEntityFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
