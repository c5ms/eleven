package com.eleven.travel.domain.booking;

import com.eleven.travel.core.SaleChannel;

import java.math.BigDecimal;

/**
 * A plan has multiple rooms in, each room known as a product of the plan.
 */
public interface Plan {

    Long getPlanId();

    Long getHotelId();

    Long getRoomId();

    boolean isOnSale();

    boolean isOnSale(long roomId);

    BigDecimal charge(SaleChannel saleChannel, int persons);

}
