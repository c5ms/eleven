package com.eleven.core.application.authorize;

public class NoReadAuthorityException extends NoAuthorityException {
    public NoReadAuthorityException() {
        super("No readable permission on the resource");
    }


}
