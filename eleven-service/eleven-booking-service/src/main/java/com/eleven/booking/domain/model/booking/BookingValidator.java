package com.eleven.booking.domain.model.booking;


import java.util.Optional;

public interface BookingValidator {

    Optional<DomainError> isBookable(Booking booking);

}
