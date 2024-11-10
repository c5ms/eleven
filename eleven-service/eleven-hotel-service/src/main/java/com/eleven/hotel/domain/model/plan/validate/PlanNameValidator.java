package com.eleven.hotel.domain.model.plan.validate;

import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanNameValidator implements PlanValidator {

    @Override
    public void validate(Plan plan) {

    }
}
