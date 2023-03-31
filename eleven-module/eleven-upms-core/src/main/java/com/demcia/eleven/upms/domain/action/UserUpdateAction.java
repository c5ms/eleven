package com.demcia.eleven.upms.domain.action;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserUpdateAction {

    private String nickname;

}
