package com.eleven.travel.domain.room;

import com.eleven.travel.domain.inventory.InventoryManager;
import com.eleven.travel.domain.room.event.RoomStockChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomListener {

    private final InventoryManager inventoryManager;

    @EventListener(RoomStockChangedEvent.class)
    public void on(RoomStockChangedEvent event) {
        inventoryManager.takeStock(event.getRoom());
    }
}
