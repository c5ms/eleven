package com.eleven.access.core;

/**
 * 记录数据异常
 */
public class RecordException extends RuntimeException {
    public RecordException() {
    }

    public RecordException(String msg) {
        super(msg);
    }

    public RecordException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RecordException(Throwable cause) {
        super(cause);
    }

    public RecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
