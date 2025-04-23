package com.motiveschina.erp.application.command;


import java.util.List;
import com.motiveschina.erp.application.dto.PurchaseOrderItemDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrderCreateCommand {

    Long supplierId;

    List<PurchaseOrderItemDto> items;
}
