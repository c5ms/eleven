package com.eleven.access.core.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Schema(description = "消息请求")
public class MessageRequest implements Serializable {

    @Schema(description = "消息主题")
    String topic;

    @Schema(description = "消息头")
    Map<String, String> headers = new HashMap<>();

    @Schema(description = "消息体")
    String body;

}
