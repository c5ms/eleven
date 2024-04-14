package com.eleven.access.core;

public class ResourceUnreadyException extends ResourceException {
    public ResourceUnreadyException() {
    }

    public ResourceUnreadyException(String message) {
        super(message);
    }

    public ResourceUnreadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceUnreadyException(Throwable cause) {
        super(cause);
    }

    public ResourceUnreadyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
