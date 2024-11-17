package com.eleven.hotel.api.interfaces.request;

import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomCreateRequest {

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

    @Min(0)
    @Max(999999)
    private Integer quantity;

    @NotNull
    private DateRangeVo availablePeriod;

    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();
}
