package com.eleven.upms.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Schema(description = "用户", name = "User")
@Getter
@Setter
@Accessors(chain = true)
public class UserDetail implements Serializable {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "锁定")
    private Boolean isLocked;

    @Schema(description = "注册时间")
    private LocalDateTime registerAt;

    @Schema(description = "删除时间")
    private LocalDateTime deleteAt;

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

    private List<String> roles = new ArrayList<>();

}
