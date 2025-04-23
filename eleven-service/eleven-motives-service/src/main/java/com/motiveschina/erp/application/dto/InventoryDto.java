package com.motiveschina.erp.application.dto;

import com.motiveschina.erp.support.layer.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Inventory")
public class InventoryDto implements Dto {
    private Long inventoryId;
    private Long productId;
    private int currentQuantity;
    private int safetyStock;
}
