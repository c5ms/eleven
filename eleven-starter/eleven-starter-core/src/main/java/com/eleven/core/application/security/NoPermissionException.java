package com.eleven.core.application.security;

public class NoPermissionException extends RuntimeException {

    public static NoPermissionException empty() {
        return new NoPermissionException();
    }

    public static NoPermissionException because(String reason) {
        return new NoPermissionException("you don't have the permission to operate the resource because " + reason);
    }

      NoPermissionException() {
    }

      NoPermissionException(String message) {
        super(message);
    }

}
