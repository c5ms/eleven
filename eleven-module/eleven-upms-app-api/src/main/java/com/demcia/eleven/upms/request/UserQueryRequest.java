package com.demcia.eleven.upms.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserQueryRequest {
    @Parameter(description = "页码")
    Integer page = 0;
    @Parameter(description = "页长")
    Integer size = 20;
    @Parameter(description = "账号")
    String login;
    @Parameter(description = "类型")
    String type;
    @Parameter(description = "状态")
    String state;
}
