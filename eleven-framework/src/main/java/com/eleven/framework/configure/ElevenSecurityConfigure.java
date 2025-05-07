package com.eleven.framework.configure;

import com.eleven.framework.security.*;
import com.eleven.framework.security.SecurityManager;
import com.eleven.framework.security.support.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@ComponentScan("com.eleven.framework.security")
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
@EnableConfigurationProperties({ElevenRestProperties.class})
final class ElevenSecurityConfigure {

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
    @Order(1)
    @ConditionalOnMissingBean(TokenStore.class)
    TokenStore tokenStore() {
        return new EmptyTokenStore();
    }

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(SubjectStore.class)
    SubjectStore subjectStore() {
        return new EmptySubjectStore();
    }

    @Bean
    @Order(2)
    SecurityManager securityService(TokenCreator tokenCreator,
                                    SubjectCreator subjectCreator,
                                    TokenStore tokenStore,
                                    SubjectStore subjectStore) {
        return new SecurityManager(tokenCreator,
            subjectCreator,
            tokenStore,
            subjectStore);
    }

    @Bean
    AuthenticationManager authenticationManager(SecurityManager authenticSupport) {
        return new ElevenAuthenticationManager(authenticSupport);
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
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .rememberMe(AbstractHttpConfigurer::disable)
            .oauth2Login(AbstractHttpConfigurer::disable)
            .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .anonymous(c -> c.principal(Subject.ANONYMOUS_INSTANCE))
            .authorizeHttpRequests(c -> c.anyRequest().permitAll())
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.NEVER))
//                .oauth2ResourceServer(oauth2 -> {
//                    oauth2.bearerTokenResolver(new ElevenTokenResolver()).opaqueToken(c -> c.authenticationManager(authenticationManager));
//                })
            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            .build();
    }


    @Configuration
    @ConditionalOnClass(RedisTemplate.class)
    static class RedisConfiguration {

        @Bean
        @Order(1)
        @ConditionalOnBean(RedisTemplate.class)
        TokenStore tokenStore(RedisTemplate<String, String> redisTemplate) {
            return new RedisTokenStore(redisTemplate);
        }

        @Bean
        @Order(1)
        @ConditionalOnBean(RedisTemplate.class)
        SubjectStore subjectStore(RedisTemplate<String, String> redisTemplate) {
            return new RedisSubjectStore(redisTemplate);
        }

    }


}
