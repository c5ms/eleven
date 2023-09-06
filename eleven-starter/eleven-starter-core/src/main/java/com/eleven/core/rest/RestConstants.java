package com.eleven.core.rest;

import com.eleven.core.exception.ElevenError;
import com.eleven.core.exception.SimpleElevenError;

public interface RestConstants {
    String ERROR_GROUP = "common";

    String INNER_API_PREFIX = "/_inner";
    String FRONT_API_PREFIX = "/_rest";

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    ElevenError ERROR_VALIDATE_FAILURE = createError("validate_failure", "请求无效或检验错误");


    // create an error
    private static ElevenError createError(String error, String message) {
        return new SimpleElevenError(ERROR_GROUP, error, message);
    }

}
