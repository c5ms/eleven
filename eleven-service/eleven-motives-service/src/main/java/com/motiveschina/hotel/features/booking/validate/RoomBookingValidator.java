package com.motiveschina.hotel.features.booking.validate;

import java.util.Optional;
import com.eleven.framework.domain.DomainError;
import com.motiveschina.hotel.features.booking.Booking;
import com.motiveschina.hotel.features.booking.BookingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomBookingValidator implements BookingValidator {

    @Override
    public Optional<DomainError> isBookable(Booking booking) {
        return Optional.empty();
    }
}
