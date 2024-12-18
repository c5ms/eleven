package com.eleven.travel.domain.hotel;

import com.eleven.framework.error.DomainError;
import com.eleven.framework.error.SimpleDomainError;

public interface HotelErrors {
    DomainError REGISTER_NOT_REVIEWABLE = SimpleDomainError.of("not_reviewable", "the register is not reviewable");
}
