package com.eleven.core.application.security;

public class NoWritePermissionException extends  NoPermissionException{

    public static NoWritePermissionException empty() {
        return new NoWritePermissionException();
    }

    public static NoWritePermissionException because(String reason) {
        return new NoWritePermissionException("Can not write to the resource because " + reason);
    }


     NoWritePermissionException() {
    }

     NoWritePermissionException(String message) {
        super(message);
    }

}
