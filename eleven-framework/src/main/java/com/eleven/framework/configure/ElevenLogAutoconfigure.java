package com.eleven.framework.configure;

import com.eleven.framework.log.RequestLogInterceptor;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("com.eleven.framework.log")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ElevenLogAutoconfigure implements WebMvcConfigurer {

    private final RequestLogInterceptor sysLogInterceptor;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(sysLogInterceptor);
    }

}
