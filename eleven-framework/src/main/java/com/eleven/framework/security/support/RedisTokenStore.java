package com.eleven.framework.security.support;

import cn.hutool.json.JSONUtil;
import com.eleven.framework.security.Token;
import com.eleven.framework.security.TokenStore;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


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
        var duration = LocalDateTime.now().until(token.getExpireAt(), ChronoUnit.DECADES);
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public void remove(String tokenValue) {
        var key = toKey(tokenValue);
        redisTemplate.delete(key);
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
