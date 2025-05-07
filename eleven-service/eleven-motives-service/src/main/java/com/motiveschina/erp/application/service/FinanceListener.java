package com.motiveschina.erp.application.service;

import com.motiveschina.core.layer.ApplicationListener;
import com.motiveschina.erp.domain.finance.event.PurchaseCostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinanceListener implements ApplicationListener {

    @EventListener(PurchaseCostCreatedEvent.class)
    public void on(PurchaseCostCreatedEvent event) {
        log.info("purchase cost has been created: {}", event.getCost().getPurchaseCost());
    }

}
