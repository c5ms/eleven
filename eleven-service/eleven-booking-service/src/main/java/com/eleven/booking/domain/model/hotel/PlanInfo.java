package com.eleven.booking.domain.model.hotel;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "plan")
public class PlanInfo {

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

    private final List<Room> rooms = new ArrayList<>();

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
        private Long roomId;
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
