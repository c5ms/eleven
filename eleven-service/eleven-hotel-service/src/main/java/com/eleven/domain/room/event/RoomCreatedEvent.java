package com.eleven.domain.room.event;

import com.eleven.core.event.DomainEvent;
import com.eleven.domain.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class RoomCreatedEvent implements DomainEvent {
    private Room room;
}
