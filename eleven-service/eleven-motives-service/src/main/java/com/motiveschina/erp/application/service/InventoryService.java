package com.motiveschina.erp.application.service;

import com.motiveschina.core.layer.ApplicationService;
import com.motiveschina.erp.domain.inventory.Inventory;
import com.motiveschina.erp.domain.inventory.InventoryFilter;
import com.motiveschina.erp.domain.inventory.InventoryFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InventoryService implements ApplicationService {

    private final InventoryFinder inventoryFinder;

    public void check() {
        var filter = InventoryFilter.builder()
            .isLow(true)
            .build();
        var lowInventories = inventoryFinder.query(filter);

        for (Inventory inventory : lowInventories) {
            log.info("low inventory is found:{}", inventory);
        }
    }


}
