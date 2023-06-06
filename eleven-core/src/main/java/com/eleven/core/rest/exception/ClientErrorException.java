package com.eleven.core.rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientErrorException extends RuntimeException {
    private final HttpStatus status;

    public ClientErrorException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.status = status;
    }

    public ClientErrorException(HttpStatus status, String message) {
        super(message);
        if (!status.is4xxClientError()) {
            throw new IllegalArgumentException(status.value() + " is not a client 4xx status ");
        }
        this.status = status;
    }

}
