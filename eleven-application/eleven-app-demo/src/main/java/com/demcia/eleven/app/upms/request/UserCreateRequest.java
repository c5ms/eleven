package com.demcia.eleven.app.upms.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCreateRequest implements Serializable {
    private  String username;
}
