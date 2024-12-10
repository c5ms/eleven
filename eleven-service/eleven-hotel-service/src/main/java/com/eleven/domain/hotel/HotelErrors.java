package com.eleven.domain.hotel;

import com.eleven.core.domain.error.DomainError;
import com.eleven.core.domain.error.SimpleDomainError;

public interface HotelErrors {
    DomainError REGISTER_NOT_REVIEWABLE = SimpleDomainError.of("not_reviewable", "the register is not reviewable");
    DomainError ROOM_NOT_FOUND = SimpleDomainError.of("ROOM_NOT_FOUND", "the room can not be found");
}
