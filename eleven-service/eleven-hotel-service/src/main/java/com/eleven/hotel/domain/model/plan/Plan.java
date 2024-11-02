package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.core.ImmutableValues;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Table(name = "hms_plan")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Plan extends AbstractEntity implements Saleable {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Integer planId;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", insertable = false, updatable = false)
    private List<PlanRoom> rooms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "sale_period_end"))
    private DateTimeRange salePeriod;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "pre_sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "pre_sale_period_end"))
    private DateTimeRange preSalePeriod;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "stay_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "stay_period_end"))
    private DateRange stayPeriod;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "count", column = @Column(name = "stock_count")),})
    private StockAmount stockAmount;

    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Embedded
    private PlanBasic basic;

    protected Plan(@NonNull Integer hotelId) {
        this.hotelId = hotelId;
        this.rooms = new ArrayList<>();
        this.saleChannels = new HashSet<>();
    }

    @PostPersist
    protected void consistence() {
        for (PlanRoom room : this.getRooms()) {
            room.getId().setPlanId(this.getPlanId());
            room.setSaleChannels(this.saleChannels);
            for (Price price : room.getPrices().values()) {
                price.getId().setPlanId(this.getPlanId());
            }
        }
    }

    @Override
    public void startSale() {
        DomainContext.must(hasRoom(), HotelErrors.PLAN_NO_ROOM);
        this.setSaleState(SaleState.STARTED);
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
    }

    @Override
    public boolean isOnSale() {
        if (!this.saleState.isOnSale()) {
            return false;
        }
        if (this.salePeriod == null) {
            return false;
        }
        if (null != preSalePeriod && preSalePeriod.isCurrent()) {
            return true;
        }
        return salePeriod.isCurrent();
    }

    public PlanRoom addRoom(Room room, StockAmount stock) {
        var planRoom = new PlanRoom(this, room, stock);
        planRoom.setSaleChannels(this.saleChannels);
        this.rooms.add(planRoom);
        return planRoom;
    }

    public PlanRoom addRoom(Room room) {
        return this.addRoom(room, StockAmount.zero());
    }

    private boolean hasRoom() {
        return !CollectionUtils.isEmpty(this.rooms);
    }

    public void openChannel(SaleChannel saleChannel) {
        this.saleChannels.add(saleChannel);
        this.rooms.forEach(planRoom -> planRoom.openChannel(saleChannel));
    }

    @Nonnull
    public DateRange getStayPeriod() {
        return Optional.ofNullable(stayPeriod).orElseGet(DateRange::empty);
    }

    @Nonnull
    public DateTimeRange getPreSalePeriod() {
        return Optional.ofNullable(preSalePeriod).orElseGet(DateTimeRange::empty);
    }

    @Nonnull
    public DateTimeRange getSalePeriod() {
        return Optional.ofNullable(salePeriod).orElseGet(DateTimeRange::empty);
    }

    @Nonnull
    public PlanBasic getBasic() {
        return Optional.ofNullable(basic).orElseGet(PlanBasic::new);
    }
    @Nonnull
    public ImmutableValues<PlanRoom> getRooms() {
        return ImmutableValues.of(rooms);
    }
    @Nonnull
    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(saleChannels);
    }

}
