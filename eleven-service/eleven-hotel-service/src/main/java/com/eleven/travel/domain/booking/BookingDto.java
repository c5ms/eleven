package com.eleven.travel.domain.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Room")
@Accessors(chain = true)
public class BookingDto {

    private Long hotelId;
    private Long roomId;
    private String name;
    private String headPicUrl;
    private String desc;
    private Integer maxPerson;
    private Integer minPerson;

}
