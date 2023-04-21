package com.eleven.upms.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccessTokenCreateRequest {
    private String identity;
    private String credential;
}
