package com.motiveschina.erp.application.command;


import com.motiveschina.erp.application.dto.PurchaseOrderItemDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PurchaseOrderCreateCommand {

    Long supplierId;

    List<PurchaseOrderItemDto> items;
}
