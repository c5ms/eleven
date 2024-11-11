package com.eleven.hotel.interfaces.web.hotel;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.web.WebContext;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.interfaces.model.PlanDetail;
import com.eleven.hotel.api.interfaces.model.PlanDto;
import com.eleven.hotel.api.interfaces.request.PlanCreateRequest;
import com.eleven.hotel.api.interfaces.request.PlanQueryRequest;
import com.eleven.hotel.api.interfaces.request.PlanUpdateRequest;
import com.eleven.hotel.application.command.PlanSetPriceCommand;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.plan.PlanKey;
import com.eleven.hotel.interfaces.convert.PlanConvertor;
import com.eleven.hotel.interfaces.support.AsMerchantApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Slf4j
@AsMerchantApi
@RequiredArgsConstructor
@Tag(name = "plan")
@RequestMapping("/hotels/{hotelId:[0-9]+}/plans")
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
    public Optional<PlanDetail> readPlan(@PathVariable("hotelId") Long hotelId, @PathVariable("planId") Long planId) {
        var plankey = PlanKey.of(hotelId, planId);
        return planService.readPlan(plankey).map(planConvertor::toDetail);
    }

    @Operation(summary = "create plan")
    @PostMapping
    public PlanDetail createPlan(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated PlanCreateRequest request) {
        var command = planConvertor.toCommand(request);
        var plan = planService.createPlan(hotelId, command);
        return planConvertor.toDetail(plan);
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
    public PlanDetail updatePlan(@PathVariable("hotelId") Long hotelId,
                                 @PathVariable("planId") Long planId,
                                 @RequestBody @Validated PlanUpdateRequest request) {
        var command = planConvertor.toCommand(request);
        var plankey = PlanKey.of(hotelId, planId);
        var plan = planService.updatePlan(plankey, command);
        return planConvertor.toDetail(plan);
    }

    @Operation(summary = "list room")
    @GetMapping("/{planId:[0-9]+}/rooms")
    public List<PlanDetail.Room> listRoom(@PathVariable("hotelId") Long hotelId, @PathVariable("planId") Long planId) {
        var plankey = PlanKey.of(hotelId, planId);
        var plan = planService.readPlan(plankey).orElseThrow(WebContext::notFoundException);
        return plan.getProducts().stream().map(planConvertor::toDto).toList();
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
