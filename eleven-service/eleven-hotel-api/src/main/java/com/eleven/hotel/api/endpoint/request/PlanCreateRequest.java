package com.eleven.hotel.api.endpoint.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlanCreateRequest {

    @NotBlank
    @Schema(example = "example plan")
    private String name;

    @NotBlank
    @Schema(example = "example plan description")
    private String desc;

    @NotNull
    @Min(1)
    @Schema(example = "200")
    private Integer stock;

    @Schema(type = "string",format = "date-time", example = "2024-01-01 00:00:00")
    private LocalDateTime preSellStartDate;

    @Schema(type = "string",format = "date-time",example = "2024-01-02 08:00:00")
    private LocalDateTime preSellEndDate;

    @NotNull
    @Schema(type = "string",format = "date-time",example = "2024-01-30 00:00:00")
    private LocalDateTime sellStartDate;

    @NotNull
    @Schema(type = "string",format = "date-time",example = "2024-05-30 00:00:00")
    private LocalDateTime sellEndDate;

    @NotNull
    @Schema(example = "2024-05-30")
    private LocalDate stayStartDate;

    @NotNull
    @Schema(example = "2024-05-30")
    private LocalDate stayEndDate;

    private List<String> rooms = new ArrayList<>();
}
