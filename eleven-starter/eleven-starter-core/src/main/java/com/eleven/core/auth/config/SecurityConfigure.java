package com.eleven.core.auth.config;

import com.eleven.core.application.authentic.*;
import com.eleven.core.auth.*;
import com.eleven.core.infrastructure.authentic.*;
import com.eleven.core.infrastructure.time.authentic.*;
import com.eleven.core.auth.support.OpaqueTokenCreator;
import com.eleven.core.auth.support.RedisSubjectStore;
import com.eleven.core.auth.support.RedisTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Configuration
public class SecurityConfigure {

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

    @Bean
    @ConditionalOnMissingBean(Authorizer.class)
    Authorizer authorizer() {
        return principal -> List.of();
    }

    @Bean
    @ConditionalOnMissingBean(SubjectCreator.class)
    SubjectCreator subjectCreator() {
        return principal -> Subject.ANONYMOUS_INSTANCE;
    }

}
