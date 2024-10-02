package com.eleven.core.security.config;

import com.eleven.core.security.Subject;
import com.eleven.core.security.support.ElevenTokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class ElevenSecurityConfigure {

    protected final AuthenticationManager authenticationManager;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .rememberMe(AbstractHttpConfigurer::disable)
            .oauth2Login(AbstractHttpConfigurer::disable)
            .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .anonymous(c -> c.principal(Subject.ANONYMOUS_INSTANCE))

            //non-trust  or ful-trust ?

//                .authorizeHttpRequests(c -> c.requestMatchers(RestConstants.ADMIN_API_PREFIX +"/**" ).permitAll())
//                .authorizeHttpRequests(c -> c.requestMatchers(RestConstants.CLIENT_API_PREFIX +"/**" ).permitAll())
//                .authorizeHttpRequests(c -> c.requestMatchers(RestConstants.SERVICE_API_PREFIX+"/**" ).authenticated())
//                .authorizeHttpRequests(c -> c.requestMatchers(RestConstants.OPEN_API_PREFIX+"/**" ).authenticated())
            .authorizeHttpRequests(c -> c.anyRequest().permitAll())

            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.NEVER))

            .oauth2ResourceServer(oauth2 -> {
                oauth2.bearerTokenResolver(new ElevenTokenResolver())
                    .opaqueToken(c -> c.authenticationManager(authenticationManager));
            })

            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
        ;

        return http.build();
    }
}
