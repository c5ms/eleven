package com.eleven.hotel.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomDeleteCommand {
    private String hotelId;
    private String roomId;
}
