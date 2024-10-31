package com.eleven.hotel.api.endpoint.request;

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
    private String name;

    @NotBlank
    private String desc;

    @NotNull
    @Min(1)
    private Integer stock;

    private LocalDateTime preSellStartDate;
    private LocalDateTime preSellEndDate;

    @NotNull
    private LocalDateTime sellStartDate;
    @NotNull
    private LocalDateTime sellEndDate;

    @NotNull
    private LocalDate stayStartDate;
    @NotNull
    private LocalDate stayEndDate;

    private List<String> rooms = new ArrayList<>();
}
