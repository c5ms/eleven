package com.eleven.hotel.domain.values;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class DateTimeRange   implements Serializable {

    private LocalDateTime start;
    private LocalDateTime end;

    public static DateTimeRange of(LocalDateTime start, LocalDateTime end) {
        return new DateTimeRange(start, end);
    }

    public static DateTimeRange nextDays(LocalDateTime start, int days) {
        return new DateTimeRange(start, start.plusDays(days));
    }

    public static DateTimeRange empty() {
        return new DateTimeRange();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return ObjectUtils.anyNull(this.start, this.end);
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @JsonIgnore
    public boolean isCurrent() {
        if (isEmpty()) {
            return false;
        }
        return contains(LocalDateTime.now());
    }

    public boolean isBefore(DateTimeRange dateTimeRange) {
        if (isEmpty()) {
            return false;
        }
        return this.end.isBefore(dateTimeRange.start);
    }

    public boolean isAfter(DateTimeRange dateTimeRange) {
        if (isEmpty()) {
            return false;
        }
        return this.end.isAfter(dateTimeRange.end);
    }

    public boolean overlap(DateTimeRange range) {
        if (isEmpty()) {
            return false;
        }
        if (this.isEmpty() || range.isEmpty()) {
            return false;
        }
        var start1 = this.getStart();
        var end1 = this.getEnd();
        var start2 = range.getStart();
        var end2 = range.getEnd();
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

    public boolean contains(LocalDateTime time) {
        if (isEmpty()) {
            return false;
        }
        return time.isAfter(this.start) && time.isBefore(this.end);
    }

    public boolean contains(DateTimeRange range) {
        if (isEmpty()) {
            return false;
        }
        if (this.start.isAfter(range.start)) {
            return false;
        }
        if (this.end.isBefore(range.end)) {
            return false;
        }
        return start.isBefore(end);
    }

}
