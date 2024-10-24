package com.eleven.hotel.application.service;

import com.eleven.core.model.PageResult;
import com.eleven.hotel.application.command.HotelQueryFilter;
import com.eleven.hotel.domain.model.hotel.Hotel;

public interface HotelQueries {

    PageResult<Hotel> queryPage(HotelQueryFilter filter);

}
