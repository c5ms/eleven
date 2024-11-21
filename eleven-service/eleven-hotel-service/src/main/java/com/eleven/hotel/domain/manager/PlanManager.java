package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.errors.HotelErrors;
import com.eleven.hotel.domain.model.room.RoomKey;
import com.eleven.hotel.domain.model.room.RoomRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanValidator;
import com.eleven.hotel.domain.model.hotel.Product;
import com.eleven.hotel.domain.model.hotel.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;


    public void validate(Plan plan) {
        for (PlanValidator validator : planValidators) {
            validator.validate(plan);
        }
    }


}
