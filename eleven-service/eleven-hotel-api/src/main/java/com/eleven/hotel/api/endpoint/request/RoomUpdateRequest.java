package com.eleven.hotel.api.endpoint.request;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomUpdateRequest {
    @NotBlank
    private String name;

    @NotNull
    private RoomSize size;

    @NotNull
    private ChargeType chargeType;

    private String headPicUrl;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotBlank
    private String desc;
}
