package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.domain.model.room.RoomBasic;
import com.eleven.hotel.domain.model.room.RoomRestriction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomUpdateCommand {
    private ChargeType chargeType;
    private RoomBasic basic;
    private RoomRestriction restriction;
}
