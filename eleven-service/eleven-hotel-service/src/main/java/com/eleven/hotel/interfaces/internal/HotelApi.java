package com.eleven.hotel.interfaces.internal;

import com.eleven.core.interfaces.web.annonation.AsInnerApi;
import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.interfaces.client.HotelClient;
import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.api.interfaces.dto.PlanDto;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.plan.PlanKey;
import com.eleven.hotel.domain.model.plan.ProductKey;
import com.eleven.hotel.interfaces.convert.HotelConvertor;
import com.eleven.hotel.interfaces.convert.PlanConvertor;
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

    private final HotelConvertor hotelConvertor;
    private final HotelRepository hotelRepository;

    private final PlanQuery planQuery;
    private final PlanService planService;
    private final PlanConvertor planConvertor;

    @Override
    public Optional<HotelDto> readHotel(@RequestParam("hotelId") Long hotelId) {
        return hotelRepository.findById(hotelId).map(hotelConvertor::toDto);
    }

    @Override
    public Optional<PlanDto> readPlan(@RequestParam("hotelId") Long hotelId, @RequestParam("planId") Long planId) {
        var planKye = PlanKey.of(hotelId, planId);
        return planQuery.readPlan(planKye).map(planConvertor::toDetail);
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
