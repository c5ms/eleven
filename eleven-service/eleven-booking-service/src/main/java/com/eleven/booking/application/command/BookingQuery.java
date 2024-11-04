package com.eleven.booking.application.command;

import com.eleven.core.application.query.PageQuery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class BookingQuery extends PageQuery {

    private String hotelName;

}
