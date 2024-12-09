package com.eleven.hotel.domain.model.hotel.event;

import com.eleven.core.domain.error.DomainEvent;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class HotelUpdatedEvent implements DomainEvent {

    private Hotel hotel;

}
