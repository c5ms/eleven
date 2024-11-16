package com.eleven.booking.infrastructure;

import com.eleven.booking.domain.model.booking.Plan;
import com.eleven.hotel.api.domain.values.SaleChannel;
import com.eleven.hotel.api.interfaces.client.HotelClient;
import com.eleven.hotel.api.interfaces.vo.PlanDto;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class RemotePlan implements Plan {

    private final PlanDto plan;
    private final Long roomId;
    private final HotelClient hotelClient;

    @Override
    public Long getPlanId() {
        return plan.getPlanId();
    }

    @Override
    public Long getHotelId() {
        return plan.getHotelId();
    }

    @Override
    public Long getRoomId() {
        return roomId;
    }

    @Override
    public boolean isOnSale() {
        return plan.getIsOnSale();
    }

    @Override
    public boolean isOnSale(long roomId) {
        return false;
    }

    @Override
    public BigDecimal charge(SaleChannel saleChannel, int persons) {
        return hotelClient.chargeRoom(plan.getHotelId(), plan.getPlanId(), roomId, saleChannel, persons);
    }

}
