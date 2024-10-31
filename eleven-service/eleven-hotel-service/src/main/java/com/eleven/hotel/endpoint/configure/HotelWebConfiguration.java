package com.eleven.hotel.endpoint.configure;

import com.eleven.core.web.utils.AnnotationPredicate;
import com.eleven.hotel.endpoint.secure.MerchantInterceptor;
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
public class HotelWebConfiguration implements WebMvcConfigurer {
    private final  static String API_PREFIX_MERCHANT = "/api/merchant";

    private final MerchantInterceptor merchantInterceptor;

    @Override
    public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
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

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(merchantInterceptor).addPathPatterns(API_PREFIX_MERCHANT + "/**");
    }


}
