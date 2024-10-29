package com.eleven.core.application;

public class NoAuthorityException extends RuntimeException {

    public NoAuthorityException(String message) {
        super(message);
    }

}
