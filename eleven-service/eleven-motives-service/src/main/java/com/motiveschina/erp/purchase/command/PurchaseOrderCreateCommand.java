package com.motiveschina.erp.purchase.command;


import java.util.List;
import com.motiveschina.erp.purchase.dto.PurchaseOrderItemDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrderCreateCommand {

    Long supplierId;

    List<PurchaseOrderItemDto> items;
}
