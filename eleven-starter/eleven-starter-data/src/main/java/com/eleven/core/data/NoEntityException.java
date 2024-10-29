package com.eleven.core.data;

/**
 * no such required entity during the runtime
 */
public class NoEntityException extends RuntimeException {
    public NoEntityException() {
        super("No such entity can be found");
    }
}
