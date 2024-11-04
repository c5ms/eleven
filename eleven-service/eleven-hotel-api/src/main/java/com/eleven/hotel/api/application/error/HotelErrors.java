package com.eleven.hotel.api.application.error;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface HotelErrors {

    DomainError REGISTRATION_NOT_REVIEWABLE = SimpleDomainError.of("registration_is_not_reviewable", "the registration is not reviewable");

    DomainError PLAN_NO_SUCH_ROOM = SimpleDomainError.of("plan_no_such_room", "the plan has no such room");
    DomainError PLAN_PRODUCT_NO_STOCK = SimpleDomainError.of("plan_product_no_stock", "the room has no stock");
    DomainError PLAN_PRODUCT_UN_SUPPORT_CHARGE_TYPE = SimpleDomainError.of("plan_product_un_support_charge_type", "the room does not support the charge type");
    DomainError PLAN_INVENTORY_NOT_ENOUGH = SimpleDomainError.of("plan_inventory_not_enough", "the plan has no inventory");

    DomainError BOOKING_NO_SUCH_ROOM = SimpleDomainError.of("no_such_plan", "No such room can be booked");
    DomainError BOOKING_STAY_PERIOD_OVERFLOW = SimpleDomainError.of("stay_period_overflow", "The stay period is overflow");
    DomainError BOOKING_PLAN_IS_NOT_ON_SELL = SimpleDomainError.of("plan_is_not_on_sell", "The plan is not on sell");
    DomainError BOOKING_HOTEL_IS_NOT_ON_SELL = SimpleDomainError.of("hotel_is_not_opened", "The hotel is not on sell");


}
