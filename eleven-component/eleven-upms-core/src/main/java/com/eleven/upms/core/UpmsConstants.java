package com.eleven.upms.core;

import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.SimpleProcessError;

public interface UpmsConstants {

    /**
     * 服务名
     */
    String SERVICE_NAME = "upms";

    /**
     * 缓存 - 权限
     */
    String CACHE_NAME_AUTHORITY = "authority";

    /**
     * 表示用户的主题类型代码，为 user
     */
    String PRINCIPAL_TYPE_USER = "user";

    // ------------------------------------------ error ------------------------------------------
    ProcessError ERROR_LOGIN_PASSWORD = SimpleProcessError.of(SERVICE_NAME, "login_password_error", "用户名/密码错误");
    ProcessError ERROR_UNSUPPORTED_IDENTITY = SimpleProcessError.of(SERVICE_NAME, "unsupported_identity", "认证方式不支持");
    ProcessError ERROR_USER_NAME_REPEAT = SimpleProcessError.of(SERVICE_NAME, "user_name_repeat", "用户名重复");
    ProcessError ERROR_ROLE_CODE_REPEAT = SimpleProcessError.of(SERVICE_NAME, "role_code_repeat", "角色代码重复");

}
