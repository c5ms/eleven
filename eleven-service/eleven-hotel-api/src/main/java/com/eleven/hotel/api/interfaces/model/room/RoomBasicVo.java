package com.eleven.hotel.api.interfaces.model.room;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "RoomBasic")
@Accessors(chain = true)
public class RoomBasicVo {

    @NotBlank
    private String name;

    private String desc;

    @Min(1)
    @NotNull
    private Integer area;

    @Min(1)
    @NotNull
    private Integer floor;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer minPerson;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer maxPerson;


}
