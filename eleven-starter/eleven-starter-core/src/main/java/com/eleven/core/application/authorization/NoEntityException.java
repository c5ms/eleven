package com.eleven.core.application.authorization;


public class NoEntityException extends RuntimeException {

    public NoEntityException() {
        super("No required entity can be found");
    }



}
