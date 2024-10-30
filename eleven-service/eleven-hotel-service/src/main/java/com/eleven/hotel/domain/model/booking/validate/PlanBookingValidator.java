package com.eleven.hotel.domain.model.booking.validate;

import com.eleven.core.domain.DomainError;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.domain.model.booking.Booking;
import com.eleven.hotel.domain.model.booking.BookingValidator;
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
        var plan = booking.getPlan();

        if (!plan.getStayPeriod().contains(booking.getStayPeriod())) {
            return Optional.of(HotelErrors.BOOKING_STAY_PERIOD_OVERFLOW);
        }

        if (!plan.isOnSale()) {
            return Optional.of(HotelErrors.BOOKING_PLAN_IS_NOT_ON_SELL);
        }

        return Optional.empty();
    }

}
