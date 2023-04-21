package com.eleven.core.app.rest;

import com.eleven.core.code.ElevenCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestFailure {

    private String error;
    private String message;

    public RestFailure(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public static RestFailure of(String error, String message) {
        return new RestFailure(error, message);
    }

    public static RestFailure of(ElevenCode codes) {
        return new RestFailure(codes.getCode(), codes.getMessage());
    }
}
