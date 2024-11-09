package com.eleven.hotel.endpoint.configure;

import com.eleven.core.web.WebConstants;
import com.eleven.core.web.utils.AnnotationPredicate;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.endpoint.support.AsAdminApi;
import com.eleven.hotel.endpoint.support.AsCustomerApi;
import com.eleven.hotel.endpoint.support.AsMerchantApi;
import com.eleven.hotel.endpoint.support.MerchantSecurityInterceptor;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class HotelWebConfiguration implements WebMvcConfigurer {
    private final  static   String API_PREFIX_ADMIN = "/api/admin";
    private final  static String API_PREFIX_MERCHANT = "/api/merchant";
    private final  static String API_PREFIX_CUSTOMER = "/api/customer";

    private final MerchantSecurityInterceptor merchantSecurityInterceptor;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(merchantSecurityInterceptor).addPathPatterns(API_PREFIX_MERCHANT + "/hotels/{hotelId}");
    }

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_PREFIX_ADMIN, new AnnotationPredicate(AsAdminApi.class));
        configurer.addPathPrefix(API_PREFIX_MERCHANT, new AnnotationPredicate(AsMerchantApi.class));
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
