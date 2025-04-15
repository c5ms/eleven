package com.eleven.travel.domain.hotel;

import com.eleven.framework.web.model.PageRequest;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class HotelFilter extends PageRequest {
    private String hotelName;

}
