package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.values.Occupancy;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class RoomUpdateCommand {
    private RoomBasic basic;
    private Integer quantity;
    private Occupancy occupancy;
    private DateRange availablePeriod;
    private Set<String> images;
}
