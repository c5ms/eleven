package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.core.ImmutableValues;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.*;

@Table(name = "hms_plan_room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class PlanRoom {

    @EmbeddedId
    private PlanRoomId id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", insertable = false, updatable = false)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    private Map<PriceId, Price> prices = new HashMap<>();

    @Column(name = "charge_type")
    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_amount"))
    private StockAmount stockAmount;

    protected PlanRoom(Plan plan, Room room, StockAmount stockAmount) {
        this.id = new PlanRoomId(plan.getHotelId(), plan.getPlanId(), room.getRoomId());
        this.stockAmount = stockAmount;
        this.saleChannels = new HashSet<>();
    }

    public void setPrice(BigDecimal wholeRoomPrice) {
        Price price = PriceCreator.wholeRoom(this.getId(), wholeRoomPrice);
        this.prices.put(price.getId(), price);
        this.setChargeType(price.getType());
    }

    public Optional<Price> findPrice() {
        // todo no price type
        return prices.values().stream()
            .findFirst();
    }

    public void setPrice(BigDecimal onePersonPrice,
                         BigDecimal twoPersonPrice,
                         BigDecimal threePersonPrice,
                         BigDecimal fourPersonPrice,
                         BigDecimal fivePersonPrice) {
        Price price = PriceCreator.byPerson(this.getId(),
            onePersonPrice,
            twoPersonPrice,
            threePersonPrice,
            fourPersonPrice,
            fivePersonPrice);
        this.prices.put(price.getId(), price);
        this.setChargeType(price.getType());
    }


    public void openChannel(SaleChannel saleChannel) {

    }

    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(this.saleChannels);
    }
}
