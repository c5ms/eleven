package com.eleven.access.api.configure;

import com.cnetong.access.api.message.MessageApiV1;
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
                .group("message")
                .displayName("数据平台")
                .packagesToScan(
                        MessageApiV1.class.getPackageName()
                )
                .build();
    }

}
