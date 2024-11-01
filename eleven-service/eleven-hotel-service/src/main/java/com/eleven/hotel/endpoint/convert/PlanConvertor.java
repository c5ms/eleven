package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.api.endpoint.request.PlanAddRoomRequest;
import com.eleven.hotel.api.endpoint.request.PlanCreateRequest;
import com.eleven.hotel.api.endpoint.request.PlanQueryRequest;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
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
                .setPlanId(plan.getId())
                .setHotelId(plan.getHotelId())
                .setName(plan.getBasic().getName())
                .setDesc(plan.getBasic().getDesc())
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

    private PlanDto.PlanRoom toDto(Plan.PlanRoom planRoom) {
        return new PlanDto.PlanRoom()
                .setRoomId(planRoom.getRoomId())
                .setStock(planRoom.getStock().getCount())
//                .setPrice(planRoom.getPrice().getAmount().doubleValue())
                ;
    }

    public PlanQuery toQuery(PlanQueryRequest request) {
        return PlanQuery.builder()
                .planName(request.getPlanName())
                .build();
    }

    public PlanCreateCommand toCommand(PlanCreateRequest request) {
        return PlanCreateCommand.builder()
                .basic(new Plan.PlanBasic(request.getName(), request.getDesc()))
                .stock(Stock.of(request.getStock()))
                .preSellPeriod(new DateTimeRange(request.getPreSellStartDate(), request.getPreSellEndDate()))
                .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
                .sellPeriod(new DateTimeRange(request.getSellStartDate(), request.getSellEndDate()))
                .build();
    }

    public PlanAddRoomCommand toCommand(PlanAddRoomRequest request) {
        return PlanAddRoomCommand.builder()
                .roomId(request.getRoomId())
                .stock(new Stock(request.getStock()))
                .build();
    }
}
