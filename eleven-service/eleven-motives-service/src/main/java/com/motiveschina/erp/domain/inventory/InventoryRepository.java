package com.motiveschina.erp.domain.inventory;

import java.util.Optional;
import com.motiveschina.core.domain.DomainRepository;

public interface InventoryRepository extends DomainRepository<Inventory, Long> {
    Optional<Inventory> findByMaterialId(Long materialId);
}
