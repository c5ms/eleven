package com.eleven.hotel.interfaces.web.inner;

import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.interfaces.client.HotelClient;
import com.eleven.hotel.api.interfaces.model.HotelDto;
import com.eleven.hotel.api.interfaces.model.PlanDto;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.interfaces.convert.HotelConvertor;
import com.eleven.hotel.interfaces.convert.PlanConvertor;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@AsInternalApi
@Tag(name = "hotel")
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
    public BigDecimal chargeRoom(@RequestParam("hotelId") Long hotelId,
                                 @RequestParam("planId") Long planId,
                                 @RequestParam("roomId") Long roomId,
                                 @RequestParam("saleChannel") SaleChannel saleChannel,
                                 @RequestParam("persons") int persons) {
        return planService.chargeRoom(hotelId, planId, roomId, saleChannel, persons);
    }


}
