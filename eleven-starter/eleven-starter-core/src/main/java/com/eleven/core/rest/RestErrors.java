package com.eleven.core.rest;

import com.eleven.core.exception.ElevenError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestErrors implements ElevenError {
    VALIDATE_FAILURE("common:validate_failure", "请求无效或检验错误");;

    final String error;
    final String message;

}
