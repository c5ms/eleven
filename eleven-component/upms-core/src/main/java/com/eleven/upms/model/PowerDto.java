package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "用户权限")
@Getter
@Setter
public class PowerDto {

    private String type;
    private String name;
}
