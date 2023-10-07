package com.eleven.core.exception;

import lombok.Data;

@Data
public class SimpleProcessError implements ProcessError {

    public final String domain;
    public final String error;
    public final String message;

    public SimpleProcessError(String domain, String error, String message) {
        this.domain = domain;
        this.error = error;
        this.message = message;
    }

    public static SimpleProcessError of(String domain, String error, String message) {
        return new SimpleProcessError(domain, error, message);
    }

}
