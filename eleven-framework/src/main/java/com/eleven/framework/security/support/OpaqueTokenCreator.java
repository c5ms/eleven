package com.eleven.framework.security.support;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.framework.security.Principal;
import com.eleven.framework.security.Token;
import com.eleven.framework.security.TokenCreator;
import com.eleven.framework.security.TokenDetail;
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
