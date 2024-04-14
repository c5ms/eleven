package com.eleven.gateway.core;

import com.eleven.core.security.Principal;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;

@Getter
public class GatewayToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String value;
    private final String issuer;
    private final LocalDateTime expireAt;
    private final Principal principal;

    @Builder
    public GatewayToken(String value, String issuer, LocalDateTime expireAt, Principal principal) {
        this.value = value;
        this.issuer = issuer;
        this.expireAt = expireAt;
        this.principal = principal;
    }

    /**
     * 读取凭证是否过期
     *
     * @param clock 时钟
     * @return true 表示凭证过期了
     */
    public boolean isExpired(Clock clock) {
        if (null == this.getExpireAt()) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now(clock);
        return getExpireAt().isBefore(now);
    }

}
