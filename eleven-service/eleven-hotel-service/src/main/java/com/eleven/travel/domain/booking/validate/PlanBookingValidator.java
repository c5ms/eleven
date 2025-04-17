package com.eleven.travel.domain.booking.validate;

import com.eleven.framework.domain.DomainError;
import com.eleven.travel.domain.booking.Booking;
import com.eleven.travel.domain.booking.BookingValidator;
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
