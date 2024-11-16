package com.eleven.core.application.authenticate.support;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.authenticate.Principal;
import com.eleven.core.application.authenticate.Token;
import com.eleven.core.application.authenticate.TokenCreator;
import com.eleven.core.application.authenticate.TokenDetail;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class OpaqueTokenCreator implements TokenCreator {

    private String getIssuer() {
        return SpringUtil.getApplicationName();
    }

    private LocalDateTime getExpireAt() {
        return LocalDateTime.now().plusDays(10);
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
