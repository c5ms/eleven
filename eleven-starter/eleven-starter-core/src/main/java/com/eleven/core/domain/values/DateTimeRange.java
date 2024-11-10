package com.eleven.core.domain.values;

import com.eleven.core.time.TimeContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class DateTimeRange {

    private final LocalDateTime start;
    private final LocalDateTime end;

    DateTimeRange() {
        this.start = null;
        this.end = null;
    }

    public DateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (null != start && null != end) {
            Assert.isTrue(start.isBefore(end), "start must before end");
        }

        this.start = start;
        this.end = end;
    }

    public static DateTimeRange of(LocalDateTime start, LocalDateTime end) {
        return new DateTimeRange(start, end);
    }

    public static DateTimeRange nextDays(LocalDateTime start, int days) {
        return new DateTimeRange(start, start.plusDays(days));
    }

    public static DateTimeRange empty() {
        return new DateTimeRange();
    }

    public boolean isEmpty() {
        return ObjectUtils.anyNull(this.start, this.end);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isCurrent() {
        if (isEmpty()) {
            return false;
        }
        return contains(TimeContext.localDateTime());
    }

    public boolean isBefore(DateTimeRange dateTimeRange) {
        if (isEmpty()) {
            return false;
        }
        return this.end.isBefore(dateTimeRange.end);
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
        if (start.isAfter(time)) {
            return false;
        }
        if (end.isBefore(time)) {
            return false;
        }
        return start.isBefore(end);
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
