package com.eleven.core.application.security;

public class NoReadAuthorityException extends NoAuthorityException {
    public NoReadAuthorityException(String message) {
        super(message);
    }

    public static NoReadAuthorityException instance() {
        return new NoReadAuthorityException("No readable permission on the resource");
    }


}
