package com.demcia.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class DataNotFoundException extends ElevenRuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }


}
