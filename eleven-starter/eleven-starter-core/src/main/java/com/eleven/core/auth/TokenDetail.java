package com.eleven.core.auth;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Hidden
@Accessors(chain = true)
public class TokenDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String clientIp;
}
