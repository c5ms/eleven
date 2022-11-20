package com.eleven.hotel.domain.model.values;

import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {

    @Test
    void basic(){
        DateRange range1 = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        assertEquals(range1.getStart(), LocalDate.of(2024, 11, 15));
        assertEquals(range1.getEnd(), LocalDate.of(2024, 11, 18));

        assertThrows(IllegalArgumentException.class,() -> DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 14)));
    }

    @Test
    void toDateTimeRange() {
        var dateRange = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        var timeRange = DateTimeRange.of(LocalDateTime.of(2024, 11, 15, 0, 0, 0), LocalDateTime.of(2024, 11, 18, 0, 0, 0));
        Assertions.assertEquals(dateRange.toDateTimeRange(), timeRange);
    }


    @Test
    void overlap() {
        DateRange range1 = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        DateRange range2 = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        assertTrue(range1.overlap(range2));

        range1 = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        range2 = DateRange.of(LocalDate.of(2024, 11, 16), LocalDate.of(2024, 11, 17));
        assertTrue(range1.overlap(range2));

        range1 = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        range2 = DateRange.of(LocalDate.of(2024, 11, 17), LocalDate.of(2024, 11, 20));
        assertTrue(range1.overlap(range2));

        range1 = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 18));
        range2 = DateRange.of(LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 20));
        assertFalse(range1.overlap(range2));

    }

    @Test
    void contains() {
        DateRange range;

        range = DateRange.of(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 20));
        assertTrue(range.contains(LocalDate.of(2024, 11, 16)));
        assertFalse(range.contains(LocalDate.of(2024, 11, 14)));
        assertFalse(range.contains(LocalDate.of(2024, 11, 21)));

    }

}
