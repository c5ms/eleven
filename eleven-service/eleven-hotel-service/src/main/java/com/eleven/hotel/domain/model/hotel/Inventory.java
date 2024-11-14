package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainValidator;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.errors.PlanErrors;
import com.eleven.hotel.domain.values.StockAmount;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;

@Table(name = "hms_hotel_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
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
    @AttributeOverride(name = "count", column = @Column(name = "stock_left"))
    private StockAmount stockLeft;

    protected Inventory() {
    }

    @Builder
    public static Inventory of(Room room, LocalDate date) {
        var inventoryKey = InventoryKey.of(room.toKey(), date);

        var inventory = new Inventory();
        inventory.setKey(inventoryKey);
        inventory.setStockTotal(StockAmount.of(room.getCount()));
        inventory.setStockLeft(StockAmount.of(room.getCount()));
        inventory.setIsValid(true);
        return inventory;
    }

}
