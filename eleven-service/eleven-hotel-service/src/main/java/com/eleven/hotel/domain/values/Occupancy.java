package com.eleven.hotel.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Occupancy implements Serializable {

    @Column(name = "occupancy_max")
    private Integer maxPerson;

    @Column(name = "occupancy_min")
    private Integer minPerson;

    public static Occupancy empty() {
        return new Occupancy();
    }
}
