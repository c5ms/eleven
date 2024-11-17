package com.eleven.booking.endpoint.web.booking;

import com.eleven.booking.api.endpoint.request.BookingCreateRequest;
import com.eleven.booking.application.service.BookingService;
import com.eleven.booking.domain.model.booking.Booking;
import com.eleven.booking.endpoint.convert.BookingConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@RequiredArgsConstructor
@Tag(name = "book")
@RequestMapping("/books")
public class BookingCustomerApi {

    private final BookingService bookingService;
    private final BookingConvertor bookingConvertor;

    @Operation(summary = "create booking")
    @PostMapping
    public Booking createBooking(@RequestBody @Validated BookingCreateRequest request) {
        var command = bookingConvertor.toCommand(request);
        var booking = bookingService.createBooking(command);
        return booking;
    }

}
