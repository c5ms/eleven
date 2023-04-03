package com.demcia.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class DataNotFoundException extends ElevenRuntimeException {

    private DataNotFoundException(String message) {
        super(message);
    }

    public static DataNotFoundException of(String message) {
        return new DataNotFoundException(message);
    }


}
