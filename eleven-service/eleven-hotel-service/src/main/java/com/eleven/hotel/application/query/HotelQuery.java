package com.eleven.hotel.application.query;

import com.eleven.core.application.query.PageQuery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class HotelQuery extends PageQuery {

    private String hotelName;

}
