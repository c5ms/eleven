package com.eleven.access.core;

public class ResourceBlockingException extends ResourceException {
    public ResourceBlockingException() {
    }

    public ResourceBlockingException(String message) {
        super(message);
    }

    public ResourceBlockingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceBlockingException(Throwable cause) {
        super(cause);
    }

    public ResourceBlockingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
