package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.error.DomainValidator;
import com.eleven.hotel.domain.model.hotel.values.StockAmount;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.errors.PlanErrors;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;

@Table(name = "hms_plan_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class PlanInventory extends AbstractEntity {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private PlanInventoryKey key;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_total"))
    private StockAmount stockTotal;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_left"))
    private StockAmount stockLeft;

    protected PlanInventory() {
    }

    public static PlanInventory of(Product product, LocalDate date) {
        var inventoryKey = PlanInventoryKey.of(product.getKey(), date);

        var inventory = new PlanInventory();
        inventory.setKey(inventoryKey);
        inventory.setStockTotal(product.getStock());
        inventory.setStockLeft(product.getStock());
        inventory.setIsValid(true);
        return inventory;
    }

    public boolean hasEnoughStock(int amount) {
        return this.stockLeft.greaterThan(amount);
    }

    public boolean hasBeenBooked() {
        if (ObjectUtils.anyNull(this.stockLeft, this.stockTotal)) {
            return false;
        }
        return this.stockLeft.lessThan(this.stockTotal);
    }

    public void reduce(int amount) {
        DomainValidator.must(this.hasEnoughStock(amount), PlanErrors.INVENTORY_NOT_ENOUGH);
        this.setStockLeft(this.stockLeft.reduce(amount));
    }

}
