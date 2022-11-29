package com.demcia.eleven.core.feign.exception;

import com.demcia.eleven.core.exception.ElevenRuntimeException;
import feign.Request;

public class FeignServiceUnAvailableException extends ElevenRuntimeException {
    private final Request request;

    public FeignServiceUnAvailableException( Request request) {
        super(request.url());
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
