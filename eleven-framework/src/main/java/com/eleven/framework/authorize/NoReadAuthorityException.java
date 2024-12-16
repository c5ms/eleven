package com.eleven.framework.authorize;

public class NoReadAuthorityException extends NoAuthorityException {
    public NoReadAuthorityException() {
        super("No readable permission on the resource");
    }


}
