package com.eleven.domain.hotel.command;

import com.eleven.domain.hotel.values.Address;
import com.eleven.domain.hotel.values.CheckPolicy;
import com.eleven.domain.hotel.values.HotelBasic;
import com.eleven.domain.hotel.values.Position;
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
