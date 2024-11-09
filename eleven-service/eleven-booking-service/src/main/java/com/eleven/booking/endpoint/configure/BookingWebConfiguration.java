package com.eleven.booking.endpoint.configure;

import com.eleven.booking.endpoint.support.CustomerSecurityInterceptor;
import com.eleven.core.web.utils.AnnotationPredicate;
import com.eleven.booking.endpoint.support.AsCustomerApi;
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
    private final  static   String API_PREFIX_ADMIN = "/api/admin";
    private final  static String API_PREFIX_MERCHANT = "/api/merchant";
    private final  static String API_PREFIX_CUSTOMER = "/api/customer";

    private final CustomerSecurityInterceptor customerSecurityInterceptor;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(customerSecurityInterceptor).addPathPatterns(API_PREFIX_MERCHANT + "/bookings/{hotelId}");
        registry.addInterceptor(customerSecurityInterceptor).addPathPatterns(API_PREFIX_MERCHANT + "/reservations/{hotelId}");
    }

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_PREFIX_CUSTOMER, new AnnotationPredicate(AsCustomerApi.class));
    }

    @Bean
    public GroupedOpenApi merchantApi() {
        return GroupedOpenApi.builder()
                .group("merchant-api")
                .displayName("merchant")
                .pathsToMatch(API_PREFIX_MERCHANT + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .displayName("admin")
                .pathsToMatch(API_PREFIX_ADMIN + "/**")
                .build();
    }

}
