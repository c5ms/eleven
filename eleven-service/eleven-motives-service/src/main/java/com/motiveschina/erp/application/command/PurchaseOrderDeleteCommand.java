package com.motiveschina.erp.application.command;


import com.motiveschina.core.layer.ApplicationCommand;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrderDeleteCommand implements ApplicationCommand {
    Long orderId;
}
