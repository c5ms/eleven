package com.eleven.travel.domain.user.command;

import com.eleven.travel.domain.user.UserStatus;
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
