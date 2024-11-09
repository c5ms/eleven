package com.eleven.hotel.domain.model.plan.validate;

import com.eleven.core.domain.DomainError;
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

    @Override
    public Optional<DomainError> validate(Plan plan) {
        return Optional.empty();
    }

}
