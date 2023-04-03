package com.demcia.eleven.core.codes;

import com.demcia.eleven.core.exception.ProcessRejectedException;

public interface ElevenCode {

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
    default ProcessRejectedException reject() {
        return ProcessRejectedException.of(this.getCode(), this.getMessage());
    }

}
