package com.eleven.hotel.api.interfaces.request;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomLevel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateRequest {

    @NotBlank
    private String name;


    @NotBlank
    private String desc;

    @NotNull
    private RoomLevel level;

    private String headPicUrl;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer minPerson;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer maxPerson;


}
