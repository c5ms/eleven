package com.eleven.access.core;

import com.cnetong.common.time.TimeContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 系统消息历史
 */
@Getter
@Setter
@FieldNameConstants
@Schema(description = "消息日志")
public class MessageLog implements Serializable {

    @Schema(description = "分区")
    private String partition;

    @Schema(description = "消息 ID")
    private String messageId;

    @Schema(description = "终端 ID")
    private String endpointId;

    @Schema(description = "消息主题")
    private String topic;

    @Schema(description = "处理状态")
    private Message.State state = Message.State.SUCCESS;

    @Schema(description = "处理方向")
    private Message.Direction direction;

    @Schema(description = "异常信息")
    private String exception;

    @Schema(description = "处理时间")
    private LocalDateTime processTime = TimeContext.localDateTime();

    @Schema(description = "接收时间")
    private LocalDateTime createTime = TimeContext.localDateTime();

    @Schema(description = "消息头")
    private Map<String, String> header;

    @Schema(description = "消息体")
    private String body;

    /**
     * 是否存在错误信息
     *
     * @return true 表示有错误
     */
    public boolean isErrored() {
        return StringUtils.isNotBlank(this.getException());
    }

    /**
     * 记录消息处理异常
     *
     * @param e 异常
     */
    public void error(Throwable e) {
        if (null != e) {
            var message = ExceptionUtils.getRootCauseMessage(e);
            this.setException(StringUtils.substring(message, 0, 1024));
            this.state = Message.State.ERRORED;
            this.processTime = TimeContext.localDateTime();
        }
    }

    /**
     * 处理成功
     */
    public void success() {
        this.setException(null);
        this.state = Message.State.SUCCESS;
        this.processTime = TimeContext.localDateTime();
    }

}
