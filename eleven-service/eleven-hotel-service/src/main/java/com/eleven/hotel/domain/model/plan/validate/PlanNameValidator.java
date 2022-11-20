package com.eleven.hotel.domain.model.plan.validate;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.eleven.core.domain.DomainError;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.eleven.hotel.domain.model.plan.PlanValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanNameValidator implements PlanValidator {

    private final PlanRepository planRepository;

    @Override
    public Optional<DomainError> validate(Plan plan) {
        return planRepository.getPlansByHotelIdAndName(plan.getHotelId(), plan.getName())
                .stream()
                .filter(exist -> !StringUtils.equals(exist.id(), plan.getId()))
                .findFirst()
                .map(planSummary -> HotelErrors.PLAN_NAME_REPEAT);
    }

}
