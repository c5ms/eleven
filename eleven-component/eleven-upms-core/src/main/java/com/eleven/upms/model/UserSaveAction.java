package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class UserSaveAction {

    @Schema(description = "用户状态")
    UserState state = UserState.NORMAL;

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不可为空")
    private String nickname;

    @Schema(description = "拥有角色")
    @Size(min = 0, max = 50, message = "用户最多拥有 50 个角色")
    private List<String> roles = new ArrayList<>();

}
