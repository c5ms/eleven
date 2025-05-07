package com.motiveschina.hotel.features.booking.command;

import com.motiveschina.hotel.common.DateRange;
import com.motiveschina.hotel.common.SaleChannel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingCreateCommand {
    private Long hotelId;
    private Long planId;
    private Long roomId;

    private Integer personCount;
    private DateRange stayPeriod;
    private SaleChannel saleChannel;
}
