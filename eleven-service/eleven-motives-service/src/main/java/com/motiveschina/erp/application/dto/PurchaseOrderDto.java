package com.motiveschina.erp.application.dto;

import com.motiveschina.core.layer.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "PurchaseOrder")
public final class PurchaseOrderDto implements Dto {

    private Long orderId;
    private String orderNumber;
    private LocalDate orderDate;
    private Long supplierId;
    private String status;
    private double totalPrice;
    private List<PurchaseOrderItemDto> items = new ArrayList<>();

}
