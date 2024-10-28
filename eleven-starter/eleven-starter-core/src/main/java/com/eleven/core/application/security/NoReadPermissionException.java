package com.eleven.core.application.security;

public class NoReadPermissionException extends NoSufficientPermissionException {
    public NoReadPermissionException(String message) {
        super(message);
    }

    public static NoReadPermissionException instance() {
        return new NoReadPermissionException("No readable permission on the resource");
    }


}
