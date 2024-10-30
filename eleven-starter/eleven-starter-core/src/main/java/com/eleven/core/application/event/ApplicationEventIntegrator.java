package com.eleven.core.application.event;

import cn.hutool.json.JSONUtil;
import com.eleven.core.authorization.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        SecurityContext.getPrincipal().ifPresent(event.getHeader()::setTrigger);

        if (log.isDebugEnabled()) {
            log.debug("send {} to outbound :{}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
        }
        var message = serializer.serialize(event);
        var json = toMessageString(message);
        for (ApplicationEventMessageSender sender : senders) {
            sender.send(json);
        }
    }

    public void receive(String content) throws ApplicationEventSerializeException {
        var message = toMessageObject(content);
        var eventOpt = serializer.deserialize(message);
        if (eventOpt.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("ignore message {}", content);
            }
            return;
        }

        eventOpt.ifPresent(event -> {
            event.getHeader().setFrom(ApplicationEventOrigin.EXTERNAL);
            if (log.isDebugEnabled()) {
                log.debug("receive {} from {} :{}", event.getClass().getSimpleName(), message.getService(), JSONUtil.toJsonStr(event));
            }
            applicationEventPublisher.publishEvent(event);
        });

    }

    private String toMessageString(ApplicationEventMessage message) {
        return JSONUtil.toJsonStr(message);
    }

    private ApplicationEventMessage toMessageObject(String content) {
        return JSONUtil.toBean(content, ApplicationEventMessage.class);
    }

}
