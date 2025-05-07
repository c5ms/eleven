package com.motiveschina.hotel.features.room;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import com.eleven.framework.utils.ImmutableValues;
import com.google.common.base.Predicates;
import com.motiveschina.hotel.core.support.DomainEntity;
import com.motiveschina.hotel.features.hotel.values.Occupancy;
import com.motiveschina.hotel.features.inventory.RoomInventory;
import com.motiveschina.hotel.features.room.event.RoomActiveEvent;
import com.motiveschina.hotel.features.room.event.RoomCreatedEvent;
import com.motiveschina.hotel.features.room.event.RoomDeactivateEvent;
import com.motiveschina.hotel.features.room.event.RoomStockChangedEvent;
import com.motiveschina.hotel.features.room.event.RoomUpdatedEvent;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;


@Table(name = "room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Room extends DomainEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "active")
    private Boolean active = true;

    @Embedded
    private RoomStock stock = RoomStock.empty();

    @Embedded
    private RoomBasic basic = RoomBasic.empty();

    @Embedded
    private Occupancy occupancy = Occupancy.empty();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "room_image", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "room_id"))
    @Column(name = "image_url", length = 200)
    private Set<String> images = new HashSet<>();

    protected Room() {
    }

    @Builder
    private static Room of(Long hotelId, RoomBasic basic, Occupancy occupancy, RoomStock stock, Set<String> images) {
        Room room = new Room();
        room.setHotelId(hotelId);
        room.setBasic(basic);
        room.setOccupancy(occupancy);
        room.setStock(stock);
        room.setImages(images);
        room.setActive(false);
        room.addEvent(RoomCreatedEvent.of(room));
        return room;
    }

    public void update(RoomPatch patch) {
        Optional.ofNullable(patch.getBasic()).ifPresent(this::setBasic);
        Optional.ofNullable(patch.getOccupancy()).ifPresent(this::setOccupancy);
        Optional.ofNullable(patch.getImages()).ifPresent(this::setImages);
        Optional.ofNullable(patch.getStock()).ifPresent(this::setStock);
        this.addEvent(RoomUpdatedEvent.of(this));
    }

    public boolean isBookable(RoomInventory roomInventory) {
        return Predicates.<RoomInventory>notNull()
            .and(theInv -> theInv.getRoomKey().equals(toKey()))
            .and(theInv -> getStock().getAvailablePeriod().contains(theInv.getKey().getDate()))
            .test(roomInventory);
    }

    public void deactivate() {
        if (!this.active) {
            return;
        }
        this.setActive(false);
        this.addEvent(RoomDeactivateEvent.of(this));
    }

    public void active() {
        if (this.active) {
            return;
        }
        this.setActive(true);
        this.addEvent(RoomActiveEvent.of(this));
    }

    @Nonnull
    public RoomKey toKey() {
        return RoomKey.of(hotelId, roomId);
    }

    public ImmutableValues<String> getImages() {
        return ImmutableValues.of(images);
    }

    public void setImages(Set<String> images) {
        if (null == this.images) {
            this.images = new HashSet<>();
        }
        this.images.clear();
        this.images.addAll(images);
    }

    public RoomStock getStock() {
        return Optional.ofNullable(stock).orElseGet(RoomStock::empty);
    }

    public void setStock(RoomStock stock) {
        if (Objects.equals(stock, this.stock)) {
            return;
        }
        this.stock = stock;
        this.addEvent(RoomStockChangedEvent.of(this));
    }

    public RoomBasic getBasic() {
        return Optional.ofNullable(basic).orElseGet(RoomBasic::empty);
    }

    public Occupancy getOccupancy() {
        return Optional.ofNullable(occupancy).orElseGet(Occupancy::empty);
    }
}
