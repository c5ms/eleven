package com.demcia.eleven.upms.core.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserCreateAction {

    @Schema(description = "登入账号")
    @NotBlank(message = "登入账号不能为空")
    private String login;

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不可为空")
    private String nickname;

}
