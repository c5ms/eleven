package com.eleven.domain.booking;


import com.eleven.core.error.DomainError;

import java.util.Optional;

public interface BookingValidator {

    Optional<DomainError> isBookable(Booking booking);

}
