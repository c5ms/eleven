package com.demcia.eleven.upms.domain.action;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UserUpdateAction {

    private String nickname;

}
