package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@Embeddable
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class RoomBasic {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "area")
    private Integer area;

    @Column(name = "floor")
    private Integer floor;

}
