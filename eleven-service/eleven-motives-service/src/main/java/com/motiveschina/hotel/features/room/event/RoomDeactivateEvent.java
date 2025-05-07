package com.motiveschina.hotel.features.room.event;

import com.eleven.framework.event.DomainEvent;
import com.motiveschina.hotel.features.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class RoomDeactivateEvent implements DomainEvent {

    private Room room;

}
