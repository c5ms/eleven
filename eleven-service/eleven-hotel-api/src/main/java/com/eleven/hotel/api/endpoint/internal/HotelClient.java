package com.eleven.hotel.api.endpoint.internal;

import com.eleven.core.web.WebConstants;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Primary
@FeignClient(value = "hotel", path = WebConstants.API_PREFIX_INTERNAL)
public interface HotelClient {

    @Operation(summary = "read hotel")
    @GetMapping("/readHotel")
    Optional<HotelDto> readHotel(@RequestParam("id") Long id);

}
