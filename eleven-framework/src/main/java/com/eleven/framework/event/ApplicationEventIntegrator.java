package com.eleven.framework.event;

import cn.hutool.json.JSONUtil;
import com.eleven.framework.security.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventIntegrator {

    private final ApplicationEventSerializer serializer;
    private final List<ApplicationEventMessageSender> senders;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void send(ApplicationEvent event) throws ApplicationEventSerializeException {
        if (CollectionUtils.isEmpty(senders)) {
            return;
        }

        SecurityContext.getPrincipal().ifPresent(event.getHeader()::setTrigger);
        if (log.isDebugEnabled()) {
            log.debug("send {} to outbound :{}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
        }
        var message = serializer.serialize(event);
        for (ApplicationEventMessageSender sender : senders) {
            sender.send(message);
        }
    }

    public void receive(ApplicationEventMessage message) throws ApplicationEventSerializeException {
        var eventOpt = serializer.deserialize(message);
        if (eventOpt.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("ignore message {}", message);
            }
            return;
        }

        eventOpt.ifPresent(event -> {
            event.getHeader().setFrom(ApplicationEventOrigin.EXTERNAL);
            if (log.isDebugEnabled()) {
                log.debug("receive {} :{}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
            }
            applicationEventPublisher.publishEvent(event);
        });

    }


}
