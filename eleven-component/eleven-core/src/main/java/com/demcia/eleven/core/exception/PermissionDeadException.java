package com.demcia.eleven.core.exception;

/**
 * 表示权限不足
 */
public class PermissionDeadException extends ElevenRuntimeException {

    public PermissionDeadException(String message) {
        super(message);
    }

    public PermissionDeadException() {
    }


}
