package com.eleven.upms.app.errors;

import com.eleven.core.errors.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpmsErrors implements Errors {
    LOGIN_PASSWORD_ERROR("upms:login_password_error", "用户名/密码错误"),
    UNSUPPORTED_IDENTITY("upms:unsupported_identity", "认证方式不支持");

    final String code;
    final String message;

}
