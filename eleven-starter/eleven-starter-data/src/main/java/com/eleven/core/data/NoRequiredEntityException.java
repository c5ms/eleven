package com.eleven.core.data;

/**
 * no such required entity during the runtime
 */
public class NoRequiredEntityException extends RuntimeException {
    public NoRequiredEntityException() {
        super("No required entity can be found");
    }
    public  static NoRequiredEntityException instance(){
        return  new NoRequiredEntityException();
    }
}
