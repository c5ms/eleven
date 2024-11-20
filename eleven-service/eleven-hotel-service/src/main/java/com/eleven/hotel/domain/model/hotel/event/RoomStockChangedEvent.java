package com.eleven.hotel.domain.model.hotel.event;

import com.eleven.core.domain.error.DomainEvent;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomStock;
import com.eleven.hotel.domain.values.DateRange;
import lombok.Value;

@Value(staticConstructor = "of")
public class RoomStockChangedEvent implements DomainEvent {
    Room room;
}
