package com.eleven.hotel.api.application.model;

import com.eleven.hotel.api.domain.model.RoomSize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RoomDto {
    private String id;
    private String name;
    private Integer amount;
    private RoomSize size;
    private String desc;
}
