package com.eleven.hotel.domain.model.plan.validate;

import com.eleven.core.domain.DomainValidator;
import com.eleven.core.domain.SimpleDomainError;
import com.eleven.hotel.api.domain.errors.PlanErrors;
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
        if(null!=plan.getPreSalePeriod()){
            DomainValidator.must(plan.getPreSalePeriod().isBefore(plan.getSalePeriod()), PlanErrors.PRE_SALE_PERIOD_ERROR);
        }
    }
}
