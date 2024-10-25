package com.eleven.hotel.application.query;

import com.eleven.core.model.PageResult;
import com.eleven.hotel.domain.model.hotel.Hotel;

public interface HotelQueryService {

    PageResult<Hotel> queryPage(HotelQuery filter);

}
