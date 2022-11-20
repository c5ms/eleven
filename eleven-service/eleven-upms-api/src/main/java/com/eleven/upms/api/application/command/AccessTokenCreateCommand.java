package com.eleven.upms.api.application.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccessTokenCreateCommand {
    @NotBlank
    private String identity;

    @NotBlank
    private String credential;
}
