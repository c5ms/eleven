package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.values.ImmutableValues;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.model.hotel.event.RoomCreatedEvent;
import com.eleven.hotel.domain.model.hotel.event.RoomUpdatedEvent;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.Occupancy;
import com.eleven.hotel.domain.values.StockAmount;
import com.google.common.base.Predicates;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Table(name = "room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Room extends AbstractEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "quantity")
    private Integer quantity;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "available_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "available_period_end"))
    private DateRange availablePeriod;

    @Embedded
    private RoomBasic basic;

    @Embedded
    private Occupancy occupancy;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "room_image", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "room_id"))
    @Column(name = "image_url", length = 200)
    private Set<String> images = new HashSet<>();

    protected Room() {
    }

    @Builder
    public static Room of(Long hotelId, RoomBasic basic, Integer quantity, Occupancy occupancy, DateRange availablePeriod, Set<String> images) {
        Room room = new Room();
        room.setHotelId(hotelId);
        room.setBasic(basic);
        room.setOccupancy(occupancy);
        room.setQuantity(quantity);
        room.setAvailablePeriod(availablePeriod);
        room.setImages(images);
        room.active();
        room.addEvent(RoomCreatedEvent.of(room));
        return room;
    }

    public void update(RoomPatch patch) {
        Optional.ofNullable(patch.getBasic()).ifPresent(this::setBasic);
        Optional.ofNullable(patch.getOccupancy()).ifPresent(this::setOccupancy);
        Optional.ofNullable(patch.getAvailablePeriod()).ifPresent(this::setAvailablePeriod);
        Optional.ofNullable(patch.getQuantity()).ifPresent(this::setQuantity);
        Optional.ofNullable(patch.getImages()).ifPresent(this::setImages);

        this.addEvent(RoomUpdatedEvent.of(this));
    }

    public Set<LocalDate> getAvailableDates() {
        if (null == availablePeriod) {
            return new HashSet<>();
        }
        return getAvailablePeriod()
                .dates()
                .filter(localDate -> localDate.isAfter(LocalDate.now()))
                .collect(Collectors.toSet());
    }

    public Inventory createInventory(LocalDate date) {
        return Inventory.builder()
                .roomKey(this.toKey())
                .date(date)
                .stock(StockAmount.of(this.quantity))
                .build();
    }

    public boolean isApplicable(Inventory inventory) {
        return Predicates.<Inventory>notNull()
                .and(theInv -> theInv.getRoomKey().equals(toKey()))
                .and(theInv -> getAvailablePeriod().contains(theInv.getKey().getDate()))
                .test(inventory);
    }

    public void deactivate() {
        this.active = false;
    }

    public void active() {
        this.active = true;
    }

    public void setImages(Set<String> images) {
        if (null == this.images) {
            this.images = new HashSet<>();
        }
        this.images.clear();
        this.images.addAll(images);
    }

    @Nonnull
    public RoomKey toKey() {
        return RoomKey.of(hotelId, roomId);
    }

    public ImmutableValues<String> getImages() {
        return ImmutableValues.of(images);
    }


}
