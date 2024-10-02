package com.eleven.access.core.openapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Schema(description = "消息处理响应")
public class MessageResponse implements Serializable {

    @Schema(description = "处理错误")
    private String error = null;

    @Schema(description = "处理描述")
    private String message = null;

    @Schema(description = "是否处理成功")
    private boolean success = true;

}
