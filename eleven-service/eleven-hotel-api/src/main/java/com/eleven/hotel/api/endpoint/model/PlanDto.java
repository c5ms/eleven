package com.eleven.hotel.api.endpoint.model;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PlanDto {

    private Integer planId;
    private Integer hotelId;

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

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Room> rooms = new ArrayList<>();

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Price {
        private BigDecimal wholeRoomPrice;
        private BigDecimal onePersonPrice;
        private BigDecimal twoPersonPrice;
        private BigDecimal threePersonPrice;
        private BigDecimal fourPersonPrice;
        private BigDecimal fivePersonPrice;
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Room {
        private Integer roomId;
        private Integer stock;
        private SaleState saleState;
        private ChargeType chargeType;
        private RoomPrices prices = new RoomPrices();
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class RoomPrices {
        private Price dh;
        private Price dp;
    }


}
