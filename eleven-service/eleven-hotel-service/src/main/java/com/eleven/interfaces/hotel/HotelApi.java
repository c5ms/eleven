package com.eleven.interfaces.hotel;

import com.eleven.common.SaleChannel;
import com.eleven.core.web.annonation.AsInnerApi;
import com.eleven.domain.hotel.HotelRepository;
import com.eleven.domain.plan.*;
import com.eleven.domain.product.ProductKey;
import com.eleven.interfaces.plan.PlanConverter;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Hidden
@Tag(name = "internal")
@Slf4j
@AsInnerApi
@RequiredArgsConstructor
public class HotelApi implements HotelClient {

    private final HotelRepository hotelRepository;

    private final PlanFinder planFinder;
    private final PlanService planService;
    private final PlanConverter planConverter;
    private final HotelConvertor hotelConvertor;

    @Override
    public Optional<HotelDto> readHotel(@RequestParam("hotelId") Long hotelId) {
        return hotelRepository.findById(hotelId).map(hotelConvertor::toDto);
    }

    @Override
    public Optional<PlanDto> readPlan(@RequestParam("hotelId") Long hotelId, @RequestParam("planId") Long planId) {
        var planKye = PlanKey.of(hotelId, planId);
        return planFinder.readPlan(planKye).map(planConverter::toDto);
    }

    @Override
    public BigDecimal chargeRoom(@RequestParam("hotelId") Long hotelId,
                                 @RequestParam("planId") Long planId,
                                 @RequestParam("roomId") Long roomId,
                                 @RequestParam("saleChannel") SaleChannel saleChannel,
                                 @RequestParam("persons") int persons) {
        var productKey = ProductKey.of(hotelId, planId, roomId);
        return planService.chargeRoom(productKey, saleChannel, persons);
    }


}
