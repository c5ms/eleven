package com.eleven.demo.hotel;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Primary
@FeignClient(value = "hotel", path = "/inner")
public interface HotelClient {

    @Operation(summary = "read plan")
    @GetMapping("/readPlan")
    Optional<String> readPlan(@RequestParam("hotelId") Long hotelId, @RequestParam("planId") Long planId);

}
