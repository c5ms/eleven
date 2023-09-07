package com.eleven.core.configure;

import com.eleven.core.rest.RestConstants;
import com.eleven.core.rest.annonation.AsInnerApi;
import com.eleven.core.rest.annonation.AsRestApi;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenRestProperties.class)
@PropertySource("classpath:/config/application-core.properties")
public class ElevenRestAutoconfigure implements WebMvcConfigurer {


    @Value("${epeius.rest.date-format:yyyy-MM-dd}")
    private String dateFormat = RestConstants.DEFAULT_DATE_FORMAT;
    @Value("${epeius.rest.time-format:HH:mm:ss}")
    private String timeFormat = RestConstants.DEFAULT_DATE_FORMAT;
    @Value("${epeius.rest.date-time-format:yyyy-MM-dd HH:mm:ss}")
    private String datetimeFormat = RestConstants.DEFAULT_DATE_FORMAT + " " + RestConstants.DEFAULT_TIME_FORMAT;
    @Value("${epeius.rest.time-zone:GMT+8}")
    private String timeZone = "GMT+8";

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 内部 API
        configurer.addPathPrefix(RestConstants.INNER_API_PREFIX, new RestfulPredicate(List.of(AsInnerApi.class)));
        // 外部 API
        configurer.addPathPrefix(RestConstants.FRONT_API_PREFIX, new RestfulPredicate(List.of(AsRestApi.class)));
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> builder
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(datetimeFormat)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(timeFormat)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(datetimeFormat)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timeFormat)))
                .timeZone(TimeZone.getTimeZone(timeZone))
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
