package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "level one room")
    private String name;

    @Schema(example = "the soft room you should have")
    private String description;

    @Min(1)
    @NotNull
    @Schema(example = "24")
    private Integer area;

    @Min(1)
    @NotNull
    @Schema(example = "3")
    private Integer floor;

}
