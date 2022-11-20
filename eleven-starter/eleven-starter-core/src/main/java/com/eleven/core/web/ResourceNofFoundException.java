package com.eleven.core.web;

public class ResourceNofFoundException extends RuntimeException {

    public static void doThrow() {
        throw new ResourceNofFoundException();
    }

    @Override
    public String getMessage() {
        return "not found";
    }
}
