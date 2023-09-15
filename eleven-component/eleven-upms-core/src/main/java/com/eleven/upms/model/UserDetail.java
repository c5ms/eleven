package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Schema(description = "用户详情", name = "user")
@Getter
@Setter
@Accessors(chain = true)
public class UserDetail extends UserDto {

    private List<String> roles = new ArrayList<>();

}
