package com.eleven.framework.configure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import com.eleven.framework.web.DownloadHttpMessageConverter;
import com.eleven.framework.web.annonation.AsInnerApi;
import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.framework.web.utils.AnnotationPredicate;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@ComponentScan("com.eleven.framework.web")
@EnableElevenCore
@EnableConfigurationProperties(ElevenRestProperties.class)
@RequiredArgsConstructor
final class ElevenRestConfiguration implements WebMvcConfigurer {

  public final static String API_PREFIX_INTERNAL = "/inner";
  public final static String API_PREFIX_REST = "/api";

  private final ElevenCoreProperties coreProperties;
  private final ElevenRestProperties properties;

  @Override
  public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
    configurer.addPathPrefix(API_PREFIX_INTERNAL, new AnnotationPredicate(AsInnerApi.class));
    configurer.addPathPrefix(API_PREFIX_REST, new AnnotationPredicate(AsRestApi.class));
  }

  @Bean
  @ConditionalOnBean(ObjectMapper.class)
  ModelResolver modelResolver(ObjectMapper objectMapper) {
    return new ModelResolver(objectMapper);
  }

  @Bean
  @ConditionalOnMissingBean
  HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
    return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
  }

  @Bean
  GroupedOpenApi restApi() {
    return GroupedOpenApi.builder()
      .group("rest-api")
      .displayName("rest")
      .pathsToMatch(API_PREFIX_REST + "/**")
      .build();
  }

  @Bean
  GroupedOpenApi innerApi() {
    return GroupedOpenApi.builder()
      .group("inner-api")
      .displayName("internal")
      .pathsToMatch(API_PREFIX_INTERNAL + "/**")
      .build();
  }


  @Bean
  OpenAPI openAPI() {

    SpringDocUtils.getConfig().replaceWithSchema(LocalDate.class, new Schema<LocalDate>()
      .type("string")
      .format(coreProperties.getJson().getTimeFormat())
      .example(LocalDate.of(2024, 1, 1).format(DateTimeFormatter.ofPattern(coreProperties.getJson().getDateFormat()))));

    SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, new Schema<LocalTime>()
      .type("string")
      .format(coreProperties.getJson().getTimeFormat())
      .example(LocalTime.of(20, 0, 0).format(DateTimeFormatter.ofPattern(coreProperties.getJson().getTimeFormat()))));

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
      .servers(List.of())
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

  @Bean
  DownloadHttpMessageConverter downloadHttpMessageConverter() {
    return new DownloadHttpMessageConverter();
  }


}
