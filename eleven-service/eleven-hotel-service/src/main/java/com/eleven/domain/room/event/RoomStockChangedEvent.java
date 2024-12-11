package com.eleven.domain.room.event;

import com.eleven.core.event.DomainEvent;
import com.eleven.domain.room.Room;
import lombok.Value;

@Value(staticConstructor = "of")
public class RoomStockChangedEvent implements DomainEvent {
    Room room;
}
