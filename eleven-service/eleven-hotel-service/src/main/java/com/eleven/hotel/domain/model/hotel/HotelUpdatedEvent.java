package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class HotelUpdatedEvent implements DomainEvent {
    private Hotel hotel;
}
