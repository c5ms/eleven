package com.eleven.upms.action;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccessTokenCreateAction {
    @NotBlank
    private String identity;

    @NotBlank
    private String credential;
}
