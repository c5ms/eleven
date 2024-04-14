package com.eleven.access.core;

/**
 * 通用异常,表示平台处理逻辑错误
 */
public class AccessRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccessRuntimeException() {
    }

    public AccessRuntimeException(String msg) {
        super(msg);
    }

    public AccessRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AccessRuntimeException(Throwable cause) {
        super(cause);
    }

    public AccessRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
