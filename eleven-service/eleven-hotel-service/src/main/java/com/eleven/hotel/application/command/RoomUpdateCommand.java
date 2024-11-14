package com.eleven.hotel.application.command;

import com.eleven.core.domain.values.DateRange;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.RoomRestriction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomUpdateCommand {
    private RoomBasic basic;
    private DateRange stayPeriod;
    private RoomRestriction restriction;
    private Integer count;
}
