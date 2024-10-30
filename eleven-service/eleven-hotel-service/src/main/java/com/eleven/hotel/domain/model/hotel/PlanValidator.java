package com.eleven.hotel.domain.model.hotel;


import com.eleven.core.domain.DomainError;

import java.util.Optional;

public interface PlanValidator {

    default Optional<DomainError> validate(Plan plan) {
        return Optional.empty();
    }

}
