package com.eleven.gateway.management.core;

import com.eleven.core.exception.DomainError;
import com.eleven.core.exception.SimpleDomainError;

public interface GatewayAdminConstants {

    String DOMAIN_NAME = "gateway";

    String ERROR_CODE_ADMIN = "admin";


    DomainError ERROR_ROLE_CODE_REPEAT = SimpleDomainError.of(DOMAIN_NAME, "role_code_repeat", "角色代码重复");

}
