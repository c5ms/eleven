package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainUtils;
import com.eleven.core.domain.DomainError;
import com.eleven.hotel.domain.model.plan.validate.PlanNameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanNameValidator> planNameValidators;

    public void validate(Plan plan) {
        for (PlanNameValidator planNameValidator : planNameValidators) {
            planNameValidator.validate(plan).ifPresent(DomainError::throwException);
        }
    }

    public String planId() {
        return DomainUtils.nextId();
    }

}
