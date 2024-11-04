package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.api.endpoint.request.PlanAddRoomRequest;
import com.eleven.hotel.api.endpoint.request.PlanCreateRequest;
import com.eleven.hotel.api.endpoint.request.PlanQueryRequest;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.model.plan.Product;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanConvertor {
    public final ValuesConvertor values;
    private final ModelMapper modelMapper;

    public PlanDto toDto(Plan plan) {
        return toDto(plan,false);
    }

    public PlanDto toDetail(Plan plan) {
        return toDto(plan,true);
    }

    private PlanDto toDto(Plan plan, boolean withDetail) {
        var dto = new PlanDto()
            .setPlanId(plan.getPlanId())
            .setHotelId(plan.getHotelId())
            .setName(plan.getBasic().getName())
            .setDesc(plan.getBasic().getDesc())
            .setStock(plan.getStockAmount().getCount())
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
            .setStayEndDate(plan.getStayPeriod().getEnd())

            // detail
            .setRooms(null);

        if (withDetail) {
            dto.setRooms(plan.getProducts().stream().map(this::toDto).toList());
        }

        return dto;
    }

    private PlanDto.Room toDto(Product product) {
        return new PlanDto.Room()
            .setRoomId(product.getProductId().getRoomId())
            .setStock(product.getStockAmount().getCount())
            .setChargeType(product.getChargeType())
            .setSaleState(product.getSaleState())
            .setPrices(new PlanDto.RoomPrices()
                .setDh(product.findPrice(SaleChannel.DH).map(price -> modelMapper.map(price, PlanDto.Price.class)).orElse(null))
                .setDp(product.findPrice(SaleChannel.DP).map(price -> modelMapper.map(price, PlanDto.Price.class)).orElse(null))
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
            .preSellPeriod(new DateTimeRange(request.getPreSellStartDate(), request.getPreSellEndDate()))
            .stayPeriod(new DateRange(request.getStayStartDate(), request.getStayEndDate()))
            .sellPeriod(new DateTimeRange(request.getSellStartDate(), request.getSellEndDate()))
            .rooms(request.getRooms())
            .channels(request.getChannels())
            .build();
    }

    public PlanAddRoomCommand toCommand(PlanAddRoomRequest request) {
        return PlanAddRoomCommand.builder()
            .roomId(request.getRoomId())
            .stock(StockAmount.of(request.getStock()))
            .build();
    }
}
