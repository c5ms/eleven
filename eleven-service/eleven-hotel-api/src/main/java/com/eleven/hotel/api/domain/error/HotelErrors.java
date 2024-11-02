package com.eleven.hotel.api.domain.error;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface HotelErrors {

    DomainError HOTEL_NAME_REPEAT = SimpleDomainError.of("repeated_hotel_name", "the hotel name is existing");

    DomainError REGISTRATION_NOT_REVIEWABLE = SimpleDomainError.of("registration_is_not_reviewable", "the registration is not reviewable");

    DomainError PLAN_NO_ROOM = SimpleDomainError.of("plan_has_no_room", "the plan has no room");
    DomainError PLAN_NO_PRICE = SimpleDomainError.of("plan_has_no_price", "the plan has no price for any room");
    DomainError PLAN_PRE_SALE_NOT_BEFORE_SALE = SimpleDomainError.of("pre_sale_not_before_sale", "the per-sale period must before sale period");

    DomainError BOOKING_NO_SUCH_ROOM = SimpleDomainError.of("no_such_plan", "No such room can be booked");
    DomainError BOOKING_STAY_PERIOD_OVERFLOW = SimpleDomainError.of("stay_period_overflow", "The stay period is overflow");
    DomainError BOOKING_PLAN_IS_NOT_ON_SELL = SimpleDomainError.of("plan_is_not_on_sell", "The plan is not on sell");
    DomainError BOOKING_HOTEL_IS_NOT_ON_SELL = SimpleDomainError.of("hotel_is_not_opened", "The hotel is not on sell");

}
