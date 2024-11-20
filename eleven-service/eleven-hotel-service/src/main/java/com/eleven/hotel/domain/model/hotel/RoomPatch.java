package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.Occupancy;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoomPatch {

    private RoomStock stock;

    private RoomBasic basic;

    private Occupancy occupancy;

    private Set<String> images;

}
