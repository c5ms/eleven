package com.eleven.hotel.endpoint.secure;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.core.web.WebHelper;
import com.eleven.hotel.application.authorize.HotelAuthorizer;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MerchantInterceptor implements HandlerInterceptor {

    private final HotelService hotelRepository;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (handler instanceof HandlerMethod) {
            var variables = getAccess(request);

//            Optional.ofNullable(variables.get("hotelId"))
//                .map(Integer::parseInt)
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
