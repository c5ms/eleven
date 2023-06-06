package com.eleven.core.time;

public class TimestampProviderException extends RuntimeException {
    public TimestampProviderException() {
    }

    public TimestampProviderException(String message) {
        super(message);
    }

    public TimestampProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimestampProviderException(Throwable cause) {
        super(cause);
    }

    public TimestampProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
