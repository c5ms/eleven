package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityException;

public class RoomNotFoundException extends NoEntityException {

    public RoomNotFoundException(String hotelId, String roomId) {
        super(String.format("not found room with id %s in hotel %s", roomId, hotelId));
    }
}
