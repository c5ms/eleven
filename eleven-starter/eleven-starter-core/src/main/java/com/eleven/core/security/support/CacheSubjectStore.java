package com.eleven.core.security.support;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.core.security.SubjectStore;
import com.eleven.core.security.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@CacheConfig(cacheNames = CacheSubjectStore.CACHE_NAME)
@RequiredArgsConstructor
public class CacheSubjectStore implements SubjectStore {

    public final static String CACHE_NAME = "AuthorizedSubject";

    private final CacheManager cacheManager;

    private static String toKey(Principal principal) {
        return principal.identify() ;
    }

    @Override
    public void save(Principal principal, Subject subject) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;
        cache.put(toKey(principal), subject);
    }

    @Override
    public Optional<Subject> retrieval(Principal principal) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;
        return Optional.ofNullable(cache.get(toKey(principal), Subject.class));
    }


}
