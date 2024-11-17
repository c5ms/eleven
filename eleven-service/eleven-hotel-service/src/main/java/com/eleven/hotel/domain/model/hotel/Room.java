package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.values.DateRange;
import com.eleven.hotel.api.domain.values.StockAmount;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Table(name = "room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Room extends AbstractEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
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

    @Setter
    @Embedded
    private RoomBasic basic;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "room_image", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "room_id"))
    @Column(name = "image_url", length = 200)
    private Set<String> images = new HashSet<>();

    protected Room() {
    }

    @Builder
    public static Room of(Long hotelId,
                          DateRange availablePeriod,
                          RoomBasic basic,
                          Set<String> images,
                          Integer quantity) {
        var room = new Room();
        room.setHotelId(hotelId);
        room.setBasic(basic);
        room.setQuantity(quantity);
        room.setAvailablePeriod(availablePeriod);
        room.setImages(images);
        room.setActive(true);
        return room;
    }

    public List<Inventory> createInventories() {
        if (null == availablePeriod) {
            return new ArrayList<>();
        }
        var inventoryBuilder = Inventory.builder()
            .roomKey(this.toKey())
            .stock(StockAmount.of(this.quantity));
        return getAvailablePeriod()
            .dates()
            .filter(localDate -> localDate.isAfter(LocalDate.now()))
            .map(inventoryBuilder::date)
            .map(Inventory.InventoryBuilder::build)
            .collect(Collectors.toList());
    }

    public void deactivate() {
        this.active=false;
    }

    public void active() {
        this.active=true;
    }

    @Nonnull
    public RoomKey toKey() {
        return RoomKey.of(hotelId, roomId);
    }
}
