package com.eleven.hotel.infrastructure.security;

import com.eleven.core.web.WebConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class HotelSecurityConfig implements WebMvcConfigurer {

    private final MerchantApiInterceptor merchantApiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(merchantApiInterceptor)
                .addPathPatterns(WebConstants.API_PREFIX_MERCHANT + "/**");
    }
}
