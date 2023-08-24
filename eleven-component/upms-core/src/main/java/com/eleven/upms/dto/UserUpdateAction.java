package com.eleven.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UserUpdateAction {


    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不可为空")
    private String nickname;

}
