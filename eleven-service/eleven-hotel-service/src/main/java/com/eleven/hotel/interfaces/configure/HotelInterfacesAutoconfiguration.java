package com.eleven.hotel.interfaces.configure;

import com.eleven.core.web.utils.AnnotationPredicate;
import com.eleven.hotel.interfaces.support.AsAdminApi;
import com.eleven.hotel.interfaces.support.AsMerchantApi;
import com.eleven.hotel.interfaces.support.MerchantSecurityInterceptor;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HotelInterfacesProperties.class)
public class HotelInterfacesAutoconfiguration implements WebMvcConfigurer {

    private final static String API_PREFIX_ADMIN = "/api/admin";
    private final static String API_PREFIX_MERCHANT = "/api/merchant";

    private final MerchantSecurityInterceptor merchantSecurityInterceptor;

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(merchantSecurityInterceptor).addPathPatterns(API_PREFIX_MERCHANT + "/hotels/{hotelId}");
    }

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_PREFIX_ADMIN, new AnnotationPredicate(AsAdminApi.class));
        configurer.addPathPrefix(API_PREFIX_MERCHANT, new AnnotationPredicate(AsMerchantApi.class));
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
