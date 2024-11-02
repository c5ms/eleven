package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.ImmutableValues;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.plan.event.PlanStartedSale;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.collections4.MapUtils;
import org.hibernate.annotations.Type;

import java.util.*;

@Table(name = "hms_plan")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
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
    private Map<PlanRoomId, PlanRoom> rooms;

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

    protected Plan() {
        this.rooms = new HashMap<>();
        this.saleChannels = new HashSet<>();
    }

    @PostPersist
    protected void consistence() {
        for (PlanRoom room : this.getRooms()) {
            room.getId().setPlanId(this.getPlanId());
            room.setSaleChannels(this.saleChannels);
            for (Price price : room.getPrices()) {
                price.getId().setPlanId(this.getPlanId());
            }
        }
    }

    @Override
    public void startSale() {
        DomainContext.must(hasRoom(), HotelErrors.PLAN_NO_ROOM);
        DomainContext.must(this.getRooms().stream().anyMatch(PlanRoom::hasPrice), HotelErrors.PLAN_NO_PRICE);

        this.setSaleState(SaleState.STARTED);
        addEvent(new PlanStartedSale(this));
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
        this.rooms.put(planRoom.getId(), planRoom);
        return planRoom;
    }

    public PlanRoom addRoom(Room room) {
        return this.addRoom(room, StockAmount.zero());
    }

    private boolean hasRoom() {
        return MapUtils.isNotEmpty(this.rooms);
    }

    public void openChannel(SaleChannel... saleChannel) {
        this.saleChannels.addAll(Arrays.asList(saleChannel));
        this.getRooms().forEach(planRoom -> planRoom.setSaleChannels(this.saleChannels));
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
        return ImmutableValues.of(rooms.values());
    }

    @Nonnull
    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(saleChannels);
    }

}
