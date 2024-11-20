package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.interfaces.model.plan.PlanCreateRequest;
import com.eleven.hotel.api.interfaces.model.plan.PlanDetail;
import com.eleven.hotel.api.interfaces.model.plan.PlanDto;
import com.eleven.hotel.api.interfaces.model.plan.PlanUpdateRequest;
import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.DateTimeRangeVo;
import com.eleven.hotel.api.interfaces.values.PlanBasicVo;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.model.plan.PlanPatch;
import com.eleven.hotel.domain.model.plan.Product;
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

    public PlanDetail toDetail(Plan plan) {
        return new PlanDetail()
                .setPlan(toDto(plan))
                .setRooms(plan.getProducts().stream().map(this::toDto).toList());
    }

    public PlanDetail.Room toDto(Product product) {
        return new PlanDetail.Room()
                .setRoomId(product.getKey().getRoomId())
                .setName(product.getRoom().getBasic().getName())
                .setDesc(product.getRoom().getBasic().getDescription())
                .setStock(product.getStock())
                .setChargeType(product.getChargeType())
                .setSaleState(product.getSaleState())
                .setPrices(new PlanDetail.RoomPrices()
                        .setDh(product.priceOf(SaleChannel.DH).map(price -> modelMapper.map(price, PlanDetail.Price.class)).orElse(null))
                        .setDp(product.priceOf(SaleChannel.DP).map(price -> modelMapper.map(price, PlanDetail.Price.class)).orElse(null))
                );
    }


    public PlanCreateCommand toCommand(PlanCreateRequest request) {
        return PlanCreateCommand.builder()
                .basic(modelMapper.map(request.getBasic(), PlanBasic.class))
                .stock(request.getStock())
                .preSalePeriod(modelMapper.map(request.getPerSalePeriod(), DateTimeRange.class))
                .salePeriod(modelMapper.map(request.getSalePeriod(), DateTimeRange.class))
                .stayPeriod(modelMapper.map(request.getStayPeriod(), DateRange.class))
                .rooms(request.getRooms())
                .channels(request.getChannels())
                .build();
    }


    public PlanUpdateCommand toCommand(PlanUpdateRequest request) {
        return PlanUpdateCommand.builder()
                .patch(PlanPatch.builder()
                        .basic(new PlanBasic(request.getName(), request.getDesc()))
                        .stock(request.getStock())
                        .preSalePeriod(new DateTimeRange(request.getPreSaleStartDate(), request.getPreSaleEndDate()))
                        .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
                        .salePeriod(new DateTimeRange(request.getSaleStartDate(), request.getSaleEndDate()))
                        .channels(request.getChannels())
                        .build())
                .rooms(request.getRooms())
                .build();
    }


}
