package com.eleven.upms.api.domain.core;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface UpmsConstants {

    String SERVICE_NAME = "upms";

    // ------------------------------------------ error ------------------------------------------
    DomainError ERROR_LOGIN_PASSWORD = SimpleDomainError.of( "login_password_error", "用户名/密码错误");
    DomainError ERROR_UNSUPPORTED_IDENTITY = SimpleDomainError.of( "unsupported_identity", "认证方式不支持");
    DomainError ERROR_USER_NAME_REPEAT = SimpleDomainError.of( "user_name_repeat", "用户名重复");
    DomainError ERROR_USER_ALREADY_DELETED = SimpleDomainError.of( "user_already_deleted", "用户已被删除");
    DomainError ERROR_ROLE_CODE_REPEAT = SimpleDomainError.of( "role_code_repeat", "角色代码重复");

}
