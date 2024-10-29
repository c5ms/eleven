package com.eleven.hotel.endpoint.resource;

import com.eleven.core.application.model.PageResult;
import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.api.endpoint.request.PlanAddRoomRequest;
import com.eleven.hotel.api.endpoint.request.PlanCreateRequest;
import com.eleven.hotel.api.endpoint.request.PlanQueryRequest;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.application.service.HotelQueryService;
import com.eleven.hotel.application.service.PlanCommandService;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import com.eleven.hotel.endpoint.convert.PlanConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@AsMerchantApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.PLAN)
@RequestMapping(HotelEndpoints.Paths.PLAN)
public class PlanMerchantApi {

    private final PlanRepository planRepository;
    private final PlanConvertor planConvertor;

    private final HotelQueryService hotelQueryService;
    private final PlanCommandService planCommandService;


    @Operation(summary = "query plan")
    @GetMapping
    public PageResult<PlanDto> queryPlan(@PathVariable("hotelId") String hotelId, @ParameterObject @Validated PlanQueryRequest request) {
        var command = PlanQuery.builder()
                .hotelId(hotelId)
                .planName(request.getPlanName())
                .build();
        return hotelQueryService.queryPage(command).map(planConvertor::toDto);
    }

    @Operation(summary = "read plan")
    @GetMapping("/{planId}")
    public Optional<PlanDto> readPlan(@PathVariable("hotelId") String hotelId, @PathVariable("planId") String planId) {
        return planRepository.findByHotelIdAndPlanId(hotelId, planId).map(planConvertor::toDto);
    }

    @Operation(summary = "create plan")
    @PostMapping
    public PlanDto createPlan(@PathVariable("hotelId") String hotelId, @RequestBody @Validated PlanCreateRequest request) {
        var command = PlanCreateCommand.builder()
                .hotelId(hotelId)
                .description(new Plan.Description(request.getName(), request.getDesc()))
                .stock(Stock.of(request.getStock()))
                .preSellPeriod(DateTimeRange.of(request.getPreSellStartDate(), request.getPreSellEndDate()))
                .stayPeriod(DateRange.of(request.getStayStartDate(), request.getStayEndDate()))
                .sellPeriod(DateTimeRange.of(request.getSellStartDate(), request.getSellEndDate()))
                .build();
        var plan = planCommandService.createPlan(command);
        return planConvertor.toDto(plan);
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
        planCommandService.addRoom(command);
    }

}
