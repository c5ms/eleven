package com.eleven.travel.domain.booking;


import com.eleven.framework.error.DomainError;

import java.util.Optional;

public interface BookingValidator {

    Optional<DomainError> isBookable(Booking booking);

}
