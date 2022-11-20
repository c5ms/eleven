package com.demcia.eleven.app.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "demcia.eleven.open-api")
public class OpenapiProperties {
    private String title;
    private String description;
    private String version;
    private String termsOfService;

    private Contact contact;
    private License license;

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }

    @Data
    public static class License {
        private String name;
        private String url;
    }
}
