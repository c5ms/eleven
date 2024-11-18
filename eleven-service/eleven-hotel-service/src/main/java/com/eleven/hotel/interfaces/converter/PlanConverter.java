package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.domain.model.hotel.values.DateRange;
import com.eleven.hotel.domain.model.hotel.values.DateTimeRange;
import com.eleven.hotel.domain.model.hotel.values.StockAmount;
import com.eleven.hotel.api.interfaces.dto.PlanDetail;
import com.eleven.hotel.api.interfaces.dto.PlanDto;
import com.eleven.hotel.api.interfaces.request.PlanCreateRequest;
import com.eleven.hotel.api.interfaces.request.PlanUpdateRequest;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.model.plan.PlanPatch;
import com.eleven.hotel.domain.model.plan.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanConverter {
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;

    public PlanDto toDto(Plan plan) {
        var dto = new PlanDto();
        fillUp(dto, plan);
        return dto;
    }

    public PlanDetail toDetail(Plan plan) {
        var dto = new PlanDetail();
        fillUp(dto, plan);
        return dto;
    }

    private void fillUp(PlanDto dto, Plan plan) {
        dto.setPlanId(plan.getPlanId())
                .setHotelId(plan.getHotelId())
                .setName(plan.getBasic().getName())
                .setDesc(plan.getBasic().getDesc())
                .setStock(plan.getStock().getCount())
                .setType(plan.getSaleType())
                .setState(plan.getSaleState())
                .setIsOnSale(plan.isOnSale())
                .setChannels(plan.getSaleChannels().toList())

                // pre sale
                .setIsPreSale(Optional.ofNullable(plan.getPreSalePeriod()).map(DateTimeRange::isNotEmpty).orElse(false))
                .setIsPreSalePeriodOngoing(plan.getPreSalePeriod().isCurrent())
                .setPreSaleStartDate(plan.getPreSalePeriod().getStart())
                .setPreSaleEndDate(plan.getPreSalePeriod().getEnd())

                // sale period
                .setIsSalePeriodOngoing(plan.getPreSalePeriod().isCurrent())
                .setSaleStartDate(plan.getSalePeriod().getStart())
                .setSaleEndDate(plan.getSalePeriod().getEnd())

                // stay period
                .setIsStayPeriodOngoing(plan.getStayPeriod().isCurrent())
                .setStayStartDate(plan.getStayPeriod().getStart())
                .setStayEndDate(plan.getStayPeriod().getEnd());

        if (dto instanceof PlanDetail detail) {
            detail.setRooms(plan.getProducts().stream().map(this::toDto).toList());
        }

    }

    public PlanDetail.Room toDto(Product product) {
        return new PlanDetail.Room()
                .setRoomId(product.getKey().getRoomId())
                .setName(product.getRoom().getBasic().getName())
                .setDesc(product.getRoom().getBasic().getDesc())
                .setStock(product.getStock().getCount())
                .setChargeType(product.getChargeType())
                .setSaleState(product.getSaleState())
                .setPrices(new PlanDetail.RoomPrices()
                        .setDh(product.priceOf(SaleChannel.DH).map(price -> modelMapper.map(price, PlanDetail.Price.class)).orElse(null))
                        .setDp(product.priceOf(SaleChannel.DP).map(price -> modelMapper.map(price, PlanDetail.Price.class)).orElse(null))
                );
    }


    public PlanCreateCommand toCommand(PlanCreateRequest request) {
        return PlanCreateCommand.builder()
                .basic(new PlanBasic(request.getName(), request.getDesc()))
                .stock(StockAmount.of(request.getStock()))
                .preSalePeriod(new DateTimeRange(request.getPreSaleStartDate(), request.getPreSaleEndDate()))
                .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
                .salePeriod(new DateTimeRange(request.getSaleStartDate(), request.getSaleEndDate()))
                .rooms(request.getRooms())
                .channels(request.getChannels())
                .build();
    }

    public PlanUpdateCommand toCommand(PlanUpdateRequest request) {
        return PlanUpdateCommand.builder()
                .patch(PlanPatch.builder()
                        .basic(new PlanBasic(request.getName(), request.getDesc()))
                        .stock(StockAmount.of(request.getStock()))
                        .preSalePeriod(new DateTimeRange(request.getPreSaleStartDate(), request.getPreSaleEndDate()))
                        .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
                        .salePeriod(new DateTimeRange(request.getSaleStartDate(), request.getSaleEndDate()))
                        .channels(request.getChannels())
                        .build())
                .rooms(request.getRooms())
                .build();
    }


}
