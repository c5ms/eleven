package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.application.model.PlanDto;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.PlanAddRoomRequest;
import com.eleven.hotel.api.endpoint.request.PlanCreateRequest;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.convert.HotelConvertor;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@AsMerchantApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.PLAN)
@RequestMapping(HotelEndpoints.Paths.PLAN)
public class PlanMerchantApi {

    private final PlanService planService;
    private final HotelConvertor hotelConvertor;
    private final PlanRepository planRepository;

    @Operation(summary = "read plan")
    @GetMapping("/{planId}")
    public Optional<PlanDto> readPlan(@PathVariable("hotelId") String hotelId, @PathVariable("planId") String planId) {
        return planRepository.find(hotelId, planId).map(hotelConvertor.entities::toDto);
    }

    @Operation(summary = "create plan")
    @PostMapping
    public PlanDto createPlan(@PathVariable("hotelId") String hotelId, @RequestBody @Validated PlanCreateRequest request) {
        var command = PlanCreateCommand.builder()
                .hotelId(hotelId)
                .name(request.getName())
                .desc(request.getDesc())
                .stock(Stock.of(request.getTotal()))
                .stayPeriod(DateRange.of(request.getStayStartDate(), request.getStayEndDate()))
                .sellPeriod(DateTimeRange.of(request.getSellStartDate(), request.getSellEndDate()))
                .build();
        var plan = planService.createPlan(command);
        return hotelConvertor.entities.toDto(plan);
    }

    @Operation(summary = "add room")
    @PostMapping("/{planId}/rooms")
    public void addRoom(@PathVariable("hotelId") String hotelId, @PathVariable("planId") String planId, @RequestBody @Validated PlanAddRoomRequest request) {
        var command = PlanAddRoomCommand.builder()
                .hotelId(hotelId)
                .planId(planId)
                .roomId(request.getRoomId())
                .stock(Stock.of(request.getStock()))
                .price(Price.of(request.getPrice()))
                .build();
        planService.addRoom(command);
    }

}
