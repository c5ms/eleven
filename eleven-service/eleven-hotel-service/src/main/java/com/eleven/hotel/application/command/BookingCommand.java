package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.values.DateRange;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingCommand  {
    private String hotelId;
    private String planId;
    private String roomId;
    private DateRange stayPeriod;
}
