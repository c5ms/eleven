package com.eleven.booking.api.endpoint.internal;

import com.eleven.core.web.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Primary
@FeignClient(value = "booking", path = WebConstants.API_PREFIX_INTERNAL)
public interface BookingClient {

}
