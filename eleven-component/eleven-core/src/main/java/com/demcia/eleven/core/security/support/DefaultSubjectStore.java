package com.demcia.eleven.core.security.support;

import cn.hutool.extra.spring.SpringUtil;
import com.demcia.eleven.core.security.Principal;
import com.demcia.eleven.core.security.Subject;
import com.demcia.eleven.core.security.SubjectStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@CacheConfig(cacheNames = DefaultSubjectStore.CACHE_NAME)
public class DefaultSubjectStore implements SubjectStore {

    public final static String CACHE_NAME = "AuthorizedSubject";

    private final CacheManager cacheManager;

    private static String toKey(Principal principal) {
        return principal.identify() + "@" + SpringUtil.getApplicationName();
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
