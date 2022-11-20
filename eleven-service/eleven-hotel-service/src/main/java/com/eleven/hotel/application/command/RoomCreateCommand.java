package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.RoomSize;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand implements HotelAware {

    private String hotelId;

    private String name;

    private RoomSize size;

    private SaleType saleType;

    private Stock stock;

    private String desc;

}
