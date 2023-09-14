package com.eleven.core.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SimpleElevenError implements ElevenError {

    public final String domain;
    public final String error;
    public final String message;

    public SimpleElevenError(String domain, String error, String message) {
        this.domain = domain;
        this.error = error;
        this.message = message;
    }

    public static SimpleElevenError of(String domain, String error, String message) {
        return new SimpleElevenError(domain, error, message);
    }

}
