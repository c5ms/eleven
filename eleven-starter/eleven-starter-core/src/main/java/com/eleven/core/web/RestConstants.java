package com.eleven.core.web;

import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.SimpleProcessError;

public interface RestConstants {
    String ERROR_DOMAIN = "common";

    String INNER_API_PREFIX = "/inner";
    String FRONT_API_PREFIX = "/api";
    String OPEN_API_PREFIX = "/open";

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    ProcessError ERROR_VALIDATE_FAILURE = createError("validate_failure", "请求无效或检验错误");

    // create an error
    private static ProcessError createError(String error, String message) {
        return SimpleProcessError.of(ERROR_DOMAIN, error, message);
    }

}
