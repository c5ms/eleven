package com.demcia.eleven.upms.constants;

/**
 * 用户权限管理服务常量
 */
public interface UpmsConstants {
    /**
     * 服务名
     */
    String SERVICE_NAME = "eleven-upms-service";

    String SERVICE_URL = "${service.eleven-upms-service-url:9000:}";

    /**
     * 命名空间 - 用户
     */
    String NAMESPACE_USERS = "/users";

}
