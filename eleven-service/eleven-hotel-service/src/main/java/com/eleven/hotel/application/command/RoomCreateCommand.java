package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.values.DateRange;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.RoomOccupancy;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand {
    private RoomBasic basic;
    private RoomOccupancy restriction;
    private Integer count;
    private DateRange stayPeriod;
}
