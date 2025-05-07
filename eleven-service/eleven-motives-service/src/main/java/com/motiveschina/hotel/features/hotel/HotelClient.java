package com.motiveschina.hotel.features.hotel;

import java.math.BigDecimal;
import java.util.Optional;
import com.motiveschina.hotel.core.SaleChannel;
import com.motiveschina.hotel.features.plan.PlanDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(value = "hotel", path = "/inner")
public interface HotelClient {

    @Operation(summary = "read hotel")
    @GetMapping("/readHotel")
    Optional<HotelDto> readHotel(@RequestParam("hotelId") Long hotelId);

    @Operation(summary = "read plan")
    @GetMapping("/readPlan")
    Optional<PlanDto> readPlan(@RequestParam("hotelId") Long hotelId, @RequestParam("planId") Long planId);

    @Operation(summary = "charge room")
    @PostMapping("/chargeRoom")
    BigDecimal chargeRoom(@RequestParam("hotelId") Long hotelId,
                          @RequestParam("planId") Long planId,
                          @RequestParam("roomId") Long roomId,
                          @RequestParam("saleChannel") SaleChannel saleChannel,
                          @RequestParam("persons") int persons);
}
