package com.eleven.hotel.application.command;

import com.eleven.core.model.PageQuery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class HotelQueryFilter extends PageQuery {

    private String hotelName;

}
