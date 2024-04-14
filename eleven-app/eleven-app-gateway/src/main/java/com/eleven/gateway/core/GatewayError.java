package com.eleven.gateway.core;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GatewayError {
    PROCESS_FAILURE(HttpStatus.BAD_GATEWAY, "Gateway internal error"),

    // route 相关
    NO_SERVICE(HttpStatus.NOT_FOUND, "No service"),
    NO_ROUTE(HttpStatus.NOT_FOUND, "No route"),
    NO_ROUTE_TARGET(HttpStatus.BAD_GATEWAY, "No route target"),
    ERROR_ROUTE_TARGET(HttpStatus.BAD_GATEWAY, "Error route target"),

    // api 相关
    NO_APP(HttpStatus.UNAUTHORIZED, "App-id error"),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "Permission required"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Token invalid"),
    ;

    private final HttpStatus status;
    private final String message;

    GatewayError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
