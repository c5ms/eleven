package com.eleven.booking.endpoint.configure;

import com.eleven.booking.endpoint.support.AsCustomerApi;
import com.eleven.booking.endpoint.support.CustomerSecurityInterceptor;
import com.eleven.core.web.utils.AnnotationPredicate;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class BookingWebConfiguration implements WebMvcConfigurer {
    private final static String API_PREFIX_CUSTOMER = "/api/customer";

    private final CustomerSecurityInterceptor customerSecurityInterceptor;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(customerSecurityInterceptor).addPathPatterns(API_PREFIX_CUSTOMER + "/customer");
    }

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_PREFIX_CUSTOMER, new AnnotationPredicate(AsCustomerApi.class));
    }

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
            .group("customer-api")
            .displayName("customer")
            .pathsToMatch(API_PREFIX_CUSTOMER + "/**")
            .build();
    }


}
