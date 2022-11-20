package com.eleven.gateway.management.exception;

import com.eleven.core.exception.DomainError;
import com.eleven.gateway.management.core.GatewayAdminConstants;

public class GatewayAdminException extends RuntimeException implements DomainError {

    private final String error;

    private GatewayAdminException(String error, String message) {
        super(message);
        this.error = error;
    }

    private GatewayAdminException(String error, String message, Throwable throwable) {
        super(message, throwable);
        this.error = error;
    }


    @Override
    public String getDomain() {
        return GatewayAdminConstants.DOMAIN_NAME;
    }

    @Override
    public String getError() {
        return error;
    }

    public static GatewayAdminException of(String msg) {
        return new GatewayAdminException(GatewayAdminConstants.ERROR_CODE_ADMIN, msg, null);
    }
}
