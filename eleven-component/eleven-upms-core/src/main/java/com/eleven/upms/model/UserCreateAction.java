package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class UserCreateAction {
    @Schema(description = "用户状态")
    @NotNull
    UserState state = UserState.NORMAL;

    @Schema(description = "登入账号")
    @NotBlank(message = "账号不能为空")
    @Length(min = 6, max = 50, message = "账号必须在 6-50 长度内")
    private String username;

    @Schema(description = "用户昵称")
    @NotBlank(message = "昵称不可为空")
    @Length(min = 2, max = 100, message = "昵称必须在 2-100 长度内")
    private String nickname;

    @Schema(description = "拥有角色")
    @Size(min = 0, max = 50, message = "用户拥有角色最多 50 个")
    private List<String> roles = new ArrayList<>();
}
