package com.eleven.upms.dto;

import com.eleven.core.model.Pagination;
import com.eleven.upms.enums.UserState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserFilter extends Pagination {

    @Schema(description = "账号")
    String username;

    @Schema(description = "类型")
    String type;

    @Schema(description = "状态")
    UserState state;

    @Schema(description = "锁定")
    Boolean isLocked;

}
