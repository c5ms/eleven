package com.eleven.travel.domain.hotel.command;

import com.eleven.travel.domain.hotel.HotelPatch;
import com.eleven.travel.domain.hotel.values.Address;
import com.eleven.travel.domain.hotel.values.CheckPolicy;
import com.eleven.travel.domain.hotel.values.HotelBasic;
import com.eleven.travel.domain.hotel.values.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelUpdateCommand implements HotelPatch {
    private HotelBasic basic;
    private Position position;
    private Address address;
    private CheckPolicy checkPolicy;
}
