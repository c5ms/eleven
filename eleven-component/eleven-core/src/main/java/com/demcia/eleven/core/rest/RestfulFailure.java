package com.demcia.eleven.core.rest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestfulFailure {
    private String error;
    private String message;
}
