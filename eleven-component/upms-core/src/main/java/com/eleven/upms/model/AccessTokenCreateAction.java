package com.eleven.upms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccessTokenCreateAction {
    private String identity;
    private String credential;
}
