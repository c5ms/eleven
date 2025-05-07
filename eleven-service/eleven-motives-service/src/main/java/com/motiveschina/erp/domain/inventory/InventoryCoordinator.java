package com.motiveschina.erp.domain.inventory;

/**
 * The interface is responsible to lock a transaction for the inventory
 * When lock a transaction, is makes sure the inventory operation is safe even in a distributed env.
 * // todo lock transaction or inventory ?
 */
public interface InventoryCoordinator {

    /**
     * begin a safe operation for the transaction
     *
     * @param transaction the transaction we want to conduct on an inventory
     */
    void begin(InventoryTransaction transaction);

    /**
     * unlock the transaction, so the related inventory will be free to lock.
     *
     * @param transaction the transaction we want to conduct on an inventory
     */
    void finish(InventoryTransaction transaction);

}
