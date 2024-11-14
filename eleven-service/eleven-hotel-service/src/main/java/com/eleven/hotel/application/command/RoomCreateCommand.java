package com.eleven.hotel.application.command;

import com.eleven.core.domain.values.DateRange;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.RoomRestriction;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand {
    private RoomBasic basic;
    private RoomRestriction restriction;
    private Integer count;
    private DateRange stayPeriod;
}
