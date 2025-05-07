package com.motiveschina.erp.infrastructure.domain;

import com.motiveschina.core.distributed.DistributedLock;
import com.motiveschina.core.distributed.SampleLockableResource;
import com.motiveschina.erp.domain.inventory.InventoryCoordinator;
import com.motiveschina.erp.domain.inventory.InventoryTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedInventoryCoordinator implements InventoryCoordinator {

    private final DistributedLock distributedLock;

    @Override
    public void begin(InventoryTransaction transaction) {
        var res = SampleLockableResource.of(transaction.toInventoryKey());
        distributedLock.lock(res);
    }

    @Override
    public void finish(InventoryTransaction transaction) {
        var res = SampleLockableResource.of(transaction.toInventoryKey());
        distributedLock.unlock(res);
    }
}
