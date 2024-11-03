package com.eleven.hotel.domain.model.booking;

import com.eleven.hotel.api.domain.model.SaleChannel;

public interface BookingCharger {

    ChargeResult charge(Integer hotelId,
                 Integer planId,
                 Integer roomId,
                 SaleChannel saleChannel,
                 int persons);

}
