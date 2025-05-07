package com.motiveschina.hotel.features.plan;

import com.motiveschina.hotel.common.DateRange;
import com.motiveschina.hotel.common.DateRangeVo;
import com.motiveschina.hotel.common.DateTimeRange;
import com.motiveschina.hotel.common.DateTimeRangeVo;
import com.motiveschina.hotel.features.plan.command.PlanCreateCommand;
import com.motiveschina.hotel.features.plan.command.PlanUpdateCommand;
import com.motiveschina.hotel.features.plan.request.PlanCreateRequest;
import com.motiveschina.hotel.features.plan.request.PlanUpdateRequest;
import com.motiveschina.hotel.features.plan.vo.PlanBasicVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanConverter {
    private final ModelMapper modelMapper;

    public PlanDto toDto(Plan plan) {
        return new PlanDto()
            .setPlanId(plan.getPlanId())
            .setHotelId(plan.getHotelId())
            .setStock(plan.getStock())
            .setSaleType(plan.getSaleType())
            .setSaleState(plan.getSaleState())
            .setIsOnSale(plan.isOnSale())
            .setChannels(plan.getSaleChannels().toList())
            .setBasic(modelMapper.map(plan.getBasic(), PlanBasicVo.class))
            .setPreSalePeriod(modelMapper.map(plan.getPreSalePeriod(), DateTimeRangeVo.class))
            .setSalePeriod(modelMapper.map(plan.getSalePeriod(), DateTimeRangeVo.class))
            .setStayPeriod(modelMapper.map(plan.getStayPeriod(), DateRangeVo.class));
    }


    public PlanCreateCommand toCommand(PlanCreateRequest request) {
        return PlanCreateCommand.builder()
            .basic(modelMapper.map(request.getBasic(), PlanBasic.class))
            .stock(request.getStock())
            .preSalePeriod(modelMapper.map(request.getPreSalePeriod(), DateTimeRange.class))
            .salePeriod(modelMapper.map(request.getSalePeriod(), DateTimeRange.class))
            .stayPeriod(modelMapper.map(request.getStayPeriod(), DateRange.class))
            .rooms(request.getRooms())
            .channels(request.getChannels())
            .build();
    }


    public PlanUpdateCommand toCommand(PlanUpdateRequest request) {
        return PlanUpdateCommand.builder()
            .basic(modelMapper.map(request.getBasic(), PlanBasic.class))
            .stock(request.getStock())
            .preSalePeriod(modelMapper.map(request.getPreSalePeriod(), DateTimeRange.class))
            .salePeriod(modelMapper.map(request.getSalePeriod(), DateTimeRange.class))
            .stayPeriod(modelMapper.map(request.getStayPeriod(), DateRange.class))
            .rooms(request.getRooms())
            .channels(request.getChannels())
            .build();
    }


}
