package com.eleven.core.application.authenticate;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Token implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String value;
    private String issuer;
    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime expireAt = LocalDateTime.now();
    private Principal principal;
    private TokenDetail detail;

}
