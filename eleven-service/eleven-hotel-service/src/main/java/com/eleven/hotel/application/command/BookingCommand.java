package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.core.domain.values.DateRange;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingCommand {
    private Long hotelId;
    private Long planId;
    private Long roomId;
    private Integer personCount;
    private SaleChannel saleChannel;
    private DateRange stayPeriod;
}
