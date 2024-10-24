package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomUpdateCommand  {

    private String hotelId;
    private String roomId;

    private ChargeType chargeType;
    private Stock stock;
    private Room.Desc desc;
    private Room.Restrict restrict;

}
