package com.demcia.eleven.api.configure;

import com.demcia.eleven.ElevenApiApplication;
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
