package com.motiveschina.hotel.features.booking;


import java.util.Optional;
import com.eleven.framework.domain.DomainError;

public interface BookingValidator {

    Optional<DomainError> isBookable(Booking booking);

}
