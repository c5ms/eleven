package com.eleven.core.app.rest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestSuccess<T> {
    private String code = "0";
    private String message;
    private T data;

    public static <T> RestSuccess<T> of(String message, T data) {
        return new RestSuccess<T>()
                .setData(data)
                .setMessage(message);
    }

    public static <T> RestSuccess<T> of(T data) {
        return new RestSuccess<T>()
                .setData(data);
    }


    public static <T> RestSuccess<T> of(String code, String message, T data) {
        return new RestSuccess<T>()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }


}
