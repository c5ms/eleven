package com.demcia.eleven.core.exception;

/**
 * 表示权限不足
 */
public class PermissionDeadException extends RuntimeException {

    public PermissionDeadException(String message) {
        super(message);
    }

    public PermissionDeadException() {
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }


}
