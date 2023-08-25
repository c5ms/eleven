package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Accessors(chain = true)
public class RoleUpdateAction extends RoleCreateAction {


    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不可为空")
    @Length(min = 2, max = 100, message = "角色名称必须在 2-100 长度内")
    private String name;

}
