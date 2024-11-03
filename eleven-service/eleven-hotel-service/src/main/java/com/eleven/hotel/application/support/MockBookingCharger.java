package com.eleven.hotel.application.support;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.model.booking.BookingCharger;
import com.eleven.hotel.domain.model.booking.ChargeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockBookingCharger implements BookingCharger {

    @Override
    public ChargeResult charge(Integer hotelId, Integer planId, Integer roomId, SaleChannel saleChannel, int persons) {
        var result = new ChargeResult();
        result.setAmount(BigDecimal.valueOf(5400));
        return result;
    }
}
