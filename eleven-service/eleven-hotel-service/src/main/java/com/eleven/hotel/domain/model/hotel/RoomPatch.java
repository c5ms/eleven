package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class RoomPatch {
    private Integer quantity;

    private DateRange availablePeriod;

    private RoomBasic basic;

    private Occupancy occupancy;

    private Set<String> images;
}
