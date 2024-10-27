package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanConvertor {
    public final ValuesConvertor values;

    public PlanDto toDto(Plan plan) {
        return new PlanDto()
            .setPlanId(plan.getPlanId())
            .setHotelId(plan.getHotelId())
            .setName(plan.getDescription().getName())
            .setDesc(plan.getDescription().getDesc())
            .setStock(plan.getStock().getCount())
            .setType(plan.getSaleType())
            .setState(plan.getSaleState())

            .setIsPreSale(plan.getPreSalePeriod().isNotEmpty())
            .setIsPreSaleOngoing(plan.getPreSalePeriod().isCurrent())
            .setPreSellStartDate(plan.getPreSalePeriod().getStart())
            .setPreSellEndDate(plan.getPreSalePeriod().getEnd())

            .setIsSaleOngoing(plan.getPreSalePeriod().isCurrent())
            .setSellStartDate(plan.getSalePeriod().getStart())
            .setSellEndDate(plan.getSalePeriod().getEnd())

            .setStayStartDate(plan.getStayPeriod().getStart())
            .setStayEndDate(plan.getStayPeriod().getEnd())

            .setRooms(plan.getRooms().stream().map(this::toDto).collect(Collectors.toList()))
            ;
    }

    private PlanDto.PlanRoom toDto(PlanRoom planRoom) {
        return new PlanDto.PlanRoom()
            .setRoomId(planRoom.getRoomId())
            .setStock(planRoom.getStock().getCount())
            .setPrice(planRoom.getPrice().getAmount().doubleValue());
    }

}
