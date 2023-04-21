package com.eleven.core.app.errors;

import com.eleven.core.errors.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoreErrors implements Errors {
    VALIDATE_FAILURE("validate_failure", "校验失败");
    ;

    final String code;
    final String message;

}
