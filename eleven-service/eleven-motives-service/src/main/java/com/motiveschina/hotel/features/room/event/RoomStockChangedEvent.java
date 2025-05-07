package com.motiveschina.hotel.features.room.event;

import com.eleven.framework.event.DomainEvent;
import com.motiveschina.hotel.features.room.Room;
import lombok.Value;

@Value(staticConstructor = "of")
public class RoomStockChangedEvent implements DomainEvent {
    Room room;
}
