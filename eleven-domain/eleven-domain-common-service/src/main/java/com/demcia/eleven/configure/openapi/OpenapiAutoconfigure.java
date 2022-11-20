package com.demcia.eleven.configure.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenapiAutoconfigure {

    @Bean
    public OpenAPI customOpenAPI(OpenapiProperties properties) {
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
