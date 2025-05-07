package com.motiveschina.hotel.features.booking;

import com.motiveschina.hotel.features.booking.command.BookingCreateCommand;
import com.motiveschina.hotel.features.booking.request.BookingCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingConvertor {

    public BookingCreateCommand toCommand(BookingCreateRequest request) {
        return null;
    }
}
