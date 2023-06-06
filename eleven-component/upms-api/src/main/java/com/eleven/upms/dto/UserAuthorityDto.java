package com.eleven.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "用户权限")
@Getter
@Setter
public class UserAuthorityDto {
    private String name;

    private String type;
}
