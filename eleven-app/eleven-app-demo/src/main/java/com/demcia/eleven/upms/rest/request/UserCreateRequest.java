package com.demcia.eleven.upms.rest.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCreateRequest implements Serializable {
    private String username;
}
