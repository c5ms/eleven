package com.eleven.configure;

import com.eleven.UpmsApplication;
import com.eleven.core.web.RestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class OpenapiConfiguration {

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("open")
                .displayName("开放 API")
                .pathsToMatch(RestConstants.OPEN_API_PREFIX + "/**")
                .packagesToScan(UpmsApplication.class.getPackageName())
                .build();
    }

    @Bean
    public GroupedOpenApi frontApi() {
        return GroupedOpenApi.builder()
                .group("front")
                .displayName("前端 API")
                .pathsToMatch(RestConstants.FRONT_API_PREFIX + "/**")
                .packagesToScan(UpmsApplication.class.getPackageName())
                .build();
    }


    @Bean
    public GroupedOpenApi innerApi() {
        return GroupedOpenApi.builder()
                .group("inner")
                .displayName("内部 API")
                .pathsToMatch(RestConstants.INNER_API_PREFIX + "/**")
                .packagesToScan(UpmsApplication.class.getPackageName())
                .build();
    }


}
