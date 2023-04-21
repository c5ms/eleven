package com.eleven.upms.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户权限")
@Data
public class UserAuthorityDto {
    private String name;

    private String type;
}
