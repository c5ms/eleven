package com.demcia.eleven.upms.core.dto;

import com.demcia.eleven.upms.core.enums.UserState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Schema(description = "用户")
@Data
public class UserDto implements Serializable {
    @Schema(description = "ID")
    private String id;

    @Schema(description = "登入账号")
    private String login;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "状态")
    private UserState state = UserState.NORMAL;

    @Schema(description = "角色")
    private Set<Long> roles = new HashSet<>();
}