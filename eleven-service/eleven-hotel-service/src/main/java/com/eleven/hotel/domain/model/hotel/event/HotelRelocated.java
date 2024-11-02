package com.eleven.hotel.domain.model.hotel.event;

import com.eleven.core.domain.DomainEvent;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@AllArgsConstructor
public class HotelRelocated implements DomainEvent {

      Hotel hotel;

}

