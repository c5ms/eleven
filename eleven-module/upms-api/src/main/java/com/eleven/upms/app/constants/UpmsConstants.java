package com.eleven.upms.app.constants;

public interface UpmsConstants {
    String PRINCIPAL_TYPE_LOCAL_USER = "user";

    String SERVICE_NAME = "${service.upms.name:upms}";

    String SERVICE_URL = "${service.upms.url:}";

    String NAMESPACE_USERS = "/users";

    String NAMESPACE_ACCESS_TOKENS = "/access_tokens";
}
