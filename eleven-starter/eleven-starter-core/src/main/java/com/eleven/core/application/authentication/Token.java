package com.eleven.core.application.authentication;

import com.eleven.core.time.TimeContext;
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
    private LocalDateTime createAt = TimeContext.localDateTime();
    private LocalDateTime expireAt = TimeContext.localDateTime();
    private Principal principal;
    private TokenDetail detail;

}
