package com.eleven.hotel.api.endpoint.model;

import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PlanDto {

    private String id;
    private String name;
    private String desc;
    private Integer stock;
    private SaleType type;
    private SaleState state;

    private Boolean isPreSale;
    private Boolean isPreSaleOngoing;
    private LocalDateTime preSellStartDate;
    private LocalDateTime preSellEndDate;

    private Boolean isSaleOngoing;
    private LocalDateTime sellStartDate;
    private LocalDateTime sellEndDate;

    private LocalDate stayStartDate;
    private LocalDate stayEndDate;

    private List<PlanRoom> rooms = new ArrayList<>();


    @Getter
    @Setter
    @Accessors(chain = true)
    public static class PlanRoom {
        private String roomId;
        private Integer stock;
        private Double price;
    }
}
