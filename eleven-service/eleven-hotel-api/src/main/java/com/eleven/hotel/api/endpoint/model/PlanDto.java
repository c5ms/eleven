package com.eleven.hotel.api.endpoint.model;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
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

    private Boolean isPreSale;
    private Boolean isPreSaleOngoing;
    private LocalDateTime preSellStartDate;
    private LocalDateTime preSellEndDate;

    private Boolean isSaleOngoing;
    private LocalDateTime sellStartDate;
    private LocalDateTime sellEndDate;

    private LocalDate stayStartDate;
    private LocalDate stayEndDate;

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
