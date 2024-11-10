package com.eleven.hotel.domain.model.plan;

import cn.hutool.extra.validation.ValidationUtil;
import com.eleven.core.domain.DomainValidator;
import com.eleven.core.domain.utils.ImmutableValues;
import com.eleven.hotel.api.domain.errors.PlanErrors;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.springframework.validation.ValidationUtils;

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

    protected Product(@NonNull ProductId productId,
                      @NonNull SaleType saleType,
                      @NonNull Collection<SaleChannel> saleChannels,
                      @NonNull StockAmount stockAmount) {
        this.productId = productId;
        this.stockAmount = stockAmount;
        this.saleType = saleType;
        this.saleState = SaleState.STOPPED;
        this.saleChannels = new HashSet<>(saleChannels);
    }

    protected void startSale() {
        DomainValidator.must(hasStock(), PlanErrors.NO_STOCK);
        DomainValidator.must(hasPrice(), PlanErrors.NO_PRICE);
        this.setSaleState(SaleState.STARTED);
    }

    protected void stopSale() {
        this.setSaleState(SaleState.STOPPED);
    }

    public boolean isOnSale() {
        return this.saleState.isOnSale();
    }

    protected void setPrice(SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        DomainValidator.must(this.saleChannels.contains(saleChannel), PlanErrors.UN_SUPPORTED_CHANNEL);

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
        DomainValidator.must(this.saleChannels.contains(saleChannel), PlanErrors.UN_SUPPORTED_CHANNEL);
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

    public boolean hasPrice() {
        return MapUtils.isNotEmpty(this.prices);
    }

    public boolean hasStock() {
        return this.getStockAmount().greaterThanZero();
    }


}
