package com.eleven.core.web.config;

import com.eleven.core.web.WebConstants;
import com.eleven.core.endpoint.web.annonation.*;
import com.eleven.core.web.annonation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenWebProperties.class)
@PropertySource("classpath:/config/application-core.properties")
public class ElevenWebConfiguration implements WebMvcConfigurer {

    private final ElevenWebProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(WebConstants.API_PREFIX_INTERNAL, new RestfulPredicate(List.of(AsInternalApi.class)));
        configurer.addPathPrefix(WebConstants.API_PREFIX_MERCHANT, new RestfulPredicate(List.of(AsMerchantApi.class)));
        configurer.addPathPrefix(WebConstants.API_PREFIX_CUSTOMER, new RestfulPredicate(List.of(AsCustomerApi.class)));
        configurer.addPathPrefix(WebConstants.API_PREFIX_OPEN, new RestfulPredicate(List.of(AsOpenApi.class)));
        configurer.addPathPrefix(WebConstants.API_PREFIX_ADMIN, new RestfulPredicate(List.of(AsAdminApi.class)));
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
                new JavaTimeModule()
            );
    }

    @RequiredArgsConstructor
    public static class RestfulPredicate implements Predicate<Class<?>> {

        private final List<Class<? extends Annotation>> annotations;

        @Override
        public boolean test(Class<?> aClass) {
            for (Class<? extends Annotation> annotatedInterface : annotations) {
                if (null != aClass.getAnnotation(annotatedInterface)) {
                    return true;
                }
            }
            return false;
        }
    }
}
