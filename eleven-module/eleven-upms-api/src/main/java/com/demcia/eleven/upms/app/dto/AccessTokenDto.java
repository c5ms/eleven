package com.demcia.eleven.upms.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "访问令牌")
@Data
public class AccessTokenDto {
    private String token;

    private LocalDateTime createAt;

    private LocalDateTime expireAt;
}
