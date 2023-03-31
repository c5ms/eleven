package com.demcia.eleven.upms.domain.action;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserGrantAction {

    private String type;

    private String name;

}
