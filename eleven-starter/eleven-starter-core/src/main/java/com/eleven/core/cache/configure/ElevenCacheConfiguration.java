package com.eleven.core.cache.configure;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.cache.KryoRedisSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenCacheProperties.class)
public class ElevenCacheConfiguration {

    private final ElevenCacheProperties properties;

    @Bean
    RedisSerializer<Object> kryRedisSerializer() {
        return new KryoRedisSerializer<>();
    }

    @Bean
    RedisCacheConfiguration defaultCacheConfig(RedisSerializer<Object> redisSerializer) {
        var prefix = SpringUtil.getApplicationName();
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .entryTtl(properties.getDuration())
                .prefixCacheNameWith(prefix + ":");
    }

    @Bean
    CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration defaultCacheConfig) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .enableCreateOnMissingCache()
                .transactionAware()
                .build();
    }

}
