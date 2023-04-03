package com.demcia.eleven.upms.application.request;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserUpdateRequest {

    private String nickname;

}
