package com.motiveschina.hotel.features.plan;

import cn.hutool.json.JSONUtil;
import com.motiveschina.hotel.features.plan.event.PlanCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanListener {

    @EventListener
    public void on(PlanCreatedEvent event) {
        log.debug("handle plan created event: {}", JSONUtil.toJsonStr(event));
    }

}
