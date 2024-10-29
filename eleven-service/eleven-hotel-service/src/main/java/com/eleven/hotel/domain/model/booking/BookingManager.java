package com.eleven.hotel.domain.model.booking;

import com.eleven.core.domain.DomainHelper;
import com.eleven.core.domain.DomainError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingManager {

    private final List<BookingValidator> bookingValidators;

    public String bookId() {
        return DomainHelper.nextId();
    }

    public void validate(Booking booking) {
        for (BookingValidator bookingValidator : bookingValidators) {
            bookingValidator.isBookable(booking).ifPresent(DomainError::throwException);
        }
    }


}
