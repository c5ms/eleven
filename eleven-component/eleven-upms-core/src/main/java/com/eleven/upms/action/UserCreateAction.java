package com.eleven.upms.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Accessors(chain = true)
public class UserCreateAction extends UserSaveAction {

    @Schema(description = "登入账号")
    @NotBlank(message = "账号不能为空")
    @Length(min = 6, max = 50, message = "账号必须在 6-50 长度内")
    private String username;

}
