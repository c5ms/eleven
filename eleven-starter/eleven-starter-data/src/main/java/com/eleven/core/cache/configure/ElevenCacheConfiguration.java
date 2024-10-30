package com.eleven.core.cache.configure;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.cache.KryoRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@EnableCaching
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ElevenCacheProperties.class)
public class ElevenCacheConfiguration {

    private final ElevenCacheProperties properties;

    @Bean
    RedisSerializer<Object> redisSerializer() {
        return switch (properties.getSerializer()) {
            case JACKSON -> jackson2JsonRedisSerializer();
            case KRYO5 -> kryRedisSerializer();
        };
    }

    KryoRedisSerializer<Object> kryRedisSerializer() {
        return new KryoRedisSerializer<>();
    }

    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .configure(MapperFeature.USE_ANNOTATIONS, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .activateDefaultTyping(
                        BasicPolymorphicTypeValidator.builder()
                                .allowIfSubType(Serializable.class)
                                .build(),
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY
                )
                .addModules(
                        new Jdk8Module(),
                    new JavaTimeModule()
                )
                .build();

        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    @Bean
    RedisCacheConfiguration defaultCacheConfig(RedisSerializer<Object> redisSerializer) {
        var prefix = SpringUtil.getApplicationName();
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .entryTtl(properties.getDuration())
                .prefixCacheNameWith(prefix+":");
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
