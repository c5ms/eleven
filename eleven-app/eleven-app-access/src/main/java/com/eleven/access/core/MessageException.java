package com.eleven.access.core;

public class MessageException extends RuntimeException {
    private final String error;

    public MessageException(String message, Exception cause) {
        this(MessageErrors.INTERNAL_ERROR.getError(), message, cause);
    }

    public MessageException(String error, String message) {
        this(error, message, null);
    }

    public MessageException(String error, String message, Exception cause) {
        super(message, cause);
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
