package com.eleven.travel.domain.room;

import com.eleven.travel.core.DateRange;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class RoomStock implements Serializable {

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "available_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "available_period_end"))
    private DateRange availablePeriod = DateRange.empty();

    @Column(name = "quantity")
    private Integer quantity = 0;

    public static RoomStock empty() {
        return new RoomStock();
    }

    public static RoomStock of(DateRange availablePeriod, Integer quantity) {
        return new RoomStock(availablePeriod, quantity);
    }

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
