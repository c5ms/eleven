package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.DateRange;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@Embeddable
@FieldNameConstants
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomStock {

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "available_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "available_period_end"))
    private DateRange availablePeriod;

    @Column(name = "quantity")
    private Integer quantity;

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
