package com.eleven.core.event;

public class ApplicationEventSerializeException extends Exception {

    public ApplicationEventSerializeException(String message) {
        super(message);
    }

    public ApplicationEventSerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
