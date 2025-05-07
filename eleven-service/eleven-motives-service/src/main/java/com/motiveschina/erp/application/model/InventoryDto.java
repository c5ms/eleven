package com.motiveschina.erp.application.model;

import com.motiveschina.core.layer.ApplicationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Inventory")
public class InventoryDto implements ApplicationDto {
    private Long inventoryId;
    private Long productId;
    private int currentQuantity;
    private int safetyStock;
}
