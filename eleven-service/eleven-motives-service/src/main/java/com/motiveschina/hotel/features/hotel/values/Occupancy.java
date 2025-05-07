package com.motiveschina.hotel.features.hotel.values;

import java.io.Serializable;
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
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Occupancy implements Serializable {

    @Column(name = "occupancy_min")
    private Integer minPerson;


    @Column(name = "occupancy_max")
    private Integer maxPerson;

    public static Occupancy empty() {
        return new Occupancy();
    }

    public static Occupancy of(Integer maxPerson, Integer minPerson) {
        return new Occupancy(maxPerson, minPerson);
    }
}
