package com.eleven.travel.domain.hotel.event;

import com.eleven.framework.event.DomainEvent;
import com.eleven.travel.domain.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class HotelCreatedEvent implements DomainEvent {

    private Hotel hotel;

}
