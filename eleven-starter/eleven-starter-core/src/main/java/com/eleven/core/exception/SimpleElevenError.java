package com.eleven.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleElevenError implements ElevenError {

    public final String error;
    public final String message;

    public SimpleElevenError(String group, String error, String message) {
        this.error = group + ":" + error;
        this.message = message;
    }

}
