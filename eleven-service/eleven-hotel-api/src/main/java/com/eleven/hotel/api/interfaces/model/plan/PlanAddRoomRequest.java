package com.eleven.hotel.api.interfaces.model.plan;

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
