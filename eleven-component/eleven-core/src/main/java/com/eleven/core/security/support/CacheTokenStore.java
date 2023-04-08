package com.eleven.core.security.support;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@CacheConfig(cacheNames = CacheTokenStore.CACHE_NAME)
public class CacheTokenStore implements TokenStore {

    public final static String CACHE_NAME = "AuthorizedToken";

    private final CacheManager cacheManager;

    private static String toKey(String token) {
        return token + "@" + SpringUtil.getApplicationName();
    }

    @Override
    public void save(Token token) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;
        cache.put(toKey(token.getValue()), token);
    }

    @Override
    public Optional<Token> retrieval(String tokenValue) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;
        var token = cache.get(toKey(tokenValue), Token.class);
        return Optional.ofNullable(token);
    }
}
