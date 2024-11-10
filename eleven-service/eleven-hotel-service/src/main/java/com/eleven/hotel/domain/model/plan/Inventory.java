package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainValidator;
import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.SimpleDomainError;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Table(name = "hms_plan_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, of = "inventoryKey")
public class Inventory extends AbstractEntity  {
    public final static DomainError ERROR_NOT_ENOUGH = SimpleDomainError.of("plan_inventory_not_enough", "the inventory is not enough");

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
        inventory.setInventoryKey(inventoryKey);
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
        DomainValidator.must(this.hasEnoughStock(amount), ERROR_NOT_ENOUGH);
        this.setStockLeft(this.stockLeft.reduct(amount));
    }

}
