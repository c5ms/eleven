package com.eleven.core.authorization.config;

import com.eleven.core.authorization.SecurityManager;
import com.eleven.core.authorization.*;
import com.eleven.core.authorization.support.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

import java.util.List;
import java.util.Optional;

@Configuration
public class AuthorizationConfigure {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    @ConditionalOnMissingBean(TokenStore.class)
    TokenStore tokenStore(RedisTemplate<String, String> redisTemplate) {
        return new RedisTokenStore(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
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


    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    TokenStore noTokenStore() {
        return  new TokenStore() {
            @Override
            public void save(Token token) {

            }

            @Override
            public void remove(String tokenValue) {

            }

            @Override
            public Optional<Token> retrieval(String tokenValue) {
                return Optional.empty();
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(SubjectStore.class)
    SubjectStore noSubjectStore() {
        return new SubjectStore() {
            @Override
            public void save(Principal principal, Subject subject) {

            }

            @Override
            public void remove(Principal principal) {

            }

            @Override
            public Optional<Subject> retrieval(Principal principal) {
                return Optional.empty();
            }
        };
    }



    @Bean
    SecurityManager securityManager(TokenCreator tokenCreator,
                                    SubjectCreator subjectCreator,
                                    TokenStore tokenStore,
                                    SubjectStore subjectStore) {
        return new SecurityManager(tokenCreator,
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
    AuthenticationManager authenticationManager(SecurityManager securityManager) {
        return new ElevenAuthenticationManager(securityManager);
    }

}
