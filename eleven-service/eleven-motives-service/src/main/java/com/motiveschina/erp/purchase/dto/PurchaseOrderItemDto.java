package com.motiveschina.erp.purchase.dto;

import com.motiveschina.erp.support.layer.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PurchaseOrderItem")
public final class PurchaseOrderItemDto implements Dto {
    private Long productId;
    private int quantity;
    private double price;
}
