package com.eleven.travel.domain.booking.command;

import com.eleven.travel.core.DateRange;
import com.eleven.travel.core.SaleChannel;
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
