package com.eleven.core.application;

public class NoReadPermissionException extends NoPermissionException {
    public NoReadPermissionException(String message) {
        super(message);
    }

    public static NoReadPermissionException instance() {
        return new NoReadPermissionException("No readable permission on the resource");
    }


}
