package com.eleven.core.web.log.autoconfigure;

import com.eleven.core.web.log.RequestLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SysLogAutoconfigure implements WebMvcConfigurer {

    private final RequestLogInterceptor sysLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysLogInterceptor);
    }


}
