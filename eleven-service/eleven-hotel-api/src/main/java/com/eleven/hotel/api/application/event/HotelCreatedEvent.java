package com.eleven.hotel.api.application.event;

import com.eleven.core.application.event.support.AbstractApplicationEvent;
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

    public static HotelCreatedEvent of(String hotelId) {
        var event = new HotelCreatedEvent();
        event.setHotelId(hotelId);
        return event;
    }

}
