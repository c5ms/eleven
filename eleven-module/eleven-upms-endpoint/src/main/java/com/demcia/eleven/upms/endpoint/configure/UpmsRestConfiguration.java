package com.demcia.eleven.upms.endpoint.configure;

import com.demcia.eleven.upms.UpmsModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class UpmsRestConfiguration {

    @Bean
    public GroupedOpenApi messageApi() {
        return GroupedOpenApi.builder()
                .group("upms")
                .displayName("用户权限")
                .packagesToScan(UpmsModule.class.getPackageName())
                .build();
    }

}
