package com.eleven.hotel.domain.model.hotel.validate;

import com.eleven.core.domain.DomainError;
import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.model.hotel.PlanRepository;
import com.eleven.hotel.domain.model.hotel.PlanValidator;
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
        return Optional.empty();
    }

}
