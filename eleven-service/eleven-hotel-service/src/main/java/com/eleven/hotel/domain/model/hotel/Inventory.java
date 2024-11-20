package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "room_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Inventory extends AbstractEntity {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private InventoryKey key;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "stock_total")
    private Integer stockTotal;

    @Column(name = "stock_booked")
    private Integer stockBooked;

    protected Inventory() {
    }

    @Builder
    public static Inventory of(RoomKey roomKey, LocalDate date, Integer stock) {
        var inventoryKey = InventoryKey.of(roomKey, date);

        var inventory = new Inventory();
        inventory.setKey(inventoryKey);
        inventory.setStockTotal(stock);
        inventory.setStockBooked(stock);
        inventory.setIsValid(true);
        return inventory;
    }

    public RoomKey getRoomKey() {
        return this.getKey().toRoomKey();
    }

    public void active() {
        this.isValid = true;
    }

    public void inactive() {
        this.isValid = false;
    }
}
