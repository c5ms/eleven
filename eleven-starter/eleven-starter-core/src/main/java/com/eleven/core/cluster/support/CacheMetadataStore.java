package com.eleven.core.cluster.support;

import com.eleven.core.cluster.Metadata;
import com.eleven.core.cluster.MetadataException;
import com.eleven.core.cluster.MetadataStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;

@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheMetadataStore.CACHE_NAME)
public class CacheMetadataStore implements MetadataStore {

    public final static String CACHE_NAME = "SystemMetadata";

    private final CacheManager cacheManager;

    @Override
    public Metadata put(String name, String data) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (null == cache) {
            return new Metadata(name, null);
        }
        cache.put(name, data);
        return new Metadata(name, data);
    }

    @Override
    public Metadata get(String name) {
        try {
            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (null == cache) {
                return new Metadata(name, null);
            }
            String value = cache.get(name, String.class);
            return new Metadata(name, value);
        } catch (Exception e) {
            throw new MetadataException(String.format("读取元数据异常:%s",name), e);
        }
    }
}
