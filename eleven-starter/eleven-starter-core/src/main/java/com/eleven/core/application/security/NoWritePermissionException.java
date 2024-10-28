package com.eleven.core.application.security;

public class NoWritePermissionException extends NoSufficientPermissionException {
    public NoWritePermissionException(String message) {
        super(message);
    }

    public static NoWritePermissionException instance() {
        return new NoWritePermissionException("No writeable permission on the resource");
    }

}
