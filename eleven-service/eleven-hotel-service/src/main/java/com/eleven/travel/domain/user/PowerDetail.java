package com.eleven.travel.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "用户权限", name = "Power")
@Getter
@Setter
public class PowerDetail {
    private String type;
    private String name;
}
