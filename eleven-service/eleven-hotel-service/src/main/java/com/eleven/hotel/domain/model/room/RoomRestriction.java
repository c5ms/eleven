package com.eleven.hotel.domain.model.room;

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
public final class RoomRestriction {

    @Column(name = "restrict_max_person")
    private Integer maxPerson;

    @Column(name = "restrict_min_person")
    private Integer minPerson;

    public RoomRestriction(Integer minPerson, Integer maxPerson) {
        //        Validate.notNull(maxPerson, "max person must not null");
        //        Validate.notNull(minPerson, "min person must not null");
        //        Validate.isTrue(maxPerson > minPerson, "max person must be greater than min");
        this.maxPerson = maxPerson;
        this.minPerson = minPerson;
    }

}
