package com.eleven.hotel.api.application.error;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface HotelErrors {

    DomainError REGISTRATION_NOT_REVIEWABLE = SimpleDomainError.of("registration_is_not_reviewable", "the registration is not reviewable");

    DomainError PLAN_NO_SUCH_ROOM = SimpleDomainError.of("plan_no_such_room", "the plan has no such room");
    DomainError PLAN_PRODUCT_NO_STOCK = SimpleDomainError.of("plan_product_no_stock", "the room has no stock");
    DomainError PLAN_PRODUCT_NO_PRICE = SimpleDomainError.of("plan_product_no_price", "the room has no price");
    DomainError PLAN_PRODUCT_UN_SUPPORT_CHARGE_TYPE = SimpleDomainError.of("plan_product_un_support_charge_type", "the room does not support the charge type");
    DomainError PLAN_INVENTORY_NOT_ENOUGH = SimpleDomainError.of("plan_inventory_not_enough", "the plan has no inventory");

}
