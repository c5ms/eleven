package com.eleven.hotel.domain.model.booking;

import com.eleven.hotel.api.domain.model.SaleChannel;

public interface BookingCharger {

    ChargeResult charge(Long hotelId,
                 Long planId,
                 Long roomId,
                 SaleChannel saleChannel,
                 int persons);

}
