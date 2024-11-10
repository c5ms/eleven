package com.eleven.hotel.interfaces.convert;

import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.interfaces.model.PlanDetail;
import com.eleven.hotel.api.interfaces.model.PlanDto;
import com.eleven.hotel.api.interfaces.request.PlanAddRoomRequest;
import com.eleven.hotel.api.interfaces.request.PlanCreateRequest;
import com.eleven.hotel.api.interfaces.request.PlanQueryRequest;
import com.eleven.hotel.api.interfaces.request.PlanUpdateRequest;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanQuery;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.model.plan.Product;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanConvertor {
    public final ValuesConvertor values;
    private final ModelMapper modelMapper;

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

                // pre sale
                .setIsPreSale(plan.getPreSalePeriod().isNotEmpty())
                .setIsPreSalePeriodOngoing(plan.getPreSalePeriod().isCurrent())
                .setPreSellStartDate(plan.getPreSalePeriod().getStart())
                .setPreSellEndDate(plan.getPreSalePeriod().getEnd())

                // sale period
                .setIsSalePeriodOngoing(plan.getPreSalePeriod().isCurrent())
                .setSellStartDate(plan.getSalePeriod().getStart())
                .setSellEndDate(plan.getSalePeriod().getEnd())

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
                .setRoomId(product.getProductKey().getRoomId())
                .setStock(product.getStockAmount().getCount())
                .setChargeType(product.getChargeType())
                .setSaleState(product.getSaleState())
                .setPrices(new PlanDetail.RoomPrices()
                        .setDh(product.findPrice(SaleChannel.DH).map(price -> modelMapper.map(price, PlanDetail.Price.class)).orElse(null))
                        .setDp(product.findPrice(SaleChannel.DP).map(price -> modelMapper.map(price, PlanDetail.Price.class)).orElse(null))
                );
    }

    public PlanQuery toQuery(PlanQueryRequest request) {
        return PlanQuery.builder()
                .planName(request.getPlanName())
                .build();
    }

    public PlanCreateCommand toCommand(PlanCreateRequest request) {
        return PlanCreateCommand.builder()
                .basic(new PlanBasic(request.getName(), request.getDesc()))
                .stock(StockAmount.of(request.getStock()))
                .preSalePeriod(new DateTimeRange(request.getPreSellStartDate(), request.getPreSellEndDate()))
                .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
                .salePeriod(new DateTimeRange(request.getSellStartDate(), request.getSellEndDate()))
                .rooms( request.getRooms().stream().map(this::toCommand).collect(Collectors.toList()))
                .channels(request.getChannels())
                .build();
    }

    public PlanUpdateCommand toCommand(PlanUpdateRequest request) {
        return PlanUpdateCommand.builder()
            .basic(new PlanBasic(request.getName(), request.getDesc()))
            .stock(StockAmount.of(request.getStock()))
            .preSalePeriod(new DateTimeRange(request.getPreSellStartDate(), request.getPreSellEndDate()))
            .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
            .salePeriod(new DateTimeRange(request.getSellStartDate(), request.getSellEndDate()))
            .rooms( request.getRooms().stream().map(this::toCommand).collect(Collectors.toList()))
            .channels(request.getChannels())
            .build();
    }

    private PlanAddRoomCommand toCommand(PlanAddRoomRequest request) {
         return PlanAddRoomCommand.builder()
             .roomId(request.getRoomId())
             .stock(StockAmount.of(request.getStock()))
             .build();
    }


}
