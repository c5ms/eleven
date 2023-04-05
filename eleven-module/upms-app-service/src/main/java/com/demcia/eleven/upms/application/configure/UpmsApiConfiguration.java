package com.demcia.eleven.upms.application.configure;

import com.demcia.eleven.upms.application.endpoint.UserResourceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class UpmsApiConfiguration {

    @Bean
    public GroupedOpenApi upmsApi() {
        return GroupedOpenApi.builder()
                .group("upms")
                .displayName("用户权限")
                .packagesToScan(UserResourceV1.class.getPackageName())
                .build();
    }

}
