package com.eleven.hotel.api.interfaces.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanAddRoomRequest {

    @NotNull
    private Long roomId;

    @NotNull
    private Integer stock;

}
