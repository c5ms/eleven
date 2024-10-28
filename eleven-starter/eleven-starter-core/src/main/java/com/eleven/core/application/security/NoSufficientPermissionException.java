package com.eleven.core.application.security;

public class NoSufficientPermissionException extends RuntimeException {

    public NoSufficientPermissionException(String message) {
        super(message);
    }

}
