package com.eleven.hotel.api.interfaces.request;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RoomUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    private RoomLevel type;

    private String headPicUrl;

    @Min(1)
    @Max(5)
    private Integer minPerson;

    @Min(1)
    @Max(5)
    private Integer maxPerson;

    @NotBlank
    private String desc;

    @Min(0)
    @Max(999999)
    private Integer count;

    @NotNull
    @Schema(example = "2024-05-01")
    private LocalDate stayStartDate;

    @NotNull
    @Schema(example = "2024-05-30")
    private LocalDate stayEndDate;
}
