package com.demcia.eleven.core.exception;

/**
 * 表示权限不足
 */
public class PermissionDeadException extends ElevenRuntimeException {

    private PermissionDeadException(String message) {
        super(message);
    }

    public static PermissionDeadException of(String message) {
        return new PermissionDeadException(message);
    }

}
