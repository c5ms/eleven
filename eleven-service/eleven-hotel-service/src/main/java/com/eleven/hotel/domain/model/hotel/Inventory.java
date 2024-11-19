package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
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

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_total"))
    private StockAmount stockTotal;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_booked"))
    private StockAmount stockBooked;

    protected Inventory() {
    }

    @Builder
    public static Inventory of(RoomKey roomKey, LocalDate date, StockAmount stock) {
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
