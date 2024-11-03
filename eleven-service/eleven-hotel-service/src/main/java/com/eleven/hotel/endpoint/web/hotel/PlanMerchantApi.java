package com.eleven.hotel.endpoint.web.hotel;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.api.endpoint.request.PlanAddRoomRequest;
import com.eleven.hotel.api.endpoint.request.PlanCreateRequest;
import com.eleven.hotel.api.endpoint.request.PlanQueryRequest;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.endpoint.convert.PlanConvertor;
import com.eleven.hotel.endpoint.support.AsMerchantApi;
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
    public PageResult<PlanDto> queryPlan(@PathVariable("hotelId") Long hotelId, @ParameterObject @Validated PlanQueryRequest request) {
        var query = planConvertor.toQuery(request);
        return planService.queryPage(hotelId, query, request.toPagerequest()).map(planConvertor::toDto);
    }

    @Operation(summary = "read plan")
    @GetMapping("/{planId:[0-9]+}")
    public Optional<PlanDto> readPlan(@PathVariable("hotelId") Long hotelId, @PathVariable("planId") Long planId) {
        return planService.readPlan(hotelId, planId).map(planConvertor::toDetail);
    }

    @Operation(summary = "create plan")
    @PostMapping
    public PlanDto createPlan(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated PlanCreateRequest request) {
        var command = planConvertor.toCommand(request);
        var plan = planService.createPlan(hotelId, command);
        return planConvertor.toDetail(plan);
    }

    @Operation(summary = "add room")
    @PostMapping("/{planId:[0-9]+}/rooms")
    public void addRoom(@PathVariable("hotelId") Long hotelId, @PathVariable("planId") Long planId, @RequestBody @Validated PlanAddRoomRequest request) {
        var command = planConvertor.toCommand(request);
        planService.addRoom(hotelId, planId, command);
    }

    @Operation(summary = "start sale")
    @PostMapping("/{planId:[0-9]+}/rooms/{roomId:[0-9]+}/commands/startSale")
    public void startSaleProduct(@PathVariable("hotelId") Long hotelId,
                                 @PathVariable("planId") Long planId,
                                 @PathVariable("roomId") Long roomId) {
        planService.startSale(hotelId, planId, roomId);
    }

    @Operation(summary = "stop sale")
    @PostMapping("/{planId:[0-9]+}/rooms/{roomId:[0-9]+}/commands/stopSale")
    public void stopSaleProduct(@PathVariable("hotelId") Long hotelId,
                                @PathVariable("planId") Long planId,
                                @PathVariable("roomId") Long roomId) {
        planService.stopSale(hotelId, planId, roomId);
    }


}
