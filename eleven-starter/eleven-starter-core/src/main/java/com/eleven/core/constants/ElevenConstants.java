package com.eleven.core.constants;

import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.SimpleProcessError;

public interface ElevenConstants {
    String DOMAIN_COMMON = "common";

    ProcessError ERROR_PAYLOAD_ERROR = createError("payload_error", "请求数据格式错误");
    ProcessError ERROR_VALIDATE_FAILURE = createError("validate_failure", "请求数据检验失败");
    ProcessError ERROR_DISABLE_EDIT_ON_DEMO = createError("disabled_editing_on_demo", "演示模式下禁止编辑");

    // create an error
    static ProcessError createError(String error, String message) {
        return SimpleProcessError.of(DOMAIN_COMMON, error, message);
    }
}
