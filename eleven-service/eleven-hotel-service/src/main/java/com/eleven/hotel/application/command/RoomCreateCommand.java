package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.domain.model.hotel.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand {
    private ChargeType chargeType;
    private Room.RoomBasic basic;
    private Room.RoomRestriction restriction;

}
