package com.eleven.domain.booking.validate;

import com.eleven.core.error.DomainError;
import com.eleven.domain.booking.Booking;
import com.eleven.domain.booking.BookingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelBookingValidator implements BookingValidator {

    @Override
    public Optional<DomainError> isBookable(Booking booking) {
        return Optional.empty();
    }

}
