package com.eleven.core.app.rest;

import com.eleven.core.exception.ElevenError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestErrors implements ElevenError {
    VALIDATE_FAILURE("common:validate_failure", "校验失败");
    ;

    final String error;
    final String message;

}
