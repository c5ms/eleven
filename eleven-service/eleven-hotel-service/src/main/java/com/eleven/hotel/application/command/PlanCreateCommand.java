package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanCreateCommand implements HotelAware {
    private String hotelId;

    private String name;

    private String desc;

    private Stock stock;

    private DateTimeRange sellPeriod;

    private DateRange stayPeriod;

}
