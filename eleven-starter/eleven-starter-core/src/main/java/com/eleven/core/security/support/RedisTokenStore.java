package com.eleven.core.security.support;

import cn.hutool.json.JSONUtil;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenStore;
import com.eleven.core.time.TimeContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore {

    public final static String KEY_PREFIX = "token";

    private final RedisTemplate<String, String> redisTemplate;

    private String toKey(String token) {
        return String.format("%s:%s", KEY_PREFIX, token);
    }

    @Override
    public void save(Token token) {
        var key = toKey(token.getValue());
        var value = JSONUtil.toJsonStr(token);
        var duration = TimeContext.localDateTime().until(token.getExpireAt(), ChronoUnit.DECADES);
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public Optional<Token> retrieval(String tokenValue) {
        var key = toKey(tokenValue);
        var valueStr = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(valueStr)
                .filter(StringUtils::isNotBlank)
                .map(s -> JSONUtil.toBean(s, Token.class));
    }
}
