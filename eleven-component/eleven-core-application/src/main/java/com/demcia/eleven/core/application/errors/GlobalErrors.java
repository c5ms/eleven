package com.demcia.eleven.core.application.errors;

import com.demcia.eleven.core.errors.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrors implements Errors {

    ;

    final String code;
    final String message;

}
