package com.eleven.booking.domain.model.booking.validate;

import com.eleven.core.domain.DomainError;
import com.eleven.booking.domain.model.booking.Booking;
import com.eleven.booking.domain.model.booking.BookingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanBookingValidator implements BookingValidator {

    @Override
    public Optional<DomainError> isBookable(Booking booking) {

        return Optional.empty();
    }

}
