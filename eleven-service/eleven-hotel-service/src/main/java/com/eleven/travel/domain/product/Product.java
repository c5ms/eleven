package com.eleven.travel.domain.product;

import com.eleven.framework.domain.DomainValidator;
import com.eleven.framework.utils.ImmutableValues;
import com.eleven.travel.core.ChargeType;
import com.eleven.travel.core.SaleChannel;
import com.eleven.travel.core.SaleState;
import com.eleven.travel.core.SaleType;
import com.eleven.travel.domain.plan.Plan;
import com.eleven.travel.domain.plan.PlanErrors;
import com.eleven.travel.domain.room.Room;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Table(name = "product")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Product {

    @EmbeddedId
    private ProductKey key;

    @Column(name = "charge_type")
    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", insertable = false, updatable = false)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    private Map<PriceKey, Price> prices = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    private Plan plan;

    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Column(name = "stock")
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    protected Product(@NonNull Plan plan, @NonNull Room room) {
        this.key = ProductKey.of(plan.toKey(), room.getRoomId());
        this.room = room;
        this.plan = plan;
        this.stock = room.getStock().getQuantity();
        this.saleType = plan.getSaleType();
        this.saleState = SaleState.STOPPED;
        this.saleChannels = plan.getSaleChannels().toSet();
    }

    public static Product of(@NonNull Plan plan, @NonNull Room room) {
        return new Product(plan, room);
    }

    @PrePersist
    protected void complete() {
        for (Price price : this.getPrices()) {
            price.getKey().apply(getKey());
        }
    }

    public boolean isOnSale() {
        return this.saleState.isOnSale();
    }

    public void setPrice(SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        DomainValidator.must(this.saleChannels.contains(saleChannel), PlanErrors.UN_SUPPORTED_CHANNEL);

        var price = Price.wholeRoom(this.getKey(), saleChannel, wholeRoomPrice);
        this.prices.put(price.getKey(), price);
        this.setChargeType(price.getChargeType());
    }

    public void setPrice(SaleChannel saleChannel,
                         BigDecimal onePersonPrice,
                         BigDecimal twoPersonPrice,
                         BigDecimal threePersonPrice,
                         BigDecimal fourPersonPrice,
                         BigDecimal fivePersonPrice) {
        DomainValidator.must(this.saleChannels.contains(saleChannel), PlanErrors.UN_SUPPORTED_CHANNEL);
        var price = Price.byPerson(this.getKey(),
            saleChannel,
            onePersonPrice,
            twoPersonPrice,
            threePersonPrice,
            fourPersonPrice,
            fivePersonPrice);
        this.prices.put(price.getKey(), price);
        this.setChargeType(price.getChargeType());
    }

    public Optional<Price> priceOf(SaleChannel channel) {
        return getPrices().stream()
            .filter(price -> price.is(channel))
            .findFirst();
    }

    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(this.saleChannels);
    }

    public ImmutableValues<Price> getPrices() {
        return ImmutableValues.of(prices.values());
    }


    public Integer getStock() {
        if (null == this.stock) {
            return 0;
        }
        return stock;
    }

    public boolean is(ProductKey key) {
        return key.equals(this.getKey());
    }

}
