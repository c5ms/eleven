package com.demcia.eleven.core.feign.exception;


import com.demcia.eleven.core.rest.RestfulFailure;
import org.springframework.http.HttpStatus;

public class FeignFailureException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final RestfulFailure restfulFailure;


    public FeignFailureException(HttpStatus httpStatus, RestfulFailure restfulFailure) {
        this.httpStatus = httpStatus;
        this.restfulFailure = restfulFailure;
    }

    public RestfulFailure getRestApiFailureResult() {
        return restfulFailure;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
