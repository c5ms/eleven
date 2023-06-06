package com.eleven.core.rest.autoconfigure;

import com.eleven.core.rest.RestRequestArgumentResolver;
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
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
@PropertySource("classpath:/config/application-rest.properties")
public class ElevenRestAutoconfigure implements WebMvcConfigurer {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    private final ElevenRestProperties elevenRestProperties;
    private final RestRequestArgumentResolver restRequestArgumentResolver;

    @Value("${epeius.rest.date-format:yyyy-MM-dd}")
    private String dateFormat = DEFAULT_DATE_FORMAT;
    @Value("${epeius.rest.time-format:HH:mm:ss}")
    private String timeFormat = DEFAULT_DATE_FORMAT;
    @Value("${epeius.rest.date-time-format:yyyy-MM-dd HH:mm:ss}")
    private String datetimeFormat = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;
    @Value("${epeius.rest.time-zone:GMT+8}")
    private String timeZone = "GMT+8";

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(elevenRestProperties.getPrefix(), new RestfulPredicate(
                elevenRestProperties.getPackages(),
                elevenRestProperties.getAnnotations()
        ));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(restRequestArgumentResolver);
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

        private final List<String> packages;
        private final List<Class<? extends Annotation>> annotations;

        @Override
        public boolean test(Class<?> aClass) {

            for (String aPackage : packages) {
                if (aClass.getPackageName().startsWith(aPackage)) {
                    return true;
                }
            }
            for (Class<? extends Annotation> annotatedInterface : annotations) {
                if (null != aClass.getAnnotation(annotatedInterface)) {
                    return true;
                }
            }
            return false;
        }
    }
}
