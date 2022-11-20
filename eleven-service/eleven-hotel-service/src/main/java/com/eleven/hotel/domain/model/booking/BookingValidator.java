package com.eleven.hotel.domain.model.booking;

import com.eleven.core.domain.DomainError;

import java.util.Optional;

public interface BookingValidator {

    Optional<DomainError> isBookable(Booking booking);

}
