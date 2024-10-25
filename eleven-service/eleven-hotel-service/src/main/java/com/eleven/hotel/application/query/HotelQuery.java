package com.eleven.hotel.application.query;

import com.eleven.core.model.PageQuery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class HotelQuery extends PageQuery {

    private String hotelName;

}
