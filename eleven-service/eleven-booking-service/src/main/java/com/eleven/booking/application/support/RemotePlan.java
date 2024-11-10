package com.eleven.booking.application.support;

import com.eleven.booking.domain.model.booking.Plan;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.interfaces.client.HotelClient;
import com.eleven.hotel.api.interfaces.model.PlanDto;
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
