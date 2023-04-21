package com.eleven.core.app.errors;

import com.eleven.core.code.ElevenCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoreError implements ElevenCode {
    VALIDATE_FAILURE("validate_failure", "校验失败");
    ;

    final String code;
    final String message;

}
