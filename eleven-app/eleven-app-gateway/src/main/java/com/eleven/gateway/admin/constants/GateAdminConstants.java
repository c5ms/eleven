package com.eleven.gateway.admin.constants;

public final class GateAdminConstants {
    public static final String VERSION_KEY_ROUTES = "gateway_routes_version";
    public static final String VERSION_KEY_STACKS = "gateway_stacks_version";
    public static final String VERSION_KEY_SERVICES = "gateway_services_version";
    public static final String VERSION_KEY_RESOURCES = "gateway_resources_version";

    public static final String VERSION_KEY_APP = "gateway_app_version";
    public static final String VERSION_KEY_TOKEN = "gateway_token_version";

    public static final String VERSION_KEY_RESOURCE_FORMAT = "gateway_resource:%s:version";

    public static final String STACK_ID_PREFIX = "stack-";
    public static final String ROUTE_ID_PREFIX = "route-";
    public static final String API_ID_PREFIX = "api-";
    public static final String APP_ID_PREFIX = "app-";
    public static final String SERVICE_ID_PREFIX = "service-";
    public static final String RESOURCE_ID_PREFIX = "resource-";

    public static String getResourceKey(String id) {
        return String.format(VERSION_KEY_RESOURCE_FORMAT, id);
    }
}
