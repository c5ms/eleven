package com.eleven.domain.user.command;

import com.eleven.domain.user.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class UserQueryCommand {

    @Schema(description = "账号")
    private String username;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "状态")
    private UserStatus state;

    @Schema(description = "锁定")
    private Boolean isLocked;

    @Schema(description = "查询范围")
    private List<Range> ranges;

    public enum Range {
        locked,
        unlocked
    }

}
