package com.motiveschina.erp.application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.motiveschina.erp.support.layer.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PurchaseOrder")
public final class PurchaseOrderDto implements Dto {

    private Long orderId;
    private String orderNumber;
    private LocalDate orderDate;
    private Long supplierId;
    private String status;
    private List<PurchaseOrderItemDto> items = new ArrayList<>();

}
