package com.eleven.hotel.api.interfaces.model.room;

import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomCreateRequest {

    @NotNull
    private RoomBasicVo basic;

    @Min(0)
    @Max(999999)
    private Integer quantity;

    @NotNull
    private DateRangeVo availablePeriod;

    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();
}
