package com.eleven.hotel.api.application.constants;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class HotelConstants {

    public static final String REGEXP_YEAR_MONTH = "\\d{4}-\\d{2}";
    public static final String REGEXP_HOUR_MINUTES = "\\d{2}:\\d{2}";


    public static final String FORMAT_YEAR_MONTH = "yyyy-MM";
    public static final String FORMAT_HOUR_MINUTES = "HH:mm";

    public static final DateTimeFormatter FORMATTER_HOUR_MINUTES = DateTimeFormatter.ofPattern(FORMAT_HOUR_MINUTES);
    public static final DateTimeFormatter FORMATTER_YEAR_MONTH = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH);

}
