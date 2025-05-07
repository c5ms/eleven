package com.motiveschina.hotel.features.booking.support;

import java.math.BigDecimal;
import com.motiveschina.hotel.common.SaleChannel;
import com.motiveschina.hotel.features.booking.Plan;
import com.motiveschina.hotel.features.hotel.HotelClient;
import com.motiveschina.hotel.features.plan.PlanDto;
import lombok.RequiredArgsConstructor;

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
