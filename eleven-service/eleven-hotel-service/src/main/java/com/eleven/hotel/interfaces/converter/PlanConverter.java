package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.model.plan.PlanCreateRequest;
import com.eleven.hotel.api.interfaces.model.plan.PlanDto;
import com.eleven.hotel.api.interfaces.model.plan.PlanUpdateRequest;
import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.DateTimeRangeVo;
import com.eleven.hotel.api.interfaces.values.PlanBasicVo;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.model.hotel.PlanBasic;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
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
                .setType(plan.getSaleType())
                .setState(plan.getSaleState())
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
