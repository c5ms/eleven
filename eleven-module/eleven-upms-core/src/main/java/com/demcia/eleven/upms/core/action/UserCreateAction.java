package com.demcia.eleven.upms.core.action;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateAction {

    @NotBlank(message = "登入账号不能为空")
    private String login;

}
