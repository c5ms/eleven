package com.motiveschina.erp.domain.finance;

import com.motiveschina.core.domain.DomainHelper;
import com.motiveschina.core.layer.DomainManager;
import com.motiveschina.erp.domain.finance.event.PurchaseCostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinanceManager implements DomainManager {

    private final PurchaseCostRepository purchaseCostRepository;

    public void createCost(PurchaseCost cost) {
        purchaseCostRepository.save(cost);

        var event = PurchaseCostCreatedEvent.of(cost);
        DomainHelper.publishDomainEvent(event);
    }

}
