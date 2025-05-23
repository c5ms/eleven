package com.motiveschina.hotel.features.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Schema(description = "角色", name = "Role")
@Getter
@Setter
@Accessors(chain = true)
public class RoleDetail {
    @Schema(description = "ID")
    private String id;

    @Schema(description = "代码")
    private String code;

    @Schema(description = "角色名")
    private String name;
}
