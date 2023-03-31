package com.demcia.eleven.upms.domain;

import com.demcia.eleven.upms.domain.enums.UserState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


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
}