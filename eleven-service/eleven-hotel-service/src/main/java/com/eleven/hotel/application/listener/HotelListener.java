package com.eleven.hotel.application.listener;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.domain.error.DomainEvent;
import com.eleven.hotel.domain.manager.InventoryManager;
import com.eleven.hotel.domain.model.room.event.RoomStockChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelListener {

    private final InventoryManager inventoryManager;

    @EventListener
    public void on(DomainEvent event) {
        log.debug("handle domain event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
    }

    @EventListener
    public void on(ApplicationEvent event) {
        log.debug("handle application event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
    }

    @EventListener(RoomStockChangedEvent.class)
    public void on(RoomStockChangedEvent event) {
        inventoryManager.takeStock(event.getRoom());
    }
}
