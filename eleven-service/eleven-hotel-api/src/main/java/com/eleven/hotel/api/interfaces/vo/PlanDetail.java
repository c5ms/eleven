package com.eleven.hotel.api.interfaces.vo;

import com.eleven.hotel.api.domain.values.ChargeType;
import com.eleven.hotel.api.domain.values.SaleState;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PlanDetail extends PlanDto {

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Room> rooms = new ArrayList<>();

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Price {
        private BigDecimal wholeRoom;
        private BigDecimal onePerson;
        private BigDecimal twoPerson;
        private BigDecimal threePerson;
        private BigDecimal fourPerson;
        private BigDecimal fivePerson;
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Room {
        private Long roomId;
        private String name;
        private ChargeType chargeType;
        private String headPicUrl;
        private String desc;
        private Integer maxPerson;
        private Integer minPerson;
        private Integer stock;
        private SaleState saleState;
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
