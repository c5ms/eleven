package com.eleven.travel.domain.booking;

import com.eleven.travel.domain.booking.request.BookingCreateRequest;
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
public class BookingResource {

    private final BookingService bookingService;
    private final BookingConvertor bookingConvertor;

    @Operation(summary = "create booking")
    @PostMapping
    public Booking createBooking(@RequestBody @Validated BookingCreateRequest request) {
        var command = bookingConvertor.toCommand(request);
        return bookingService.createBooking(command);
    }

}
