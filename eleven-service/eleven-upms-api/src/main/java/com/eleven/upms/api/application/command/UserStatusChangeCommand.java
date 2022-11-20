package com.eleven.upms.api.application.command;

import com.eleven.upms.api.domain.model.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UserStatusChangeCommand {

    @Schema(description = "user status")
    @NotBlank(message = "status is required")
    private UserStatus status;

}
