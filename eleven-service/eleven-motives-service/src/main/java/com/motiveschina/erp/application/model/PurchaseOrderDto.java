package com.motiveschina.erp.application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.motiveschina.core.layer.ApplicationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PurchaseOrder")
public final class PurchaseOrderDto implements ApplicationDto {

    private Long orderId;
    private String orderNumber;
    private LocalDate orderDate;
    private Long supplierId;
    private String status;
    private double amount;
    private List<Item> items = new ArrayList<>();

    @Data
    @Schema(name = "PurchaseOrderItem")
    public static final class Item implements ApplicationDto {
        private int quantity;
        private double price;
        private long materialId;
    }

    // currently we don't need this detail class
    // we'll need it when we want to show material info on order page.
    @Data
    @Schema(name = "PurchaseOrderItemDetail")
    public static final class ItemDetail implements ApplicationDto {
        private int quantity;
        private double price;
        private MaterialDto material;
    }

}
