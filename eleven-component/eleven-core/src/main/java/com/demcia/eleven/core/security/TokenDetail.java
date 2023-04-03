package com.demcia.eleven.core.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TokenDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String clientIp;
}
