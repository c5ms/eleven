package com.demcia.eleven.upms.core.dto;

import com.demcia.eleven.upms.core.enums.UserState;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String nickname;
    private String type;
    private UserState state = UserState.NORMAL;
    private Set<Long> roles = new HashSet<>();
}