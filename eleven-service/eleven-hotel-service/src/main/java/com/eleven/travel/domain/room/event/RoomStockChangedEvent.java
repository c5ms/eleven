package com.eleven.travel.domain.room.event;

import com.eleven.framework.event.DomainEvent;
import com.eleven.travel.domain.room.Room;
import lombok.Value;

@Value(staticConstructor = "of")
public class RoomStockChangedEvent implements DomainEvent {
    Room room;
}
