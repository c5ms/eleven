package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.domain.model.room.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand {

    private String hotelId;

    private ChargeType chargeType;
    private Room.Description description;
    private Room.Restriction restriction;

}
