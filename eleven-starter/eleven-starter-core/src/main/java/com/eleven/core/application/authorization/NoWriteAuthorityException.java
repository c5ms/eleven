package com.eleven.core.application.authorization;

public class NoWriteAuthorityException extends NoAuthorityException {

    public NoWriteAuthorityException() {
        super("No writeable permission on the resource");
    }

}
