package com.eleven.domain.plan;

import com.eleven.common.ChargeType;
import com.eleven.common.SaleChannel;
import com.eleven.core.web.PageResponse;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.domain.plan.command.PlanSetPriceCommand;
import com.eleven.domain.plan.request.PlanCreateRequest;
import com.eleven.domain.plan.request.PlanQueryRequest;
import com.eleven.domain.plan.request.PlanUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;


@Slf4j
@AsRestApi
@RequiredArgsConstructor
@Tag(name = "plan")
@RequestMapping("/hotels/{hotelId:[0-9]+}/plans")
public class PlanResource {

    private final PlanFinder planFinder;
    private final PlanService planService;
    private final PlanConverter planConverter;

    @Operation(summary = "query plan")
    @GetMapping
    public PageResponse<PlanDto> queryPlan(@PathVariable("hotelId") Long hotelId, @ParameterObject @Validated PlanQueryRequest request) {
        var filter = PlanFilter.builder()
                .hotelId(hotelId)
                .planName(request.getPlanName())
                .build();
        var page = planFinder.queryPage(filter, request.toPagerequest()).map(planConverter::toDto);
        return PageResponse.of(page.getContent(), page.getTotalElements());
    }

    @Operation(summary = "read plan")
    @GetMapping("/{planId:[0-9]+}")
    public Optional<PlanDto> readPlan(@PathVariable("hotelId") Long hotelId, @PathVariable("planId") Long planId) {
        var plankey = PlanKey.of(hotelId, planId);
        return planFinder.readPlan(plankey).map(planConverter::toDto);
    }

    @Operation(summary = "create plan")
    @PostMapping
    public PlanDto createPlan(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated PlanCreateRequest request) {
        var command = planConverter.toCommand(request);
        var plan = planService.createPlan(hotelId, command);
        return planConverter.toDto(plan);
    }

    @Operation(summary = "delete plan")
    @DeleteMapping("/{planId:[0-9]+}")
    public void deletePlan(@PathVariable("hotelId") Long hotelId,
                           @PathVariable("planId") Long planId) {
        var plankey = PlanKey.of(hotelId, planId);
        planService.deletePlan(plankey);
    }

    @Operation(summary = "update plan")
    @PutMapping("/{planId:[0-9]+}")
    public PlanDto updatePlan(@PathVariable("hotelId") Long hotelId,
                              @PathVariable("planId") Long planId,
                              @RequestBody @Validated PlanUpdateRequest request) {
        var command = planConverter.toCommand(request);
        var plankey = PlanKey.of(hotelId, planId);
        var plan = planService.updatePlan(plankey, command);
        return planConverter.toDto(plan);
    }

    @Operation(summary = "set price")
    @PostMapping("/{planId:[0-9]+}/rooms/{roomId:[0-9]+}/prices")
    public void setPrice(@PathVariable("hotelId") Long hotelId,
                         @PathVariable("planId") Long planId,
                         @PathVariable("roomId") Long roomId) {
        // todo
        var command = PlanSetPriceCommand.builder()
                .chargeType(ChargeType.BY_ROOM)
                .saleChannel(SaleChannel.DP)
                .wholeRoomPrice(BigDecimal.valueOf(200))
                .build();
        var planKey = PlanKey.of(hotelId, planId);
        planService.setPrice(planKey, roomId, command);
    }

    @Operation(summary = "start sale")
    @PostMapping("/{planId:[0-9]+}/rooms/{roomId:[0-9]+}/commands/startSale")
    public void startSale(@PathVariable("hotelId") Long hotelId,
                          @PathVariable("planId") Long planId,
                          @PathVariable("roomId") Long roomId) {
        var planKey = PlanKey.of(hotelId, planId);
        planService.startSale(planKey, roomId);
    }

    @Operation(summary = "stop sale")
    @PostMapping("/{planId:[0-9]+}/rooms/{roomId:[0-9]+}/commands/stopSale")
    public void stopSale(@PathVariable("hotelId") Long hotelId,
                         @PathVariable("planId") Long planId,
                         @PathVariable("roomId") Long roomId) {
        var planKey = PlanKey.of(hotelId, planId);
        planService.stopSale(planKey, roomId);
    }

}
