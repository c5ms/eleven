package com.eleven.core.application.secure;

public class NoAuthorityException extends RuntimeException {

    public NoAuthorityException(String message) {
        super(message);
    }

}
