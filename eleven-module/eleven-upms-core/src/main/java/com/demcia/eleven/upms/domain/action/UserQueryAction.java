package com.demcia.eleven.upms.domain.action;

import com.demcia.eleven.core.pageable.Pagination;
import com.demcia.eleven.upms.domain.enums.UserState;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserQueryAction extends Pagination {

    @Parameter(description = "登录账号")
    private String login;

    @Parameter(description = "类型")
    private String type;

    @Parameter(description = "状态")
    private UserState state;


}
