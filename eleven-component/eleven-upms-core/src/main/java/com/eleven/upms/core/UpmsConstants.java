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
    ElevenError ERROR_LOGIN_PASSWORD = SimpleElevenError.of(SERVICE_NAME, "login_password_error", "用户名/密码错误");
    ElevenError ERROR_UNSUPPORTED_IDENTITY = SimpleElevenError.of(SERVICE_NAME, "unsupported_identity", "认证方式不支持");
    ElevenError ERROR_USER_NAME_REPEAT = SimpleElevenError.of(SERVICE_NAME, "user_name_repeat", "用户名重复");
    ElevenError ERROR_ROLE_CODE_REPEAT = SimpleElevenError.of(SERVICE_NAME, "role_code_repeat", "角色代码重复");

}
