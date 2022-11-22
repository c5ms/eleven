package com.demcia.eleven.core.rest.support.result;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestApiSuccessResult<T> {
    private String code;
    private String message;
    private T data;
}
