package com.eleven.core.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "eleven.web")
class ElevenWebProperties {

    private ElevenOpenapiProperties openapi = new ElevenOpenapiProperties();

    @Data
    public static class ElevenOpenapiProperties {
        private String title;
        private String version;
        private String description;
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
}
