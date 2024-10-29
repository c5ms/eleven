package com.eleven.hotel.endpoint.secure;

import com.eleven.hotel.domain.model.hotel.HotelRepository;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MerchantInterceptor implements HandlerInterceptor {

    private final HotelRepository hotelRepository;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            var variables = getAccess(request);
            log.info("request uri variables = {}", variables);

            var hotelId = variables.get("hotelId");
            if (StringUtils.isNotBlank(hotelId)) {

//                hotelRepository.findByHotelId(hotelId)
//                    .filter(ApplicationContext::mustReadable)
//                    .orElseThrow(ApplicationContext::noResource);

            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getAccess(HttpServletRequest request) {
        return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }


}
