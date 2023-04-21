package com.eleven.upms.app.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserQueryRequest {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    @Parameter(description = "页码")
    int page = 0;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数至少为1条")
    @Max(value = 1000, message = "每页条数不能超过1000")
    @Parameter(description = "页长")
    int size = 20;

    @Parameter(description = "账号")
    String login;

    @Parameter(description = "类型")
    String type;

    @Parameter(description = "状态")
    String state;
}
