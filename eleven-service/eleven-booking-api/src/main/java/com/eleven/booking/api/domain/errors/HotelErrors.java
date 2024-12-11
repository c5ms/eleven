package com.eleven.booking.api.domain.errors;


import com.eleven.core.data.error.DomainError;
import com.eleven.core.data.error.SimpleDomainError;

public interface HotelErrors {
    DomainError REGISTER_NOT_REVIEWABLE = SimpleDomainError.of("not_reviewable", "the register is not reviewable");
    DomainError ROOM_NOT_FOUND = SimpleDomainError.of("ROOM_NOT_FOUND", "the room can not be found");

}
