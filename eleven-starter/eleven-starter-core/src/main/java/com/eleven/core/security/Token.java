package com.eleven.core.security;

import com.eleven.core.time.TimeHelper;
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
    private LocalDateTime createAt = TimeHelper.localDateTime();
    private LocalDateTime expireAt = TimeHelper.localDateTime();
    private Principal principal;
    private TokenDetail detail;

}
