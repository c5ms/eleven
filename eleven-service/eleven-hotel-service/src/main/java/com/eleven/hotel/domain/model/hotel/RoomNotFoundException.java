package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityFoundException;

public class RoomNotFoundException extends NoEntityFoundException {

    public RoomNotFoundException(String hotelId, String roomId) {
        super(String.format("not found room with id %s in hotel %s", roomId, hotelId));
    }
}
