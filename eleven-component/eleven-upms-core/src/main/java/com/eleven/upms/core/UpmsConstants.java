package com.eleven.upms.core;

import com.eleven.core.exception.DomainError;
import com.eleven.core.exception.SimpleDomainError;

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
     * 缓存 - 用户
     */
    String CACHE_NAME_USER = "user";

    /**
     * 表示用户的主题类型代码，为 user
     */
    String PRINCIPAL_TYPE_USER = "user";

    // ------------------------------------------ error ------------------------------------------
    DomainError ERROR_LOGIN_PASSWORD = SimpleDomainError.of(SERVICE_NAME, "login_password_error", "用户名/密码错误");
    DomainError ERROR_UNSUPPORTED_IDENTITY = SimpleDomainError.of(SERVICE_NAME, "unsupported_identity", "认证方式不支持");
    DomainError ERROR_USER_NAME_REPEAT = SimpleDomainError.of(SERVICE_NAME, "user_name_repeat", "用户名重复");
    DomainError ERROR_USER_ALREADY_DELETED = SimpleDomainError.of(SERVICE_NAME, "user_already_deleted", "用户已被删除");
    DomainError ERROR_ROLE_CODE_REPEAT = SimpleDomainError.of(SERVICE_NAME, "role_code_repeat", "角色代码重复");

}
