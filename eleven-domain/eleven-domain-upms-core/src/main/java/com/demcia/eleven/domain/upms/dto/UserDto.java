package com.demcia.eleven.domain.upms.dto;

import com.demcia.eleven.domain.upms.enums.UserState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
public class UserDto implements Serializable {


    private String id;
    private String username;
    private String password;
    private String displayName;
    private String fromId;
    @Schema
    private String type ;
    private UserState state = UserState.NORMAL;
    private Set<String> roles = new HashSet<>();
}