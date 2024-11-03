package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
public class Inventory extends AbstractEntity {

    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long inventoryId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "sale_date")
    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_total"))
    private StockAmount stockTotal;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_left"))
    private StockAmount stockLeft;

    public Inventory() {
    }

    public static Inventory of(ProductId productId, LocalDate date, StockAmount stockAmount) {
        Inventory inventory = new Inventory();
        inventory.setHotelId(productId.getHotelId());
        inventory.setPlanId(productId.getPlanId());
        inventory.setRoomId(productId.getRoomId());
        inventory.setDate(date);
        inventory.setStockTotal(stockAmount);
        inventory.setStockLeft(stockAmount);
        return inventory;
    }

    public void reduce(int amount) {
        Validate.isTrue(this.stockLeft.greaterThan(amount), "no su much stock left");
        this.setStockLeft(this.stockLeft.subtract(amount));
    }
}
