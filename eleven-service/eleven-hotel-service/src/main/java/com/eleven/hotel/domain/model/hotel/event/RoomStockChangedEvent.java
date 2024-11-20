package com.eleven.hotel.domain.model.hotel.event;

import com.eleven.core.domain.error.DomainEvent;
import com.eleven.hotel.domain.model.hotel.Room;
import lombok.Value;

@Value(staticConstructor = "of")
public class RoomStockChangedEvent implements DomainEvent {
    Room room;
}
