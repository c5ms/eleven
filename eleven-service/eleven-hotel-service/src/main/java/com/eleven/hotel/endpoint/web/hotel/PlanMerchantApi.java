package com.eleven.hotel.endpoint.web.hotel;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.api.endpoint.request.PlanAddRoomRequest;
import com.eleven.hotel.api.endpoint.request.PlanCreateRequest;
import com.eleven.hotel.api.endpoint.request.PlanQueryRequest;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import com.eleven.hotel.endpoint.support.AsMerchantApi;
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

    private final PlanService planService;
    private final PlanConvertor planConvertor;

    @Operation(summary = "query plan")
    @GetMapping
    public PageResult<PlanDto> queryPlan(@PathVariable("hotelId") Integer hotelId, @ParameterObject @Validated PlanQueryRequest request) {
        var query = planConvertor.toQuery(request);
        return planService.queryPage(hotelId, query).map(planConvertor::toDto);
    }

    @Operation(summary = "read plan")
    @GetMapping("/{planId:[0-9]+}")
    public Optional<PlanDto> readPlan(@PathVariable("hotelId") Integer hotelId, @PathVariable("planId") Integer planId) {
        return planService.readPlan(hotelId, planId).map(planConvertor::toDto);
    }

    @Operation(summary = "create plan")
    @PostMapping
    public PlanDto createPlan(@PathVariable("hotelId") Integer hotelId, @RequestBody @Validated PlanCreateRequest request) {
        var command = planConvertor.toCommand(request);
        var plan = planService.createPlan(hotelId, command);
        return planConvertor.toDto(plan);
    }

    @Operation(summary = "add room")
    @PostMapping("/{planId:[0-9]+}/rooms")
    public void addRoom(@PathVariable("hotelId") Integer hotelId, @PathVariable("planId") Integer planId, @RequestBody @Validated PlanAddRoomRequest request) {
        var command = planConvertor.toCommand(request);
        planService.addRoom(hotelId, planId, command);
    }

}
