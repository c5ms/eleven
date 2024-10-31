package com.eleven.hotel.api.application.event;

import com.eleven.core.application.event.IntegrationEvent;
import com.eleven.core.application.event.support.AbstractApplicationEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IntegrationEvent
public class HotelUpdatedEvent extends AbstractApplicationEvent {

    private Integer hotelId;

    public static HotelUpdatedEvent of(Integer hotelId) {
        var event = new HotelUpdatedEvent();
        event.setHotelId(hotelId);
        return event;
    }

}
