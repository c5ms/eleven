package com.eleven.framework.authorize;

public class NoWriteAuthorityException extends NoAuthorityException {

    public NoWriteAuthorityException() {
        super("No writeable permission on the resource");
    }

}
