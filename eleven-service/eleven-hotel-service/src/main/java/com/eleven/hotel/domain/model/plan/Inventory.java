package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainValidator;
import com.eleven.hotel.api.domain.errors.PlanErrors;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
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
@EqualsAndHashCode(callSuper = false, of = "inventoryKey")
public class Inventory extends AbstractEntity {

    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long inventoryId;

    @Embedded
    private InventoryKey inventoryKey;

    @Embedded
    @AttributeOverride(name = "hotelId", column = @Column(name = "hotel_id", updatable = false, insertable = false))
    @AttributeOverride(name = "planId", column = @Column(name = "plan_id", updatable = false, insertable = false))
    private PlanKey planKey;

    @Embedded
    @AttributeOverride(name = "hotelId", column = @Column(name = "hotel_id", updatable = false, insertable = false))
    @AttributeOverride(name = "planId", column = @Column(name = "plan_id", updatable = false, insertable = false))
    @AttributeOverride(name = "roomId", column = @Column(name = "room_id", updatable = false, insertable = false))
    private ProductKey productKey;

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

    public static Inventory of(ProductKey productKey, LocalDate date, StockAmount stockAmount) {
        var inventoryKey = InventoryKey.of(productKey, date);
        var planKey = PlanKey.of(inventoryKey.getHotelId(), inventoryKey.getPlanId());

        var inventory = new Inventory();
        inventory.setInventoryKey(inventoryKey);
        inventory.setPlanKey(planKey);
        inventory.setProductKey(productKey);
        inventory.setStockTotal(stockAmount);
        inventory.setStockLeft(stockAmount);
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

    public boolean isNew() {
        return this.inventoryId == null;
    }

    public void reduce(int amount) {
        DomainValidator.must(this.hasEnoughStock(amount), PlanErrors.INVENTORY_NOT_ENOUGH);
        this.setStockLeft(this.stockLeft.reduce(amount));
    }

}
