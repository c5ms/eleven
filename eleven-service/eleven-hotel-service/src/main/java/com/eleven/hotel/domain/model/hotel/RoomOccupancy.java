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
public final class RoomOccupancy {

    @Column(name = "restrict_max_person")
    private Integer maxPerson;

    @Column(name = "restrict_min_person")
    private Integer minPerson;

    public RoomOccupancy(Integer minPerson, Integer maxPerson) {
        this.maxPerson = maxPerson;
        this.minPerson = minPerson;
    }

}
