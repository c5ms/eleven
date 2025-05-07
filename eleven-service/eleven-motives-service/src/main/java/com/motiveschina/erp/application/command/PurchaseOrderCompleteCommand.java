package com.motiveschina.erp.application.command;


import java.time.LocalDate;
import com.motiveschina.core.layer.ApplicationCommand;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrderCompleteCommand implements ApplicationCommand {

    long orderId;

    boolean pass;

    LocalDate stockDate;

    double transportationCost;
}
