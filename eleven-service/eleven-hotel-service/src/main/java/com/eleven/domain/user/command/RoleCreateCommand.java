package com.eleven.domain.user.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Accessors(chain = true)
public class RoleCreateCommand {

    @Schema(description = "角色代码")
    @NotBlank(message = "角色代码不能为空")
    @Pattern(regexp = "[a-z|A-Z0-9_]*", message = "角色代码必须由数字字母下划线组成")
    @Length(min = 2, max = 50, message = "角色代码必须在 2-50 长度内")
    private String code;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不可为空")
    @Length(min = 2, max = 100, message = "角色名称必须在 2-100 长度内")
    private String name;

}
