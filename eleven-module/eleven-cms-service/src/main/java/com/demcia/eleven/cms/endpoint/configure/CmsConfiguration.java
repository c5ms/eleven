package com.demcia.eleven.cms.endpoint.configure;

import com.demcia.eleven.cms.CmsModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class CmsConfiguration {

    @Bean
    public GroupedOpenApi cmsApi() {
        return GroupedOpenApi.builder()
                .group("cms")
                .displayName("内容管理")
                .packagesToScan(CmsModule.class.getPackageName())
                .build();
    }

}
