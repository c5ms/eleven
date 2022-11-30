package com.demcia.eleven.core.exception;

import com.demcia.eleven.core.codes.ElevenCode;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
public class ProcessFailureException extends ElevenRuntimeException {
    private final String error;
    private final String message;

    private ProcessFailureException(String error, String message) {
        super(message);
        this.message = message;
        this.error = error;
    }

    public static ProcessFailureException of(String error, String message) {
        return new ProcessFailureException(error, message);
    }

    public static ProcessFailureException of(String message) {
        return new ProcessFailureException(null, message);
    }

    public static ProcessFailureException of(ElevenCode errorCode) {
        return new ProcessFailureException(errorCode.getCode(), errorCode.getMessage());
    }


    public String getError() {
        return error;
    }


}
