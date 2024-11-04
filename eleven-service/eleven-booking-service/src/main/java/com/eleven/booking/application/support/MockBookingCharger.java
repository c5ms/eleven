package com.eleven.booking.application.support;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.booking.domain.model.booking.BookingCharger;
import com.eleven.booking.domain.model.booking.ChargeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockBookingCharger implements BookingCharger {

    @Override
    public ChargeResult charge(Long hotelId, Long planId, Long roomId, SaleChannel saleChannel, int persons) {
        var result = new ChargeResult();
        result.setAmount(BigDecimal.valueOf(5400));
        return result;
    }
}
