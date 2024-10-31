package com.eleven.hotel.domain.manager;

import com.eleven.core.domain.DomainError;
import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.model.hotel.PlanValidator;
import com.eleven.hotel.domain.model.hotel.validate.PlanNameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;

    public void validate(Plan plan) {
        for (PlanValidator validator : planValidators) {
            validator.validate(plan).ifPresent(DomainError::throwException);
        }
    }

}
