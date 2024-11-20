package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.RoomPatch;
import com.eleven.hotel.domain.model.hotel.RoomStock;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.values.Occupancy;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class RoomUpdateCommand  implements RoomPatch {
    private RoomBasic basic;
    private RoomStock stock;
    private Occupancy occupancy;
    private Set<String> images;
}
