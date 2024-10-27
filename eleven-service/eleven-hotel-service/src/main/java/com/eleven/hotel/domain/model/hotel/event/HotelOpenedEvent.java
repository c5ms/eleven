package com.eleven.hotel.domain.model.hotel.event;

import com.eleven.core.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelOpenedEvent implements DomainEvent {

    private String hotelId;

}
