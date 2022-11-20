package com.eleven.core.web.config;

import com.eleven.core.web.WebConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenOpenapiProperties.class)
public class ElevenOpenapiConfiguration {

    @Bean
    public GroupedOpenApi merchantApi() {
        return GroupedOpenApi.builder()
            .group("merchant-api")
            .displayName("merchant")
            .pathsToMatch(WebConstants.API_PREFIX_MERCHANT + "/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
            .group("customer-api")
            .displayName("customer")
            .pathsToMatch(WebConstants.API_PREFIX_CUSTOMER + "/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("admin-api")
            .displayName("admin")
            .pathsToMatch(WebConstants.API_PREFIX_ADMIN + "/**")
            .build();
    }


    @Bean
    public GroupedOpenApi openApi() {
        return GroupedOpenApi.builder()
            .group("open-api")
            .displayName("open")
            .pathsToMatch(WebConstants.API_PREFIX_OPEN + "/**")
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
    public OpenAPI openAPI(ElevenOpenapiProperties properties) {
        return new OpenAPI()
            .info(new Info()
                .version(properties.getVersion())
                .title(properties.getTitle())
                .description(properties.getDescription())
                .termsOfService(properties.getTermsOfService())
                .contact(new Contact()
                    .name(properties.getContact().getName())
                    .url(properties.getContact().getUrl())
                    .email(properties.getContact().getEmail()))
                .license(new License()
                    .name(properties.getLicense().getName())
                    .url(properties.getLicense().getUrl()))
            );
    }

}
