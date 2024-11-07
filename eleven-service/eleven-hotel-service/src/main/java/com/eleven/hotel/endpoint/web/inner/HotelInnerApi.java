package com.eleven.hotel.endpoint.web.inner;

import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.endpoint.convert.HotelConvertor;
import com.eleven.hotel.endpoint.convert.PlanConvertor;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@AsInternalApi
@Tag(name = HotelEndpoints.Tags.HOTEL)
@RequiredArgsConstructor
public class HotelInnerApi implements HotelClient {

    private final HotelConvertor hotelConvertor;
    private final HotelRepository hotelRepository;
    private final PlanService planService;
    private final PlanConvertor planConvertor;

    @Override
    public Optional<HotelDto> readHotel(@RequestParam("hotelId") Long hotelId) {
        return hotelRepository.findById(hotelId).map(hotelConvertor::toDto);
    }

    @Override
    public Optional<PlanDto> readPlan(@RequestParam("hotelId") Long hotelId, @RequestParam("planId") Long planId) {
        return planService.readPlan(hotelId, planId).map(planConvertor::toDetail);
    }

    @Override
    public Optional<BigDecimal> charge(@RequestParam("hotelId") Long hotelId,
                                       @RequestParam("planId") Long planId,
                                       @RequestParam("roomId") Long roomId,
                                       SaleChannel saleChannel,
                                       int persons) {
        return planService.readPlan(hotelId,planId).map(plan -> plan.chargeRoom(roomId,saleChannel,persons));
    }


}
