package com.eleven.hotel.api.interfaces.model.plan;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.domain.enums.SaleState;
import com.eleven.hotel.api.domain.enums.SaleType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PlanDto implements Serializable {

    private Long planId;
    private Long hotelId;

    private String name;
    private String desc;
    private Integer stock;
    private SaleType type;
    private SaleState state;

    private List<SaleChannel> channels;

    private Boolean isOnSale;

    private Boolean isPreSale;
    private Boolean isPreSalePeriodOngoing;
    private LocalDateTime preSaleStartDate;
    private LocalDateTime preSaleEndDate;

    private Boolean isSalePeriodOngoing;
    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;

    private Boolean isStayPeriodOngoing;
    private LocalDate stayStartDate;
    private LocalDate stayEndDate;

}
