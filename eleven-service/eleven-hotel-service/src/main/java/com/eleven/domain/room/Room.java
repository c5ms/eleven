package com.eleven.domain.room;

import com.eleven.common.support.DomainEntity;
import com.eleven.core.utils.ImmutableValues;
import com.eleven.domain.hotel.values.Occupancy;
import com.eleven.domain.inventory.RoomInventory;
import com.eleven.domain.room.event.*;
import com.google.common.base.Predicates;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


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

    public void setStock(RoomStock stock) {
        if (Objects.equals(stock, this.stock)) {
            return;
        }
        this.stock = stock;
        this.addEvent(RoomStockChangedEvent.of(this));
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

}
