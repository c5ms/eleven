package com.eleven.hotel.api.endpoint.request;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
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

    @NotNull
    private RoomType type;

    @NotNull
    private ChargeType chargeType;

    private String headPicUrl;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer maxPerson;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer minPerson;

    @NotBlank
    private String desc;

}
