package com.eleven.hotel.domain.values;

import com.eleven.core.time.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@FieldNameConstants
public class DateRange {

    @Column(value = "start_date")
    private final LocalDate start;

    @Column(value = "end_date")
    private final LocalDate end;

    private DateRange(LocalDate start, LocalDate end) {
        if (null != start && null != end) {
            Assert.isTrue(start.isBefore(end), "start must before end");
        }

        this.start = start;
        this.end = end;
    }

    public static DateRange empty() {
        return new DateRange(null, null);
    }


    public static DateRange of(LocalDate startDate, LocalDate endDate) {
        return new DateRange(startDate, endDate);
    }

    public int days() {
        return start.until(end).getDays();
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

    public boolean isEmpty() {
        return ObjectUtils.anyNull(this.start, this.end);
    }

    public boolean isCurrent() {
        if (isEmpty()) {
            return false;
        }
        return contains(TimeHelper.localDate());
    }

    public DateTimeRange toDateTimeRange() {
        if (isEmpty()) {
            return DateTimeRange.empty();
        }
        return DateTimeRange.of(start.atStartOfDay(), end.atStartOfDay());
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
        return toDateTimeRange().contains(date.atStartOfDay());
    }

    public boolean contains(DateRange range) {
        return toDateTimeRange().contains(range.toDateTimeRange());
    }

}
