package com.eleven.core.constants;

import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.SimpleProcessError;

public interface ElevenConstants {
    String DOMAIN_COMMON = "common";

    ProcessError ERROR_JSON_PARSING = createError("json_parsing_error", "Problems parsing JSON");
    ProcessError ERROR_VALIDATE_FAILED = createError("validate_failed", "Validation Failed");

    // create an error
    static ProcessError createError(String error, String message) {
        return SimpleProcessError.of(DOMAIN_COMMON, error, message);
    }

}
