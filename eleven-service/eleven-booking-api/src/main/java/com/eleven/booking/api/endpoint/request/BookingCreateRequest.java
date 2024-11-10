package com.eleven.booking.api.endpoint.request;

import com.eleven.core.domain.values.DateRange;
import com.eleven.hotel.api.domain.model.SaleChannel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingCreateRequest {

    @NotNull
    private Long hotelId;

    @NotNull
    private Long planId;

    @NotNull
    private Long roomId;

    @NotNull
    private Integer personCount;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull
    private SaleChannel saleChannel;

}
