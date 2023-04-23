package com.eleven.api.configure;

import com.eleven.ElevenApiApplication;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiAutoconfigure {

    @Bean
    public GroupedOpenApi messageApi() {
        return GroupedOpenApi.builder()
                .group("app")
                .displayName("一个示例应用层")
                .packagesToScan(
                        ElevenApiApplication.class.getPackageName()
                )
                .build();
    }

}
