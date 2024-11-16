package com.eleven.core.configure;

import com.eleven.core.application.authenticate.Subject;
import com.eleven.core.interfaces.rest.annonation.AsInternalApi;
import com.eleven.core.interfaces.rest.annonation.AsResourceApi;
import com.eleven.core.interfaces.rest.utils.AnnotationPredicate;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
@EnableConfigurationProperties({ElevenWebProperties.class})
class ElevenWebConfiguration implements WebMvcConfigurer {
    public final static String API_PREFIX_INTERNAL = "/api/internal";
    public final static String API_PREFIX_RESOURCE = "/api/resource";

    private final ElevenCoreProperties coreProperties;
    private final ElevenWebProperties properties;
//    private final AuthenticationManager authenticationManager;

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
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

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_PREFIX_INTERNAL, new AnnotationPredicate(AsInternalApi.class));
        configurer.addPathPrefix(API_PREFIX_RESOURCE, new AnnotationPredicate(AsResourceApi.class));
    }

    @Bean
    public GroupedOpenApi internalApi() {
        return GroupedOpenApi.builder()
            .group("inner-api")
            .displayName("internal")
            .pathsToMatch(API_PREFIX_INTERNAL + "/**")
            .build();
    }

    @Bean
    public GroupedOpenApi resourceApi() {
        return GroupedOpenApi.builder()
            .group("resource-api")
            .displayName("resource")
            .pathsToMatch(API_PREFIX_RESOURCE + "/**")
            .build();
    }


    @Bean
    public OpenAPI openAPI( ) {

        SpringDocUtils.getConfig().replaceWithSchema(LocalDate.class, new Schema<LocalDate>()
            .type("string")
            .format(coreProperties.getJson().getTimeFormat())
            .example(LocalDate.of(2024, 1, 1).format(DateTimeFormatter.ofPattern(coreProperties.getJson().getDateFormat()))));

        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class,  new Schema<LocalTime>()
            .type("string")
            .format(coreProperties.getJson().getTimeFormat())
            .example(LocalTime.of(20, 0,0).format(DateTimeFormatter.ofPattern(coreProperties.getJson().getTimeFormat()))));


        SpringDocUtils.getConfig().replaceWithSchema(LocalDateTime.class, new Schema<LocalDateTime>()
            .type("string")
            .format(coreProperties.getJson().getTimeFormat())
            .example(LocalDateTime.of(2024, 1, 1, 20, 0, 0).format(DateTimeFormatter.ofPattern(coreProperties.getJson().getDatetimeFormat()))));

        SpringDocUtils.getConfig().replaceWithSchema(YearMonth.class, new Schema<YearMonth>()
            .type("string")
            .format(coreProperties.getJson().getTimeFormat())
            .example(YearMonth.of(2024, 1).format(DateTimeFormatter.ofPattern(coreProperties.getJson().getYearMonthFormat()))));

        var openApiProperties = properties.getOpenapi();

        return new OpenAPI()
            .info(new Info()
                .version(openApiProperties.getVersion())
                .title(openApiProperties.getTitle())
                .description(openApiProperties.getDescription())
                .termsOfService(openApiProperties.getTermsOfService())
                .contact(new Contact()
                    .name(openApiProperties.getContact().getName())
                    .url(openApiProperties.getContact().getUrl())
                    .email(openApiProperties.getContact().getEmail()))
                .license(new License()
                    .name(openApiProperties.getLicense().getName())
                    .url(openApiProperties.getLicense().getUrl()))
            );
    }



}
