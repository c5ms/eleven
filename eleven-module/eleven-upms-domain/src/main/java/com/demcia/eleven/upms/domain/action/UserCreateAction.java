package com.demcia.eleven.upms.domain.action;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserCreateAction {
  private    String login;
     private    String nickname;
}
