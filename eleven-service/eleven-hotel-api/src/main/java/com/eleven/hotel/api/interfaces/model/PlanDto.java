package com.eleven.hotel.api.interfaces.model;

import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private Boolean isOnSale;

    private Boolean isPreSale;
    private Boolean isPreSalePeriodOngoing;
    private LocalDateTime preSellStartDate;
    private LocalDateTime preSellEndDate;

    private Boolean isSalePeriodOngoing;
    private LocalDateTime sellStartDate;
    private LocalDateTime sellEndDate;

    private Boolean isStayPeriodOngoing;
    private LocalDate stayStartDate;
    private LocalDate stayEndDate;

}
