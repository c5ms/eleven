package com.eleven.upms.model;

import com.eleven.core.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserFilter extends Pagination {

    @Schema(description = "账号")
    String username;

    @Schema(description = "类型")
    String type;

    @Schema(description = "状态")
    UserState state;

    @Schema(description = "锁定")
    Boolean isLocked;

    @Schema(description = "查询范围")
    List<Range> ranges;

    public enum Range {
        locked,
        unlocked
    }

}
