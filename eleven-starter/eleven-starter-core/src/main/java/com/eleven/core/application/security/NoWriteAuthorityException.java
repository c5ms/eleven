package com.eleven.core.application.security;

public class NoWriteAuthorityException extends NoAuthorityException {

    public NoWriteAuthorityException(String message) {
        super(message);
    }

    public static NoWriteAuthorityException instance() {
        return new NoWriteAuthorityException("No writeable permission on the resource");
    }

}
