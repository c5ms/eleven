package com.demcia.eleven.alfred.endpoint.configure;

import com.demcia.eleven.alfred.AlfredModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AfricaConfiguration {

    @Bean
    public GroupedOpenApi africaApi() {
        return GroupedOpenApi.builder()
                .group("africa")
                .displayName("项目管理")
                .packagesToScan(
                        AlfredModule.class.getPackageName()
                )
                .build();
    }

}
