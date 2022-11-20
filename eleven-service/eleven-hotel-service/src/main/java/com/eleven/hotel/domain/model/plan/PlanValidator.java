package com.eleven.hotel.domain.model.plan;


import com.eleven.core.domain.DomainError;

import java.util.Optional;

public interface PlanValidator {

    default Optional<DomainError> validate(Plan plan) {
        return Optional.empty();
    }


    default Optional<DomainError> validateForBooking(Plan plan) {
        return Optional.empty();
    }

}
