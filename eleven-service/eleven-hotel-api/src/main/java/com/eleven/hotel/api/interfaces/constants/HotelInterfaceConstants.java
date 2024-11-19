package com.eleven.hotel.api.interfaces.constants;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class HotelInterfaceConstants {

    public static final String REGEXP_HH_MM = "\\d{2}:\\d{2}";
    public static final String REGEXP_YYYY_MM = "\\d{4}-\\d{2}";

    public static final String FORMAT_HH_MM = "HH:mm";
    public static final String FORMAT_YYYY_MM = "yyyy-MM";

    public static final DateTimeFormatter FORMATTER_HH_MM = DateTimeFormatter.ofPattern(FORMAT_HH_MM);
    public static final DateTimeFormatter FORMATTER_YYYY_MM = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM);

}
