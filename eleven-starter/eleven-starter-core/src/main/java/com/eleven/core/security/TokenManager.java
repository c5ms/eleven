package com.eleven.core.security;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.time.TimeContext;
import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenManager {
    private final TokenReader tokenReader;
    private final TokenStore tokenStore;

    /**
     * verify a token , fetch real token object from token store
     *
     * @param value the token pure value
     * @return the token if it is existing, or empty
     */
    public Optional<Token> verifyOpaqueToken(String value) {
        // TODO 这里少一个过滤器机制，过滤那些无效的 token，否则用户可以用一个无效的 token，无限访问造成 Dos
        // consider using JWT instead of opaque token，the benefits is that we can check token immediately, ensure the token is a valid token

        // 本地缓存
        var token = tokenStore.retrieval(value);

        // 检查 token 是否还有效
        var needRefresh = token.map(this::verify).orElse(true);

        // 本地没有缓存
        if (needRefresh) {
            token = tokenReader.read(value);
            token.ifPresent(tokenStore::save);
            log.debug("refresh token and save to the token store");
        }

        // 无有效 token
        return token;

    }

    public Optional<Token> verifyJwtToken(JWT jwt) {
        throw new NotImplementedException("don't support create jwt token right now !");
    }

    public Token createOpaqueToken(Principal principal, TokenDetail detail) {
        var value = IdUtil.fastSimpleUUID();
        // 用户的令牌可用 10 天,但是目前还没有超时的处理
        return new Token()
                .setIssuer(getIssuer())
                .setExpireAt(getExpireAt())
                .setPrincipal(principal)
                .setDetail(detail)
                .setValue(value);
    }

    public Token createJwtToken(Principal principal, TokenDetail detail) {
         throw new NotImplementedException("don't support create jwt token right now !");
    }

    private String getIssuer() {
        return SpringUtil.getApplicationName();
    }

    private LocalDateTime getExpireAt() {
        return TimeContext.localDateTime().plusDays(10);
    }

    private boolean verify(Token token) {
        log.debug("found token from local store for {} {}", token.getPrincipal(), token.getValue());

        // token must not be expired
        if (token.getExpireAt().isBefore(TimeContext.localDateTime())) {
            return false;
        }

        // token must be issued by a range of secure issuers
        if (StringUtils.equals(token.getIssuer(), getIssuer())) {
            return false;
        }

        return true;
    }
}
