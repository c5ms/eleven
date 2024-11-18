package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.model.hotel.values.Occupancy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;

@Getter
@Embeddable
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class RoomBasic {

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String desc;

    @Column(name = "area")
    private Integer area;

    @Column(name = "floor")
    private Integer floor;

    @Embedded
    private Occupancy occupancy;

    @Builder
    public RoomBasic(String name, String desc, Integer area, Integer floor, Occupancy occupancy) {
        Validate.notNull(name, "name must not null");
        this.name = name;
        this.desc = desc;
        this.area = area;
        this.floor = floor;
        this.occupancy = occupancy;
    }
}
