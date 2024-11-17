package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
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

    public RoomBasic(String name,  String desc,Integer area, Integer floor) {
        Validate.notNull(name, "name must not null");
        this.name = name;
        this.desc = desc;
        this.area = area;
        this.floor = floor;
    }
}
