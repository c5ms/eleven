package com.demcia.eleven.upms.app.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    @Schema(description = "登入账号")
    @NotBlank(message = "登入账号不能为空")
    private String login;

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不可为空")
    private String nickname;

}
