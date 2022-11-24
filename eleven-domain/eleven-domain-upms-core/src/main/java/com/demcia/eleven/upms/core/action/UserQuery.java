package com.demcia.eleven.upms.core.action;

import com.demcia.eleven.core.pageable.PageableQueryAction;
import com.demcia.eleven.upms.core.enums.UserState;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQuery extends PageableQueryAction {

    @Parameter(description = "登录账号")
    private String username;

    @Parameter(description = "类型")
    private String type;

    @Parameter(description = "状态")
    private UserState state;


}
