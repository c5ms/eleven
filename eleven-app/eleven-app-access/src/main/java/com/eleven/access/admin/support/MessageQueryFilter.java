package com.eleven.access.admin.support;

import com.cnetong.access.core.Message;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class MessageQueryFilter {

    @NotNull(message = "页码不可为空")
    @Parameter(description = "页码")
    private Integer page = 1;

    @NotNull(message = "每页条数不可为空")
    @Parameter(description = "每页条数")
    private Integer size = 20;

    @Parameter(description = "开始时间")
    private LocalDateTime startTime;

    @Parameter(description = "结束时间")
    private LocalDateTime endTime;

    @Parameter(description = "消息主题")
    private String topic;

    @Parameter(description = "消息状态")
    private Message.State state;

    @Parameter(description = "消息方向")
    private Message.Direction direction;

    @Parameter(description = "终端")
    private String endpoint;

}
