package com.eleven.hotel.domain.values;

import com.eleven.core.time.TimeContext;
import com.eleven.hotel.domain.core.Effectiveness;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@FieldNameConstants
public class DateTimeRange  {

    @Column(value = "start_date")
    private final LocalDateTime start;

    @Column(value = "end_date")
    private final LocalDateTime end;


    private DateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (null != start && null != end) {
            Assert.isTrue(start.isBefore(end), "start must before end");
        }

        this.start = start;
        this.end = end;
    }

    public static DateTimeRange empty() {
        return new DateTimeRange(null, null);
    }

    public boolean isEmpty() {
        return ObjectUtils.anyNull(this.start, this.end);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isCurrent() {
        if(isEmpty()){
            return false;
        }
        return contains(TimeContext.localDateTime());
    }

    public boolean isBefore(DateTimeRange dateTimeRange) {
        if(isEmpty()){
            return false;
        }
        return this.end.isBefore(dateTimeRange.end);
    }

    public boolean isAfter(DateTimeRange dateTimeRange) {
        if(isEmpty()){
            return false;
        }
        return this.end.isAfter(dateTimeRange.end);
    }

    public static DateTimeRange of(LocalDateTime start, LocalDateTime end) {
        return new DateTimeRange(start, end);
    }

    public boolean overlap(DateTimeRange range) {
        if(isEmpty()){
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
        if(isEmpty()){
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
        if(isEmpty()){
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

    public boolean contains(DateRange range) {
        if(isEmpty()){
            return false;
        }
        return this.contains(range.toDateTimeRange());
    }

}
