package com.eleven.hotel.application.listener;

import cn.hutool.json.JSONUtil;
import com.eleven.hotel.domain.manager.InventoryManager;
import com.eleven.hotel.domain.model.plan.PlanCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanListener {

    private final InventoryManager inventoryManager;

    @EventListener
    public void on(PlanCreatedEvent event) {
        var plan = event.getPlan();
        inventoryManager.initialize(plan);
        log.debug("handle plan created event: {}", JSONUtil.toJsonStr(event));
    }

}
