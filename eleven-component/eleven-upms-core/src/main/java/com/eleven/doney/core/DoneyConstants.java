package com.eleven.doney.core;

import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.SimpleProcessError;

public interface DoneyConstants {

    /**
     * 服务名
     */
    String SERVICE_NAME = "upms";


    // ------------------------------------------ error ------------------------------------------
    ProcessError ERROR_MEMBER_EXIST = SimpleProcessError.of(SERVICE_NAME, "member_exist", "成员已存在");
    ProcessError ERROR_PROJECT_CODE_EXIST = SimpleProcessError.of(SERVICE_NAME, "project_code_exist", "项目标识已存在");

}
