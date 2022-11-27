package com.demcia.eleven.upms.core.action;

import com.demcia.eleven.upms.core.enums.UserState;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryAction {

    @Parameter(description = "登录账号")
    private String login;

    @Parameter(description = "类型")
    private String type;

    @Parameter(description = "状态")
    private UserState state;


}
