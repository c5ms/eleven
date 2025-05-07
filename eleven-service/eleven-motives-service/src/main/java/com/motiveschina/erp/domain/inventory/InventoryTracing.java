package com.motiveschina.erp.domain.inventory;

public class InventoryTracing {
    private final Inventory inventory;
    private final Snapshot before;

    private Snapshot after;

    public InventoryTracing(Inventory inventory) {
        this.inventory = inventory;
        this.before = new Snapshot(inventory);
    }

    public void end() {
        this.after = new Snapshot(inventory);
    }

    public boolean hasCausedStockLow() {
        return this.before.isLowBefore == this.after.isLowBefore;
    }

    public int getQuantity() {
        return this.after.quantityBefore - this.before.quantityBefore;
    }

    public static class Snapshot {
        private final int quantityBefore;
        private final boolean isLowBefore;

        public Snapshot(Inventory inventory) {
            this.isLowBefore = inventory.isLow();
            this.quantityBefore = inventory.getCurrentQuantity();
        }
    }

}
