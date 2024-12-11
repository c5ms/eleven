package com.eleven.domain.inventory;

import com.eleven.common.support.DomainEntity;
import com.eleven.domain.room.RoomKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Table(name = "room_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
public class RoomInventory extends DomainEntity {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private RoomInventoryKey key;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "stock_total")
    private Integer stockTotal;

    @Column(name = "stock_booked")
    private Integer stockBooked;

    protected RoomInventory() {
    }

    @Builder
    public static RoomInventory of(RoomKey roomKey, LocalDate date, Integer stock) {
        var inventoryKey = RoomInventoryKey.of(roomKey, date);

        var inventory = new RoomInventory();
        inventory.setKey(inventoryKey);
        inventory.setStockTotal(stock);
        inventory.setStockBooked(stock);
        inventory.setIsEnabled(true);
        return inventory;
    }

    public RoomKey getRoomKey() {
        return this.getKey().toRoomKey();
    }

    public void enable() {
        this.setIsEnabled(true);
    }

    public void disable() {
        this.setIsEnabled(false);
    }
}
