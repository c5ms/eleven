package com.eleven.upms.api.application.command;

import com.eleven.upms.api.domain.model.UserStatus;
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
public class UserCreateCommand {

    @Schema(description = "登入账号")
    @NotBlank(message = "账号不能为空")
    @Length(min = 6, max = 50, message = "账号必须在 6-50 长度内")
    private String username;

    @Schema(description = "用户状态")
    @NotNull(message = "用户状态不可为空")
    private UserStatus state = UserStatus.NORMAL;

    @Schema(description = "拥有角色")
    @Size(min = 0, max = 50, message = "用户最多拥有 50 个角色")
    private List<String> roles = new ArrayList<>();

}
