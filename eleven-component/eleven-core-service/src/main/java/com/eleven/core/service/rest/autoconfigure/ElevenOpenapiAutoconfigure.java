package com.eleven.core.service.rest.autoconfigure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenOpenapiProperties.class)
public class ElevenOpenapiAutoconfigure {

    @Bean
    public OpenAPI customOpenAPI(ElevenOpenapiProperties properties) {
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