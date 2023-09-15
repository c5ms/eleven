package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "用户", name = "user")
public class UserDto implements Serializable {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "锁定")
    private Boolean isLocked;

    @Schema(description = "注册时间")
    private LocalDateTime registerAt;

    @Schema(description = "登入时间")
    private LocalDateTime loginAt;

    @Schema(description = "登入账号")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "状态")
    private UserState state;

}
