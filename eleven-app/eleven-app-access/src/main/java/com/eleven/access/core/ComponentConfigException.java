package com.eleven.access.core;

public class ComponentConfigException extends RuntimeException {
    public ComponentConfigException() {
    }

    public ComponentConfigException(String message) {
        super(message);
    }

    public ComponentConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentConfigException(Throwable cause) {
        super(cause);
    }

    public ComponentConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
