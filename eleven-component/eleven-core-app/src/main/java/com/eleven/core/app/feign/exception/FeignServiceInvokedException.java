package com.eleven.core.app.feign.exception;

public class FeignServiceInvokedException extends RuntimeException {
    private final int status;

    public FeignServiceInvokedException(int status, String response) {
        super(response);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
