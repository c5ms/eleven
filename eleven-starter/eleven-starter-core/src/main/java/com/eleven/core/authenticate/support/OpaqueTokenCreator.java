package com.eleven.core.authenticate.support;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.authenticate.Principal;
import com.eleven.core.authenticate.Token;
import com.eleven.core.authenticate.TokenCreator;
import com.eleven.core.authenticate.TokenDetail;
import com.eleven.core.time.TimeContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class OpaqueTokenCreator implements TokenCreator {

    private String getIssuer() {
        return SpringUtil.getApplicationName();
    }

    private LocalDateTime getExpireAt() {
        return TimeContext.localDateTime().plusDays(10);
    }

    @Override
    public Token create(Principal principal, TokenDetail detail) {
        var value = IdUtil.fastSimpleUUID();
        return new Token()
            .setIssuer(getIssuer())
            .setExpireAt(getExpireAt())
            .setPrincipal(principal)
            .setDetail(detail)
            .setValue(value);
    }
}
