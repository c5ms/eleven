package com.eleven.upms.configure;

import com.eleven.core.security.SubjectStore;
import com.eleven.core.security.TokenCreator;
import com.eleven.core.security.TokenStore;
import com.eleven.core.security.support.RedisSubjectStore;
import com.eleven.core.security.support.RedisTokenStore;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.support.OpaqueTokenCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@CacheConfig(cacheNames = {
    UpmsConstants.CACHE_NAME_AUTHORITY
})
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(UpmsProperties.class)
public class UpmsConfiguration {

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    TokenStore tokenStore(RedisTemplate<String, String> redisTemplate) {
        return new RedisTokenStore(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(SubjectStore.class)
    SubjectStore subjectStore(RedisTemplate<String, String> redisTemplate) {
        return new RedisSubjectStore(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(TokenCreator.class)
    TokenCreator tokenCreator() {
        return new OpaqueTokenCreator();
    }

}
