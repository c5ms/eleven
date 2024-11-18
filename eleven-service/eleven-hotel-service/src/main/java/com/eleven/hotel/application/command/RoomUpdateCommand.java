package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.values.DateRange;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class RoomUpdateCommand {
    private RoomBasic basic;
    private Integer quantity;
    private DateRange availablePeriod;
    private Set<String> images;
}
