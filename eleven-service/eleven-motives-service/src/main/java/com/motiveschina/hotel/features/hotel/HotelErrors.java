package com.motiveschina.hotel.features.hotel;

import com.eleven.framework.domain.DomainError;
import com.eleven.framework.domain.SimpleDomainError;

public interface HotelErrors {
    DomainError REGISTER_NOT_REVIEWABLE = SimpleDomainError.of("not_reviewable", "the register is not reviewable");
}
