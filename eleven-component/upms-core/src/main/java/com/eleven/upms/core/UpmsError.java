package com.eleven.upms.core;

import com.eleven.core.exception.ElevenError;
import lombok.Getter;

@Getter
public enum UpmsError implements ElevenError {
    // login
    LOGIN_PASSWORD_ERROR("login_password_error", "用户名/密码错误"),
    UNSUPPORTED_IDENTITY("unsupported_identity", "认证方式不支持"),

    // user
    USER_NAME_REPEAT("user_name_repeat", "用户名重复"),

    // role
    ROLE_CODE_REPEAT("role_code_repeat", "角色代码重复");

    final String error;
    final String message;

    UpmsError(String error, String message) {
        this.error = UpmsConstants.MODULE_NAME + ":" + error;
        this.message = message;
    }
}
