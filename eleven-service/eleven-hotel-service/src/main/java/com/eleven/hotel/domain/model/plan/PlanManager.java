package com.eleven.hotel.domain.model.plan;

import com.eleven.core.data.SerialGenerator;
import com.eleven.core.domain.DomainContext;
import com.eleven.core.domain.DomainError;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.plan.validate.PlanNameValidator;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanNameValidator> planNameValidators;
    private final SerialGenerator serialGenerator;

    public String nextPlanId(String hotelId) {
        return serialGenerator.nextString(Plan.DOMAIN_NAME, Hotel.DOMAIN_NAME, hotelId);
    }

    public void validate(Plan plan) {
        for (PlanNameValidator planNameValidator : planNameValidators) {
            planNameValidator.validate(plan).ifPresent(DomainError::throwException);
        }
    }

    public Plan create(Hotel hotel, DateTimeRange sellPeriod, DateTimeRange preSellPeriod, DateRange stayPeriod, Plan.Description description, Stock stock) {
        var plan = Plan.normal()
            .hotelId(hotel.getHotelId())
            .planId(nextPlanId(hotel.getHotelId()))
            .sellPeriod(sellPeriod)
            .preSellPeriod(preSellPeriod)
            .stayPeriod(stayPeriod)
            .description(description)
            .stock( stock)
            .create();
        validate(plan);
        return plan;
    }
}
