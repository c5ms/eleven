package com.motiveschina.hotel.features.hotel.command;

import com.motiveschina.hotel.features.hotel.values.Address;
import com.motiveschina.hotel.features.hotel.values.CheckPolicy;
import com.motiveschina.hotel.features.hotel.values.HotelBasic;
import com.motiveschina.hotel.features.hotel.values.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelCreateCommand {
    private HotelBasic basic;
    private Position position;
    private Address address;
    private CheckPolicy checkPolicy;
}
