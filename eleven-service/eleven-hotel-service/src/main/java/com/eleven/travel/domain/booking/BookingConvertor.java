package com.eleven.travel.domain.booking;

import com.eleven.travel.domain.booking.command.BookingCreateCommand;
import com.eleven.travel.domain.booking.request.BookingCreateRequest;
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
