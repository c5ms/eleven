package com.eleven.booking.application.command;

import com.eleven.core.domain.values.DateRange;
import com.eleven.hotel.api.domain.model.SaleChannel;
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
