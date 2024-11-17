package com.eleven.hotel.api.interfaces.vo;

import com.eleven.hotel.api.domain.values.DateRange;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Schema(name = "Period")
@Accessors(chain = true)
public class DateRangeVo {

    private LocalDate start;
    private LocalDate end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

    public DateRangeVo() {
    }

    public static DateRangeVo from(DateRange dateRange) {
        var vo = new DateRangeVo();
        vo.start = dateRange.getStart();
        vo.end = dateRange.getEnd();
        vo.isCurrent = dateRange.isCurrent();
        return vo;
    }

    public DateRange toDateRange() {
        return DateRange.of(this.start, this.end);
    }

}
