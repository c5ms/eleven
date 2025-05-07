package com.motiveschina.erp.application.command;


import com.eleven.framework.utils.ImmutableValues;
import com.motiveschina.core.layer.ApplicationCommand;
import com.motiveschina.erp.application.model.PurchaseOrderDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrderModifyCommand implements ApplicationCommand {

    Long orderId;

    Long supplierId;

    ImmutableValues<PurchaseOrderDto.Item> items;
}
