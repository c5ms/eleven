package com.eleven.hotel.domain.manager;

import com.eleven.core.domain.DomainError;
import com.eleven.hotel.domain.model.booking.Booking;
import com.eleven.hotel.domain.model.booking.BookingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingManager {

    private final List<BookingValidator> bookingValidators;

    public void validate(Booking booking) {
        for (BookingValidator bookingValidator : bookingValidators) {
            bookingValidator.isBookable(booking).ifPresent(DomainError::throwException);
        }
    }

}
