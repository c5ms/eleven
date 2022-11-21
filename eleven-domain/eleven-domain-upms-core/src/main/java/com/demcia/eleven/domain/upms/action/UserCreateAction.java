package com.demcia.eleven.domain.upms.action;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateAction {

    @NotBlank
    private String username;

}
