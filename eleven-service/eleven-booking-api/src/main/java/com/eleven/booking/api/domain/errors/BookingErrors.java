package com.eleven.booking.api.domain.errors;


import com.eleven.core.data.error.DomainError;
import com.eleven.core.data.error.SimpleDomainError;

public interface BookingErrors {

    DomainError PLAN_NOT_FOUND = SimpleDomainError.of("plan_not_found", "The plan can not be found");
    DomainError PLAN_NOT_ON_SALE = SimpleDomainError.of("plan_not_on_sale", "The plan is not on sale");
    DomainError ROOM_NOT_ON_SALE = SimpleDomainError.of("room_not_on_sale", "The room is not on sale");

}
