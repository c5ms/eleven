package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

@Table(name = "hms_plan_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, of = "key")
public class Inventory extends AbstractEntity {

    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long inventoryId;

    @Embedded
    private InventoryKey key;

    @Embedded
    @AttributeOverride(name = "hotelId", column = @Column(name = "hotel_id", updatable = false, insertable = false))
    @AttributeOverride(name = "planId", column = @Column(name = "plan_id", updatable = false, insertable = false))
    private PlanKey planKey;

    @Embedded
    @AttributeOverride(name = "hotelId", column = @Column(name = "hotel_id", updatable = false, insertable = false))
    @AttributeOverride(name = "planId", column = @Column(name = "plan_id", updatable = false, insertable = false))
    @AttributeOverride(name = "roomId", column = @Column(name = "room_id", updatable = false, insertable = false))
    private ProductId productId;

    @Column(name = "isValid")
    private Boolean isValid;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_total"))
    private StockAmount stockTotal;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_left"))
    private StockAmount stockLeft;

    protected Inventory() {
    }

    public static Inventory of(ProductId productId, LocalDate date, StockAmount stockAmount) {
        var inventoryKey = InventoryKey.of(productId, date);
        var planKey = PlanKey.of(inventoryKey.getHotelId(), inventoryKey.getPlanId());
        var inventory = new Inventory();

        inventory.setKey(inventoryKey);
        inventory.setPlanKey(planKey);
        inventory.setProductId(productId);
        inventory.setStockTotal(stockAmount);
        inventory.setStockLeft(stockAmount);
        inventory.setIsValid(true);
        return inventory;
    }

    public boolean hasEnoughStock(int amount) {
        return this.stockLeft.greaterThan(amount);
    }

    public boolean hasBeenBooked() {
        return this.stockLeft.lessThan(this.stockTotal);
    }

    public boolean isNew() {
        return this.inventoryId == null;
    }

    public void reduce(int amount) {
        Validate.isTrue(this.hasEnoughStock(amount), "no su much stock left");
        this.setStockLeft(this.stockLeft.reduct(amount));
    }

}
