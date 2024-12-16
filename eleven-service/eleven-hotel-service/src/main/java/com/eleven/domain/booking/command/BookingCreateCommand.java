package com.eleven.domain.booking.command;

import com.eleven.core.DateRange;
import com.eleven.core.SaleChannel;
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
