package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.DateRange;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Embeddable
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomStock {

    @Column(name = "quantity")
    private Integer quantity;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "available_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "available_period_end"))
    private DateRange availablePeriod;

    public Set<LocalDate> getAvailableDates() {
        if (null == availablePeriod) {
            return new HashSet<>();
        }
        return getAvailablePeriod()
                .dates()
                .filter(localDate -> localDate.isAfter(LocalDate.now()))
                .collect(Collectors.toSet());
    }
}
