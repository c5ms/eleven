package com.eleven.domain.security;

import com.eleven.domain.hotel.HotelService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MerchantSecurityInterceptor implements HandlerInterceptor {

    private final HotelService hotelRepository;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (handler instanceof HandlerMethod) {
            var variables = getAccess(request);

//            Optional.ofNullable(variables.get("hotelId"))
//                .map(Long::parseInt)
//                .flatMap(hotelRepository::read)
//                .ifPresentOrElse(ApplicationHelper::mustWritable, WebHelper::notFoundException);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getAccess(HttpServletRequest request) {
        return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }


}
