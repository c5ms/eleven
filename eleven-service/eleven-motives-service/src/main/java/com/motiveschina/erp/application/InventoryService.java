package com.motiveschina.erp.application;

import com.motiveschina.erp.domain.inventory.Inventory;
import com.motiveschina.erp.domain.inventory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void check() {
        var lows = inventoryRepository.findLowInventories();
        for (Inventory low : lows) {
            log.info("low inventory is found:{}", low);
        }
    }


}
