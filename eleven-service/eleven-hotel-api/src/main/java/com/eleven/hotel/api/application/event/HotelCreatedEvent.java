package com.eleven.hotel.api.application.event;

import com.eleven.core.application.event.AbstractApplicationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelCreatedEvent extends AbstractApplicationEvent {

    private String hotelId;

}
