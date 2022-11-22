package com.demcia.eleven.domain.upms.dto;

import com.demcia.eleven.domain.upms.enums.UserState;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
public class UserDto implements Serializable {
    private String id;
    private String username;
    private String nickname;
    private String type;
    private UserState state = UserState.NORMAL;
    private Set<String> roles = new HashSet<>();
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}