package com.eleven.upms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCreatedEventDto implements Serializable {
    private String userId;
}
