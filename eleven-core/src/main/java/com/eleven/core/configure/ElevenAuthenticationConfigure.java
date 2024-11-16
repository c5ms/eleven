package com.eleven.core.configure;

import com.eleven.core.application.authenticate.*;
import com.eleven.core.application.authenticate.support.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

import java.util.List;


class ElevenAuthenticationConfigure {

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(TokenStore.class)
    TokenStore tokenStore(RedisTemplate<String, String> redisTemplate) {
        return new RedisTokenStore(redisTemplate);
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(SubjectStore.class)
    SubjectStore subjectStore(RedisTemplate<String, String> redisTemplate) {
        return new RedisSubjectStore(redisTemplate);
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(TokenCreator.class)
    TokenCreator tokenCreator() {
        return new OpaqueTokenCreator();
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(Authorizer.class)
    Authorizer authorizer() {
        return principal -> List.of();
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(SubjectCreator.class)
    SubjectCreator subjectCreator() {
        return principal -> Subject.ANONYMOUS_INSTANCE;
    }

    @Bean
    @Order(2)
    AuthenticSupport securityManager(TokenCreator tokenCreator,
                                     SubjectCreator subjectCreator,
                                     TokenStore tokenStore,
                                     SubjectStore subjectStore) {
        return new AuthenticSupport(tokenCreator,
            subjectCreator,
            tokenStore,
            subjectStore);
    }

    @Bean
    BearerTokenResolver tokenResolver() {
        return new ElevenTokenResolver();
    }

    @Bean
    AuthenticationTrustResolver authenticationTrustResolver() {
        return new ElevenAuthenticationTrustResolver();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticSupport authenticSupport) {
        return new ElevenAuthenticationManager(authenticSupport);
    }

}
