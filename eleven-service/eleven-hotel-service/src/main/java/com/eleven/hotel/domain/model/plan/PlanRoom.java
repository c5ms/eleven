package com.eleven.hotel.domain.model.plan;

import cn.hutool.core.map.MapUtil;
import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.core.ImmutableValues;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Validate;
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

    public void setPrice(SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        Validate.isTrue(this.saleChannels.contains(saleChannel), "the plan has no such sale channel");

        var price = PriceCreator.wholeRoom(this.getId(), saleChannel, wholeRoomPrice);
        this.prices.put(price.getId(), price);
        this.setChargeType(price.getPriceType());
    }

    public void setPrice(SaleChannel saleChannel,
                         BigDecimal onePersonPrice,
                         BigDecimal twoPersonPrice,
                         BigDecimal threePersonPrice,
                         BigDecimal fourPersonPrice,
                         BigDecimal fivePersonPrice) {
        Validate.isTrue(this.saleChannels.contains(saleChannel), "the plan has no such sale channel");

        var price = PriceCreator.byPerson(this.getId(),
            saleChannel,
            onePersonPrice,
            twoPersonPrice,
            threePersonPrice,
            fourPersonPrice,
            fivePersonPrice);
        this.prices.put(price.getId(), price);
        this.setChargeType(price.getPriceType());
    }

    public Optional<Price> findPrice(SaleChannel channel) {
        var matcher = new PriceMatcher()
            .should(price -> price.is(channel));
        return prices.values().stream()
            .filter(matcher::match)
            .findFirst();
    }

    public void openChannel(SaleChannel saleChannel) {
        this.saleChannels.add(saleChannel);
    }

    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(this.saleChannels);
    }

    public ImmutableValues<Price> getPrices() {
        return ImmutableValues.of(prices.values());
    }

    public boolean hasPrice() {
        return MapUtils.isNotEmpty(this.prices);
    }
}
