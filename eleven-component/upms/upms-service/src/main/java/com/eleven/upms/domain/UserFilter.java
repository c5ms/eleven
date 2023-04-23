package com.eleven.upms.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UserFilter {

    private String login;

    private String type;

    private String state;


}
