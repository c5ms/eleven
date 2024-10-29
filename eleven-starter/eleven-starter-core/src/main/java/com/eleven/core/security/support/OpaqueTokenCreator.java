package com.eleven.core.security.support;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenCreator;
import com.eleven.core.security.TokenDetail;
import com.eleven.core.time.TimeHelper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class OpaqueTokenCreator implements TokenCreator {

    private String getIssuer() {
        return SpringUtil.getApplicationName();
    }

    private LocalDateTime getExpireAt() {
        return TimeHelper.localDateTime().plusDays(10);
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
