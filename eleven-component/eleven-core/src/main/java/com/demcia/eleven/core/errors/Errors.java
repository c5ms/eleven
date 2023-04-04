package com.demcia.eleven.core.errors;


import com.demcia.eleven.core.exception.ProcessRejectedException;

public interface Errors {

    /**
     * 获取编码
     *
     * @return 编码
     */
    String getCode();

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
    default ProcessRejectedException makeRejectException() {
        return ProcessRejectedException.of(this.getCode(), this.getMessage());
    }

    /**
     * 使用指定消息生产一个异常
     *
     * @param message 消息
     * @return 异常
     */
    default ProcessRejectedException makeRejectException(String message) {
        return ProcessRejectedException.of(this.getCode(), message);
    }

}
