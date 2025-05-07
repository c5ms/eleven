package com.motiveschina.erp.domain.finance.event;

import com.motiveschina.core.layer.DomainEvent;
import com.motiveschina.erp.domain.finance.PurchaseCost;
import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseCostCreatedEvent implements DomainEvent {

    PurchaseCost cost;

}

