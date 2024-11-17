package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.values.Address;
import com.eleven.hotel.domain.model.hotel.values.CheckPolicy;
import com.eleven.hotel.domain.model.hotel.values.Position;
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
