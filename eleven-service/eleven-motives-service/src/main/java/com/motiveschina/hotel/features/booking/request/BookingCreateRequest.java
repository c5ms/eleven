package com.motiveschina.hotel.features.booking.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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


}
