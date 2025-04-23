package com.motiveschina.erp.application.dto;

import com.motiveschina.core.layer.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PurchaseOrderItem")
public final class PurchaseOrderItemDto implements Dto {
    private Long productId;
    private int quantity;
    private double price;
}
