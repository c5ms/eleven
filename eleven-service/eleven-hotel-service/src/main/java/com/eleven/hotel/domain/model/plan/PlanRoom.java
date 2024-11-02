package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.PriceType;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Period;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Table(name = "hms_plan_room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class PlanRoom {

    @EmbeddedId
    private Id id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", insertable = false, updatable = false)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    private Set<PlanPrice> prices = new HashSet<>();

    @AttributeOverride(name = Stock.Fields.count, column = @Column(name = "stock_count"))
    private Stock stock;

    PlanRoom(Plan plan, Room room, Stock stock) {
        this.id = new Id(plan.getHotelId(), plan.getId(), room.getId());
        this.stock = stock;
    }

    public void setPrice(PriceType type, BigDecimal amount) {
        this.prices.add(new PlanPrice(id, type, new Price(amount)));
    }

    public Optional<PlanPrice> findPrice(PriceType type) {
        return this.prices.stream()
            .filter(planPrice -> planPrice.isType(type))
            .findFirst();
    }

    public Optional<Price> charge(PriceType type, int nights) {
        return findPrice(type)
            .map(PlanPrice::getPrice)
            .map(aPrice -> aPrice.multiply(Period.ofDays(nights)));
    }

    void setPlanId(Integer planId) {
        this.id.planId = planId;
    }

    @PostPersist
    void afterSave() {
        for (PlanPrice price : this.getPrices()) {
            price.setPlanId(this.getId().getPlanId());
        }
    }

    @Getter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable {

        @Column(name = "hotel_id")
        private Integer hotelId;

        @Column(name = "plan_id")
        private Integer planId;

        @Column(name = "room_id")
        private Integer roomId;

    }
}
