package com.demcia.eleven.core.exception;

import com.demcia.eleven.core.ElevenCode;

/**
 * 请求处理失败，通常是由于服务器错误，跟客户端关系不大
 */
public class ProcessErrorException extends ElevenRuntimeException {
    private final String error;

    private ProcessErrorException(String error, String message) {
        super(message);
        this.error = error;
    }

    public static ProcessErrorException of(String error, String message) {
        return new ProcessErrorException(error, message);
    }

    public static ProcessErrorException of(String message) {
        return new ProcessErrorException(null, message);
    }

    public static ProcessErrorException of(ElevenCode errorCode) {
        return new ProcessErrorException(errorCode.getCode(), errorCode.getMessage());
    }

    public String getError() {
        return error;
    }



}
