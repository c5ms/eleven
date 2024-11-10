package com.eleven.hotel.api.domain.errors;

import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;

public interface PlanErrors {
    DomainError NO_PRODUCT = SimpleDomainError.of("no_product", "the plan has no product");
    DomainError NO_PRICE = SimpleDomainError.of("no_price", "the product has no price");
    DomainError NO_STOCK = SimpleDomainError.of("not_stock", "the product has no stock");

    DomainError PRODUCT_NOT_FOUND = SimpleDomainError.of("product_not_found", "the product can not be found");
    DomainError PRICE_NOT_FOUND = SimpleDomainError.of("price_not_found", "the price can not be found");

    DomainError UN_SUPPORTED_CHANNEL = SimpleDomainError.of("un_supported_channel", "the channel is unsupported");
}
