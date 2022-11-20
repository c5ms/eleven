package com.demcia.eleven.upms.domain.action;

import com.demcia.eleven.upms.constants.UpmsConstants;
import com.demcia.eleven.upms.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateAction {

    @NotBlank
    private String username;

}
