package com.eleven.core.application;

public class NoWritePermissionException extends NoPermissionException {
    public NoWritePermissionException(String message) {
        super(message);
    }

    public static NoWritePermissionException instance() {
        return new NoWritePermissionException("No writeable permission on the resource");
    }

}
