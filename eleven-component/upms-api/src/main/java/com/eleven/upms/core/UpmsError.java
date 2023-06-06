package com.eleven.upms.core;

import com.eleven.core.exception.ElevenError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpmsError implements ElevenError {
    LOGIN_PASSWORD_ERROR("upms:login_password_error", "用户名/密码错误"),
    UNSUPPORTED_IDENTITY("upms:unsupported_identity", "认证方式不支持");

    final String error;
    final String message;

}
