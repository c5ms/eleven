package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.room.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand {
    private Room.RoomBasic basic;
    private Room.RoomRestriction restriction;

}
