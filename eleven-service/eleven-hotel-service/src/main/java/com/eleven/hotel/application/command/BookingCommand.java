package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.values.DateRange;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingCommand {
    private Integer hotelId;
    private Integer planId;
    private Integer roomId;
    private SaleChannel saleChannel;
    private Integer personCount;
    private DateRange stayPeriod;
}
