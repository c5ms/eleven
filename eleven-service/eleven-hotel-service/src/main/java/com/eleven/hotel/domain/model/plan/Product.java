package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.ImmutableValues;
import com.eleven.hotel.domain.core.ObjectMatcher;
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
public class Product {

    @EmbeddedId
    private ProductId productId;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    protected Product(Plan plan, Long roomId, StockAmount stockAmount) {
        this.productId = ProductId.of(plan.getHotelId(), plan.getPlanId(), roomId);
        this.stockAmount = stockAmount;
        this.saleChannels = new HashSet<>();
        this.saleType = plan.getSaleType();
        this.saleState = SaleState.STOPPED;
    }

    protected void startSale() {
        Validate.isTrue(hasPrice(), "the room has no price");
        Validate.isTrue(hasStock(), "the room has no stock");
        this.setSaleState(SaleState.STARTED);
    }

    protected void stopSale() {
        this.setSaleState(SaleState.STOPPED);
    }

    public boolean isOnSale() {
        return this.saleState.isOnSale();
    }

    protected void setPrice(SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        Validate.isTrue(this.saleChannels.contains(saleChannel), "don't support the channel " + saleChannel);
        Validate.isTrue(this.chargeType == ChargeType.BY_ROOM, "don't support the charge by whole room");

        var price = Price.wholeRoom(this.getProductId(), saleChannel, wholeRoomPrice);
        this.prices.put(price.getPriceId(), price);
        this.setChargeType(price.getChargeType());
    }

    protected void setPrice(SaleChannel saleChannel,
                         BigDecimal onePersonPrice,
                         BigDecimal twoPersonPrice,
                         BigDecimal threePersonPrice,
                         BigDecimal fourPersonPrice,
                         BigDecimal fivePersonPrice) {
        Validate.isTrue(this.saleChannels.contains(saleChannel), "don't support the channel" + saleChannel);
        Validate.isTrue(this.chargeType == ChargeType.BY_PERSON, "don't support the charge by person");

        var price = Price.byPerson(this.getProductId(),
            saleChannel,
            onePersonPrice,
            twoPersonPrice,
            threePersonPrice,
            fourPersonPrice,
            fivePersonPrice);
        this.prices.put(price.getPriceId(), price);
        this.setChargeType(price.getChargeType());
    }

    public Optional<Price> findPrice(SaleChannel channel) {
        var matcher = new ObjectMatcher<Price>()
            .should(price -> price.is(channel));
        return getPrices().stream()
            .filter(matcher)
            .findFirst();
    }

    protected void openChannel(SaleChannel saleChannel) {
        this.saleChannels.add(saleChannel);
    }

    protected void closeChannel(SaleChannel saleChannel, boolean dropPrice) {
        this.saleChannels.add(saleChannel);
        if (dropPrice) {
            var priceId = PriceId.of(productId, saleChannel);
            this.prices.remove(priceId);
        }
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

    public boolean hasStock() {
        return this.getStockAmount().greaterThanZero();
    }


}
