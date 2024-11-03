package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

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
    private Integer inventoryId;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "sale_date")
    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_count"))
    private StockAmount stockAmount;

    @Column(name = "sale_channel")
    @Enumerated(EnumType.STRING)
    private SaleChannel saleChannel;

    public Inventory() {
    }

    public static Inventory of(ProductId productId, StockAmount stockAmount, SaleChannel saleChannel, LocalDate date) {
        Inventory inventory = new Inventory();
        inventory.setHotelId(productId.getHotelId());
        inventory.setPlanId(productId.getPlanId());
        inventory.setRoomId(productId.getRoomId());
        inventory.setSaleChannel(saleChannel);
        inventory.setDate(date);
        inventory.setStockAmount(stockAmount);
        return inventory;
    }
}
