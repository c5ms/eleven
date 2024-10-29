package com.eleven.hotel.endpoint.secure;

import com.eleven.core.web.WebConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class HotelSecurityConfig implements WebMvcConfigurer {

    private final MerchantInterceptor merchantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(merchantInterceptor)
            .addPathPatterns(WebConstants.API_PREFIX_MERCHANT + "/**");
    }
}
