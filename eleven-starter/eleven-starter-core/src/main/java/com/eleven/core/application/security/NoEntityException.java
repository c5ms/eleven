package com.eleven.core.application.security;


public class NoEntityException extends RuntimeException {

    public NoEntityException() {
        super("No required entity can be found");
    }



}
