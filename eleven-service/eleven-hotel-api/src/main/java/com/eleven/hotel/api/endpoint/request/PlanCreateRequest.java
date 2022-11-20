package com.eleven.hotel.api.endpoint.request;

import com.eleven.hotel.api.domain.model.SaleType;
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
    private SaleType saleType;

    @NotNull
    @Min(1)
    private Integer total;

    private LocalDateTime sellStartDate;
    private LocalDateTime sellEndDate;

    private LocalDate stayStartDate;
    private LocalDate stayEndDate;

    private List<String> rooms = new ArrayList<>();
}
