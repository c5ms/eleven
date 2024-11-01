package com.eleven.core.application.authorization;

public class NoReadAuthorityException extends NoAuthorityException {
    public NoReadAuthorityException() {
        super("No readable permission on the resource");
    }



}
