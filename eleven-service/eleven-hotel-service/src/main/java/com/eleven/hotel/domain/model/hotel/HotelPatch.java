package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.Address;
import com.eleven.hotel.domain.values.CheckPolicy;
import com.eleven.hotel.domain.values.Position;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelPatch {
    private HotelBasic basic;
    private Address address;
    private Position position;
    private CheckPolicy checkPolicy;
}
