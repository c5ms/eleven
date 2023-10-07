package com.eleven.core.exception;


public interface ProcessError {

    /**
     * 获取业务领域
     * @return 领域
     */
    String getDomain();
    /**
     * 获取编码
     *
     * @return 编码
     */
    String getError();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getMessage();


    /**
     * 表示由于此编码的情况，导出处理呗拒绝
     *
     * @return 处理被拒异常
     */
    default ProcessRuntimeException exception() {
        return ProcessRuntimeException.of(this);
    }

    /**
     * 使用指定消息生产一个异常
     *
     * @param message 消息
     * @return 异常
     */
    default ProcessRuntimeException exception(String message) {
        return ProcessRuntimeException.of(this, message);
    }

}
