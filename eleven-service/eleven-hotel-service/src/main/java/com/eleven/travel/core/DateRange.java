package com.eleven.travel.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class DateRange implements Serializable {

    private LocalDate start;
    private LocalDate end;

    public static DateRange of(LocalDate start, LocalDate end) {
        return new DateRange(start, end);
    }

    public static DateRange empty() {
        return new DateRange();
    }

    public int days() {
        return start.until(end).getDays();
    }

    public Stream<LocalDate> dates() {
        if (isEmpty()) {
            return Stream.empty();
        }
        return start.datesUntil(end);
    }

    public boolean isBefore(DateRange range) {
        if (isEmpty()) {
            return false;
        }
        return this.end.isBefore(range.end);
    }

    public boolean isAfter(DateRange range) {
        if (isEmpty()) {
            return false;
        }
        return this.end.isAfter(range.end);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return ObjectUtils.anyNull(this.start, this.end);
    }

    @JsonIgnore
    public boolean isCurrent() {
        if (isEmpty()) {
            return false;
        }
        return contains(LocalDate.now());
    }

    public DateTimeRange toDateTimeRange() {
        if (isEmpty()) {
            return DateTimeRange.empty();
        }
        return new DateTimeRange(start.atStartOfDay(), end.atStartOfDay());
    }

    public boolean overlap(DateRange time) {
        if (isEmpty()) {
            return false;
        }
        return toDateTimeRange().overlap(time.toDateTimeRange());
    }

    public boolean contains(LocalDate date) {
        if (isEmpty()) {
            return false;
        }
        return date.isAfter(this.start) && date.isBefore(this.end);
    }

    public boolean contains(DateRange range) {
        return toDateTimeRange().contains(range.toDateTimeRange());
    }

    public Period toPeriod() {
        return Period.between(this.start, this.end);
    }
}
