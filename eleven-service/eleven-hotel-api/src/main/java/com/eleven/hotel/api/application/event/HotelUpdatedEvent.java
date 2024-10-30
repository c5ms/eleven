package com.eleven.hotel.api.application.event;

import com.eleven.core.application.event.IntegrationEvent;
import com.eleven.core.application.event.support.AbstractApplicationEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IntegrationEvent
public class HotelUpdatedEvent extends AbstractApplicationEvent {

    private String hotelId;

    public static HotelUpdatedEvent of(String hotelId) {
        var event = new HotelUpdatedEvent();
        event.setHotelId(hotelId);
        return event;
    }

}
