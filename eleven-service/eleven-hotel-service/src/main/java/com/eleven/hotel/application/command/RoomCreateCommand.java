package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand  {

    private String hotelId;

    private Stock stock;
    private ChargeType chargeType;
    private Room.Desc desc;
    private Room.Restrict restrict;

}
