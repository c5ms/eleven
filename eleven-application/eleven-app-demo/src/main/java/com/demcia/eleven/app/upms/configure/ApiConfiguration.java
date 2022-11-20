package com.demcia.eleven.app.upms.configure;

import com.demcia.eleven.ElevenApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ApiConfiguration {

    @Bean
    public GroupedOpenApi messageApi() {
        return GroupedOpenApi.builder()
                .group("app")
                .displayName("一个示例应用层")
                .packagesToScan(
                        ElevenApplication.class.getPackageName()
                )
                .build();
    }

}
