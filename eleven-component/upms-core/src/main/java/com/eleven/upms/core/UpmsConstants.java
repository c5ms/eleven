package com.eleven.upms.core;

import com.eleven.core.exception.ElevenError;
import com.eleven.core.exception.SimpleElevenError;

public interface UpmsConstants {

    /**
     * 服务名
     */
    String SERVICE_NAME = "upms";

    /**
     * 表示用户的主题类型代码，为 user
     */
    String PRINCIPAL_TYPE_USER = "user";

    // ------------------------------------------ error ------------------------------------------
    ElevenError ERROR_LOGIN_PASSWORD = createError("login_password_error", "用户名/密码错误");
    ElevenError ERROR_UNSUPPORTED_IDENTITY = createError("unsupported_identity", "认证方式不支持");
    ElevenError ERROR_USER_NAME_REPEAT = createError("user_name_repeat", "用户名重复");
    ElevenError ERROR_ROLE_CODE_REPEAT = createError("role_code_repeat", "角色代码重复");

    // create an error
    private static ElevenError createError(String error, String message) {
        return new SimpleElevenError(UpmsConstants.SERVICE_NAME, error, message);
    }

}
