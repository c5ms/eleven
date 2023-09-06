package com.eleven.core.rest.exception;

import feign.Request;

public class FeignServiceUnAvailableException extends RuntimeException {
    private final Request request;

    public FeignServiceUnAvailableException(Request request) {
        super(request.url());
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
