package com.eleven.gateway.core;

public class GatewayConstants {
    public static final String GATEWAY_HEADER_GATEWAY_APP_ID = "app-id";

    public static final String GATEWAY_HEADER_GATEWAY_SERVER = "X-Gateway-Server";
    public static final String GATEWAY_HEADER_GATEWAY_ROUTE = "X-Gateway-Route";
    public static final String GATEWAY_HEADER_GATEWAY_SERVICE = "X-Gateway-Service";
    public static final String GATEWAY_HEADER_GATEWAY_RESOURCE = "X-Gateway-Resource";
    public static final String GATEWAY_HEADER_GATEWAY_ERROR = "X-Gateway-Error";
    public static final String GATEWAY_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    public static final String GATEWAY_HEADER_TRACE_ID = "X-Gateway-Trace-Id";

    public static final String GATEWAY_ROUTE_ATTR = qualify("gatewayRoute");
    public static final String GATEWAY_APP_ATTR = qualify("gatewayApp");
    public static final String GATEWAY_SERVICE_ATTR = qualify("gatewayService");
    public static final String GATEWAY_RESOURCE_ATTR = qualify("gatewayResource");
    public static final String GATEWAY_HTTP_CLIENT = qualify("gatewayHttpClient");
    public static final String GATEWAY_WEB_SOCKET_CLIENT = qualify("gatewayWebSocketClient");
    public static final String GATEWAY_WEB_SOCKET_SERVICE = qualify("gatewayWebSocketService");
    public static final String GATEWAY_REQUEST_URL_ATTR = qualify("gatewayRequestUrl");
    public static final String GATEWAY_ALREADY_ROUTED_ATTR = qualify("gatewayAlreadyRouted");
    public static final String URI_TEMPLATE_VARIABLES_ATTRIBUTE = qualify("uriTemplateVariables");
    public static final String GATEWAY_PREDICATE_MATCHED_PATH_ATTR = qualify("gatewayPredicateMatchedPathAttr");
    public static final String CLIENT_RESPONSE_ATTR = qualify("gatewayClientResponse");
    public static final String CLIENT_RESPONSE_CONN_ATTR = qualify("gatewayClientResponseConnection");
    public static final String PRESERVE_HOST_HEADER_ATTRIBUTE = qualify("preserveHostHeader");
    public static final String GATEWAY_ORIGINAL_REQUEST_URL_ATTR = qualify("gatewayOriginalRequestUrl");

    private static String qualify(String key) {
        return GatewayConstants.class.getName() + "." + key;
    }
}
