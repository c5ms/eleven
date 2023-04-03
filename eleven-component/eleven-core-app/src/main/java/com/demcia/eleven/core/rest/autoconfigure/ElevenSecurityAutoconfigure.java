package com.demcia.eleven.core.rest.autoconfigure;

import com.demcia.eleven.core.rest.support.ElevenBearerTokenResolver;
import com.demcia.eleven.core.security.Subject;
import com.demcia.eleven.core.security.TokenReader;
import com.demcia.eleven.core.security.support.ElevenAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class ElevenSecurityAutoconfigure {

    private final ElevenAuthenticationManager elevenAuthenticationManager;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // @formatter:off
        http
            .csrf().disable()
            .cors().disable()
            .logout().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .rememberMe().disable()
            .oauth2Login().disable()
            .headers().frameOptions().disable()
            .and()
            .anonymous().principal(Subject.ANONYMOUS_INSTANCE)
            .and()
            . authorizeHttpRequests().anyRequest().permitAll()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
            .oauth2ResourceServer(configurer ->
                configurer
                    .bearerTokenResolver(new ElevenBearerTokenResolver())
                    .opaqueToken()
                    .authenticationManager(elevenAuthenticationManager)
            )
            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
        ;
        // @formatter:on

        return http.build();
    }


    @Configuration
    @RequiredArgsConstructor
    public static class NoneSecurityAutoconfigure {

        @Bean
        @ConditionalOnMissingBean
        public TokenReader unSupportTokenReader() {
            return token -> Optional.empty();
        }


    }

}