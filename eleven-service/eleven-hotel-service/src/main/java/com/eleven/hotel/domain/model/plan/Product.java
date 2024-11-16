package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.error.DomainValidator;
import com.eleven.core.domain.values.ImmutableValues;
import com.eleven.hotel.api.domain.values.ChargeType;
import com.eleven.hotel.api.domain.values.SaleChannel;
import com.eleven.hotel.api.domain.values.SaleState;
import com.eleven.hotel.api.domain.values.SaleType;
import com.eleven.hotel.domain.errors.PlanErrors;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.api.domain.values.StockAmount;
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

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_amount"))
    private StockAmount stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    @PrePersist
    protected void complete() {
        for (Price price : this.getPrices()) {
            price.getKey().apply(getKey());
        }
    }

    protected Product(@NonNull Plan plan,
                      @NonNull Room room,
                      @NonNull SaleType saleType,
                      @NonNull Collection<SaleChannel> saleChannels,
                      @NonNull StockAmount stock) {
        this.key = ProductKey.of(plan.toKey(), room.getRoomId());
        this.room = room;
        this.stock = stock;
        this.saleType = saleType;
        this.saleState = SaleState.STOPPED;
        this.saleChannels = new HashSet<>(saleChannels);
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






    public StockAmount getStock() {
        if (null == this.stock) {
            return StockAmount.zero();
        }
        return stock;
    }

    public boolean is(ProductKey key) {
        return key.equals(this.getKey());
    }

}
