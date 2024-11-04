package com.eleven.core.web.config;

import com.eleven.core.authenticate.Subject;
import com.eleven.core.web.WebConstants;
import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.core.web.utils.AnnotationPredicate;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties({ElevenWebProperties.class})
public class ElevenWebConfiguration implements WebMvcConfigurer {

    private final ElevenWebProperties properties;
//    private final AuthenticationManager authenticationManager;

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
        configurer.addPathPrefix(WebConstants.API_PREFIX_INTERNAL, new AnnotationPredicate(AsInternalApi.class));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> builder
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(properties.getDateFormat())))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(properties.getTimeFormat())))
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(properties.getDatetimeFormat())))

                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(properties.getDateFormat())))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(properties.getTimeFormat())))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(properties.getDatetimeFormat())))

                .timeZone(properties.getTimeZone())
                .featuresToDisable(
                        SerializationFeature.FAIL_ON_EMPTY_BEANS,
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .featuresToEnable(
                        JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                        JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                        SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                        JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(),
                        JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(),
                        JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature()
                )
                .modules(
                        new Jdk8Module(),
                        new JavaTimeModule()/*,
                        new Hibernate6Module()*/
                );
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

    @Bean
    public GroupedOpenApi innerApi() {
        return GroupedOpenApi.builder()
                .group("inner-api")
                .displayName("internal")
                .pathsToMatch(WebConstants.API_PREFIX_INTERNAL + "/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(ElevenWebProperties properties) {
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
