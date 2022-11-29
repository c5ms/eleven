package com.demcia.eleven.openapi.configure;

import com.demcia.eleven.ApiElevenApplication;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoRestAutoconfigure {

    @Bean
    public GroupedOpenApi messageApi() {
        return GroupedOpenApi.builder()
                .group("app")
                .displayName("一个示例应用层")
                .packagesToScan(
                        ApiElevenApplication.class.getPackageName()
                )
                .build();
    }

}
