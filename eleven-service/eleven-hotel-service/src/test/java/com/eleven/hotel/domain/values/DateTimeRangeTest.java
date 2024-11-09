package com.eleven.hotel.domain.values;

import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeRangeTest {
    @Test
    void basic() {
        DateTimeRange range1 = new DateTimeRange(LocalDateTime.of(2024, 11, 15, 0, 0), LocalDateTime.of(2024, 11, 18, 0, 0));
        assertEquals(range1.getStart(), LocalDateTime.of(2024, 11, 15, 0, 0));
        assertEquals(range1.getEnd(), LocalDateTime.of(2024, 11, 18, 0, 0));

        assertThrows(IllegalArgumentException.class,() -> new DateRange(LocalDate.of(2024, 11, 15), LocalDate.of(2024, 11, 14)));
    }

    @Test
    void overlap() {
        DateTimeRange range1 = new DateTimeRange(LocalDate.of(2024, 11, 15).atStartOfDay(), LocalDate.of(2024, 11, 18).atStartOfDay());
        DateTimeRange range2 = new DateTimeRange(LocalDate.of(2024, 11, 15).atStartOfDay(), LocalDate.of(2024, 11, 18).atStartOfDay());
        assertTrue(range1.overlap(range2));

        range1 = new DateTimeRange(LocalDate.of(2024, 11, 15).atStartOfDay(), LocalDate.of(2024, 11, 18).atStartOfDay());
        range2 = new DateTimeRange(LocalDate.of(2024, 11, 16).atStartOfDay(), LocalDate.of(2024, 11, 17).atStartOfDay());
        assertTrue(range1.overlap(range2));

        range1 = new DateTimeRange(LocalDate.of(2024, 11, 15).atStartOfDay(), LocalDate.of(2024, 11, 18).atStartOfDay());
        range2 = new DateTimeRange(LocalDate.of(2024, 11, 17).atStartOfDay(), LocalDate.of(2024, 11, 20).atStartOfDay());
        assertTrue(range1.overlap(range2));

        range1 = new DateTimeRange(LocalDate.of(2024, 11, 15).atStartOfDay(), LocalDate.of(2024, 11, 18).atStartOfDay());
        range2 = new DateTimeRange(LocalDate.of(2024, 11, 19).atStartOfDay(), LocalDate.of(2024, 11, 20).atStartOfDay());
        assertFalse(range1.overlap(range2));

    }

    @Test
    void contains() {
        DateTimeRange range;

        range = new DateTimeRange(LocalDate.of(2024, 11, 15).atStartOfDay(), LocalDate.of(2024, 11, 20).atStartOfDay());
        assertTrue(range.contains(LocalDate.of(2024, 11, 16).atStartOfDay()));
        assertFalse(range.contains(LocalDate.of(2024, 11, 14).atStartOfDay()));
        assertFalse(range.contains(LocalDate.of(2024, 11, 21).atStartOfDay()));
    }


}
