package com.eleven.travel.domain.room;

import com.eleven.framework.domain.DomainError;
import com.eleven.framework.domain.SimpleDomainError;

public interface RoomErrors {
    DomainError ROOM_NOT_FOUND = SimpleDomainError.of("ROOM_NOT_FOUND", "the room can not be found");
}
