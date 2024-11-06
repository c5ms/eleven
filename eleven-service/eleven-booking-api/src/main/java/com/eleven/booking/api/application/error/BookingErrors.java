package com.eleven.booking.api.application.error;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface BookingErrors {

    DomainError HOTEL_NOT_EXIST = SimpleDomainError.of("hotel_not_exist", "The hotel is not exist");
    DomainError PLAN_NOT_EXIST = SimpleDomainError.of("plan_not_exist", "The plan is not exist");
    DomainError BOOKING_NO_PRICE = SimpleDomainError.of("booking_no_price", "Can not calculate the price for the booking");
}
