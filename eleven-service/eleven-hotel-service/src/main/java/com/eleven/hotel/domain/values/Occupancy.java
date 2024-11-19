package com.eleven.hotel.domain.values;

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
public final class Occupancy {

    @Column(name = "occupancy_max")
    private Integer maxPerson;

    @Column(name = "occupancy_min")
    private Integer minPerson;

    public Occupancy(Integer minPerson, Integer maxPerson) {
        this.maxPerson = maxPerson;
        this.minPerson = minPerson;
    }

}
