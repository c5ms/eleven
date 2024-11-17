package com.eleven.booking.endpoint.convert;

import com.eleven.booking.api.endpoint.request.BookingCreateRequest;
import com.eleven.booking.application.command.BookingCreateCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingConvertor {

    public final ValuesConvertor values;

    public BookingCreateCommand toCommand(BookingCreateRequest request) {
        return BookingCreateCommand.builder()
            .hotelId(request.getHotelId())
            .planId(request.getPlanId())
            .roomId(request.getRoomId())
            .personCount(request.getPersonCount())
            .saleChannel(request.getSaleChannel())
            .build();
    }
}
