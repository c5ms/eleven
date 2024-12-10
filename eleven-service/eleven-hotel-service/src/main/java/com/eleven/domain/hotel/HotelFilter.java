package com.eleven.domain.hotel;

import com.eleven.core.interfaces.model.PageRequest;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class HotelFilter extends PageRequest {
    private String hotelName;

}
