package com.eleven.booking.domain.model.booking;

import com.eleven.hotel.api.domain.model.SaleChannel;

import java.math.BigDecimal;

public interface Plan {

    BigDecimal charge(Long roomId, SaleChannel saleChannel, int persons);
}
