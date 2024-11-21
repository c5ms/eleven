package com.eleven.hotel.domain.model.room.event;

import com.eleven.core.domain.error.DomainEvent;
import com.eleven.hotel.domain.model.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class RoomDeactivateEvent implements DomainEvent {

    private Room room;

}
