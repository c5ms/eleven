package com.eleven.booking.application.support;

import com.eleven.booking.domain.model.booking.Plan;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public class RemotePlan implements Plan {

    @Nonnull
    private final PlanDto dto;

    public RemotePlan(@Nonnull PlanDto dto) {
        this.dto = dto;
    }

    @Override
    public BigDecimal charge(Long roomId, SaleChannel saleChannel, int persons) {
        if (null == dto.getRooms()) {
            return BigDecimal.ZERO;
        }
        var roomOpt = dto.getRooms().stream()
                .filter(aRoom -> aRoom.getRoomId().equals(roomId))
                .findFirst();

        if (roomOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }

        var room = roomOpt.get();

//        switch (saleChannel){
//            case DH -> room.getPrices().getDh()
//        }
        return  BigDecimal.ZERO;
    }

}
