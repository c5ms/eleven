package com.eleven.hotel.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Occupancy {

    @Column(name = "occupancy_max")
    private Integer maxPerson;

    @Column(name = "occupancy_min")
    private Integer minPerson;


}
