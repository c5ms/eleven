package com.motiveschina.hotel.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import com.motiveschina.hotel.core.DateRange;
import com.motiveschina.hotel.core.DateTimeRange;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateRangeTestUtils {

    public DateTimeRange empty() {
        return DateTimeRange.empty();
    }

    public DateRange today() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        return DateRange.of(now, tomorrow);
    }

    public DateRange tomorrow() {
        LocalDate now = LocalDate.now().plusDays(1);
        LocalDate tomorrow = now.plusDays(1);
        return DateRange.of(now, tomorrow);
    }

    public DateRange nextWeek() {
        LocalDate monday = LocalDate.now().plusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate friday = monday.with(DayOfWeek.FRIDAY);
        return DateRange.of(monday, friday);
    }

}
