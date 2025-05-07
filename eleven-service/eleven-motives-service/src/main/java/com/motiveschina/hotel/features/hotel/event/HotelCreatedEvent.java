package com.motiveschina.hotel.features.hotel.event;

import com.eleven.framework.event.DomainEvent;
import com.motiveschina.hotel.features.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class HotelCreatedEvent implements DomainEvent {

    private Hotel hotel;

}
