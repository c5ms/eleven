package com.demcia.eleven.core.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestSuccess<T> {
    private String code = "0";
    private String message;
    private T data;
}
