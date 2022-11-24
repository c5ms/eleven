package com.demcia.eleven.upms.core.action;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateAction {

    @NotBlank
    private String username;

}
