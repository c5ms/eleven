package com.demcia.eleven.core.rest.support.result;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestApiFailureResult {
    private String error;
    private String message;
}
