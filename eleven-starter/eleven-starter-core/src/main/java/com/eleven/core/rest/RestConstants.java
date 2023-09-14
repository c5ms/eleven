package com.eleven.core.rest;

import com.eleven.core.exception.ElevenError;
import com.eleven.core.exception.SimpleElevenError;

public interface RestConstants {
    String ERROR_DOMAIN = "common";

    String SERVICE_API_PREFIX = "/service";
    String CLIENT_API_PREFIX = "/client";
    String ADMIN_API_PREFIX = "/admin";
    String OPEN_API_PREFIX = "/open";

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    ElevenError ERROR_VALIDATE_FAILURE = createError("validate_failure", "请求无效或检验错误");

    // create an error
    private static ElevenError createError(String error, String message) {
        return SimpleElevenError.of(ERROR_DOMAIN, error, message);
    }

}
