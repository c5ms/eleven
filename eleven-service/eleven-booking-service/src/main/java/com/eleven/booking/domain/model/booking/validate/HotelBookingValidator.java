package com.eleven.booking.domain.model.booking.validate;

import com.eleven.booking.domain.model.booking.Booking;
import com.eleven.booking.domain.model.booking.BookingValidator;
import com.eleven.core.data.DomainError;
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
