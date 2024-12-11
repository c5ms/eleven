package com.eleven.domain.hotel.event;

import com.eleven.core.event.DomainEvent;
import com.eleven.domain.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class HotelCreatedEvent implements DomainEvent {

    private Hotel hotel;

}
