package com.eleven.core.application.security;

public class NoAccessPermissionException  extends  NoPermissionException{

    public static NoAccessPermissionException empty() {
        return new NoAccessPermissionException();
    }

    public static NoAccessPermissionException because(String reason) {
        return new NoAccessPermissionException("Can not read on the resource because " + reason);
    }


      NoAccessPermissionException() {
    }

      NoAccessPermissionException(String message) {
        super(message);
    }

}
