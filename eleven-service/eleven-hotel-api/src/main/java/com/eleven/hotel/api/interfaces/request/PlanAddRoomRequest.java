package com.eleven.hotel.api.interfaces.request;

import com.eleven.hotel.api.domain.model.SaleChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PlanAddRoomRequest {

    @NotNull
    private Long roomId;

    @NotNull
    private Integer stock;

}
